package com.fathzer.mail;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class EmailTest {

	@Test
	void test() {
		assertThrows(IllegalArgumentException.class, () -> new EMail(null,"test","content"));
		assertThrows(IllegalArgumentException.class, () -> new EMail(EMailAddress.list("a@b.com"),null,"content"));
		assertThrows(IllegalArgumentException.class, () -> new EMail(EMailAddress.list("a@b.com"),"test",null));
		
		final EMail mail = new EMail(EMailAddress.list("a@b.com"),"test","content");
		assertEquals(EMailAddress.list("a@b.com"),mail.getRecipients());
		assertEquals("test",mail.getSubject());
		assertEquals("content",mail.getContent());
		assertEquals(MimeType.TEXT, mail.getMimeType());
		assertNull(mail.getSender());
		assertNull(mail.getReplyTo());
		
		assertThrows(IllegalArgumentException.class, () -> mail.withMimeType(null));
		mail.withMimeType(MimeType.HTML);
		assertEquals(MimeType.HTML, mail.getMimeType());
		
		mail.withSender(new EMailAddress("me@gmail.com"));
		assertEquals(new EMailAddress("me@gmail.com"), mail.getSender());
		
		mail.withReplyTo(EMailAddress.list("x@y.net","z@0.fr"));
		assertEquals(2, mail.getReplyTo().size());
		assertTrue(mail.getReplyTo().contains(new EMailAddress("x@y.net")));
		assertTrue(mail.getReplyTo().contains(new EMailAddress("z@0.fr")));
	}

}
