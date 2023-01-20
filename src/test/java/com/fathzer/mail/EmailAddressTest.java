package com.fathzer.mail;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

class EmailAddressTest {

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
		
		testEquals(addr,"a@b.com");
	}
	
	@SuppressWarnings("java:S5785") // Suppress wrong Sonar warning (see comment below)
	static void testEquals(Object toTest, Object notSameClass) {
		// Sonar wants the developer to use assert(Not)Equals here ... but in this particular case, it
		// would be a mistake: assert(Not)Equals does not specify which object's equals method is called.
		// It also perform some comparison before calling the equals method.
		assertFalse(toTest.equals(null));
		assertFalse(toTest.equals(notSameClass));
		assertTrue(toTest.equals(toTest));
	}
}
