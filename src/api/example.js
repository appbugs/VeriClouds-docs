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
        
$.post(url, {mode:'search_leaked_password_with_userid',api_key:api_key,api_secret:api_secret,userid:userid}, function(data){
        var obj = jQuery.parseJSON( data );
        if (obj.result === 'succeeded') {
            var index = 1;
            var match = false;
            for (var password_encrypted in obj.passwords_encrypted) {
                password_encrypted = obj.passwords_encrypted[password_encrypted].split(':');
                var enc = aesjs.utils.hex.toBytes(password_encrypted[0]);
                var iv = aesjs.utils.hex.toBytes(password_encrypted[1]);
                var aesCbc = new aesjs.ModeOfOperation.cbc( aesjs.utils.hex.toBytes(api_secret), iv);
                var decrypted = aesjs.utils.utf8.fromBytes(aesCbc.decrypt(enc));
                decrypted = decrypted.slice(0, decrypted.length - decrypted.charCodeAt(decrypted.length-1))
                if (mark_password(decrypted) == mark_password(password))
                    match = true;
                    
                if (match) {
                    alert('true')
                    break;
                }
                index++;
            }
            if (!match) {
                alert('false')
            }
        } else {
            alert("Query failed! Reason: "+obj.reason);
        }
});