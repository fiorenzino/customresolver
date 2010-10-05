package it.jflower.cc.par;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Utente implements Serializable {

	private static final long serialVersionUID = 1L;

	private boolean nuovo = true;
	private String username;
	private String password;
	private List<String> roles;
	private String ruolo;
	private String email;

	public Utente() {
		// TODO Auto-generated constructor stub
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<String> getRoles() {
		if (this.roles == null)
			this.roles = new ArrayList<String>();
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public void addRole(String role) {
		getRoles().add(role);
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRuoli() {
		StringBuffer ruoli = new StringBuffer();
		for (String ruolo : getRoles()) {
			ruoli.append("," + ruolo);
		}
		return ruoli.toString().substring(1);
	}

	public String getRuolo() {
		return ruolo;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isNuovo() {
		return nuovo;
	}

	public void setNuovo(boolean nuovo) {
		this.nuovo = nuovo;
	}
	
	
}
