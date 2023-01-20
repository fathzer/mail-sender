package com.fathzer.mail;

import java.util.Properties;
import java.util.function.Consumer;

/** A SMTP encryption protocol.
 */
public enum Encryption {
	/** No Encryption */
	NONE(25, p -> {}),
	/** <a href="https://datatracker.ietf.org/doc/html/rfc6409">TLS</a>*/
	TLS(587, p -> p.put("mail.smtp.starttls.enable", true)),
	/** <a href="https://datatracker.ietf.org/doc/html/rfc8314">SSL</a>*/
	SSL(465, p -> {
		p.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		p.put("mail.smtp.ssl.checkserveridentity",true);
	});

	private int defaultPort;
	private Consumer<Properties> propBuilder;
	
	private Encryption(int port, Consumer<Properties> propBuilder) {
		this.defaultPort = port;
		this.propBuilder = propBuilder;
	}

	int getDefaultPort() {
		return defaultPort;
	}
	
	void apply(Properties properties) {
		propBuilder.accept(properties);
	}
}
