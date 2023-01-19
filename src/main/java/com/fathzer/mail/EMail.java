package com.fathzer.mail;

import java.util.List;

/** A Mail message.
 */
public class EMail {
	private MailAddress sender;
	private List<MailAddress> recipients;
	private List<MailAddress> replyTo;
	private String subject;
	private String content;
	private MimeType mimeType;
	
	/**
	 * Constructor.
	 * @param recipients The list of recipients
	 * @param subject The mail's subject
	 * @param content The mail's content. By default, mime type is text/plain
	 * @see #withMimeType(MimeType)
	 * @throws IllegalArgumentException if recipients is empty or null or one of others arguments is null 
	 */
	public EMail(List<MailAddress> recipients, String subject, String content) {
		super();
		if (recipients==null || recipients.isEmpty() || subject == null || content==null) {
			throw new IllegalArgumentException();
		}
		this.recipients = recipients;
		this.subject = subject;
		this.content = content;
		this.mimeType = MimeType.TEXT;
	}
	
	/** Sets the sender of this message.
	 * <br>If the mailer has a default sender defined, the message's sender will override it.
	 * @param sender The sender of this message. Null to use the mailer's default sender
	 * @return this
	 */
	public EMail withSender(MailAddress sender) {
		this.sender = sender;
		return this;
	}
	
	/** Sets the '<i>reply to</i>' of this message.
	 * @param replyTo a list of email addresses or null to not specifying reply '<i>reply to</i>'
	 * @return this
	 */
	public EMail withReplyTo(List<MailAddress> replyTo) {
		this.replyTo = replyTo;
		return this;
	}

	/** Sets the type of the message.
	 * @param mimeType The message type.
	 * @return this
	 * @throws IllegalArgumentException if mimeType is null
	 */
	public EMail withMimeType(MimeType mimeType) {
		if (mimeType==null) {
			throw new IllegalArgumentException();
		}
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

	MailAddress getSender() {
		return sender;
	}

	List<MailAddress> getReplyTo() {
		return replyTo;
	}
}
