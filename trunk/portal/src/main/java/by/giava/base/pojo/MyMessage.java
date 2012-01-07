package by.giava.base.pojo;

import java.io.InputStream;

import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

public class MyMessage extends MimeMessage {
	private String id;

	protected MyMessage(Folder arg0, InputStream arg1, int arg2)
			throws MessagingException {
		super(arg0, arg1, arg2);
	}

	public MyMessage(Session session) {
		super(session);
	}

	protected void updateMessageID() throws MessagingException {
		setHeader("Message-Id", getId());
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
