package by.giava.moduli.controller;

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
import by.giava.moduli.model.type.TipoModulo;
import by.giava.moduli.repository.TipoModuloRepository;

@Named
@SessionScoped
public class TipiModuloController extends AbstractLazyController<TipoModulo> {

	private static final long serialVersionUID = 1L;

	// --------------------------------------------------------
	@BackPage
	public static String BACK = "/private/amministrazione.xhtml";
	@ViewPage
	public static String VIEW = "/private/tipi-modulo/scheda-tipo-modulo.xhtml";
	@ListPage
	public static String LIST = "/private/tipi-modulo/lista-tipi-modulo.xhtml";
	@EditPage
	public static String NEW_OR_EDIT = "/private/tipi-modulo/gestione-tipi-modulo.xhtml";

	// --------------------------------------------------------

	@Inject
	@OwnRepository(TipoModuloRepository.class)
	TipoModuloRepository tipoModuloRepository;

	@Inject
	PropertiesHandler propertiesHandler;

	@Inject
	OperazioniLogController operazioniLogController;

	// ------------------------------------------------

	public TipiModuloController() {

	}

	@Override
	public String reset() {
		propertiesHandler.setTipiModuloItems(null);
		return super.reset();
	}

	@Override
	public String save() {

		// vista di destinazione
		operazioniLogController.save(OperazioniLog.NEW, JSFUtils.getUserName(),
				"creazione tipo modulo: " + getElement().getNome());
		return super.save();
	}

	@Override
	public String update() {
		operazioniLogController.save(OperazioniLog.MODIFY,
				JSFUtils.getUserName(), "modifica tipo modulo: "
						+ getElement().getNome());
		return super.update();
	}

	@Override
	public String delete() {
		// operazione su db
		operazioniLogController.save(OperazioniLog.DELETE,
				JSFUtils.getUserName(), "eliminazione tipo modulo: "
						+ getElement().getNome());

		return super.delete();
	}
}