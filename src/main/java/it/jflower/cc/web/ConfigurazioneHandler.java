package it.jflower.cc.web;

import it.jflower.base.utils.JSFUtils;
import it.jflower.cc.par.Configurazione;
import it.jflower.cc.par.OperazioniLog;
import it.jflower.cc.session.ConfigurazioneSession;
import it.jflower.cc.session.EmailSession;

import java.io.Serializable;
import java.util.Date;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;


@Named
@SessionScoped
public class ConfigurazioneHandler implements Serializable {

	private static final long serialVersionUID = 1L;

	// --------------------------------------------------------

	private static String FACES_REDIRECT = "?faces-redirect=true";

	public static String BACK = "/private/amministrazione.xhtml"
			+ FACES_REDIRECT;

	public static String CONF = "/private/configurazione/gestione-configurazione.xhtml"
			+ FACES_REDIRECT;
	// --------------------------------------------------------

	private Configurazione configurazione;

	private String emailTest;
	private String resultTest;

	@Inject
	EmailSession emailSession;

	@Inject
	ConfigurazioneSession configurazioneSession;

	@Inject
	OperazioniLogHandler operazioniLogHandler;

	private Logger logger = Logger.getLogger(ConfigurazioneHandler.class);

	public ConfigurazioneHandler() {

	}

	public Configurazione getConfigurazione() {
		if (configurazione == null) {
			configurazione = configurazioneSession.caricaConfigurazione();
		}
		return configurazione;
	}

	public void setConfigurazione(Configurazione configurazione) {
		this.configurazione = configurazione;
	}

	public String update() {
		configurazioneSession.update(configurazione);
		operazioniLogHandler.save(OperazioniLog.MODIFY, JSFUtils.getUserName(),
				"modifica configurazione: " + this.configurazione.getSmtp());
		this.resultTest = "";
		return CONF;
	}

	public String reset() {
		return BACK;
	}

	public String getEmailTest() {
		return emailTest;
	}

	public void setEmailTest(String emailTest) {
		this.emailTest = emailTest;
	}

	public void test() {
		String result = emailSession.sendEmail("noreply@colonnella.it",
				"email di test dal comune di colonnella",
				"email di test dal comune di colonnella: " + new Date(),
				new String[] { this.emailTest }, null,
				new String[] { "fiorenzino@gmail.com" }, null);
		setResultTest(result);
	}

	public String getResultTest() {
		return resultTest;
	}

	public void setResultTest(String resultTest) {
		this.resultTest = resultTest;
	}

}
