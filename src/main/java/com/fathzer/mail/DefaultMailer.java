package com.fathzer.mail;

import java.io.IOException;
import java.util.List;

import jakarta.mail.Address;
//import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.MimeMessage;

public class DefaultMailer implements Mailer {
	private final Session session;
	private Address sender;
	private Address[] replyTo;

	public DefaultMailer(Session session, MailAddress sender) {
		this.session = session;
		this.sender = sender.getAddress();
	}
	
	public void setDebug(boolean debug) {
		session.setDebug(debug);
	}
	
	public void setSender(MailAddress sender) {
		this.sender = sender.getAddress();
	}
	
	public void setReplyTo(List<MailAddress> replyTo) {
		this.replyTo = replyTo==null?null:toAddresses(replyTo);
	}

	@Override
	public void send(Message message) throws IOException {
		try {
			final jakarta.mail.Message msg = new MimeMessage(session);
			msg.setFrom(sender);
			if (replyTo!=null) {
				msg.setReplyTo(replyTo);
			}
			msg.setRecipients(jakarta.mail.Message.RecipientType.TO, message.getRecipients().stream().map(MailAddress::getAddress).toArray(Address[]::new));
			msg.setSubject(message.getSubject());
			msg.setContent(message.getContent(), message.getMimeType().toString());
	
			// Setting the Subject and Content Type
			Transport.send(msg);
		} catch (MessagingException e) {
			throw new IOException(e);
		}
	}
	
	private Address[] toAddresses(List<MailAddress> addr) {
		return addr.stream().map(MailAddress::getAddress).toArray(Address[]::new);
	}
}
