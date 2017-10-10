<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js" integrity="sha384-b/U6ypiBEHpOf/4+1nzFpr53nxSS+GLCkfwBdFNTxtclqqenISfwAzpKaMNFNmj4" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js" integrity="sha384-h0AbiXch4ZDo7tp9hKZ4TsHbi047NrKGLO3SEJAg45jXxnGIfYzk4Si90RDIqNm1" crossorigin="anonymous"></script>

# Lieberman RED – Rapid Enterprise Defense™ Suite

## Enhanced Identity Protection for Lieberman RED Rapid Enterprise Defense™ Suite

Lieberman Software RED Rapid Enterprise Defense™ Suite and VeriClouds CredVerify are helping enable digital transformation by providing sophisticated account level security and protection against data breaches for your privileged accounts on your cross-platform network. Very few organizations can distinguish the genuine user from the sophisticated attacker because of the weakest link in cyber security, weak and/or stolen passwords. With RED Identity Management, enabling VeriClouds CredVerify allows you to:

* Detect if a user's credentials are weak or stolen and display a warning or prohibit the user from logging in
* Prevent the use of weak, reused or stolen credentials during self-service password reset
* Choose between VeriClouds' SaaS service or premium on-premise hardware appliance for maximum scalability and security

<h1>Hello World</h1>

<ul class="nav nav-tabs" id="myTab" role="tablist">
  <li class="nav-item">
    <a class="nav-link active" id="home-tab" data-toggle="tab" href="#home" role="tab" aria-controls="home" aria-expanded="true">Home</a>
  </li>
  <li class="nav-item">
    <a class="nav-link" id="profile-tab" data-toggle="tab" href="#profile" role="tab" aria-controls="profile">Profile</a>
  </li>
  <li class="nav-item dropdown">
    <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false">
      Dropdown
    </a>
    <div class="dropdown-menu">
      <a class="dropdown-item" id="dropdown1-tab" href="#dropdown1" role="tab" data-toggle="tab" aria-controls="dropdown1">@fat</a>
      <a class="dropdown-item" id="dropdown2-tab" href="#dropdown2" role="tab" data-toggle="tab" aria-controls="dropdown2">@mdo</a>
    </div>
  </li>
</ul>
<div class="tab-content" id="myTabContent">
  <div class="tab-pane fade show active" id="home" role="tabpanel" aria-labelledby="home-tab">...</div>
  <div class="tab-pane fade" id="profile" role="tabpanel" aria-labelledby="profile-tab">...</div>
  <div class="tab-pane fade" id="dropdown1" role="tabpanel" aria-labelledby="dropdown1-tab">...</div>
  <div class="tab-pane fade" id="dropdown2" role="tabpanel" aria-labelledby="dropdown2-tab">...</div>
</div>


<p>hello world

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
</p>
