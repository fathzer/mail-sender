package com.fathzer.mail;

import java.util.Properties;

import jakarta.mail.Authenticator;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;

/** A class able to build a Mailer.
 */
public class MailerBuilder {
	private final String host;
	private String user;
	private String pwd;
	private EMailAddress defaultSender;
	private Encryption encryption;
	private int port;
	
	/** Constructor
	 * <br>By default, the encryption is set to TLS, the port is the default TLS port (587) and connection is made with no authentication.
	 * @param host The SMTP host, for example <i>smtp.gmail.com</i>
	 * @throws IllegalArgumentException if host is null
	 */
	public MailerBuilder(String host) {
		if (host==null) {
			throw new IllegalArgumentException("Host is null");
		}
		this.host = host;
		this.withEncryption(Encryption.TLS);
	}
	
	/** Sets the encryption method to communicate with the server.
	 * <br>The port is also modify to match the default port accordingly with the encryption. 
	 * @param encryption Tee new encryption.
	 * @return this
	 * @throws IllegalArgumentException if encryption is null
	 */
	public MailerBuilder withEncryption(Encryption encryption) {
		if (encryption==null) {
			throw new IllegalArgumentException("Host is null");
		}
		this.encryption = encryption;
		this.port = encryption.getDefaultPort();
		return this;
	}
	
	/** Defines the SMTP host authentication.
	 * @param user The user used for login.<br>
	 * If no default sender has already been set and <i>user</i> is a valid email address, it is used as default sender.<br>
	 * Pass null to remove authentication.
	 * @param pwd The password used for login, null to remove authentication.
	 * @return this
	 * @throws IllegalArgumentException if one of the arguments is null and not the other
	 */
	public MailerBuilder withAuthentication(String user, String pwd) {
		if (user!=null && pwd==null) {
			throw new IllegalArgumentException("Password is null and not user");
		}
		if (pwd!=null && user==null) {
			throw new IllegalArgumentException("User is null and not password");
		}
		this.user = user;
		this.pwd = pwd;
		if (defaultSender==null) {
			try {
				defaultSender = new EMailAddress(user);
			} catch (IllegalArgumentException e) {
				// Ok, user is not a mail address 
			}
		}
		return this;
	}

	/** Sets a default sender for messages sent with this server.
	 * <br>This default sender is also set by {@link #withAuthentication(String, String)} if not already set and user passed to the method is a valid email address.
	 * <br>The sender can always be redefined with {@link EMail#withSender(EMailAddress)}
	 * @param defaultSender the new default sender address.
	 * @return this
	 */
	public MailerBuilder withDefaultSender(EMailAddress defaultSender) {
		this.defaultSender = defaultSender;
		return this;
	}

	/** Sets the port.
	 * <br>As {@link #withEncryption(Encryption)} sets the default port accordingly with the encryption,
	 * this method is only useful for servers that use custom ports.
	 * @param port The new port
	 * @return this
	 * @throws IllegalArgumentException if port is &lt;= 0
	 */
	public MailerBuilder withPort(int port) {
		if (port<=0) {
			throw new IllegalArgumentException("Port is <= 0");
		}
		this.port = port;
		return this;
	}

	/** Builds the mailer.
	 * @return a new Mailer
	 */
	public Mailer build() {
		final Properties props = new Properties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", port);
		encryption.apply(props);
		final Authenticator auth = getAuthenticator();
		if (auth!=null) {
			props.put("mail.smtp.auth", true); //enable authentication
		}
		return build(props, auth);
	}
	
	Mailer build(Properties props, Authenticator auth) {
		return new DefaultMailer(Session.getInstance(props, auth), defaultSender);
	}
	
	private Authenticator getAuthenticator() {
		return user==null ? null : new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(user, pwd);
				}
			};
	}

	/** Gets the SMTP host.
	 * @return a string.
	 */
	public String getHost() {
		return host;
	}

	/** Gets The encryption used to connect to the SMTP host.
	 * @return an encryption.
	 */
	public Encryption getEncryption() {
		return encryption;
	}

	/** Gets The SMTP host's port.
	 * @return an int &gt; 0.
	 */
	public int getPort() {
		return port;
	}

	/** Gets The user used for authentication.
	 * @return a string. Null is no authentication is set.
	 */
	public String getUser() {
		return user;
	}

	/** Gets The password used for authentication.
	 * @return a string. Null is no authentication is set.
	 */
	public String getPwd() {
		return pwd;
	}

	/** Gets The default message sender.
	 * @return a mail address. Null is no default sender is set.
	 */
	public EMailAddress getDefaultSender() {
		return defaultSender;
	}
}
