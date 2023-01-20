package com.fathzer.mail;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.junit.jupiter.api.Test;

import jakarta.mail.Address;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;

class DefaultMailerTestCase {

	@Test
	void test() throws Exception {
		final EMailAddress defaultSender = new EMailAddress("me@google.com");
		final ObservableMailer mailer = (ObservableMailer) new MailerBuilder("stmp") {
			@Override
			Mailer build(Properties props, Authenticator auth) {
				final Session session = Session.getInstance(props, auth);
				return new ObservableMailer(session, getDefaultSender());
			}
		}.withDefaultSender(defaultSender).build();
		assertFalse(mailer.session.getDebug());
		mailer.setDebug(true);
		assertTrue(mailer.session.getDebug());
		
		List<EMailAddress> to = EMailAddress.list("me@yahoo.de");
		EMail mail = new EMail(to, "subject", "content");
		mailer.send(mail);
		assertArrayEquals(to.stream().map(EMailAddress::getAddress).toArray(Address[]::new), mailer.msg.getRecipients(Message.RecipientType.TO));
		assertEquals(1, mailer.msg.getFrom().length);
		assertEquals(defaultSender.getAddress(), mailer.msg.getFrom()[0]);
		assertEquals("subject", mailer.msg.getSubject());
		assertEquals("content", mailer.msg.getDataHandler().getContent());
		assertEquals("text/plain", mailer.msg.getDataHandler().getContentType());
		
		EMailAddress other = new EMailAddress("other@amazon.com");
		mail.withMimeType(MimeType.HTML).withSender(other).withReplyTo(Collections.singletonList(defaultSender));
		mailer.send(mail);
		assertEquals(1, mailer.msg.getFrom().length);
		assertEquals(other.getAddress(), mailer.msg.getFrom()[0]);
		assertEquals("text/html", mailer.msg.getDataHandler().getContentType());
		assertEquals(1, mailer.msg.getReplyTo().length);
		assertEquals(defaultSender.getAddress(), mailer.msg.getReplyTo()[0]);
	}
	
	private static class ObservableMailer extends DefaultMailer {
		private Session session;
		private Message msg;

		public ObservableMailer(Session session, EMailAddress defaultSender) {
			super(session, defaultSender);
			this.session = session;
		}

		@Override
		void send(Message msg) throws MessagingException {
			this.msg = msg;
		}
	}
}
