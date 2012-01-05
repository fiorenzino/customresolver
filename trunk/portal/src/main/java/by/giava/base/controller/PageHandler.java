package by.giava.base.controller;

import it.coopservice.commons2.annotations.BackPage;
import it.coopservice.commons2.annotations.EditPage;
import it.coopservice.commons2.annotations.ListPage;
import it.coopservice.commons2.annotations.ViewPage;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.DataModel;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;

import by.giava.base.common.ejb.SuperSession;
import by.giava.base.common.util.JSFUtils;
import by.giava.base.common.web.datamodel.LocalLazyDataModel;
import by.giava.base.controller.util.PageUtils;
import by.giava.base.model.OperazioniLog;
import by.giava.base.model.Page;
import by.giava.base.model.Ricerca;
import by.giava.base.model.Template;
import by.giava.base.model.TemplateImpl;
import by.giava.base.repository.PageSession;
import by.giava.base.repository.TemplateSession;

@Named
@SessionScoped
public class PageHandler implements Serializable {

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
	PageSession session;

	@Inject
	PropertiesHandler propertiesHandler;

	@Inject
	TemplateSession templateSession;

	@Inject
	OperazioniLogHandler operazioniLogHandler;

	private Long idTemplate;

	// --------------------------------------------------------

	private Ricerca<Page> ricerca;
	private Page element;
	private DataModel<Page> model;

	private int rowCount;
	private int pageSize = 10;
	private int rowsPerPage = 10;
	private int scrollerPage = 1;

	private String backPage = BACK;

	// ------------------------------------------------

	/**
	 * Obbligatoria l'invocazione 'appropriata' di questo super construttore
	 * protetto da parte delle sottoclassi
	 */
	public PageHandler() {
	}

	// ------------------------------------------------

	/**
	 * Metodo da implementare per assicurare che i criteri di ricerca contengano
	 * sempre tutti i vincoli desiderati (es: identità utente, selezioni
	 * esterne, ecc...)
	 */
	@PostConstruct
	protected void gatherCriteria() {
		ricerca = new Ricerca<Page>(Page.class);
		ricerca.getOggetto().setTemplate(new TemplateImpl());
		ricerca.getOggetto().getTemplate().setTemplate(new Template());
	}

	/**
	 * Metodo per ottenere l'id di ricerca
	 */
	protected Object getId(Page t) {
		return t.getId();
	}

	protected SuperSession<Page> getSession() {
		return session;
	}

	public Ricerca<Page> getRicerca() {
		return this.ricerca;
	}

	public DataModel<Page> getModel() {
		if (model == null)
			refreshModel();
		return model;
	}

	public void setModel(DataModel<Page> model) {
		this.model = model;
	}

	@SuppressWarnings("unchecked")
	protected void refreshModel() {
		// setModel(new LocalDataModel<Page>(pageSize, ricerca, getSession()));
		setModel(new LocalLazyDataModel<Page>(ricerca, session));
	}

	/**
	 * Metodo di navigazione per resettare lo stato interno e tornare alla
	 * pagina dell'elenco generale
	 * 
	 * @return
	 */
	public String reset() {
		this.element = null;
		this.model = null;
		return listPage();
	}

	// -----------------------------------------------------

	public Page getElement() {
		return element;
	}

	public void setElement(Page element) {
		this.element = element;
	}

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getRowsPerPage() {
		return rowsPerPage;
	}

	public void setRowsPerPage(int rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}

	public int getScrollerPage() {
		return scrollerPage;
	}

	public void setScrollerPage(int scrollerPage) {
		this.scrollerPage = scrollerPage;
	}

	// -----------------------------------------------------

	public void backPage(String backPage) {
		this.backPage = backPage;
	}

	public String backPage() {
		return this.backPage == null ? BACK : this.backPage;
	}

	public String viewPage() {
		return VIEW;
	}

	public String listPage() {
		return LIST;
	}

	public String editPage() {
		return NEW_OR_EDIT;
	}

	public boolean getClear() {
		reset();
		return true;
	}

	public String cerca() {
		refreshModel();
		return LIST;
	}

	// -----------------------------------------------------

	/**
	 * action che carica il modello dei dati, se inizialmente vuoto, tenendo
	 * conto dei vari criteri esterni
	 */
	public String loadModel() {
		gatherCriteria();
		refreshModel();
		return listPage();
	}

	// -----------------------------------------------------

	public String addPaginaStatica() {
		this.idTemplate = null;
		String rv = addElement();
		this.element.getTemplate().getTemplate().setStatico(true);
		return rv;
	}

	public String addPaginaDinamica() {
		this.idTemplate = null;
		String rv = addElement();
		this.element.getTemplate().getTemplate().setStatico(false);
		return rv;
	}

	public String addElement() {
		// impostazioni locali
		this.idTemplate = null;
		Page p = new Page();
		p.setTemplate(new TemplateImpl());
		p.getTemplate().setTemplate(new Template());
		// settaggi
		this.element = p;
		// vista di destinazione
		return editPage();
	}

	// -----------------------------------------------------

	public String save() {
		// recupero e preelaborazioni dati in input
		Template template = templateSession.find(getIdTemplate());
		element.getTemplate().setTemplate(template);
		// salvataggio
		element = getSession().persist(element);
		// refresh locale
		refreshModel();
		// altre dipendenze
		propertiesHandler.setPageItems(null);
		// vista di destinazione
		operazioniLogHandler.save(OperazioniLog.NEW, JSFUtils.getUserName(),
				"creazione nuova pagina: " + this.element.getTitle());
		return viewPage();
	}

	public String update() {
		// recupero dati in input
		Template template = templateSession.find(getIdTemplate());
		element.getTemplate().setTemplate(template);

		// salvataggio
		getSession().update(element);
		// refresh locale
		Page result = getSession().fetch(getId(element));
		if (result != null) {
			refreshModel();
			// altre dipendenze
			propertiesHandler.setPageItems(null);
			// vista di destinzione
			operazioniLogHandler.save(OperazioniLog.MODIFY,
					JSFUtils.getUserName(),
					"modifica pagina: " + this.element.getTitle());
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

	public String delete() {
		// operazione su db
		operazioniLogHandler.save(OperazioniLog.DELETE, JSFUtils.getUserName(),
				"eliminazione pagina: " + this.element.getTitle());
		getSession().delete(getId(element));
		// refresh locale
		refreshModel();
		element = null;
		// altre dipendenze
		propertiesHandler.setPageItems(null);
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

	// -----------------------------------------------------

	// public List<Page> getList() {
	// return session.getAllList();
	// }

	public String viewElement(Object id) {
		// for (Page t : session.getAllList() ) {
		// if ( t.getId().equals(id) ) {
		// this.element = t;
		// break;
		// }
		// }
		this.element = session.fetch(id);
		return viewPage();
	}

	public String modElement(Object id) {
		// for (Page t : session.getAllList() ) {
		// if ( t.getId().equals(id) ) {
		// this.element = t;
		// break;
		// }
		// }
		this.element = session.fetch(id);
		this.idTemplate = this.element.getTemplate().getTemplate().getId();
		return editPage();
	}

	public void cambioTemplate(ActionEvent event) {
		// Long id = this.element.getTemplate().getTemplate().getId();
		Template template = templateSession.find(getIdTemplate());
		this.element.getTemplate().setTemplate(template);
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

	public Long getIdTemplate() {
		return idTemplate;
	}

	public void setIdTemplate(Long idTemplate) {
		this.idTemplate = idTemplate;
	}
}