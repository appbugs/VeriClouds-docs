import java.io.*;
import java.net.*;
import javax.crypto.*;
import javax.crypto.spec.*;
import org.json.*;

//https://stackoverflow.com/questions/6481627/java-security-illegal-key-size-or-default-parameters
//Please goto above url if you got "Illegal key size" exception.
public class Example {
	static String url = "https://api.vericlouds.com/index.php";
	static String reqdata = "mode=search_leaked_password_with_userid&api_key=%s&api_secret=%s&userid=%s";
	static String api_key = "XXXXXXXX";
	static String api_secret = "xxxxxxxxxxxxxxxx";
	static byte[] key = hexToByteArray(api_secret);

	public static byte[] hexToByteArray(String s) {
		int len = s.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
		}
		return data;
	}

	public static String decrypt(String encrypted) {
		String[] strs = encrypted.split(":");
		try {
			IvParameterSpec iv = new IvParameterSpec(hexToByteArray(strs[1]));
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"), iv);
			return new String(cipher.doFinal(hexToByteArray(strs[0])));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static JSONObject makeRequest(String userid) {
		try {
			HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
			conn.setDoOutput(true);
			OutputStream os = conn.getOutputStream();
			os.write(String.format(reqdata, api_key, api_secret, URLEncoder.encode(userid, "utf-8")).getBytes());

			JSONObject obj = new JSONObject(new JSONTokener(conn.getInputStream()));
			os.close();
			return obj;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static boolean is_compromised(String userid, String password) throws Exception {
		JSONObject js = makeRequest(userid);
		if (js != null && js.getString("result").equals("succeeded")) {
			for (Object str : js.getJSONArray("passwords_encrypted")) {
				String tmp = decrypt(str.toString());
				int tlen = tmp.length();
				if (tlen == password.length() && tmp.charAt(0) == password.charAt(0)
						&& tmp.charAt(tlen - 1) == password.charAt(tlen - 1))
					return true;
			}
		} else {
			throw new Exception(js.getString("reason"));
		}
		return false;
	}

	public static void main(String[] args) throws Exception {
		System.out.println(is_compromised("this_is_test@gmail.com", "123456"));
	}
}