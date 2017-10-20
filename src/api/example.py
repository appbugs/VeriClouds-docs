import urllib, urllib2, json
from Crypto.Cipher import AES

unpad = lambda s : s[:-ord(s[len(s)-1:])]
def AESCipherdecrypt( key, enc ):
    enc, iv = enc.split(':')
    cipher = AES.new(key.decode("hex"), AES.MODE_CBC, iv.decode("hex") )
    return unpad(cipher.decrypt( enc.decode("hex") ))

url = "https://api.vericlouds.com/index.php"
api_key = 'XXXXXXXX'
api_secret = 'xxxxxxxxxxxxxxxx'

def is_compromised(userid, password):
	reqdata = {'mode':'search_leaked_password_with_userid', 'api_key': api_key, 'api_secret': api_secret, 'userid': userid}
	reqdata = urllib.urlencode(reqdata)
	resp = urllib2.urlopen(urllib2.Request(url, reqdata)).read()
	resp = json.loads(resp)
	if resp['result'] != 'succeeded':
		print resp['reason']
		return None
	for pass_enc in resp['passwords_encrypted']:
		plaintext = AESCipherdecrypt(api_secret, pass_enc)
		if (len(password), password[0], password[-1]) == (len(plaintext), plaintext[0], plaintext[-1]) :
			return True
	return False

print is_compromised('this_is_test@gmail.com', '123456')
