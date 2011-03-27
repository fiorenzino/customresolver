package it.jflower.cc.web;

import it.jflower.base.par.Ricerca;
import it.jflower.base.session.SuperSession;
import it.jflower.base.utils.FileUtils;
import it.jflower.base.utils.JSFUtils;
import it.jflower.base.utils.XlsCreator;
import it.jflower.base.web.model.LocalDataModel;
import it.jflower.base.web.model.LocalLazyDataModel;
import it.jflower.cc.par.OperazioniLog;
import it.jflower.cc.par.Pubblicazione;
import it.jflower.cc.par.XlsDoc;
import it.jflower.cc.par.attachment.Documento;
import it.jflower.cc.par.type.TipoPubblicazione;
import it.jflower.cc.session.PubblicazioniSession;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

	private Logger logger = LoggerFactory.getLogger(getClass());

	// ------------------------------------------------

	private Ricerca<Pubblicazione> ricerca;
	private Pubblicazione element;
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
		this.element = null;
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
		List<Pubblicazione> lista = (List<Pubblicazione>) getModel()
				.getWrappedData();
		XlsDoc file = XlsCreator.createPubblicazioniFile(lista,
				"log_" + data.getTime() + ".xls");
		return file;
	}
}
