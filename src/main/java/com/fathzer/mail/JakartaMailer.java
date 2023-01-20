package com.fathzer.mail;

import java.io.IOException;
import java.util.List;

import jakarta.mail.Address;
import jakarta.mail.Message;
import jakarta.mail.Message.RecipientType;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.MimeMessage;

/** A default implementation of mailer based on javax/jakarta.mail.
 */
public class JakartaMailer implements Mailer {
	private final Session session;
	private Address defaultSender;

	/** Constructor.
	 * @param session The jakarta Session object
	 * @param defaultSender The default sender mail address or null if no default sender is defined
	 */
	public JakartaMailer(Session session, EMailAddress defaultSender) {
		this.session = session;
		this.defaultSender = defaultSender==null ? null : defaultSender.getAddress();
	}
	
	/** Sets the debug mode of underlying jakarta mail api.
	 * @param debug true to activate debug
	 */
	public void setDebug(boolean debug) {
		session.setDebug(debug);
	}
	
	@Override
	public void send(EMail email) throws IOException {
		try {
			final Message msg = new MimeMessage(session);
			msg.setFrom(getSender(email));
			if (email.getReplyTo()!=null) {
				msg.setReplyTo(toAddresses(email.getReplyTo()));
			}
			addRecipients(msg, Message.RecipientType.TO, email.getRecipients().getTo());
			addRecipients(msg, Message.RecipientType.CC, email.getRecipients().getCc());
			addRecipients(msg, Message.RecipientType.BCC, email.getRecipients().getBcc());
			msg.setSubject(email.getSubject());
			msg.setContent(email.getContent(), email.getMimeType().toString());
	
			Transport.send(msg);
		} catch (MessagingException e) {
			throw new IOException(e);
		}
	}
	
	private void addRecipients(Message msg, RecipientType recipientType, List<EMailAddress> addresses) throws MessagingException {
		msg.setRecipients(recipientType, addresses.stream().map(EMailAddress::getAddress).toArray(Address[]::new));
	}
	
	private Address getSender(EMail message) {
		return message.getSender()==null ? defaultSender : message.getSender().getAddress();
	}
	
	private Address[] toAddresses(List<EMailAddress> addr) {
		return addr.stream().map(EMailAddress::getAddress).toArray(Address[]::new);
	}
	
	Session getSession() {
		return session;
	}
}
