var url = "https://api.vericlouds.com/index.php"
var api_key = 'XXXXXXXX'
var api_secret = 'xxxxxxxxxxxxxxxx'
var userid = 'this_is_test@gmail.com'
var password = '123456'

function mark_password(password) {
    var masked_password = '';
    if (password.length > 2) {
        masked_password = password.charAt(0)+Array(password.length-1).join("*")+password.charAt(password.length-1);
    } else {
        masked_password = password;
    }
    return masked_password
}
