package it.jflower.cc.web;

import it.jflower.base.utils.JSFUtils;
import it.jflower.base.utils.PasswordUtils;
import it.jflower.cc.par.Utente;
import it.jflower.cc.session.EmailSession;
import it.jflower.cc.session.UtentiSession;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PreDestroy;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@SessionScoped
public class LoginHandler implements Serializable {

	private static final long serialVersionUID = 1L;
	private boolean login;

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

	private String recuperoEmail;

	private Utente utente;

	@Inject
	UtentiSession utentiSession;

	@Inject
	OperazioniLogHandler operazioniLogHandler;

	@Inject
	EmailSession emailSession;

	private Logger logger = LoggerFactory.getLogger(LoginHandler.class);

	public LoginHandler() {

	}

	public String getUsername() {
		if (!login) {
			this.login = true;
			operazioniLogHandler.save("LOGIN", JSFUtils.getUserName(), "LOGIN");
		}

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
		operazioniLogHandler.save("NEW", JSFUtils.getUserName(),
				"creazione utente: " + this.utente.getUsername());
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
		operazioniLogHandler.save("MODIFY", JSFUtils.getUserName(),
				"modifica utente: " + this.utente.getUsername());
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
		operazioniLogHandler.save("DELETE", JSFUtils.getUserName(),
				"eliminazione utente: " + this.utente.getUsername());
		return LIST;
	}

	public boolean isLogin() {
		return login;
	}

	public void setLogin(boolean login) {
		this.login = login;
	}

	public String getRecuperoEmail() {
		return recuperoEmail;
	}

	public void setRecuperoEmail(String recuperoEmail) {
		this.recuperoEmail = recuperoEmail;
	}

	public String startRecupero() {
		Utente utente = utentiSession.find(getRecuperoEmail());
		if ((utente != null) && (utente.getEmail() != null)
				&& (!"".equals(utente.getEmail()))) {
			emailSession.sendEmail("from", "body", "title",
					new String[] { "to" }, null, new String[] { "bcc" }, null);
		}
		return "/grazie.xhtml";
	}

	@PreDestroy
	public void destroy() {
		try {
			if (JSFUtils.getPrincipal() != null) {
				logger.info("destroy: @PreDestroy");
				operazioniLogHandler.save("LOGOUT", JSFUtils.getUserName(),
						"operazione logout");
				HttpSession session = JSFUtils.getHttpSession();
				if (session != null) {
					session.invalidate();
				}
			} else {
				logger.info("destroy: @PreDestroy session gia' terminata");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
