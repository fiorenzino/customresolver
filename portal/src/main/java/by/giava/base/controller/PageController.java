package by.giava.base.controller;

import it.coopservice.commons2.annotations.BackPage;
import it.coopservice.commons2.annotations.EditPage;
import it.coopservice.commons2.annotations.ListPage;
import it.coopservice.commons2.annotations.OwnRepository;
import it.coopservice.commons2.annotations.ViewPage;
import it.coopservice.commons2.controllers.AbstractLazyController;
import it.coopservice.commons2.utils.JSFUtils;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;

import by.giava.base.controller.util.PageUtils;
import by.giava.base.model.OperazioniLog;
import by.giava.base.model.Page;
import by.giava.base.model.Template;
import by.giava.base.repository.PageRepository;
import by.giava.base.repository.TemplateRepository;

@Named
@SessionScoped
public class PageController extends AbstractLazyController<Page> {

	private static final long serialVersionUID = 1L;

	// --------------------------------------------------------
	@BackPage
	public static String BACK = "/private/amministrazione.xhtml";
	@ViewPage
	public static String VIEW = "/private/pagine/scheda-pagina.xhtml";
	@ListPage
	public static String LIST = "/private/pagine/lista-pagine.xhtml";
	@EditPage
	public static String NEW_OR_EDIT = "/private/pagine/gestione-pagina.xhtml";

	// ------------------------------------------------

	protected Logger logger = Logger.getLogger(getClass());

	// --------------------------------------------------------

	@Inject
	@OwnRepository(PageRepository.class)
	PageRepository pageRepository;

	@Inject
	PropertiesHandler propertiesHandler;

	@Inject
	TemplateRepository templateRepository;

	@Inject
	OperazioniLogController operazioniLogController;

	// --------------------------------------------------------

	// ------------------------------------------------

	/**
	 * Obbligatoria l'invocazione 'appropriata' di questo super construttore
	 * protetto da parte delle sottoclassi
	 */
	public PageController() {
	}

	@Override
	public String reset() {
		propertiesHandler.setPageItems(null);
		return super.reset();
	}

	// -----------------------------------------------------

	public String addPaginaStatica() {
		setElement(new Page());
		getElement().getTemplate().getTemplate().setStatico(true);
		return editPage();
	}

	public String addPaginaDinamica() {
		setElement(new Page());
		getElement().getTemplate().getTemplate().setStatico(false);
		return editPage();
	}

	// -----------------------------------------------------

	public String save() {
		operazioniLogController.save(OperazioniLog.NEW, JSFUtils.getUserName(),
				"creazione nuova pagina: " + getElement().getTitle());
		return super.save();
	}

	@Override
	public String update() {

		super.update();
		// refresh locale
		Page result = pageRepository.fetch(getElement().getId());
		if (result != null) {
			refreshModel();
			// altre dipendenze
			propertiesHandler.setPageItems(null);
			// vista di destinzione
			operazioniLogController.save(OperazioniLog.MODIFY,
					JSFUtils.getUserName(), "modifica pagina: "
							+ getElement().getTitle());
			return viewPage();
		} else {
			// messaggio di errore
			FacesContext
					.getCurrentInstance()
					.addMessage(
							"titolo",
							new FacesMessage(
									"Attenzione: titolo di pagina non valido o gia' utilizzato!"));
			// vista di destinzione
			return null;
		}
	}

	@Override
	public String delete() {
		operazioniLogController.save(OperazioniLog.DELETE,
				JSFUtils.getUserName(), "eliminazione pagina: "
						+ getElement().getTitle());
		return super.delete();
	}

	// -----------------------------------------------------

	public void cambioTemplate(ActionEvent event) {
		// Long id = getElement().getTemplate().getTemplate().getId();
		Template template = templateRepository.find(getElement().getTemplate()
				.getTemplate().getId());
		getElement().getTemplate().setTemplate(template);
	}

	public String anteprimaTestuale() {
		PageUtils.generateContent(getElement());
		return "/private/pagine/anteprima-testuale.xhtml";
	}

	/**
	 * Necessario salvare per l'anteprima, ma se ridirigessi all'uscita di
	 * questo metodo e non in outputLink causerei la morte di hibernate in caso
	 * di errori nel parser facelet
	 * 
	 * @return
	 */
	public String salvaPerAnteprimaRisultato() {
		if (this.getElement().getId() == null)
			save();
		else
			update();
		return editPage();
	}

}