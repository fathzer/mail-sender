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
	}

}
