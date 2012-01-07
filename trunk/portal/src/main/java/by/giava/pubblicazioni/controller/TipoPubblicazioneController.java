package by.giava.pubblicazioni.controller;

import it.coopservice.commons2.annotations.BackPage;
import it.coopservice.commons2.annotations.EditPage;
import it.coopservice.commons2.annotations.ListPage;
import it.coopservice.commons2.annotations.OwnRepository;
import it.coopservice.commons2.annotations.ViewPage;
import it.coopservice.commons2.controllers.AbstractLazyController;
import it.coopservice.commons2.utils.JSFUtils;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import by.giava.attivita.model.type.CategoriaAttivita;
import by.giava.base.controller.OperazioniLogController;
import by.giava.base.controller.PropertiesHandler;
import by.giava.base.model.OperazioniLog;
import by.giava.pubblicazioni.model.type.TipoPubblicazione;
import by.giava.pubblicazioni.repository.TipoPubblicazioneRepository;

@Named
@SessionScoped
public class TipoPubblicazioneController extends
		AbstractLazyController<TipoPubblicazione> {

	// --------------------------------------------------------
	@BackPage
	public static String BACK = "/private/amministrazione.xhtml";
	@ListPage
	@ViewPage
	public static String LIST = "/private/tipi-pubblicazione/lista.xhtml";
	@EditPage
	public static String NEW_OR_EDIT = "/private/tipi-pubblicazione/gestione.xhtml";

	// --------------------------------------------------------

	private static final long serialVersionUID = 1L;

	@Inject
	@OwnRepository(TipoPubblicazioneRepository.class)
	TipoPubblicazioneRepository tipoPubblicazioneRepository;

	@Inject
	PropertiesHandler propertiesHandler;

	@Inject
	OperazioniLogController operazioniLogController;

	@Override
	public String save() {
		operazioniLogController.save(OperazioniLog.NEW, JSFUtils.getUserName(),
				"creazione tipo pubblicazione': " + getElement().getNome());
		return super.save();
	}

	@Override
	public String update() {
		operazioniLogController.save(OperazioniLog.MODIFY,
				JSFUtils.getUserName(), "modifica tipo pubblicazione': "
						+ getElement().getNome());
		return super.update();
	}

	@Override
	public String delete() {
		operazioniLogController.save(OperazioniLog.DELETE,
				JSFUtils.getUserName(), "eliminazione tipo pubblicazione': "
						+ getElement().getNome());
		return super.delete();
	}

}