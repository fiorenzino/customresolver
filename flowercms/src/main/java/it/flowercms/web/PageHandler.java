package it.flowercms.web;

import it.flowercms.par.Page;
import it.flowercms.par.Template;
import it.flowercms.par.TemplateImpl;
import it.flowercms.par.base.Ricerca;
import it.flowercms.session.PageSession;
import it.flowercms.session.TemplateSession;
import it.flowercms.session.base.SuperSession;
import it.flowercms.web.model.LocalDataModel;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

@Named
@SessionScoped
public class PageHandler
implements Serializable {

	private static final long serialVersionUID = 1L;

	// --------------------------------------------------------

	public static String BACK = "/private/amministrazione.xhtml";
	public static String VIEW = "/private/pagine/scheda-pagina.xhtml";
	public static String LIST = "/private/pagine/lista-pagine.xhtml";
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
	
	// --------------------------------------------------------

	private Ricerca<Page> ricerca;
	private Page element;
	private DataModel<Page> model;

	private int rowCount;
	private int pageSize = 10;
	private int rowsPerPage = 10;
	private int scrollerPage = 1;

	private String backPage = null;

	// ------------------------------------------------
	
	/**
	 * Obbligatoria l'invocazione 'appropriata' di questo super construttore protetto
	 * da parte delle sottoclassi
	 */
	protected PageHandler(Class<Page> clazz) {
		super();
		ricerca = new Ricerca<Page>(clazz);
		gatherCriteria();
	}

	// ------------------------------------------------

	/**
	 * Metodo da implementare per assicurare che i criteri di ricerca
	 * contengano sempre tutti i vincoli desiderati (es: identità utente, selezioni esterne, ecc...)
	 */
	protected void gatherCriteria() {
		ricerca.getOggetto().setTemplate( new TemplateImpl() );
		ricerca.getOggetto().getTemplate().setTemplate( new Template() );
	}
	
	/**
	 * Metodo per ottenere l'id di ricerca
	 */
	protected Object getId(Page t) { return t.getId(); }

	protected SuperSession<Page> getSession() { return session; }
	
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
		setModel( new LocalDataModel<Page>(pageSize, ricerca, getSession()) );
	}

	/**
	 * Metodo di navigazione per resettare lo stato interno e tornare alla pagina 
	 * dell'elenco generale
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

	public void backPage(String backPage) { this.backPage = backPage; }
	public String backPage() { return this.backPage == null ? BACK : this.backPage; }

	public String viewPage() { return VIEW; }
	public String listPage() { return LIST; }
	public String editPage() { return NEW_OR_EDIT; }
	
	// -----------------------------------------------------

	/**
	 * action che carica il modello dei dati, se inizialmente vuoto,
	 * tenendo conto dei vari criteri esterni
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
		p.setTemplate( new TemplateImpl() );
		p.getTemplate().setTemplate( new Template() );
		// settaggi nel super handler
		try {
			this.element = (Page) ricerca.getOggetto().getClass().newInstance();
		} catch (Exception e) {
//			logger.error(e.getMessage());
			e.printStackTrace();
		}
		// vista di destinazione
		return editPage();
	}
	
	public String viewElement() {
		// fetch dei dati
		Page t = (Page) getModel().getRowData();
		t = getSession().fetch( getId(t) );
		// settaggi nel super handler
		this.element = t;
		// vista di destinazione
		return viewPage();
	}

	public String modElement() {
		// fetch dei dati;
		Page t = (Page) getModel().getRowData();
		t = getSession().fetch( getId(t) );
		// settaggi nel super handler
		this.element = t;
		// vista di destinazione
		return editPage();
	}

	// -----------------------------------------------------
	
	public String save() {
		// recupero e preelaborazioni dati in input
		Page p = new Page();
		p.setTemplate( new TemplateImpl() );
		p.getTemplate().setTemplate( new Template() );
		// salvataggio
		element = getSession().persist(element);
		// refresh locale
		refreshModel();
		// altre dipendenze
		propertiesHandler.setPageItems(null);
		// vista di destinazione
		return viewPage();
	}

	public String update() {
		// recupero dati in input
		Page p = new Page();
		p.setTemplate( new TemplateImpl() );
		p.getTemplate().setTemplate( new Template() );
		// salvataggio
		getSession().update( element );
		// refresh locale
		element = getSession().fetch( getId(element) );
		refreshModel();
		// altre dipendenze
		propertiesHandler.setPageItems(null);
		// vista di destinzione
		return viewPage();
	}
	
	public String delete() {
		// operazione su db
		getSession().delete( getId(element) );
		// refresh locale
		refreshModel();
		element = null ;
		// altre dipendenze
		propertiesHandler.setPageItems(null);
		// visat di destinazione
		return listPage();
	}

	// -----------------------------------------------------

	public String modCurrent() {
		// fetch dei dati
		element = getSession().fetch( getId(element) );
		// vista di arrivo
		return editPage();
	}
	
	public String viewCurrent() {
		// fetch dei dati
		element = getSession().fetch( getId(element) );
		// vista di arrivo
		return viewPage();
	}
	
}