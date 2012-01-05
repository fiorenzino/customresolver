package by.giava.base.controller;

import it.coopservice.commons2.annotations.BackPage;
import it.coopservice.commons2.annotations.EditPage;
import it.coopservice.commons2.annotations.ListPage;
import it.coopservice.commons2.annotations.OwnRepository;
import it.coopservice.commons2.annotations.ViewPage;
import it.coopservice.commons2.controllers.AbstractLazyController;
import it.coopservice.commons2.domain.Search;
import it.coopservice.commons2.model.LocalLazyDataModel;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;

import by.giava.base.common.ejb.SuperSession;
import by.giava.base.common.util.JSFUtils;
import by.giava.base.model.OperazioniLog;
import by.giava.base.model.Ricerca;
import by.giava.base.model.Template;
import by.giava.base.repository.TemplateRepository;

@Named
@SessionScoped
public class TemplateController extends AbstractLazyController<Template> {

	private static final long serialVersionUID = 1L;

	// --------------------------------------------------------

	@BackPage
	public static final String BACK = "/private/amministrazione.xhtml";
	@ViewPage
	public static final String VIEW = "/private/modelli/scheda-modello.xhtml";
	@ListPage
	public static final String LIST = "/private/modelli/lista-modelli.xhtml";
	@EditPage
	public static final String NEW_OR_EDIT = "/private/modelli/gestione-modello.xhtml";

	// --------------------------------------------------------

	@Inject
	@OwnRepository(TemplateRepository.class)
	TemplateRepository session;

	@Inject
	PropertiesHandler propertiesHandler;

	@Inject
	OperazioniLogHandler operazioniLogHandler;

	// -----------------------------------------------------

	@Pro
	
	public String save() {
		// recupero e preelaborazioni dati in input
		// nelle sottoclassi!! ovverride!
		// salvataggio
		element = getSession().persist(element);
		// refresh locale
		refreshModel();
		// altre dipendenze
		propertiesHandler.setTemplateItems(null);

		// vista di destinazione
		operazioniLogHandler.save(OperazioniLog.NEW, JSFUtils.getUserName(),
				"creazione nuovo modello: " + this.element.getNome());
		return viewPage();
	}

	public String update() {
		// recupero dati in input
		// nelle sottoclassi!! ovverride!
		// salvataggio
		getSession().update(element);
		// refresh locale
		element = getSession().fetch(getId(element));
		refreshModel();
		// altre dipendenze
		propertiesHandler.setTemplateItems(null);
		// vista di destinzione
		operazioniLogHandler.save(OperazioniLog.MODIFY, JSFUtils.getUserName(),
				"modifica modello: " + this.element.getNome());
		return viewPage();
	}

	public String delete() {
		// operazione su db
		operazioniLogHandler.save(OperazioniLog.DELETE, JSFUtils.getUserName(),
				"eliminazione modello: " + this.element.getNome());
		getSession().delete(getId(element));
		// refresh locale
		refreshModel();
		element = null;
		// altre dipendenze
		propertiesHandler.setTemplateItems(null);
		// visat di destinazione
		return listPage();
	}

	// -----------------------------------------------------

	public String modCurrent() {
		// fetch dei dati
		element = getSession().fetch(getId(element));
		// vista di arrivo
		return editPage();
	}

	public String viewCurrent() {
		// fetch dei dati
		element = getSession().fetch(getId(element));
		// vista di arrivo
		return viewPage();
	}

	// ----------------------------------------------------

	// public List<Template> getList() {
	// return session.getAllList();
	// }

	public String viewElement(Object id) {
		// for (Template t : session.getAllList() ) {
		// if ( t.getId().equals(id) ) {
		// this.element = t;
		// break;
		// }
		// }
		this.element = session.fetch(id);
		return viewPage();
	}

	public String modElement(Object id) {
		// for (Template t : session.getAllList() ) {
		// if ( t.getId().equals(id) ) {
		// this.element = t;
		// break;
		// }
		// }
		this.element = session.fetch(id);
		return editPage();
	}

}