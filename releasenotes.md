<br>
<img src="https://www.vericlouds.com/wp-content/uploads/2017/10/logo-2.png" alt="Logo" style="width: 300px;">

# Release Notes

#### version: 1.1.9 (3/13/2018)

* New: Check common passwords by domain (top 1,000, 10,000, etc.)  

![Common Password by Domain](https://appbugs.github.io/img/common-passwords-by-domain.png "Check common passwords by domain")

* New: Query based on user name or email address

![query-username-email](https://appbugs.github.io/img/query-username-email.png "Query based on user name or email address")

*	New: Support query with hashed passwords

![Hashed-passwords](https://appbugs.github.io/img/Hashed-passwords.png "Support query with hashed passwords")

#### version 1.1.8 (1/23/2018)
* New: VIP labeling and daily scan  
* New: ‘First Seen’ date added  
* New: ‘Profiles’ data added showing the types of data leaked 

![VIP-and-first-seen](https://appbugs.github.io/img/VIP-and-first-seen.png "‘Profiles’ data added showing the types of data leaked")

* New: Data insights via graphical interface

![Insights](https://appbugs.github.io/img/Insights.png "Data insights via graphical interface")

#### version: 1.1.7 (12/22/2017)
* New: CredMonitor supports monitoring leaked profile information
* New: API supports email as userid_type

![credmonitor_profile](https://appbugs.github.io/img/feature_credmonitor_profile.png "In addition to leaked passwords, CredMonitor now supports monitoring leaked profile data such as full name, physical address, phone number, and others.")

#### version: 1.1.6 (12/08/2017)
* New: VIP labeling in CredMonitor
* New: Upgrade of reminder

![credmonitor_vip](https://appbugs.github.io/img/feature_credmonitor_vip.png "CredMonitor supports label key employees as VIP. VIP accounts will be monitored daily and admin will be notified immediately when leaked passwords are found for VIPs.")

#### version: 1.1.5 (12/01/2017)
* New: Domain monitoring and Personal ID UI unified
* New: API supports ai_bot
* Fix: Portal UI blocked calling API

![api_ai_bot](https://appbugs.github.io/img/feature_api_ai_bot.png "API supports AI bot which can intelligently find more leaked passwords for the same user. 6% additional leaked passwords can be found with this version of AI bot.")

#### version: 1.1.4 (11/29/2017)
* New: API usage display on API Portal
* New: API supports phone_number as a new userid type
* Fix: Browser favicon display issue

![api_phone_number](https://appbugs.github.io/img/feature_api_phonenumber.png "API supports query with phone number as user identity.")

#### version: 1.1.3 (11/10/2017)
* New: Language button removed from Customer portal. Customer portal language configurable by Partner.
* New: demo on how to use CredCrypto API in a website
* Fix: Language support minor issue

![credcrypto_api_demo](https://appbugs.github.io/img/demo_registration.png "Website Registration demo using VeriClouds CredCrypto API. All user data will be double encrypted and are uncrackable even if database is stolen.")

#### version: 1.1.2 (11/02/2017)
* New: Revoke access when wrong email is sent by moving passwords from email to link
* Fix: Release Note scroll issue

#### version: 1.1.1 (10/20/2017)
* New: Consolidate customer management buttons into dropdown menus
* New: Manage customer license by setting paid expiration date and notes
* Fix: Attach partner name when creating new customer account
* New: Load API doc from Github web portal

![expiration_date](https://appbugs.github.io/img/manage_paid_expiration_feature.png "Expiration date and notes feature. Only partner role admin can use the feature in Customer Management section.")

#### version: 1.1.0 (10/12/2017)
* New: Show release notes to partner admin at the homepage
* Fix: Update of auth token

#### version: 1.0.9 (10/10/2017)
* New: Add Use Cases section to CredVerify API page
* New: Add Live Demo to CredVerify API page
* Fix: fix Personal ID empty id issue

![use cases](https://appbugs.github.io/img/feature_usecases.png "Use Cases feature. You can see it after clicking CredVerify API in front page. Click on Live Demo button to try a live demo of web login with CredVerify enabled.")

#### version: 1.0.8 (09/28/2017)
* New: CredMonitor allows export Summary Report and Details Report

![summary_report](https://appbugs.github.io/img/summary_report_feature.png "Summary Report feature. Partner role admin can use this feature in Customer Management to export a summary report for a given Customer.")
![details_report](https://appbugs.github.io/img/details_report_feature.png "Details Report feature. Partner role admin can use this feature in Customer Management to export a details report for a given Customer.")

#### version: 1.0.7 (09/21/2017)
* Fix: API portal Edge browser issue
* New: CredVerify API supports source_type and source_count

![API_source_support](https://appbugs.github.io/img/API_source_type_count_feature.png "API source_type and source_count support. Customer can load source types and source counts for a given userid from the API. For more details, read the API Docs")

#### version: 1.0.6 (09/15/2017)
* Fix: API portal Edge browser issue
* New: Portal supports Partner branding

#### version: 1.0.5 (08/31/2017)
* New: Portal show total number of leaked accounts and newly added accounts

![portal_show_stats](https://appbugs.github.io/img/portal_show_stats_feature.png "Portal shows the total number of leaked accounts and newly added accounts in front page")

#### version: 1.0.4 (08/18/2017)
* New: CredMonitor Personal IDs Notify button
* Fix: Log IP address issue

#### version: 1.0.3 (08/14/2017)
* New: CredMonitor Personal IDs Batch Import
* Fix: UI layout fix of language button

#### version: 1.0.2 (08/01/2017)
* New: CredMonitor Personal IDs feature

![personal_ids](https://appbugs.github.io/img/personal_ids_feature.png "Personal IDs feature in CredMonitor. A Customer can use this feature to help its employees monitor leaked passwords based on personal email addresses.")

#### version: 1.0.1 (07/12/2017)
* Fix: HTTPS redirect loop issue
* Fix: localhost issue in index.html

#### version: 1.0.0 (06/01/2017)
* New: Portal 2 lauched. Domain name: portal2.vericlouds.com
* New: support HTTPS
