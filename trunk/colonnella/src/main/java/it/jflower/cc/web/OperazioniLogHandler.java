package it.jflower.cc.web;

import it.jflower.base.par.Ricerca;
import it.jflower.base.web.model.LocalLazyDataModel;
import it.jflower.cc.par.Notizia;
import it.jflower.cc.par.OperazioniLog;
import it.jflower.cc.session.OperazioniLogSession;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@SessionScoped
public class OperazioniLogHandler implements Serializable {

	private static final long serialVersionUID = 1L;

	// --------------------------------------------------------

	private static String FACES_REDIRECT = "?faces-redirect=true";

	public static String BACK = "/private/amministrazione.xhtml"
			+ FACES_REDIRECT;
	public static String VIEW = "/private/operazioni/scheda-operazione.xhtml"
			+ FACES_REDIRECT;
	public static String LIST = "/private/operazioni/lista-operazioni.xhtml"
			+ FACES_REDIRECT;

	// --------------------------------------------------------

	@Inject
	OperazioniLogSession operazioniLogSession;

	private OperazioniLog element;
	private Ricerca<OperazioniLog> ricerca;
	private boolean editMode;
	private DataModel<OperazioniLog> model;

	private int rowCount;
	private int pageSize = 10;
	private int rowsPerPage = 10;
	private int scrollerPage = 1;

	private Notizia evidenza;

	private String backPage = BACK;

	// --------------------------------------------------------

	public OperazioniLogHandler() {
	}

	// --------------------------------------------------------

	/**
	 * Metodo da implementare per assicurare che i criteri di ricerca contengano
	 * sempre tutti i vincoli desiderati (es: identità utente, selezioni
	 * esterne, ecc...)
	 */
	@PostConstruct
	protected void gatherCriteria() {
		ricerca = new Ricerca<OperazioniLog>(OperazioniLog.class);
	}

	// --------------------------------------------------------

	public Ricerca<OperazioniLog> getRicerca() {
		return this.ricerca;
	}

	public DataModel<OperazioniLog> getModel() {
		if (model == null)
			refreshModel();
		return model;
	}

	public void setModel(DataModel<OperazioniLog> model) {
		this.model = model;
	}

	@SuppressWarnings("unchecked")
	protected void refreshModel() {
		setModel(new LocalLazyDataModel(this.ricerca, this.operazioniLogSession));
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

	// -----------------------------------------------------

	public OperazioniLog getElement() {
		return element;
	}

	public void setElement(OperazioniLog element) {
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

	public String viewPage() {
		return VIEW;
	}

	public String listPage() {
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

	public String cerca() {
		refreshModel();
		return null;
	}

	// --------------------------------------------------------

	public void save(OperazioniLog operazioniLog) {
		operazioniLogSession.persist(operazioniLog);
		this.model = null;
	}

	public String detail(Long id) {
		this.element = operazioniLogSession.find(id);
		return VIEW;
	}

	public OperazioniLog getOperazioniLog() {
		return element;
	}

	public void setOperazioniLog(OperazioniLog operazioniLog) {
		this.element = operazioniLog;
	}
}
