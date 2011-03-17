package it.jflower.cc.web;

import it.jflower.base.par.Ricerca;
import it.jflower.base.session.SuperSession;
import it.jflower.base.utils.JSFUtils;
import it.jflower.base.web.model.LocalLazyDataModel;
import it.jflower.cc.par.OperazioniLog;
import it.jflower.cc.par.type.TipoModulo;
import it.jflower.cc.session.TipoModuloSession;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

@Named
@SessionScoped
public class TipiModuloHandler implements Serializable {

	private static final long serialVersionUID = 1L;

	// --------------------------------------------------------

	private static String FACES_REDIRECT = "?faces-redirect=true";

	public static String BACK = "/private/amministrazione.xhtml"
			+ FACES_REDIRECT;
	public static String VIEW = "/private/tipi-modulo/scheda-tipo-modulo.xhtml"
			+ FACES_REDIRECT;
	public static String LIST = "/private/tipi-modulo/lista-tipi-modulo.xhtml"
			+ FACES_REDIRECT;
	public static String NEW_OR_EDIT = "/private/tipi-modulo/gestione-tipi-modulo.xhtml"
			+ FACES_REDIRECT;

	// --------------------------------------------------------

	@Inject
	TipoModuloSession session;

	@Inject
	PropertiesHandler propertiesHandler;

	@Inject
	OperazioniLogHandler operazioniLogHandler;

	// ------------------------------------------------

	protected Logger logger = Logger.getLogger(getClass());

	// ------------------------------------------------

	private Ricerca<TipoModulo> ricerca;
	private TipoModulo element;
	private DataModel<TipoModulo> model;

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
	public TipiModuloHandler() {

	}

	// ------------------------------------------------

	/**
	 * Metodo da implementare per assicurare che i criteri di ricerca contengano
	 * sempre tutti i vincoli desiderati (es: identità utente, selezioni
	 * esterne, ecc...)
	 */
	@PostConstruct
	protected void gatherCriteria() {
		ricerca = new Ricerca<TipoModulo>(TipoModulo.class);
	}

	/**
	 * Metodo per ottenere l'id di ricerca
	 */
	protected Object getId(TipoModulo t) {
		return t.getId();
	}

	protected SuperSession<TipoModulo> getSession() {
		return session;
	}

	public Ricerca<TipoModulo> getRicerca() {
		return this.ricerca;
	}

	public DataModel<TipoModulo> getModel() {
		if (model == null)
			refreshModel();
		return model;
	}

	public void setModel(DataModel<TipoModulo> model) {
		this.model = model;
	}

	@SuppressWarnings("unchecked")
	protected void refreshModel() {
		// setModel(new LocalDataModel<TipoInformazione>(pageSize, ricerca,
		// getSession()));
		// setModel(new ListDataModel<TipoModulo>(session.getAllList()));
		setModel(new LocalLazyDataModel<TipoModulo>(this.ricerca, this.session));
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

	public TipoModulo getElement() {
		return element;
	}

	public void setElement(TipoModulo element) {
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
		// n.d.
		// settaggi nel super handler
		try {
			this.element = (TipoModulo) ricerca.getOggetto().getClass()
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
		TipoModulo t = (TipoModulo) getModel().getRowData();
		t = getSession().fetch(getId(t));
		// settaggi nel super handler
		this.element = t;
		// vista di destinazione
		return viewPage();
	}

	public String modElement() {
		// fetch dei dati;
		TipoModulo t = (TipoModulo) getModel().getRowData();
		t = getSession().fetch(getId(t));
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
		// altre dipendenze
		propertiesHandler.setTipiModuloItems(null);
		// vista di destinazione
		operazioniLogHandler.save(OperazioniLog.NEW, JSFUtils.getUserName(),
				"creazione tipo modulo: " + this.element.getNome());
		return LIST;
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
		propertiesHandler.setTipiModuloItems(null);
		// vista di destinzione
		operazioniLogHandler.save(OperazioniLog.MODIFY, JSFUtils.getUserName(),
				"modifica tipo modulo: " + this.element.getNome());
		return LIST;
	}

	public String delete() {
		// operazione su db
		getSession().delete(getId(element));
		// refresh locale
		refreshModel();
		element = null;
		// altre dipendenze
		propertiesHandler.setTipoInformazioneItems(null);
		// visat di destinazione
		operazioniLogHandler.save(OperazioniLog.DELETE, JSFUtils.getUserName(),
				"eliminazione tipo modulo: " + this.element.getNome());
		return LIST;
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

	// public List<TipoInformazione> getList() {
	// return session.getAllList();
	// }

	public String viewElement(Object id) {
		// for (TipoInformazione t : session.getAllList() ) {
		// if ( t.getId().equals(id) ) {
		// this.element = t;
		// break;
		// }
		// }
		this.element = session.fetch(id);
		return viewPage();
	}

	public String modElement(Object id) {
		// for (TipoInformazione t : session.getAllList() ) {
		// if ( t.getId().equals(id) ) {
		// this.element = t;
		// break;
		// }
		// }
		this.element = session.fetch(id);
		return editPage();
	}

}