package com.fathzer.mail;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Properties;

import org.junit.jupiter.api.Test;

import jakarta.mail.Authenticator;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;

class MailerBuilderTest {

	@Test
	void test() throws AddressException {
		assertThrows(IllegalArgumentException.class, ()-> new MailerBuilder(null));
		
		final ObservableBuilder builder = new ObservableBuilder("toto");
		assertEquals("toto",builder.getHost());
		assertEquals(Encryption.TLS, builder.getEncryption());
		assertEquals(Encryption.TLS.getDefaultPort(), builder.getPort());
		assertNull(builder.getDefaultSender());
		assertNull(builder.getPwd());
		assertNull(builder.getUser());
		builder.build();
		assertNull(builder.auth);
		assertEquals(3, builder.props.size());
		assertEquals(Encryption.TLS.getDefaultPort(), builder.props.get("mail.smtp.port"));
		assertEquals(true, builder.props.get("mail.smtp.starttls.enable"));
		assertEquals("toto", builder.props.get("mail.smtp.host"));
		
		assertThrows(IllegalArgumentException.class, () -> builder.withEncryption(null));
		assertThrows(IllegalArgumentException.class, () -> builder.withPort(0));
		assertThrows(IllegalArgumentException.class, () -> builder.withAuthentication(null, "x"));
		assertThrows(IllegalArgumentException.class, () -> builder.withAuthentication("x", null));
		
		builder.withAuthentication("x", "pwd");
		assertEquals("x",builder.getUser());
		assertNull(builder.getDefaultSender());
		assertEquals("pwd", builder.getPwd());
		builder.build();
		assertEquals(4, builder.props.size());
		assertEquals(true, builder.props.get("mail.smtp.auth"));
		assertNotNull(builder.auth);

		builder.withAuthentication("me@gmail.com", "gmailpwd");
		assertEquals("me@gmail.com",builder.getUser());
		assertEquals(new EMailAddress("me@gmail.com"),builder.getDefaultSender());
		assertEquals("gmailpwd", builder.getPwd());
		builder.withAuthentication(null, null);
		assertNull(builder.getPwd());
		assertNull(builder.getUser());
		assertNotNull(builder.getDefaultSender());
		assertEquals(new InternetAddress("me@gmail.com"), builder.getDefaultSender().getAddress());
		builder.withDefaultSender(null);
		assertNull(builder.getDefaultSender());
		
		builder.withEncryption(Encryption.SSL);
		assertEquals(Encryption.SSL, builder.getEncryption());
		assertEquals(Encryption.SSL.getDefaultPort(), builder.getPort());
		builder.build();
		assertEquals(4, builder.props.size());
		assertEquals(Encryption.SSL.getDefaultPort(), builder.props.get("mail.smtp.port"));
		assertEquals(true, builder.props.get("mail.smtp.ssl.checkserveridentity"));
		assertEquals("javax.net.ssl.SSLSocketFactory", builder.props.get("mail.smtp.socketFactory.class"));

		builder.withEncryption(Encryption.NONE);
		builder.build();
		assertEquals(2, builder.props.size());
		assertEquals(Encryption.NONE.getDefaultPort(), builder.props.get("mail.smtp.port"));
		
		builder.withPort(9815);
		builder.build();
		assertEquals(9815, builder.props.get("mail.smtp.port"));
	}
	
	private static class ObservableBuilder extends MailerBuilder {
		private Properties props;
		private Authenticator auth;
		
		public ObservableBuilder(String host) {
			super(host);
		}

		@Override
		Mailer build(Properties props, Authenticator auth) {
			this.props = props;
			this.auth = auth;
			return super.build(props, auth);
		}
	}

}
