package com.fathzer.mail;

import java.util.Collections;
import java.util.List;

/** The recipients of an email.
 * <br>An email can have 3 types of recipients:<ul>
 * <li><b>to</b>: direct recipients</li>
 * <li><b>cc</b>: recipients that get a copy of the mail</li>
 * <li><b>bcc</b>: recipients that get a <i>blind</i> copy of the mail</li>
 * </ul>
 */
public class Recipients {
	private List<EMailAddress> to;
	private List<EMailAddress> cc;
	private List<EMailAddress> bcc;
	
	/** Constructor.
	 * <br>Creates an empty instance (no recipient of any type)
	 */
	public Recipients() {
		this.to = Collections.emptyList();
		this.cc = Collections.emptyList();
		this.bcc = Collections.emptyList();
	}
	
	/** Builds a recipients list.
	 * <br>All recipients are <i>direct</i> ones (of <i>to</i> type, not <i>cc</i> or <i>bcc</i>)  
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

	/** Sets the direct recipients.
	 * @param to The email addresses of the recipients 
	 */
	public void setTo(List<EMailAddress> to) {
		this.to = to;
	}

	List<EMailAddress> getCc() {
		return cc;
	}

	/** Sets the recipients that will get a copy.
	 * @param cc The email addresses of the recipients 
	 */
	public void setCc(List<EMailAddress> cc) {
		this.cc = cc;
	}

	List<EMailAddress> getBcc() {
		return bcc;
	}

	/** Sets the recipients that will get a <i>blind</i> copy.
	 * @param bcc The email addresses of the recipients 
	 */
	public void setBcc(List<EMailAddress> bcc) {
		this.bcc = bcc;
	}
	
	boolean isEmpty() {
		return to.isEmpty() && cc.isEmpty() && bcc.isEmpty();
	}
}
