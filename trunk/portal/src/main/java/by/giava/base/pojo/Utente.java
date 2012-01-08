package by.giava.base.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Transient;

public class Utente implements Serializable {

	private static final long serialVersionUID = 1L;

	private boolean nuovo = true;
	private String username;
	private String password;
	private String oldPassword;
	private String newPassword;
	private String confirmPassword;
	private List<String> roles;
	private Boolean admin;
	private boolean random;

	// private String ruolo;

	// private String email;

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

	// public String getRuolo() {
	// return ruolo;
	// }
	//
	// public void setRuolo(String ruolo) {
	// this.ruolo = ruolo;
	// }

	// public String getEmail() {
	// return email;
	// }
	//
	// public void setEmail(String email) {
	// this.email = email;
	// }

	public boolean isNuovo() {
		return nuovo;
	}

	public void setNuovo(boolean nuovo) {
		this.nuovo = nuovo;
	}

	@Transient
	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	@Transient
	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	@Transient
	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	@Transient
	public boolean isAdmin() {
		if (admin == null) {
			admin = roles != null && roles.contains("admin");
		}
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	@Transient
	public boolean isRandom() {
		return random;
	}

	public void setRandom(boolean random) {
		this.random = random;
	}

}
