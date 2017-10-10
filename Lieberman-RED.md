# Lieberman RED – Rapid Enterprise Defense™ Suite

## Enhanced Identity Protection for Lieberman RED Rapid Enterprise Defense™ Suite

Lieberman Software RED Rapid Enterprise Defense™ Suite and VeriClouds CredVerify are helping enable digital transformation by providing sophisticated account level security and protection against data breaches for your privileged accounts on your cross-platform network. Very few organizations can distinguish the genuine user from the sophisticated attacker because of the weakest link in cyber security, weak and/or stolen passwords. With RED Identity Management, enabling VeriClouds CredVerify allows you to:

* Detect if a user's credentials are weak or stolen and display a warning or prohibit the user from logging in
* Prevent the use of weak, reused or stolen credentials during self-service password reset
* Choose between VeriClouds' SaaS service or premium on-premise hardware appliance for maximum scalability and security


```javascript

var service_name = 'vericlouds.com' // please change to your service name

function is_compromised(userid, password){
    // please use the code listed in Examples section on how to call our API
    return false;
}

function check_nist_password_policies_by_json(userid, password, nist_configs) {
    
    for (var i = 0, len = nist_configs.check_list.length; i < len; i++) {
        var check_item = nist_configs.check_list[i];
        if (nist_configs != null) {
            for (var i = 0, len = nist_configs.check_list.length; i < len; i++) {
                var check_item = nist_configs.check_list[i];
                if (check_item === 'compromised') { 
                    is_compromised(userid, password);
                } else if (check_item == 'dictionary') {
                    for (var k=0, dict_len = nist_configs.dictionary_passwords.length; k < dict_len; k++) {
                        if (password == nist_configs.dictionary_passwords[k]) {
                            alert("The account violates NIST requirements: password is found in a dictionary of commonly used passwords.");
                            return false;
                        }
                    }
                } else if (check_item === 'repetitive') {
                    var first_char = password[0];
                    var is_repeat = true;
                    for (var k=1; k<password.length; k++) {
                        if (first_char != password[k]) {
                            is_repeat = false;
                            break;
                        }
                    }
                    if (is_repeat) {
                         alert("The account violates NIST requirements: password is repetitive.");
                        return false;
                    }
                } else if (check_item === 'sequential') {
                    var curr_ascii = password.charCodeAt(0);
                    var is_seq = false;
                    var switched = false;
                    for (var k=1; k<password.length; k++) {
                        var next_ascii = password.charCodeAt(k);
                        if (curr_ascii+1 == next_ascii) {
                            is_seq = true;
                            curr_ascii = next_ascii;
                        } else if (is_seq) { 
                            if (switched) { //only allow switch once
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
                         alert("The account violates NIST requirements: password is sequential.");
                        return false;
                    }
                } else if (check_item === 'context') {
                    //var service_name = window.location.hostname;
                    var username = userid;
                    if (username.indexOf(password) !== -1) { //password is part of username
                        alert("The account violates NIST requirements: password is in context (part of the user ID).");
                        return false;
                    }
                    if (service_name.indexOf(password) !== -1) { //password is part of service name
                         alert("The account violates NIST requirements: password is in context (part of the service name).");
                        return false;
                    }
                }
            }
        }
        
    }
}

function check_nist_password_policies(userid, password) {
    var nist_configs = null;
    $.get('nist_latest.json', function(data) {
        nist_configs = data;
        check_nist_password_policies_by_json(userid, password, data);
    }, "json");
}

check_nist_password_policies('this_is_test@gmail.com', 'master')
check_nist_password_policies('this_is_test@gmail.com', '888888')
check_nist_password_policies('this_is_test@gmail.com', '1234abcd')
check_nist_password_policies('this_is_test@gmail.com', 'is_test')
check_nist_password_policies('this_is_test@gmail.com', 'vericlouds')
```
