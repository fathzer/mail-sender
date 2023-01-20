package jakarta.mail;

public class Transport {
	public static Message lastMsg;
	
	public static void send(Message msg) throws MessagingException {
		lastMsg = msg;
	}
}
