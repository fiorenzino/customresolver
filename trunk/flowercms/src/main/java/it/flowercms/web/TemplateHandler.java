package it.flowercms.web;

import it.flowercms.par.Template;
import it.flowercms.par.base.Ricerca;
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
public class TemplateHandler
implements Serializable {

	private static final long serialVersionUID = 1L;

	// --------------------------------------------------------

	public static String BACK = "/private/amministrazione.xhtml";
	public static String VIEW = "/private/modelli/scheda-modello.xhtml";
	public static String LIST = "/private/modelli/lista-modelli.xhtml";
	public static String NEW_OR_EDIT = "/private/modelli/gestione-modello.xhtml";

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

	private String backPage = null;

	// ------------------------------------------------
	
	/**
	 * Obbligatoria l'invocazione 'appropriata' di questo super construttore protetto
	 * da parte delle sottoclassi
	 */
	protected TemplateHandler(Class<Template> clazz) {
		super();
		ricerca = new Ricerca<Template>(clazz);
		gatherCriteria();
	}

	// ------------------------------------------------

	/**
	 * Metodo da implementare per assicurare che i criteri di ricerca
	 * contengano sempre tutti i vincoli desiderati (es: identità utente, selezioni esterne, ecc...)
	 */
	protected void gatherCriteria() { 
	} 
	
	/**
	 * Metodo per ottenere l'id di ricerca
	 */
	protected Object getId(Template t) { return t.getId(); }

	protected SuperSession<Template> getSession() { return session; }
	
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

	protected void refreshModel() {
		setModel( new LocalDataModel<Template>(pageSize, ricerca, getSession()) );
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

	public void backPage(String backPage) { this.backPage = backPage; }
	public String backPage() { return this.backPage; }

	public String viewPage() { return null; }
	public String listPage() { return null; }
	public String editPage() { return null; }
	
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
		// n.d.
		// settaggi nel super handler
		try {
			this.element = (Template) ricerca.getOggetto().getClass().newInstance();
		} catch (Exception e) {
//			logger.error(e.getMessage());
			e.printStackTrace();
		}
		// vista di destinazione
		return editPage();
	}
	
	public String viewElement() {
		// fetch dei dati
		Template t = (Template) getModel().getRowData();
		t = getSession().fetch( getId(t) );
		// settaggi nel super handler
		this.element = t;
		// vista di destinazione
		return viewPage();
	}

	public String modElement() {
		// fetch dei dati;
		Template t = (Template) getModel().getRowData();
		t = getSession().fetch( getId(t) );
		// settaggi nel super handler
		this.element = t;
		// vista di destinazione
		return editPage();
	}

	// -----------------------------------------------------
	
	public String save() {
		// recupero e preelaborazioni dati in input
			// nelle sottoclassi!! ovverride!
		// salvataggio
		element = getSession().persist(element);
		// refresh locale
		refreshModel();
		// vista di destinazione
		return viewPage();
	}

	public String update() {
		// recupero dati in input
			// nelle sottoclassi!! ovverride!
		// salvataggio
		getSession().update( element );
		// refresh locale
		element = getSession().fetch( getId(element) );
		refreshModel();
		// vista di destinzione
		return viewPage();
	}
	
	public String delete() {
		// operazione su db
		getSession().delete( getId(element) );
		// refresh locale
		refreshModel();
		// refresh super handler e altre dipendenze
		element = null ;
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