---
date: January 13, 2022
---

# VeriClouds CredVerify API Document

## Why?

Passwords already become the weakest link in cyber security.
According to Verizon Data Breach Report 2017, 81% of data breaches confirmed in 2017 involved using weak and/or stolen passwords.
The VeriClouds CredVerify API provides a secure and easy-to-use API interface which allows an enterprise customer to verify whether a user/employee account is compromised by securely checking the account record against billions of leaked credentials collected by VeriClouds.

## API Usage

This document provides instructions on how to integrate VeriClouds CredVerify API with your account systems.
First you need to have an `api_key` and an `api_secret` (If you do not have them, request FREE TRIAL from VeriClouds.).
After obtaining the `api_key`, and `api_secret`, you can develop code to use the VeriClouds CredVerify API service.


#### Check if an account is compromised

To check whether the user ID and password combination is included in the compromised database, you first make an API call to the endpoint described below with the user ID of the account under API working mode `search_leaked_password_with_userid`.
For a successful API call, the response data will include `passwords_encrypted`, a list of leaked passwords found by VeriClouds to be associated with the user ID.
The passwords are encrypted with AES 256 CBC mode.

If you do not have the plaintext password of the account, you can use the number of records in the list as a risk indicator of the user ID.
If you have the plaintext, then you can compare the plaintext against the list of leaked passwords to detect a match. Specifically, you need to decrypt the passwords with the api_secret as the key and then do the comparison. You will notice that each decrypted password only contains first and last characters and the length of the leaked password. You simply compare this partial leaked password against the plaintext password of the account. If the three pieces of information match (i.e., first, last characters and the length), according to VeriClouds study, there is over 99.9% of possibility that your password matches the leaked password, meaning that the user account (both user ID and password) is compromised.

#### Check if a password is compromised

To verify whether a password is included in a list of already compromised passwords, you make an API call to the endpoint described below with a partial hash of the test password under API working mode `search_leaked_password_with_hash_segment`. The response data will include `password_hashes`, a list of leaked passwords in format of SHA1 hashes. Then use the SHA1 hash of the test password to check against the list to find out whether it is compromised or not.

#### API interface

[https://api.vericlouds.com/index.php](https://api.vericlouds.com/index.php)

#### Request parameters

All the parameters should be encoded by "urlencoding" and send to API Interface by POST method.

<table>
<thead>
<tr>
<td>Name</td>
<td>Description</td>
</tr>
</thead>
<tbody>
<tr>
<td>mode</td>
<td>The API working mode, currently we accept <b>search_leaked_password_with_userid</b> and <b>search_leaked_password_with_hash_segment</b>. The first mode allows to query leaked passwords with a user ID. The second mode allows to query leaked passwords with partial hash of the user password. For the first mode, <b>userid</b> is required. For the second mode, <b>hash_segment</b> is required.</td>
</tr>
<tr>
<td>api_key</td>
<td>The API key assigned by VeriClouds to the customer.</td>
</tr>
<tr>
<td>api_secret</td>
<td>The API secret assigned by VeriClouds to the customer. The secret is also used for decrypting sensitive data such as leaked passwords. It is important to never share the secret with any 3rd party.</td>
</tr>
<tr>
<td>userid</td>
<td>The user ID of the test account. The API currently only accepts email address as user ID. Only provide this field when <b>mode</b> is <b>search_leaked_password_with_userid</b>.</td>
</tr>
<tr>
<td>userid_type</td>
<td>The type of <b>userid</b>. The API currently accepts <b>email</b>, <b>username</b>, <b>phone_number</b>, <b>hashed_email</b>, <b>hashed_userid</b>, and <b>hashed_username_part</b>. If <b>hashed_*</b> is specified, <b>userid</b> field is required to have the SHA256 hash value of the email, username, or userid (email or username) instead of the plaintext version. If <b>hashed_*_part</b> is specified, you can put first part of the hash (minimum 16-byte) in <b>userid</b> instead of the full hash to preserve more privacy. </td>
</tr>
<tr>
<td>hash_segment</td>
<td>First six characters of a hex formated SHA1 hash of the test password. Only provide this field when <b>mode</b> is <b>search_leaked_password_with_hash_segment</b>. </td>
</tr>
<tr>
<td>context</td>
<td>Additional context data that can be requested. It is a list of keywords, seperated by comma. It only works with <b>mode</b>=<b>search_leaked_password_with_userid</b>. Each keyword indicate an additional task. Accepted keywords include <b>source_type</b> (task: load source type),<b>source_count</b> (task: load source count), <b>include_hash</b> (task: include leaked password hashes), <b>paste</b> (task: search pastes for leaks), <b>ai_bot</b> (task: enable AI bot to find more leaks), <b>profile</b> (task: load leaked profiles), <b>first_seen_date</b> (task: load first seen date), <b>last_seen_date</b> (task: load last seen date), <b>complexity</b> (task: load password complexity), and <b>hash_password</b> (task: hash passwords). If <b>hash_password</b> is specified, it is required to include <b>hash_algorithm</b> (supported value includes <b>sha256</b> and <b>sha512</b>) and <b>hash_salt</b> (salt for hashing passwords).</code></td>
</tr>
</tbody>
</table>
