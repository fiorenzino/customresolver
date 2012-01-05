package by.giava.attivita.controller;

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

import by.giava.attivita.model.type.TipoAttivita;
import by.giava.attivita.repository.TipoAttivitaRepository;
import by.giava.base.controller.OperazioniLogController;
import by.giava.base.controller.PropertiesHandler;
import by.giava.base.model.OperazioniLog;

@Named
@SessionScoped
public class TipoAttivitaController extends
		AbstractLazyController<TipoAttivita> {

	// --------------------------------------------------------
	@BackPage
	public static String BACK = "/private/amministrazione.xhtml";

	@ListPage
	@ViewPage
	public static String LIST = "/private/attivita/lista-tipi-attivita.xhtml";
	@EditPage
	public static String NEW = "/private/attivita/gestione-tipi-attivita.xhtml";

	// --------------------------------------------------------

	private static final long serialVersionUID = 1L;

	@Inject
	@OwnRepository(TipoAttivitaRepository.class)
	TipoAttivitaRepository tipoAttivitaRepository;

	@Inject
	PropertiesHandler propertiesHandler;

	@Inject
	OperazioniLogController operazioniLogController;

	@Override
	public String reset() {
		aggTipi();
		return super.reset();
	}

	@Override
	public String save() {
		operazioniLogController.save(OperazioniLog.NEW, JSFUtils.getUserName(),
				"creazione attivita': " + getElement().getNome());
		return super.save();
	}

	@Override
	public String update() {
		operazioniLogController.save(OperazioniLog.MODIFY,
				JSFUtils.getUserName(), "modifica tipo attivita': "
						+ getElement().getNome());
		return super.update();
	}

	@Override
	public String delete() {
		operazioniLogController.save(OperazioniLog.DELETE,
				JSFUtils.getUserName(), "eliminazione tipo attivita': "
						+ getElement().getNome());
		return super.delete();
	}

	public void aggTipi() {
		propertiesHandler.resetTipiAttivitaItems();
	}

}
