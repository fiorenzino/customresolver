package it.jflower.cc.web;

import it.jflower.base.utils.JSFUtils;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
public class LoginHandler implements Serializable {
	private String username;

	public String getUsername() {
		return JSFUtils.getUserName();
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isAdmin() {
		return JSFUtils.isUserInRole("admin");
	}

	public boolean isUser() {
		return JSFUtils.isUserInRole("user");
	}
}
