package it.jflower.base.utils;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class PopAuthenticator extends Authenticator{
	private String user;
	private String password;

	public PopAuthenticator(String user, String password) {
		this.user = user;
		this.password = password;
	}

	public PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(user, password);
	}
}
