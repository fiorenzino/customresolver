package by.giava.attivita.controller;

import it.coopservice.commons2.annotations.BackPage;
import it.coopservice.commons2.annotations.EditPage;
import it.coopservice.commons2.annotations.ListPage;
import it.coopservice.commons2.annotations.OwnRepository;
import it.coopservice.commons2.controllers.AbstractLazyController;
import it.coopservice.commons2.utils.JSFUtils;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import by.giava.attivita.model.type.CategoriaAttivita;
import by.giava.attivita.repository.CategorieAttivitaRepository;
import by.giava.base.controller.OperazioniLogController;
import by.giava.base.controller.PropertiesHandler;
import by.giava.base.model.OperazioniLog;

@Named
@SessionScoped
public class CategoriaAttivitaController extends
		AbstractLazyController<CategoriaAttivita> {

	// --------------------------------------------------------
	@BackPage
	public static String BACK = "/private/amministrazione.xhtml";
	@ListPage
	public static String LIST_CAT = "/private/attivita/lista-categorie-attivita.xhtml";
	@EditPage
	public static String NEW_OR_EDIT_CAT = "/private/attivita/gestione-categorie-attivita.xhtml";

	// --------------------------------------------------------

	private static final long serialVersionUID = 1L;

	@Inject
	@OwnRepository(CategorieAttivitaRepository.class)
	CategorieAttivitaRepository categorieAttivitaRepository;

	@Inject
	PropertiesHandler propertiesHandler;

	@Inject
	OperazioniLogController operazioniLogController;

	@Override
	public String save() {
		operazioniLogController.save(OperazioniLog.NEW, JSFUtils.getUserName(),
				"creazione categoria attivita'': " + getElement().getNome());
		return super.save();
	}

	@Override
	public String update() {
		operazioniLogController.save(OperazioniLog.MODIFY,
				JSFUtils.getUserName(), "modifica attivita': "
						+ getElement().getNome());
		return super.update();
	}

	@Override
	public String delete() {

		operazioniLogController.save(OperazioniLog.DELETE,
				JSFUtils.getUserName(), "eliminazione attivita': "
						+ getElement().getNome());
		return super.delete();
	}

}
