package com.fathzer.mail;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.validator.routines.EmailValidator;

import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;

/** A mail address.
 */
public class EMailAddress {
	private InternetAddress address;
	
	/** Constructor.
	 * @param address The address (example me@gmail.com)
	 * @throws IllegalArgumentException if the address is null or has a wronf format
	 */
	public EMailAddress(String address) {
		if (address==null) {
			throw new IllegalArgumentException();
		}
		if (!EmailValidator.getInstance().isValid(address)) {
			throw new IllegalArgumentException(address+" is not a valid email address");
		}
		try {
			this.address = new InternetAddress(address);
		} catch (AddressException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	/** Builds an mail addresses list.
	 * @param addresses the addresses.
	 * @return a list of addresses. 
	 * @throws IllegalArgumentException if one or more address is null or has a wrong format
	 */
	public static List<EMailAddress> list(String... addresses) {
		return Arrays.stream(addresses).map(EMailAddress::new).collect(Collectors.toList());
	}

	InternetAddress getAddress() {
		return address;
	}

	@Override
	public int hashCode() {
		return Objects.hash(address);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		EMailAddress other = (EMailAddress) obj;
		return Objects.equals(address, other.address);
	}
}
