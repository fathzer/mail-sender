package jakarta.mail;

public abstract class Authenticator {
	protected abstract PasswordAuthentication getPasswordAuthentication();
	
	public PasswordAuthentication getPwdAuth() {
		return getPasswordAuthentication();
	}

}
