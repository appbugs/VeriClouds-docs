import json

service_name = 'vericlouds.com' # please change to your service name

def is_compromised(userid, password):
    # please use the code listed in Examples section on how to call our API
    pass

def load_nist_configs():
    return json.load(open('nist_latest.json'))

def check_nist_password_policies(userid, password):
    nist = load_nist_configs()
    check_result = {'pass':True}
    for check_item in nist['check_list']:
        if check_item == 'compromised':
            if is_compromised(userid, password) == True:
                check_result['pass'] = False;
                check_result['reason'] = 'Password has been leaked in a previous data breach of another service';
                break;
            
        elif check_item == 'dictionary':
            for dictionary_password in nist['dictionary_passwords']:
                if password == dictionary_password:
                    check_result['pass'] = False;
                    check_result['reason'] = 'Password is found in a dictionary of commonly used passwords';
                    break;
           
        elif check_item == 'repetitive':
            first_char = password[0];
            is_repeat = True;
            for i in password: 
                if first_char != i:
                    is_repeat = False;
                    break;
            if is_repeat:
                check_result['pass'] = False;
                check_result['reason'] = 'Password is repetitive';
                break;
                
        elif check_item == 'sequential':
            curr_ascii = ord(password[0]);
            is_seq = False;
            switched = False;
            for i in password[1:]:
                next_ascii = ord(i);
                if curr_ascii + 1 == next_ascii:
                    is_seq = True;
                    curr_ascii = next_ascii;
                elif is_seq:
                    if switched: #only allow switch once
                        is_seq = False;
                        break;
                    else:
                        switched = True;
                    curr_ascii = next_ascii;
                else:
                    break;
            if is_seq:
                check_result['pass'] = False;
                check_result['reason'] = 'Password is sequential';
                break;
                
        elif check_item == 'context':
            username = userid;
            if password in username: # password is part of username
                check_result['pass'] = False;
                check_result['reason'] = 'Password is in context. It is part of the user ID';
                break;
            if service_name != None and password in service_name: # password is part of service name
                check_result['pass'] = False;
                check_result['reason'] = 'Password is in context. It is part of the service name';
                break;
                
    return check_result;

    
if __name__ == '__main__':
    print check_nist_password_policies('this_is_test@gmail.com', 'master')
    print check_nist_password_policies('this_is_test@gmail.com', '888888')
    print check_nist_password_policies('this_is_test@gmail.com', '1234abcd')
    print check_nist_password_policies('this_is_test@gmail.com', 'is_test')
    print check_nist_password_policies('this_is_test@gmail.com', 'vericlouds')