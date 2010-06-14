package it.jflower.cc.web;

import it.jflower.base.par.Ricerca;
import it.jflower.base.session.SuperSession;
import it.jflower.base.utils.FileUtils;
import it.jflower.base.utils.JSFUtils;
import it.jflower.base.web.model.LocalDataModel;
import it.jflower.base.web.model.LocalLazyDataModel;
import it.jflower.cc.par.Modulo;
import it.jflower.cc.par.attachment.Documento;
import it.jflower.cc.par.type.TipoModulo;
import it.jflower.cc.session.ModuliSession;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.primefaces.event.FileUploadEvent;

@Named
@SessionScoped
public class ModuliHandler implements Serializable {

	private static final long serialVersionUID = 1L;

	// --------------------------------------------------------

	private static String FACES_REDIRECT = "?faces-redirect=true";

	public static String BACK = "/private/amministrazione.xhtml"
			+ FACES_REDIRECT;
	public static String VIEW = "/private/moduli/scheda-modulo.xhtml"
			+ FACES_REDIRECT;
	public static String LIST = "/private/moduli/lista-moduli.xhtml"
			+ FACES_REDIRECT;
	public static String NEW_OR_EDIT = "/private/moduli/gestione-modulo.xhtml"
			+ FACES_REDIRECT;

	// --------------------------------------------------------

	@Inject
	ModuliSession session;

	@Inject
	PropertiesHandler propertiesHandler;

	@Inject
	ParamsHandler paramsHandler;
	
	@Inject
	OperazioniLogHandler operazioniLogHandler;

	// ------------------------------------------------

	protected Logger logger = Logger.getLogger(getClass());

	// ------------------------------------------------

	private Ricerca<Modulo> ricerca;
	private Modulo element;
	private DataModel<Modulo> model;

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
	public ModuliHandler() {
	}

	// ------------------------------------------------

	/**
	 * Metodo da implementare per assicurare che i criteri di ricerca contengano
	 * sempre tutti i vincoli desiderati (es: identità utente, selezioni
	 * esterne, ecc...)
	 */
	@PostConstruct
	protected void gatherCriteria() {
		ricerca = new Ricerca<Modulo>(Modulo.class);
		ricerca.getOggetto().setTipo(new TipoModulo());
	}

	/**
	 * Metodo per ottenere l'id di ricerca
	 */
	protected Object getId(Modulo t) {
		return t.getId();
	}

	public SuperSession<Modulo> getSession() {
		return session;
	}

	public Ricerca<Modulo> getRicerca() {
		return this.ricerca;
	}

	public DataModel<Modulo> getModel() {
		if (model == null)
			refreshModel();
		return model;
	}

	public void setModel(DataModel<Modulo> model) {
		this.model = model;
	}

	@SuppressWarnings("unchecked")
	protected void refreshModel() {
		setModel(new LocalLazyDataModel(this.ricerca, this.session));
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

	public Modulo getElement() {
		return element;
	}

	public void setElement(Modulo element) {
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
			this.element = new Modulo();
			this.element.setTipo(new TipoModulo());
			this.element.setDocumento(new Documento());
		} catch (Exception e) {
			// logger.error(e.getMessage());
			e.printStackTrace();
		}
		// vista di destinazione
		return editPage();
	}

	public String viewElement() {
		// fetch dei dati
		Modulo t = (Modulo) getModel().getRowData();
		t = getSession().fetch(getId(t));
		if (t.getDocumento() == null)
			t.setDocumento(new Documento());
		// settaggi nel super handler
		this.element = t;
		// vista di destinazione
		return viewPage();
	}

	public String modElement() {
		// fetch dei dati;
		Modulo t = (Modulo) getModel().getRowData();
		t = getSession().fetch(getId(t));
		if (t.getDocumento() == null)
			t.setDocumento(new Documento());
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
		Modulo t = getSession().persist(element);
		// refresh locale
		refreshModel();
		element = t;
		// vista di destinazione
		operazioniLogHandler.save("NEW", JSFUtils.getUserName(),
				"eliminazione moduli: " + this.element.getNome());
		return viewPage();
	}

	public String update() {
		// recupero dati in input
		// nelle sottoclassi!! ovverride!
		// salvataggio
		Modulo t = getSession().update(element);
		// refresh locale
		element = t;
		refreshModel();
		// vista di destinzione
		operazioniLogHandler.save("MODIFY", JSFUtils.getUserName(),
				"modifica moduli: " + this.element.getNome());
		return viewPage();
	}

	public String delete() {
		// operazione su db
		getSession().delete(getId(element));
		// refresh locale
		refreshModel();
		element = null;
		// visat di destinazione
		operazioniLogHandler.save("DELETE", JSFUtils.getUserName(),
				"eliminazione moduli: " + this.element.getNome());
		return listPage();
	}

	// -----------------------------------------------------

	public String modCurrent() {
		// fetch dei dati
		element = getSession().fetch(getId(element));
		if (element.getDocumento() == null)
			element.setDocumento(new Documento());
		// vista di arrivo
		return editPage();
	}

	public String viewCurrent() {
		// fetch dei dati
		element = getSession().fetch(getId(element));
		if (element.getDocumento() == null)
			element.setDocumento(new Documento());
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
		if (element.getDocumento() == null)
			element.setDocumento(new Documento());
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
		if (element.getDocumento() == null)
			element.setDocumento(new Documento());
		return editPage();
	}

	public void handleFileUpload(FileUploadEvent event) {
		logger.info("Uploaded: {} " + event.getFile().getFileName());
		logger.info("Uploaded: {}" + event.getFile().getContentType());
		logger.info("Uploaded: {}" + event.getFile().getSize());

		this.element.getDocumento().setUploadedData(event.getFile());
		this.element.getDocumento().setData(event.getFile().getContents());
		this.element.getDocumento().setType(event.getFile().getContentType());
		String filename = FileUtils.createFile_("docs", event.getFile()
				.getFileName(), event.getFile().getContents());
		this.element.getDocumento().setFilename(filename);
	}

	public String removeDocument() {
		this.element.setDocumento(new Documento());
		return null;
	}

	public String cerca() {
		refreshModel();
		return null;
	}

	// -----------------------------------------------------------

	private LocalDataModel<Modulo> modulisticaModel;
	private String modulisticaTipo;
	private Integer modulisticaPageSize;

	public LocalDataModel<Modulo> modulistica(int pageSize) {
		return modulistica(null, pageSize);
	}

	public LocalDataModel<Modulo> modulistica(String tipo, int pageSize) {
		leggiParams();
		if (modulisticaModel == null || this.modulisticaTipo == null
				|| this.modulisticaPageSize == null
				|| !this.modulisticaTipo.equals(tipo)
				|| this.modulisticaPageSize != pageSize) {
			Ricerca<Modulo> ricerca = new Ricerca<Modulo>(Modulo.class);
			ricerca.getOggetto().setTipo(new TipoModulo());
			ricerca.getOggetto().getTipo().setNome(tipo);
			modulisticaModel = new LocalDataModel<Modulo>(pageSize, ricerca,
					session);
		}
		return modulisticaModel;
	}

	private void leggiParams() {
		String id = paramsHandler.getParam("id");
		logger.info("TROVATO ID: " + id);
	}
	// -----------------------------------------------------------

}