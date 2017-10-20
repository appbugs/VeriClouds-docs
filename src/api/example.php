&lt;?php
$url = "https://api.vericlouds.com/index.php";
$api_key = 'XXXXXXXX';
$api_secret = 'xxxxxxxxxxxxxxxx';

function is_compromised($userid, $password) {
    global $url, $api_key, $api_secret;
    $data = array( 'mode' => 'search_leaked_password_with_userid', 
                   'api_key' => $api_key, 
                   'api_secret' => $api_secret, 
                   'userid' => $userid
                 );
    $options = array( 'http' => array( 'method'  => 'POST',
                                       'content' => http_build_query($data)
                                        )
                    );
    $context  = stream_context_create($options);
    $result = file_get_contents($url, false, $context);
    $obj = json_decode($result, TRUE);
    if($obj['result'] === 'succeeded'){
        foreach ($obj['passwords_encrypted'] as $enc) {
            list($enc, $iv) = explode(":", $enc);
            $plaintext = openssl_decrypt(hex2bin($enc), 'aes-256-cbc', hex2bin($api_secret), OPENSSL_RAW_DATA,  hex2bin($iv));
            $len = strlen($plaintext);
            if ($len === strlen($password) && $plaintext[0] === $password[0] && $plaintext[$len-1] === $password[$len-1]){
                return TRUE;
            }
        }
        return FALSE;
    }else{
        echo $obj['reason'];
        return null;
    }
}

echo is_compromised('this_is_test@gmail.com', '123456');
?&gt;