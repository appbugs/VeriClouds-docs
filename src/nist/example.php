&lt;?php

$service_name = 'vericlouds.com'; // please change to your service name

function is_compromised($userid, $password) {
    // please use the code listed in Examples section on how to call our API
    return FALSE;
}

function load_nist_configs() {
    $filename = "nist_latest.json";
    $handle = fopen($filename, "r");
    $nist_json = fread($handle, filesize($filename));
    fclose($handle);
    $nist = json_decode($nist_json, true);
    return $nist;
}

function check_nist_password_policies($userid,$password) {
    global $service_name;
    $nist = load_nist_configs();
    $check_result = array('pass'=>TRUE);
    foreach ($nist['check_list'] as $check_item) {
        if ($check_item === 'compromised') {
            if (is_compromised($userid,$password) === TRUE) {
                $check_result['pass'] = FALSE;
                $check_result['reason'] = 'Password has been leaked in a previous data breach of another service';
                break;
            }
        } else if ($check_item === 'dictionary') {
            foreach ($nist['dictionary_passwords'] as $dictionary_password) {
                if ($password === $dictionary_password) {
                    $check_result['pass'] = FALSE;
                    $check_result['reason'] = 'Password is found in a dictionary of commonly used passwords';
                    break;
                }
            }
        } else if ($check_item === 'repetitive') {
            $first_char = $password[0];
            $len = strlen($password);
            $is_repeat = TRUE;
            for ($i=1; $i<$len; $i++) {
                if ($first_char !== $password[$i]) {
                    $is_repeat = FALSE;
                    break;
                }
            }
            if ($is_repeat) {
                $check_result['pass'] = FALSE;
                $check_result['reason'] = 'Password is repetitive';
                break;
            }
        } else if ($check_item === 'sequential') {
            $curr_ascii = ord($password[0]);
            $len = strlen($password);
            $is_seq = FALSE;
            $switched = FALSE;
            for ($i=1; $i<$len; $i++) {
                $next_ascii = ord($password[$i]);
                if ($curr_ascii+1 === $next_ascii) {
                    $is_seq = TRUE;
                    $curr_ascii = $next_ascii;
                } else if ($is_seq) { 
                    if ($switched) { //only allow switch once
                        $is_seq = FALSE;
                        break;
                    } else {
                        $switched = TRUE;
                    }
                    $curr_ascii = $next_ascii;
                } else {
                    break;
                }
            }
            if ($is_seq) {
                $check_result['pass'] = FALSE;
                $check_result['reason'] = 'Password is sequential';
                break;
            }
        } else if ($check_item === 'context') {
            //$service_name = $_SERVER['HTTP_HOST'];
            $username = $userid;
            if (strpos($username, $password) !== FALSE) { //password is part of username
                $check_result['pass'] = FALSE;
                $check_result['reason'] = 'Password is in context. It is part of the user ID';
                break;
            }
            if (strpos($service_name, $password) !== FALSE) { //password is part of service name
                $check_result['pass'] = FALSE;
                $check_result['reason'] = 'Password is in context. It is part of the service name';
                break;
            }
        }
    }
    return $check_result;
}
echo json_encode(check_nist_password_policies('this_is_test@gmail.com', 'master'))     . PHP_EOL;
echo json_encode(check_nist_password_policies('this_is_test@gmail.com', '888888'))     . PHP_EOL;
echo json_encode(check_nist_password_policies('this_is_test@gmail.com', '1234abcd'))   . PHP_EOL;
echo json_encode(check_nist_password_policies('this_is_test@gmail.com', 'is_test'))    . PHP_EOL;
echo json_encode(check_nist_password_policies('this_is_test@gmail.com', 'vericlouds')) . PHP_EOL;
?&gt;