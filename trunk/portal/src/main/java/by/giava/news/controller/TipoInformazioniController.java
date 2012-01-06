package by.giava.news.controller;

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

import by.giava.base.controller.OperazioniLogController;
import by.giava.base.controller.PropertiesHandler;
import by.giava.base.model.OperazioniLog;
import by.giava.news.repository.TipoInformazioniRepository;
import by.giava.notizie.model.type.TipoInformazione;

@Named
@SessionScoped
public class TipoInformazioniController extends
		AbstractLazyController<TipoInformazione> {

	private static final long serialVersionUID = 1L;

	// --------------------------------------------------------

	@BackPage
	public static String BACK = "/private/amministrazione.xhtml";
	@ViewPage
	public static String VIEW = "/private/tipi-informazione/scheda-tipo-informazione.xhtml";
	@ListPage
	public static String LIST = "/private/tipi-informazione/lista-tipi-informazione.xhtml";
	@EditPage
	public static String NEW_OR_EDIT = "/private/tipi-informazione/gestione-tipo-informazione.xhtml";

	// --------------------------------------------------------

	@Inject
	@OwnRepository(TipoInformazioniRepository.class)
	TipoInformazioniRepository tipoInformazioniRepository;

	@Inject
	PropertiesHandler propertiesHandler;

	@Inject
	OperazioniLogController operazioniLogController;

	// ------------------------------------------------

	/**
	 * Obbligatoria l'invocazione 'appropriata' di questo super construttore
	 * protetto da parte delle sottoclassi
	 */
	public TipoInformazioniController() {

	}

	@Override
	public String reset() {
		propertiesHandler.setTipoInformazioneItems(null);
		return super.reset();
	}

	// ------------------------------------------------
	@Override
	public String save() {
		super.save();
		operazioniLogController.save(OperazioniLog.NEW, JSFUtils.getUserName(),
				"creazione nuovo tipo informazione: " + getElement().getNome());
		return viewPage();
	}

	@Override
	public String update() {
		super.update();
		operazioniLogController.save(OperazioniLog.MODIFY,
				JSFUtils.getUserName(), "modifica tipo informazione: "
						+ getElement().getNome());
		return viewPage();
	}

	@Override
	public String delete() {
		super.delete();
		operazioniLogController.save(OperazioniLog.DELETE,
				JSFUtils.getUserName(), "eliminazione tipo informazione: "
						+ getElement().getNome());
		return listPage();
	}

}