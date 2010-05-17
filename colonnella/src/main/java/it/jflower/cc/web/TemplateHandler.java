package it.jflower.cc.web;

import it.jflower.base.par.Ricerca;
import it.jflower.base.session.SuperSession;
import it.jflower.base.web.model.LocalLazyDataModel;
import it.jflower.cc.par.Template;
import it.jflower.cc.session.TemplateSession;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.seamframework.tx.Transactional;

@Named
@SessionScoped
public class TemplateHandler implements Serializable {

	private static final long serialVersionUID = 1L;

	// --------------------------------------------------------

	private static String FACES_REDIRECT = "?faces-redirect=true";

	public static String BACK = "/private/amministrazione.xhtml"+FACES_REDIRECT;
	public static String VIEW = "/private/modelli/scheda-modello.xhtml"+FACES_REDIRECT;
	public static String LIST = "/private/modelli/lista-modelli.xhtml"+FACES_REDIRECT;
	public static String NEW_OR_EDIT = "/private/modelli/gestione-modello.xhtml"+FACES_REDIRECT;

	// --------------------------------------------------------

	@Inject
	TemplateSession session;

	@Inject
	PropertiesHandler propertiesHandler;

	// ------------------------------------------------

	protected Logger logger = Logger.getLogger(getClass());

	// ------------------------------------------------

	private Ricerca<Template> ricerca;
	private Template element;
	private DataModel<Template> model;

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
	public TemplateHandler() {

	}

	// ------------------------------------------------

	/**
	 * Metodo da implementare per assicurare che i criteri di ricerca contengano
	 * sempre tutti i vincoli desiderati (es: identità utente, selezioni
	 * esterne, ecc...)
	 */
	@PostConstruct
	protected void gatherCriteria() {
		ricerca = new Ricerca<Template>(Template.class);
	}

	/**
	 * Metodo per ottenere l'id di ricerca
	 */
	protected Object getId(Template t) {
		return t.getId();
	}

	protected SuperSession<Template> getSession() {
		return session;
	}

	public Ricerca<Template> getRicerca() {
		return this.ricerca;
	}

	public DataModel<Template> getModel() {
		if (model == null)
			refreshModel();
		return model;
	}

	public void setModel(DataModel<Template> model) {
		this.model = model;
	}

	@SuppressWarnings("unchecked")
	protected void refreshModel() {
		setModel( new LocalLazyDataModel<Template>( this.ricerca, session ));
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

	public boolean getClear() {
		reset();
		return true;
	}
	
	public String cerca() {
		refreshModel();
		return null;
	}

	// -----------------------------------------------------

	public Template getElement() {
		return element;
	}

	public void setElement(Template element) {
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
		return this.backPage;
	}

	public String viewPage() { return VIEW; }
	public String listPage() { return LIST; }
	public String editPage() { return NEW_OR_EDIT; }

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
		// n.d.
		// settaggi nel super handler
		try {
			this.element = (Template) ricerca.getOggetto().getClass()
					.newInstance();
		} catch (Exception e) {
			// logger.error(e.getMessage());
			e.printStackTrace();
		}
		// vista di destinazione
		return editPage();
	}

	public String viewElement() {
		// fetch dei dati
		Template t = (Template) getModel().getRowData();
		t = getSession().fetch(getId(t));
		// settaggi nel super handler
		this.element = t;
		// vista di destinazione
		return viewPage();
	}

	public String modElement() {
		// fetch dei dati;
		Template t = (Template) getModel().getRowData();
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
		// nelle sottoclassi!! ovverride!
		// salvataggio
		element = getSession().persist(element);
		// refresh locale
		refreshModel();
		// altre dipendenze
		propertiesHandler.setTemplateItems(null);
		propertiesHandler.setTemplateItems(null);
		// vista di destinazione
		return viewPage();
	}

	@Transactional
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
	
//	public List<Template> getList() {
//		return session.getAllList();
//	}

	public String viewElement(Object id) {
//		for (Template t : session.getAllList() ) {
//			if ( t.getId().equals(id) ) {
//				this.element = t;
//				break;
//			}
//		}
		this.element = session.fetch(id);
		return viewPage();
	}

	public String modElement(Object id) {
//		for (Template t : session.getAllList() ) {
//			if ( t.getId().equals(id) ) {
//				this.element = t;
//				break;
//			}
//		}
		this.element = session.fetch(id);
		return editPage();
	}

}