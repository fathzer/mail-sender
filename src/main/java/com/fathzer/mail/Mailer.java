package com.fathzer.mail;

import java.io.IOException;

/** A class able to send emails.
 */
public interface Mailer {
	/** Sends a message.
	 * @param mail The message to send
	 * @throws IOException If something went wrong.
	 */
	void send(EMail mail) throws IOException;
}
