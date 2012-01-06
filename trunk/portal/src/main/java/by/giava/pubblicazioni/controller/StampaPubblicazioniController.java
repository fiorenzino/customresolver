package by.giava.pubblicazioni.controller;

import it.coopservice.commons2.annotations.BackPage;
import it.coopservice.commons2.annotations.EditPage;
import it.coopservice.commons2.annotations.ListPage;
import it.coopservice.commons2.annotations.ViewPage;
import it.coopservice.commons2.controllers.AbstractLazyController;

import java.util.Date;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import by.giava.base.common.util.XlsCreator;
import by.giava.base.controller.OperazioniLogController;
import by.giava.base.controller.PropertiesHandler;
import by.giava.base.controller.request.ParamsHandler;
import by.giava.pubblicazioni.model.Pubblicazione;
import by.giava.pubblicazioni.model.XlsDoc;
import by.giava.pubblicazioni.repository.PubblicazioniRepository;

@Named
@SessionScoped
public class StampaPubblicazioniController extends
		AbstractLazyController<Pubblicazione> {

	private static final long serialVersionUID = 1L;

	// --------------------------------------------------------
	@BackPage
	public static String BACK = "/private/amministrazione.xhtml";
	@ViewPage
	public static String VIEW = "/private/pubblicazioni/scheda-pubblicazione.xhtml";
	@ListPage
	public static String LIST = "/private/pubblicazioni/lista-pubblicazioni.xhtml";
	@EditPage
	public static String NEW_OR_EDIT = "/private/pubblicazioni/gestione-pubblicazione.xhtml";
	public static String PRINT_LIST = "/private/pubblicazioni/stampa-pubblicazioni.xhtml";

	// --------------------------------------------------------

	@Inject
	PubblicazioniRepository pubblicazioniRepository;

	@Inject
	PropertiesHandler propertiesHandler;

	@Inject
	ParamsHandler paramsHandler;

	@Inject
	OperazioniLogController operazioniLogController;

	// ------------------------------------------------

	/**
	 * Obbligatoria l'invocazione 'appropriata' di questo super construttore
	 * protetto da parte delle sottoclassi
	 */
	public StampaPubblicazioniController() {
	}

	// ------------------------------------------------

	public String cerca() {
		refreshModel();
		return PRINT_LIST;
	}

	public XlsDoc export() {
		Date data = new Date();
		// List<Pubblicazione> lista = (List<Pubblicazione>) getModel()
		// .getWrappedData();
		List<Pubblicazione> lista = pubblicazioniRepository.getList(
				getSearch(), 0, 0);
		XlsDoc file = XlsCreator.createPubblicazioniFile(lista,
				"log_" + data.getTime() + ".xls");
		return file;
	}
}
