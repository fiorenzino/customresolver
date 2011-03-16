package it.jflower.base.utils;

import java.io.Serializable;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Encoder;

public class PasswordUtils implements Serializable {

	private static final long serialVersionUID = 1L;

	public static String createPassword(String pwd) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(pwd.getBytes());
			byte[] enc = md.digest();

			// Encode bytes to base64 to get a string
			String encode = new BASE64Encoder().encode(enc);
			return encode;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
