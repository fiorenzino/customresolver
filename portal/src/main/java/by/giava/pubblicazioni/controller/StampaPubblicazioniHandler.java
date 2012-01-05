package by.giava.pubblicazioni.controller;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;

import by.giava.base.common.ejb.SuperSession;
import by.giava.base.common.util.XlsCreator;
import by.giava.base.common.web.datamodel.LocalLazyDataModel;
import by.giava.base.controller.OperazioniLogHandler;
import by.giava.base.controller.PropertiesHandler;
import by.giava.base.controller.request.ParamsHandler;
import by.giava.base.model.Ricerca;
import by.giava.pubblicazioni.model.Pubblicazione;
import by.giava.pubblicazioni.model.XlsDoc;
import by.giava.pubblicazioni.model.type.TipoPubblicazione;
import by.giava.pubblicazioni.repository.PubblicazioniSession;


@Named
@SessionScoped
public class StampaPubblicazioniHandler implements Serializable {

	private static final long serialVersionUID = 1L;

	// --------------------------------------------------------

	private static String FACES_REDIRECT = "?faces-redirect=true";

	public static String BACK = "/private/amministrazione.xhtml"
			+ FACES_REDIRECT;
	public static String VIEW = "/private/pubblicazioni/scheda-pubblicazione.xhtml"
			+ FACES_REDIRECT;
	public static String LIST = "/private/pubblicazioni/lista-pubblicazioni.xhtml"
			+ FACES_REDIRECT;
	public static String NEW_OR_EDIT = "/private/pubblicazioni/gestione-pubblicazione.xhtml"
			+ FACES_REDIRECT;
	public static String PRINT_LIST = "/private/pubblicazioni/stampa-pubblicazioni.xhtml"
			+ FACES_REDIRECT;

	// --------------------------------------------------------

	@Inject
	PubblicazioniSession session;

	@Inject
	PropertiesHandler propertiesHandler;

	@Inject
	ParamsHandler paramsHandler;

	@Inject
	OperazioniLogHandler operazioniLogHandler;

	// ------------------------------------------------

	protected Logger logger = Logger.getLogger(getClass());

	// ------------------------------------------------

	private Ricerca<Pubblicazione> ricerca;
	private DataModel<Pubblicazione> model;

	private int rowCount;
	private int pageSize = 10;
	private int rowsPerPage = 10;
	private int scrollerPage = 1;

	// ------------------------------------------------

	/**
	 * Obbligatoria l'invocazione 'appropriata' di questo super construttore
	 * protetto da parte delle sottoclassi
	 */
	public StampaPubblicazioniHandler() {
	}

	// ------------------------------------------------

	/**
	 * Metodo da implementare per assicurare che i criteri di ricerca contengano
	 * sempre tutti i vincoli desiderati (es: identità utente, selezioni
	 * esterne, ecc...)
	 */
	@PostConstruct
	protected void gatherCriteria() {
		ricerca = new Ricerca<Pubblicazione>(Pubblicazione.class);
		ricerca.getOggetto().setTipo(new TipoPubblicazione());
	}

	/**
	 * Metodo per ottenere l'id di ricerca
	 */
	protected Object getId(Pubblicazione t) {
		return t.getId();
	}

	public SuperSession<Pubblicazione> getSession() {
		return session;
	}

	public Ricerca<Pubblicazione> getRicerca() {
		return this.ricerca;
	}

	public DataModel<Pubblicazione> getModel() {
		if (model == null)
			refreshModel();
		return model;
	}

	public void setModel(DataModel<Pubblicazione> model) {
		this.model = model;
	}

	@SuppressWarnings("unchecked")
	protected void refreshModel() {
		setModel(new LocalLazyDataModel<Pubblicazione>(this.ricerca,
				this.session));
	}

	/**
	 * Metodo di navigazione per resettare lo stato interno e tornare alla
	 * pagina dell'elenco generale
	 * 
	 * @return
	 */
	public String reset() {
		this.model = null;
		return LIST;
	}

	public boolean getClear() {
		reset();
		return true;
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

	public String cerca() {
		refreshModel();
		return PRINT_LIST;
	}

	public XlsDoc export() {
		Date data = new Date();
		// List<Pubblicazione> lista = (List<Pubblicazione>) getModel()
		// .getWrappedData();
		List<Pubblicazione> lista = session.getList(ricerca, 0, 0);
		XlsDoc file = XlsCreator.createPubblicazioniFile(lista,
				"log_" + data.getTime() + ".xls");
		return file;
	}
}
