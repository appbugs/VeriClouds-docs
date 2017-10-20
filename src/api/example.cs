using Newtonsoft.Json.Linq; // add from http://www.newtonsoft.com/json
using System;
using System.Collections.Specialized;
using System.IO;
using System.Linq;
using System.Net;
using System.Security.Cryptography;
using System.Text;

namespace Samplecode
{
    class Program
    {
        static String url = "https://api.vericlouds.com/index.php";
        static String api_key = "XXXXXXXX";
        static String api_secret = "xxxxxxxxxxxxxxxx";

        public static byte[] stringToByteArray(string hex)
        {
            return Enumerable.Range(0, hex.Length).Where(x => x % 2 == 0)
                             .Select(x => Convert.ToByte(hex.Substring(x, 2), 16)).ToArray();
        }

        public static String decrypt(String api_secret, String iv, String encry) {
            using (Aes myAes = Aes.Create())
            {
                ICryptoTransform decryptor = myAes.CreateDecryptor(stringToByteArray(api_secret), stringToByteArray(iv));
                using (MemoryStream msDecrypt = new MemoryStream(stringToByteArray(encry)))
                    using (CryptoStream csDecrypt = new CryptoStream(msDecrypt, decryptor, CryptoStreamMode.Read))
                        using (StreamReader srDecrypt = new StreamReader(csDecrypt))
                            return srDecrypt.ReadToEnd();
            }
        }

        public static Boolean is_compromised(String userid, String password) {
            String result;
            using (WebClient client = new WebClient())
            {
                byte[] response = client.UploadValues(url, new NameValueCollection()
                { {"mode","search_leaked_password_with_userid"},{ "api_key",api_key},{ "api_secret", api_secret},{ "userid", userid}});
                result = Encoding.UTF8.GetString(response);
            }

            JObject jObject = JObject.Parse(result);
            if (jObject.GetValue("result").ToString() == "succeeded")
            {
                JArray ja = JArray.Parse(jObject.GetValue("passwords_encrypted").ToString());
                foreach (Object ob in ja)
                {
                    String[] enc = ob.ToString().Split(':');
                    String plaintext = decrypt(api_secret, enc[1], enc[0]);
                    int len = plaintext.Length;
                    if (len == password.Length && plaintext[0] == password[0] && plaintext[len - 1] == password[len - 1])
                        return true;
                }
            }
            else
            {
                throw new Exception(jObject.GetValue("reason").ToString());
            }
            return false;
        }

        static void Main(string[] args)
        {
            String userid = "this_is_test@gmail.com";
            String password = "123456";
            Console.WriteLine(is_compromised(userid, password));
        }
    }
}
