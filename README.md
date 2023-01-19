# mail-sender
A minimalist java mail client API to send emails through external SMTP server.

![Maven Central](https://img.shields.io/maven-central/v/com.fathzer/mail-sender)
![License](https://img.shields.io/badge/license-Apache%202.0-brightgreen.svg)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=fathzer_mail-sender&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=fathzer_mail-sender)
[![javadoc](https://javadoc.io/badge2/com.fathzer/mail-sender/javadoc.svg)](https://javadoc.io/doc/com.fathzer/mail-sender)


# mail-sender
A minimalist java mail client API to send emails through external SMTP server.

The library is distributed through Maven (see badge above).

WORK IN PROGRESS...

Here is an example using GMail

```
Mailer mailer = new MailerBuilder("smtp.gmail.com").withAuthentication("me@gmail.com", "anapppassword").build();
mailer.send(new Message(Arrays.asList(new MailAddress("other@hisdomain.com")), "test", "This is a test from Google"));
```