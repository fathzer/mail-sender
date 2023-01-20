package com.fathzer.mail;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;

import org.junit.jupiter.api.Test;

import jakarta.mail.Address;
import jakarta.mail.Message;
import jakarta.mail.Transport;

class JakartaMailerTestCase {

	@Test
	void test() throws Exception {
		final EMailAddress defaultSender = new EMailAddress("me@google.com");
		final JakartaMailer mailer = (JakartaMailer) new MailerBuilder("stmp").withDefaultSender(defaultSender).build();
		assertFalse(mailer.getSession().getDebug());
		mailer.setDebug(true);
		assertTrue(mailer.getSession().getDebug());
		
		Recipients to = Recipients.to("me@yahoo.de");
		EMail mail = new EMail(to, "subject", "content");
		mailer.send(mail);
		assertArrayEquals(to.getTo().stream().map(EMailAddress::getAddress).toArray(Address[]::new), Transport.lastMsg.getRecipients(Message.RecipientType.TO));
		assertEquals(1, Transport.lastMsg.getFrom().length);
		assertEquals(defaultSender.getAddress(), Transport.lastMsg.getFrom()[0]);
		assertEquals("subject", Transport.lastMsg.getSubject());
		assertEquals("content", Transport.lastMsg.getDataHandler().getContent());
		assertEquals("text/plain", Transport.lastMsg.getDataHandler().getContentType());
		
		EMailAddress other = new EMailAddress("other@amazon.com");
		mail.withMimeType(MimeType.HTML).withSender(other).withReplyTo(Collections.singletonList(defaultSender));
		mailer.send(mail);
		assertEquals(1, Transport.lastMsg.getFrom().length);
		assertEquals(other.getAddress(), Transport.lastMsg.getFrom()[0]);
		assertEquals("text/html", Transport.lastMsg.getDataHandler().getContentType());
		assertEquals(1, Transport.lastMsg.getReplyTo().length);
		assertEquals(defaultSender.getAddress(), Transport.lastMsg.getReplyTo()[0]);
	}
}
