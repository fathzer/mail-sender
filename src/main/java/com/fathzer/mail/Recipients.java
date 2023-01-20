package com.fathzer.mail;

import java.util.Collections;
import java.util.List;

/** The recipients of an email
 */
public class Recipients {
	private List<EMailAddress> to;
	private List<EMailAddress> cc;
	private List<EMailAddress> bcc;
	
	public Recipients() {
		this.to = Collections.emptyList();
		this.cc = Collections.emptyList();
		this.bcc = Collections.emptyList();
	}
	
	/** Builds a recipients list.
	 * <br>All recipients are <i>direct</i> ones (in <i>to</i> field, not <i>cc</i> or <bcc)  
	 * @param addresses A list of addresses as strings
	 * @return A new Recipient instance
	 */
	public static Recipients to(String... addresses) {
		final Recipients recipients = new Recipients();
		recipients.to = EMailAddress.list(addresses);
		return recipients;
	}

	List<EMailAddress> getTo() {
		return to;
	}

	public void setTo(List<EMailAddress> to) {
		this.to = to;
	}

	List<EMailAddress> getCc() {
		return cc;
	}

	public void setCc(List<EMailAddress> cc) {
		this.cc = cc;
	}

	List<EMailAddress> getBcc() {
		return bcc;
	}

	public void setBcc(List<EMailAddress> bcc) {
		this.bcc = bcc;
	}
	
	boolean isEmpty() {
		return to.isEmpty() && cc.isEmpty() && bcc.isEmpty();
	}
}
