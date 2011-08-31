package it.jflower.base.web;

import it.jflower.base.par.Ricerca;
import it.jflower.base.session.SuperSession;
import it.jflower.base.web.model.LocalDataModel;

import java.io.Serializable;

import javax.faces.model.DataModel;

import org.apache.log4j.Logger;

public abstract class SuperHandler<T> 
implements Serializable {

	private static final long serialVersionUID = 1L;

	// ------------------------------------------------

	protected Logger logger = Logger.getLogger(getClass());

	// ------------------------------------------------

	private Ricerca<T> ricerca;
	private T element;
	private DataModel<T> model;

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
	protected SuperHandler(Class<T> clazz) {
		super();
		ricerca = new Ricerca<T>(clazz);
		gatherCriteria();
	}

	// ------------------------------------------------

	/**
	 * Metodo da implementare per assicurare che i criteri di ricerca
	 * contengano sempre tutti i vincoli desiderati (es: identità utente, selezioni esterne, ecc...)
	 */
	abstract protected void gatherCriteria();
	
	/**
	 * Metodo per ottenere l'id di ricerca
	 */
	abstract protected Object getId(T t);

	abstract protected SuperSession<T> getSession();
	
	public Ricerca<T> getRicerca() {
		return this.ricerca;
	}
	
	public DataModel<T> getModel() {
		if (model == null)
			refreshModel();
		return model;
	}

	public void setModel(DataModel<T> model) {
		this.model = model;
	}

	protected void refreshModel() {
		setModel( new LocalDataModel<T>(pageSize, ricerca, getSession()) );
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

	public T getElement() {
		return element;
	}

	public void setElement(T element) {
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

	@SuppressWarnings("unchecked")
	public String addElement() {
		// impostazioni locali
		// n.d.
		// settaggi nel super handler
		try {
			this.element = (T) ricerca.getOggetto().getClass().newInstance();
		} catch (Exception e) {
//			logger.error(e.getMessage());
			e.printStackTrace();
		}
		// vista di destinazione
		return editPage();
	}
	
	public String viewElement() {
		// fetch dei dati
		T t = (T) getModel().getRowData();
		t = getSession().fetch( getId(t) );
		// settaggi nel super handler
		this.element = t;
		// vista di destinazione
		return viewPage();
	}

	public String modElement() {
		// fetch dei dati;
		T t = (T) getModel().getRowData();
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