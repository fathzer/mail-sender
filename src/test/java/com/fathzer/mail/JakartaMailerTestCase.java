package com.fathzer.mail;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import jakarta.mail.Address;
import jakarta.mail.Message;
import jakarta.mail.Message.RecipientType;
import jakarta.mail.MessagingException;
import jakarta.mail.Transport;

class JakartaMailerTestCase {

	@Test
	void test() throws Exception {
		final EMailAddress defaultSender = new EMailAddress("me@google.com");
		final JakartaMailer mailer = (JakartaMailer) new MailerBuilder("stmp").withDefaultSender(defaultSender).build();
		assertFalse(mailer.getSession().getDebug());
		mailer.setDebug(true);
		assertTrue(mailer.getSession().getDebug());
		
		Recipients to = Recipients.to("to@yahoo.de");
		EMail mail = new EMail(to, "subject", "content");
		mailer.send(mail);
		assertArrayEquals(to.getTo().stream().map(EMailAddress::getAddress).toArray(Address[]::new), Transport.lastMsg.getRecipients(Message.RecipientType.TO));
		testRecipientsAre(RecipientType.TO, new String[]{"to@yahoo.de"});
		assertEquals(1, Transport.lastMsg.getFrom().length);
		assertEquals(defaultSender.getAddress(), Transport.lastMsg.getFrom()[0]);
		assertEquals("subject", Transport.lastMsg.getSubject());
		assertEquals("content", Transport.lastMsg.getDataHandler().getContent());
		assertEquals("text/plain", Transport.lastMsg.getDataHandler().getContentType());
		
		EMailAddress other = new EMailAddress("other@amazon.com");
		mail.withMimeType(MimeType.HTML).withSender(other).withReplyTo(Collections.singletonList(defaultSender));
		to.setCc(EMailAddress.list("cc@gmail.com"));
		to.setBcc(EMailAddress.list("bcc@gmail.com"));
		mailer.send(mail);
		assertEquals(1, Transport.lastMsg.getFrom().length);
		assertEquals(other.getAddress(), Transport.lastMsg.getFrom()[0]);
		assertEquals("text/html", Transport.lastMsg.getDataHandler().getContentType());
		assertEquals(1, Transport.lastMsg.getReplyTo().length);
		assertEquals(defaultSender.getAddress(), Transport.lastMsg.getReplyTo()[0]);
		testRecipientsAre(RecipientType.TO, new String[]{"to@yahoo.de"});
		testRecipientsAre(RecipientType.CC, new String[]{"cc@gmail.com"});
		testRecipientsAre(RecipientType.BCC, new String[]{"bcc@gmail.com"});
//		assertEquals(Transport.lastMsg.getRecipients(Recipients))
	}
	
	void testRecipientsAre(RecipientType type, String[] mails) throws MessagingException {
		assertArrayEquals(mails, Arrays.stream(Transport.lastMsg.getRecipients(type)).map(Object::toString).toArray());

	}
}
