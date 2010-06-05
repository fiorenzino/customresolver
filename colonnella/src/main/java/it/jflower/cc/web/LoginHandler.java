package it.jflower.cc.web;

import it.jflower.base.utils.JSFUtils;
import it.jflower.base.utils.PasswordUtils;
import it.jflower.cc.par.Utente;
import it.jflower.cc.session.UtentiSession;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@SessionScoped
public class LoginHandler implements Serializable {

	// --------------------------------------------------------

	private static String FACES_REDIRECT = "?faces-redirect=true";

	public static String BACK = "/private/amministrazione.xhtml"
			+ FACES_REDIRECT;
	public static String VIEW = "/private/utenti/scheda-utente.xhtml"
			+ FACES_REDIRECT;
	public static String LIST = "/private/utenti/lista-utenti.xhtml"
			+ FACES_REDIRECT;
	public static String NEW_OR_EDIT = "/private/utenti/gestione-utente.xhtml"
			+ FACES_REDIRECT;

	// --------------------------------------------------------

	private String username;

	private Utente utente;

	@Inject
	UtentiSession utentiSession;

	public LoginHandler() {

	}

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

	public List<Utente> getList() {
		return utentiSession.getList();
		// return null;
	}

	public String addUser() {
		this.utente = new Utente();
		return NEW_OR_EDIT;
	}

	public String save() {
		boolean condition = false;
		if (condition) {
			String pwd = this.utente.getPassword();
			this.utente.setPassword(PasswordUtils.createPassword(pwd));
		}
		this.utente = utentiSession.save(this.utente);
		return LIST;
	}

	public String modUser(String username) {
		this.utente = utentiSession.find(username);
		return NEW_OR_EDIT;
	}

	public String detail(String username) {
		this.utente = utentiSession.find(username);
		return VIEW;
	}

	public String update() {
		this.utente = utentiSession.update(this.utente);
		return LIST;
	}

	public Utente getUtente() {
		return utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

	public String reset() {
		return LIST;
	}

	public String delete() {
		return LIST;
	}
}
