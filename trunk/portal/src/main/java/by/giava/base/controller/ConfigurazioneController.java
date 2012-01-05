package by.giava.base.controller;

import it.coopservice.commons2.annotations.BackPage;
import it.coopservice.commons2.annotations.EditPage;
import it.coopservice.commons2.annotations.OwnRepository;
import it.coopservice.commons2.controllers.AbstractLazyController;

import java.util.Date;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import by.giava.base.common.util.JSFUtils;
import by.giava.base.model.Configurazione;
import by.giava.base.model.OperazioniLog;
import by.giava.base.repository.ConfigurazioneRepository;
import by.giava.base.service.EmailSession;

@Named
@SessionScoped
public class ConfigurazioneController extends
		AbstractLazyController<Configurazione> {

	private static final long serialVersionUID = 1L;

	// --------------------------------------------------------

	@BackPage
	public static String BACK = "/private/amministrazione.xhtml";

	@EditPage
	public static String CONF = "/private/configurazione/gestione-configurazione.xhtml";
	// --------------------------------------------------------

	private String emailTest;
	private String resultTest;

	@Inject
	EmailSession emailSession;

	@Inject
	@OwnRepository(ConfigurazioneRepository.class)
	ConfigurazioneRepository configurazioneRepository;

	@Inject
	OperazioniLogController operazioniLogController;

	public ConfigurazioneController() {

	}

	@Override
	public Configurazione getElement() {
		if (super.getElement() == null)
			setElement(configurazioneRepository.caricaConfigurazione());
		return super.getElement();
	}

	@Override
	public String update() {
		super.update();
		operazioniLogController.save(OperazioniLog.MODIFY,
				JSFUtils.getUserName(), "modifica configurazione: "
						+ getElement().getSmtp());
		this.resultTest = "";
		return CONF;
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
