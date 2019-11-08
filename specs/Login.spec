Specification Heading
==========================

Created by neseceylan on 22.07.2019

This is an executable specification file. This file follows markdown syntax. Every heading in this file denotes a scenario. Every bulleted point denotes a step.
To execute this specification, use `mvn test`


Bireysel Login
-----------
 Tags:bireysellogin
*  Elementine js ile tıkla "loginBtn"
* "5" saniye bekle
* Elementine odaklanmadan tıkla "loginDropdown"
* Elementine odaklanmadan tıkla "sifreGiris"
* "307385720" textini elemente yaz "gsmArea"
* "530530" textini elemente yaz "passwordBtn"
* "3" saniye bekle
*  Elementine js ile tıkla "signBtn"
*  Elementine js ile tıkla "cıkısBtn"
*  Elementine js ile tıkla "cıkısYap"
* "10" saniye bekle



Kurumsal Login
-----------
 Tags: kurumsallogin
* "5" saniye bekle
* Elementine js ile tıkla "kurumsalLogin"
* "5" saniye bekle
*  Elementine js ile tıkla "loginBtn"
* Elementine odaklanmadan tıkla "loginDropdown"
* Elementine odaklanmadan tıkla "sifreGiris"
* "376441465" textini elemente yaz "gsmArea"
* "530530" textini elemente yaz "passwordBtn"
* Elementine odaklanmadan tıkla "signBtn"
* Elementine odaklanmadan tıkla "cıkısBtn"
*  Elementine js ile tıkla "cıkısYap"
* "5" saniye bekle

Ana Iletısım Hattiyla Login
-------------------------
 Tags:anaIletısımHattiLogin

*  Elementine js ile tıkla "loginBtn"
* "5" saniye bekle
* Elementine odaklanmadan tıkla "loginDropdown"
* Elementine odaklanmadan tıkla "sifreGiris"
* "307387243" textini elemente yaz "gsmArea"
* "530530" textini elemente yaz "passwordBtn"
* Elementine odaklanmadan tıkla "signBtn"
* Elementine odaklanmadan tıkla "ayarlarim"
* Elementine odaklanmadan tıkla "hatAyarlarim"
* Elementine js ile tıkla "anaIletısımHatGoruntule"
* "dıgerHatlar" elementine kadar swipe yap
* Element var mı kontrol et "dıgerHatlar"
* "5" saniye bekle