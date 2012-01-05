package by.giava.base.controller;

import it.coopservice.commons2.utils.JSFUtils;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PreDestroy;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.jboss.logging.Logger;

import by.giava.base.common.util.EmailUtils;
import by.giava.base.common.util.JSFLocalUtils;
import by.giava.base.common.util.PasswordUtils;
import by.giava.base.model.OperazioniLog;
import by.giava.base.model.Utente;
import by.giava.base.repository.UtentiSession;
import by.giava.base.service.EmailSession;

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
	public static String CAMBIO_PASSWORD = "/private/utenti/cambio-password.xhtml"
			+ FACES_REDIRECT;

	public static String GRAZIE = "/grazie.xhtml" + FACES_REDIRECT;

	public static String LOGOUT = "/logout.jsp";

	// --------------------------------------------------------

	private boolean modifica;

	private String recuperoEmail;

	private Utente utente;

	@Inject
	UtentiSession utentiSession;

	@Inject
	OperazioniLogController operazioniLogController;

	@Inject
	EmailSession emailSession;

	private Logger logger = Logger.getLogger(LoginHandler.class);

	public LoginHandler() {

	}

	public String getUsername() {
		if (!login) {
			this.login = true;
			operazioniLogController.save("LOGIN", JSFUtils.getUserName(),
					"LOGIN");
		}
		return JSFUtils.getUserName();
	}

	// public void setUsername(String username) {
	// this.username = username;
	// }

	public boolean isAdmin() {
		return JSFUtils.isUserInRole("admin");
	}

	public boolean isUser() {
		return JSFUtils.isUserInRole("user");
	}

	public boolean isInRole(String role) {
		return isAdmin() || JSFUtils.isUserInRole(role);
	}

	public List<Utente> getList() {
		return utentiSession.getList();
	}

	public String addUser() {
		modifica = false;
		this.utente = new Utente();
		this.utente.setRandom(true);
		return NEW_OR_EDIT;
	}

	public String save() {
		if (utentiSession.find(this.utente.getUsername()) != null) {
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage("Nome utente non disponibile"));
			return null;
		}
		if (!EmailUtils.isValidEmailAddress(this.utente.getUsername())) {
			FacesContext
					.getCurrentInstance()
					.addMessage(
							"",
							new FacesMessage("Nome utente non valido",
									"Il nome utente deve essere costituito da un indirizzo email valido"));
			return null;
		}
		boolean condition = false;
		if (condition) {
			String pwd = this.utente.getPassword();
			this.utente.setPassword(PasswordUtils.createPassword(pwd));
		}
		if (this.utente.isAdmin()) {
			this.utente.setRoles(Arrays.asList("admin"));
		} else {
			this.utente.getRoles().add("user");
		}
		if (this.utente.isRandom()) {
			String newPassword = UUID.randomUUID().toString().substring(1, 8);
			this.utente.setPassword(newPassword);
			String title = "Generazione nuovo account portale Colonnella";
			String body = "La password dell'utente '" + utente.getUsername()
					+ "' è : " + newPassword;
			String result = emailSession.sendEmail("noreply@colonnella.it",
					body, title, new String[] { utente.getUsername() }, null,
					new String[] { "fiorenzino@gmail.com" }, null);
			logger.info(result);
		} else {
			if (this.utente.getNewPassword() == null
					|| this.utente.getNewPassword().isEmpty()) {
				FacesContext
						.getCurrentInstance()
						.addMessage(
								"",
								new FacesMessage("Password non inserita",
										"La password deve essere inserita obbligatoriamente!"));
				return null;
			} else {
				this.utente.setPassword(this.utente.getNewPassword());
			}
		}
		operazioniLogController.save(OperazioniLog.NEW, JSFUtils.getUserName(),
				"creazione utente: " + this.utente.getUsername());
		this.utente = utentiSession.save(this.utente);
		return LIST;
	}

	public String modUser(String username) {
		modifica = true;
		this.utente = utentiSession.find(username);
		// this.utente.setRandom(true);
		// if (this.utente.getPassword() != null
		// && !this.utente.getPassword().isEmpty()) {
		// this.utente.setRandom(false);
		// }
		return NEW_OR_EDIT;
	}

	public String detail(String username) {
		modifica = false;
		this.utente = utentiSession.find(username);
		return VIEW;
	}

	public String update() {
		if (!this.utente.isAdmin()
				&& !EmailUtils.isValidEmailAddress(this.utente.getUsername())) {
			FacesContext
					.getCurrentInstance()
					.addMessage(
							"",
							new FacesMessage("Nome utente non valido",
									"Il nome utente deve essere costituito da un indirizzo email valido"));
			return null;
		}
		if (this.utente.isAdmin()) {
			this.utente.setRoles(Arrays.asList("admin"));
		} else {
			this.utente.getRoles().add("user");
		}
		if (this.utente.isRandom()) {
			String newPassword = UUID.randomUUID().toString().substring(1, 8);
			this.utente.setPassword(newPassword);
			String title = "Accesso al portale del Comune di Colonnella: modifica password";
			String body = "La password dell'utente '" + utente.getUsername()
					+ "' è : " + newPassword;
			String result = emailSession.sendEmail("noreply@colonnella.it",
					body, title, new String[] { utente.getUsername() }, null,
					new String[] { "fiorenzino@gmail.com" }, null);
			logger.info(result);
		} else {
			if (this.utente.getNewPassword() != null
					&& !this.utente.getNewPassword().isEmpty()) {
				this.utente.setPassword(this.utente.getNewPassword());
			} else {
				logger.info("non e' stato richiesto un cambio password per account: "
						+ utente.getUsername());
			}
		}
		operazioniLogController.save("MODIFY", JSFUtils.getUserName(),
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
		modifica = false;
		return BACK;
	}

	public String delete() {
		operazioniLogController.save("DELETE", JSFUtils.getUserName(),
				"eliminazione utente: " + this.utente.getUsername());
		utentiSession.delete(this.utente.getUsername());
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
		if (utente == null) {
			FacesMessage message = new FacesMessage();
			message.setDetail("Nessun utente corrispondente all'indirizzo email fornito!");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			message.setSummary("Utente non presente");
			FacesContext.getCurrentInstance().addMessage("", message);
			return null;
		}

		if ((utente.getUsername() != null) && (!utente.getUsername().isEmpty())) {
			String newPassword = UUID.randomUUID().toString().substring(1, 8);
			// ("" + utente.toString().hashCode()).substring(
			// 1, 8);
			String title = "Richiesta modifica password";
			String body = "La nuova password dell'utente '"
					+ utente.getUsername() + "' è : " + newPassword;
			String result = emailSession.sendEmail("noreply@colonnella.it",
					body, title, new String[] { utente.getUsername() }, null,
					new String[] { "fiorenzino@gmail.com" }, null);
			if (result != null && !result.isEmpty()) {
				utente.setPassword(newPassword);
				utentiSession.update(utente);
				// operazioniLogHandler.save(
				// OperazioniLog.MODIFY,
				// "admin",
				// "richiesta nuova pwd utente: "
				// + this.utente.getUsername());
			}
		}
		return GRAZIE;
	}

	@PreDestroy
	public void destroy() {
		try {
			if (JSFLocalUtils.getPrincipal() != null) {
				logger.info("destroy: @PreDestroy");
				operazioniLogController.save(OperazioniLog.LOGOUT,
						JSFUtils.getUserName(), "operazione logout");
				HttpSession session = JSFLocalUtils.getHttpSession();
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

	public String logout() {
		operazioniLogController.save(OperazioniLog.LOGOUT,
				JSFUtils.getUserName(), "operazione logout");
		ExternalContext extCtx = FacesContext.getCurrentInstance()
				.getExternalContext();
		try {
			extCtx.redirect(extCtx.encodeActionURL(LOGOUT));
		} catch (Exception e) {

		}
		return null;
	}

	public String goToChangePassword() {
		this.utente = utentiSession.find(JSFUtils.getUserName());
		return CAMBIO_PASSWORD;
	}

	public String changePassword() {
		if (!utente.getOldPassword().equals(utente.getPassword())) {
			FacesMessage message = new FacesMessage();
			message.setDetail("La password corrente non e' corretta!");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			message.setSummary("Errore password corrente");
			FacesContext.getCurrentInstance().addMessage("pwd:opwd", message);
			return null;
		}
		if (utente.getNewPassword() == null
				|| utente.getNewPassword().length() == 0) {
			FacesMessage message = new FacesMessage();
			message.setDetail("La nuova password non e' stata inserita in entrambi i campi di testo!");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			message.setSummary("Errore nuova password");
			FacesContext.getCurrentInstance().addMessage("pwd:npwd", message);
			return null;
		}
		if (utente.getConfirmPassword() == null
				|| utente.getConfirmPassword().length() == 0) {
			FacesMessage message = new FacesMessage();
			message.setDetail("La nuova password non e' stata inserita in entrambi i campi di testo!");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			message.setSummary("Errore nuova password");
			FacesContext.getCurrentInstance().addMessage("pwd:cpwd", message);
			return null;
		}
		if (!utente.getNewPassword().equals(utente.getConfirmPassword())) {
			FacesMessage message = new FacesMessage();
			message.setDetail("Sono stati inseriti valori diversi nei due campi di testo relativi alla nuova password!");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			message.setSummary("Errore nuova password");
			FacesContext.getCurrentInstance().addMessage("pwd:cpwd", message);
			return null;
		}
		utente.setPassword(utente.getNewPassword());
		utentiSession.update(utente);
		operazioniLogController.save(OperazioniLog.MODIFY,
				JSFUtils.getUserName(),
				"cambio pwd utente: " + this.utente.getUsername());
		ExternalContext extCtx = FacesContext.getCurrentInstance()
				.getExternalContext();
		try {
			extCtx.redirect(extCtx.encodeActionURL(LOGOUT));
		} catch (Exception e) {
			FacesMessage message = new FacesMessage(
					"Errore generico, riprovare piu' tardi!");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			message.setSummary("Errore generico");
			FacesContext.getCurrentInstance().addMessage("pwd:cpwd", message);
			return null;
		}
		return null;
	}

	public boolean isModifica() {
		return modifica;
	}

	public void setModifica(boolean modifica) {
		this.modifica = modifica;
	}

	public void checkRoles(ComponentSystemEvent event) {

		String acl = "" + event.getComponent().getAttributes().get("roles");
		for (String a : acl.split(",")) {
			if ("ANY".equalsIgnoreCase(a)) {
				if (JSFUtils.getUserName() != null
						&& JSFUtils.getUserName().length() > 0) {
					return;
				}
			}
			if (isInRole(a.trim())) {
				return;
			}
		}
		try {
			// ExternalContext extCtx = FacesContext.getCurrentInstance()
			// .getExternalContext();
			// extCtx.redirect(extCtx.encodeActionURL(JSFUtils.getAbsolutePath()
			// + "/" + getRedirectUrl()));
			FacesContext context = FacesContext.getCurrentInstance();
			ConfigurableNavigationHandler handler = (ConfigurableNavigationHandler) context
					.getApplication().getNavigationHandler();
			handler.performNavigation("amministrazione");
		} catch (Exception e) {
			e.printStackTrace();
			// Se siamo qui il redirect è fallito.
			// A questo punto, piuttosto che lasciare andare l'utente dove
			// non deve.. runtime exception!
			throw new RuntimeException("Accesso non consentito");
		}
	}

}
