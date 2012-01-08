package by.giava.base.controller;

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

import by.giava.base.model.OperazioniLog;
import by.giava.base.model.Template;
import by.giava.base.producer.PropertiesController;
import by.giava.base.repository.TemplateRepository;

@Named
@SessionScoped
public class TemplateController extends AbstractLazyController<Template> {

	private static final long serialVersionUID = 1L;

	// --------------------------------------------------------

	@BackPage
	public static final String BACK = "/private/amministrazione.xhtml";
	@ViewPage
	public static final String VIEW = "/private/modelli/scheda.xhtml";
	@ListPage
	public static final String LIST = "/private/modelli/lista.xhtml";
	@EditPage
	public static final String NEW_OR_EDIT = "/private/modelli/gestione.xhtml";

	// --------------------------------------------------------

	@Inject
	@OwnRepository(TemplateRepository.class)
	TemplateRepository session;

	@Inject
	PropertiesController propertiesController;

	@Inject
	OperazioniLogController operazioniLogController;

	// -----------------------------------------------------

	public String save() {
		String outcome = super.save();
		propertiesController.resetItemsForClass(Template.class);
		// vista di destinazione
		operazioniLogController.save(OperazioniLog.NEW, JSFUtils.getUserName(),
				"creazione nuovo modello: " + getElement().getNome());
		return viewPage();
	}

	public String update() {
		// recupero dati in input
		// nelle sottoclassi!! ovverride!
		// salvataggio
		String outcome = super.update();
		// altre dipendenze
		propertiesController.resetItemsForClass(Template.class);
		// vista di destinzione
		operazioniLogController.save(OperazioniLog.MODIFY, JSFUtils.getUserName(),
				"modifica modello: " + getElement().getNome());
		return viewPage();
	}

	public String delete() {
		// operazione su db
		operazioniLogController.save(OperazioniLog.DELETE, JSFUtils.getUserName(),
				"eliminazione modello: " + getElement().getNome());
		String outcome = super.delete();
		// altre dipendenze
		propertiesController.resetItemsForClass(Template.class);
		// visat di destinazione
		return listPage();
	}

}