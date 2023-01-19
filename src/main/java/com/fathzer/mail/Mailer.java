package com.fathzer.mail;

import java.io.IOException;

public interface Mailer {
	/** Sends a message.
	 * @param message The message to send
	 * @throws IOException If something went wrong.
	 */
	void send(Message message) throws IOException;
}
