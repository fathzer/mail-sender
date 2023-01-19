package com.fathzer.mail;

import java.io.IOException;

public interface Mailer {
	void send(Message message) throws IOException;
}
