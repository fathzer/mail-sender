package com.fathzer.mail;

import java.util.List;

public class Message {
	private List<MailAddress> recipients;
	private String subject;
	private String content;
	private MimeType mimeType;
	
	/**
	 * Constructor.
	 * @param recipients The list of recipients
	 * @param subject The mail's subject
	 * @param content The mail's content. By default, mime type is text/plain
	 * @see #withMimeType(MimeType)
	 */
	public Message(List<MailAddress> recipients, String subject, String content) {
		super();
		this.recipients = recipients;
		this.subject = subject;
		this.content = content;
		this.mimeType = MimeType.TEXT;
	}

	public Message withMimeType(MimeType mimeType) {
		this.mimeType = mimeType;
		return this;
	}

	List<MailAddress> getRecipients() {
		return recipients;
	}

	String getSubject() {
		return subject;
	}

	String getContent() {
		return content;
	}

	MimeType getMimeType() {
		return mimeType;
	}
}
