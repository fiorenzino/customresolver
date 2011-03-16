package it.jflower.cc.web;

import it.jflower.cc.par.Configurazione;
import it.jflower.cc.session.ConfigurazioneSession;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@SessionScoped
public class ConfigurazioneHandler implements Serializable {

	private static final long serialVersionUID = 1L;

	// --------------------------------------------------------

	private static String FACES_REDIRECT = "?faces-redirect=true";

	public static String BACK = "/private/amministrazione.xhtml"
			+ FACES_REDIRECT;
	// --------------------------------------------------------

	private Configurazione configurazione;

	@Inject
	ConfigurazioneSession configurazioneSession;

	protected Logger logger = LoggerFactory.getLogger(ConfigurazioneHandler.class);

	public ConfigurazioneHandler() {

	}

	public Configurazione getConfigurazione() {
		if ( configurazione == null ) {
			configurazione = configurazioneSession.caricaConfigurazione();
		}
		return configurazione;
	}

	public void setConfigurazione(Configurazione configurazione) {
		this.configurazione = configurazione;
	}

	public String update() {
		configurazioneSession.update(configurazione);
		return BACK;
	}

	public String reset() {
		return BACK;
	}

}
