import java.io.*;
import org.json.*;

public class NISTPolicy {

    static String service_name = "vericlouds.com";// please change to your service name

    public static boolean is_compromised(String userid, String password) {
        // please use the code listed in Examples section on how to call our API
        return false;
    }

    public static JSONObject load_nist_configs() {
        JSONObject nist = null;
        try {
            nist = new JSONObject(new JSONTokener(new FileReader("nist_latest.json")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return nist;
    }

    public static JSONObject check_nist_password_policies(String userid, String password) {
        JSONObject nist = load_nist_configs();
        JSONObject check_result = new JSONObject("{'pass':True}");
        for (Object obj : nist.getJSONArray("check_list")) {
            String check_item = obj.toString();
            if (check_item.equals("compromised")) {
                if (is_compromised(userid, password)) {
                    check_result.put("pass", false);
                    check_result.put("reason", "Password has been leaked in a previous data breach of another service");
                    break;
                }
            } else if (check_item.equals("dictionary")) {
                for (Object dictionary_password : nist.getJSONArray("dictionary_passwords")) {
                    if (password.equals(dictionary_password.toString())) {
                        check_result.put("pass", false);
                        check_result.put("reason", "Password is found in a dictionary of commonly used passwords");
                        break;
                    }
                }
            } else if (check_item.equals("repetitive")) {
                char first_char = password.charAt(0);
                int len = password.length();
                boolean is_repeat = true;
                for (int i = 1; i < len; i++) {
                    if (first_char != password.charAt(i)) {
                        is_repeat = false;
                        break;
                    }
                }
                if (is_repeat) {
                    check_result.put("pass", false);
                    check_result.put("reason", "Password is repetitive");
                    break;
                }
            } else if (check_item.equals("sequential")) {
                char curr_ascii = password.charAt(0);
                char next_ascii;
                int len = password.length();
                boolean is_seq = false;
                boolean switched = false;
                for (int i = 1; i < len; i++) {
                    next_ascii = password.charAt(i);
                    if (curr_ascii + 1 == next_ascii) {
                        is_seq = true;
                        curr_ascii = next_ascii;
                    } else if (is_seq) {
                        if (switched) { // only allow switch once
                            is_seq = false;
                            break;
                        } else {
                            switched = true;
                        }
                        curr_ascii = next_ascii;
                    } else {
                        break;
                    }
                }
                if (is_seq) {
                    check_result.put("pass", false);
                    check_result.put("reason", "Password is sequential");
                    break;
                }
            } else if (check_item.equals("context")) {
                String username = userid;
                if (username.contains(password)) {
                    // password is part of username
                    check_result.put("pass", false);
                    check_result.put("reason", "Password is in context. It is part of the user ID");
                    break;
                }
                if (service_name != null && service_name.contains(password)) {
                    // password is part of service name
                    check_result.put("pass", false);
                    check_result.put("reason", "Password is in context. It is part of the service name");
                    break;
                }
            }
        }
        
        return check_result;
    }

    public static void main(String[] args) {
        JSONObject check_result;
        check_result = check_nist_password_policies("this_is_test@gmail.com", "master");
        System.out.println(check_result);
        check_result = check_nist_password_policies("this_is_test@gmail.com", "888888");
        System.out.println(check_result);
        check_result = check_nist_password_policies("this_is_test@gmail.com", "1234abcd");
        System.out.println(check_result);
        check_result = check_nist_password_policies("this_is_test@gmail.com", "is_test");
        System.out.println(check_result);
        check_result = check_nist_password_policies("this_is_test@gmail.com", "vericlouds");
        System.out.println(check_result);
    }
}