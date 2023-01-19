package com.fathzer.mail;

/** A type of mail.
 */
public class MimeType {
	/** A text message. */
	public static final MimeType TEXT = new MimeType("text/plain");
	/** A HTML formatted message. */
	public static final MimeType HTML = new MimeType("text/html");
	
	private final String asString;

	/** Constructor.
	 * <br>Please note that mime types other than the one defined above, may be supported ... or not.
	 * @param type The string representation of the mime type (example "application/pdf").
	 */
	public MimeType(String type) {
		this.asString = type;
	}

	@Override
	public String toString() {
		return asString;
	}
}
