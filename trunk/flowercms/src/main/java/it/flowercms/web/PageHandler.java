package it.flowercms.web;

import it.flowercms.par.Page;
import it.flowercms.par.Template;
import it.flowercms.par.TemplateImpl;
import it.flowercms.par.base.Ricerca;
import it.flowercms.session.PageSession;
import it.flowercms.session.TemplateSession;
import it.flowercms.session.base.SuperSession;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.seamframework.tx.Transactional;

@Named
@SessionScoped
public class PageHandler implements Serializable {

	private static final long serialVersionUID = 1L;

	// --------------------------------------------------------

	private static String FACES_REDIRECT = "?faces-redirect=true";
	
	public static String BACK = "/private/amministrazione.xhtml"+FACES_REDIRECT;
	public static String VIEW = "/private/pagine/scheda-pagina.xhtml"+FACES_REDIRECT;
	public static String LIST = "/private/pagine/lista-pagine.xhtml"+FACES_REDIRECT;
	public static String NEW_OR_EDIT = "/private/pagine/gestione-pagina.xhtml"+FACES_REDIRECT;

	// ------------------------------------------------

	protected Logger logger = Logger.getLogger(getClass());

	// --------------------------------------------------------

	@Inject
	PageSession session;

	@Inject
	PropertiesHandler propertiesHandler;

	@Inject
	TemplateSession templateSession;

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

	protected void refreshModel() {
//		setModel(new LocalDataModel<Page>(pageSize, ricerca, getSession()));
		setModel( new ListDataModel<Page>( session.getAllList() ));
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

	public String addElement() {
		// impostazioni locali
		Page p = new Page();
		p.setTemplate(new TemplateImpl());
		p.getTemplate().setTemplate(new Template());
		// settaggi 
		this.element = p;
		// vista di destinazione
		return editPage();
	}

	public String viewElement() {
		// fetch dei dati
		Page t = (Page) getModel().getRowData();
		t = getSession().fetch(getId(t));
		// settaggi nel super handler
		this.element = t;
		// vista di destinazione
		return viewPage();
	}

	public String modElement() {
		// fetch dei dati;
		Page t = (Page) getModel().getRowData();
		t = getSession().fetch(getId(t));
		// settaggi nel super handler
		this.element = t;
		// vista di destinazione
		return editPage();
	}

	// -----------------------------------------------------

	@Transactional
	public String save() {
		// recupero e preelaborazioni dati in input
		Page p = new Page();
		p.setTemplate(new TemplateImpl());
		p.getTemplate().setTemplate(new Template());
		// salvataggio
		element = getSession().persist(element);
		// refresh locale
		refreshModel();
		// altre dipendenze
		propertiesHandler.setPageItems(null);
		// vista di destinazione
		return viewPage();
	}

	@Transactional
	public String update() {
		// recupero dati in input
		Page p = new Page();
		p.setTemplate(new TemplateImpl());
		p.getTemplate().setTemplate(new Template());
		// salvataggio
		getSession().update(element);
		// refresh locale
		element = getSession().fetch(getId(element));
		refreshModel();
		// altre dipendenze
		propertiesHandler.setPageItems(null);
		// vista di destinzione
		return viewPage();
	}

	@Transactional
	public String delete() {
		// operazione su db
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

	
//	public List<Page> getList() {
//		return session.getAllList();
//	}

	public String viewElement(Object id) {
//		for (Page t : session.getAllList() ) {
//			if ( t.getId().equals(id) ) {
//				this.element = t;
//				break;
//			}
//		}
		this.element = session.fetch(id);
		return viewPage();
	}

	public String modElement(Object id) {
//		for (Page t : session.getAllList() ) {
//			if ( t.getId().equals(id) ) {
//				this.element = t;
//				break;
//			}
//		}
		this.element = session.fetch(id);
		return editPage();
	}
	
}