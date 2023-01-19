package com.fathzer.mail;

import java.io.IOException;
import java.util.List;

import jakarta.mail.Address;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.MimeMessage;

/** A default implementation of mailer based on javax/jakarta.mail.
 */
public class DefaultMailer implements Mailer {
	private final Session session;
	private Address defaultSender;

	/** Constructor.
	 * @param session The jakarta Session object
	 * @param defaultSender The default sender mail address or null if no default sender is defined
	 */
	public DefaultMailer(Session session, EMailAddress defaultSender) {
		this.session = session;
		this.defaultSender = defaultSender.getAddress();
	}
	
	/** Sets the debug mode of underlying jakarta mail api.
	 * @param debug true to activate debug
	 */
	public void setDebug(boolean debug) {
		session.setDebug(debug);
	}
	
	@Override
	public void send(EMail message) throws IOException {
		try {
			final jakarta.mail.Message msg = new MimeMessage(session);
			msg.setFrom(getSender(message));
			if (message.getReplyTo()!=null) {
				msg.setReplyTo(toAddresses(message.getReplyTo()));
			}
			msg.setRecipients(jakarta.mail.Message.RecipientType.TO, message.getRecipients().stream().map(EMailAddress::getAddress).toArray(Address[]::new));
			msg.setSubject(message.getSubject());
			msg.setContent(message.getContent(), message.getMimeType().toString());
	
			// Setting the Subject and Content Type
			Transport.send(msg);
		} catch (MessagingException e) {
			throw new IOException(e);
		}
	}
	
	private Address getSender(EMail message) {
		return message.getSender()==null ? defaultSender : message.getSender().getAddress();
	}
	
	private Address[] toAddresses(List<EMailAddress> addr) {
		return addr.stream().map(EMailAddress::getAddress).toArray(Address[]::new);
	}
}
