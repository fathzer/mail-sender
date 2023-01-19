package com.fathzer.mail;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MailerBuilderTest {

	@Test
	void test() {
		final MailerBuilder builder = new MailerBuilder("toto");
		assertEquals("toto",builder.getHost());
		assertEquals(Encryption.TLS, builder.getEncryption());
		assertEquals(Encryption.TLS.getDefaultPort(), builder.getPort());
		assertNull(builder.getDefaultSender());
		assertNull(builder.getPwd());
		assertNull(builder.getUser());
		
		assertThrows(IllegalArgumentException.class, () -> builder.withEncryption(null));
		assertThrows(IllegalArgumentException.class, () -> builder.withPort(0));
		assertThrows(IllegalArgumentException.class, () -> builder.withAuthentication(null, "x"));
		assertThrows(IllegalArgumentException.class, () -> builder.withAuthentication("x", null));
		
		builder.withAuthentication("x", "pwd");
		assertEquals("x",builder.getUser());
		assertNull(builder.getDefaultSender());
		assertEquals("pwd", builder.getPwd());

		builder.withAuthentication("me@gmail.com", "gmailpwd");
		assertEquals("me@gmail.com",builder.getUser());
		assertEquals(new EMailAddress("me@gmail.com"),builder.getDefaultSender());
		assertEquals("gmailpwd", builder.getPwd());
		builder.withAuthentication(null, null);
		assertNull(builder.getPwd());
		assertNull(builder.getUser());
		assertNotNull(builder.getDefaultSender());
		builder.withDefaultSender(null);
		assertNull(builder.getDefaultSender());
		
		builder.withEncryption(Encryption.SSL);
		assertEquals(Encryption.SSL, builder.getEncryption());
		assertEquals(Encryption.SSL.getDefaultPort(), builder.getPort());
	}

}
