package com.fathzer.mail;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

class EmailAddressTest {

	@SuppressWarnings("unlikely-arg-type")
	@Test
	void test() {
		assertThrows(IllegalArgumentException.class, () -> new EMailAddress(null));
		assertThrows(IllegalArgumentException.class, () -> new EMailAddress("no a valid one"));

		final List<EMailAddress> lst = EMailAddress.list("a@b.com","c@d.net");
		assertEquals(2, lst.size());
		assertTrue(lst.contains(new EMailAddress("a@b.com")));
		assertTrue(lst.contains(new EMailAddress("c@d.net")));
		
		final EMailAddress addr = new EMailAddress("a@b.com");
		assertEquals(addr.hashCode(), new EMailAddress("a@b.com").hashCode());
		
		assertNotEquals(null, addr);
		assertFalse(addr.equals("a@b.com"));
		assertTrue(addr.equals(addr));
	}
}
