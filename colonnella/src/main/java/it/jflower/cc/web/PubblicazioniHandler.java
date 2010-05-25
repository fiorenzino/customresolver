package it.jflower.cc.web;

import it.jflower.base.par.Ricerca;
import it.jflower.base.session.SuperSession;
import it.jflower.base.utils.FileUtils;
import it.jflower.base.web.model.LocalLazyDataModel;
import it.jflower.cc.par.Pubblicazione;
import it.jflower.cc.par.attachment.Documento;
import it.jflower.cc.par.type.TipoPubblicazione;
import it.jflower.cc.session.PubblicazioniSession;

import java.io.Serializable;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@SessionScoped
public class PubblicazioniHandler implements Serializable {

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

	// --------------------------------------------------------

	@Inject
	PubblicazioniSession session;

	@Inject
	PropertiesHandler propertiesHandler;

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

	private String backPage = BACK;

	// ------------------------------------------------

	/**
	 * Obbligatoria l'invocazione 'appropriata' di questo super construttore
	 * protetto da parte delle sottoclassi
	 */
	public PubblicazioniHandler() {
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

	public Pubblicazione getElement() {
		return element;
	}

	public void setElement(Pubblicazione element) {
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
			this.element = new Pubblicazione();
			this.element.setTipo(new TipoPubblicazione());
			this.element.setDocumenti(new ArrayList<Documento>());
		} catch (Exception e) {
			// logger.error(e.getMessage());
			e.printStackTrace();
		}
		// vista di destinazione
		return editPage();
	}

	public String viewElement() {
		// fetch dei dati
		Pubblicazione t = (Pubblicazione) getModel().getRowData();
		t = getSession().fetch(getId(t));
		if (t.getDocumenti() == null)
			t.setDocumenti(new ArrayList<Documento>());
		// settaggi nel super handler
		this.element = t;
		// vista di destinazione
		return viewPage();
	}

	public String modElement() {
		// fetch dei dati;
		Pubblicazione t = (Pubblicazione) getModel().getRowData();
		t = getSession().fetch(getId(t));
		if (t.getDocumenti() == null)
			t.setDocumenti(new ArrayList<Documento>());
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
		Pubblicazione t = getSession().persist(element);
		// refresh locale
		refreshModel();
		element = t;
		// vista di destinazione
		return viewPage();
	}

	public String update() {
		// recupero dati in input
		// nelle sottoclassi!! ovverride!
		// salvataggio
		Pubblicazione t = getSession().update(element);
		// refresh locale
		element = t;
		refreshModel();
		// vista di destinzione
		return viewPage();
	}

	public String delete() {
		// operazione su db
		getSession().delete(getId(element));
		// refresh locale
		refreshModel();
		element = null;
		// visat di destinazione
		return listPage();
	}

	// -----------------------------------------------------

	public String modCurrent() {
		// fetch dei dati
		element = getSession().fetch(getId(element));
		if (element.getDocumenti() == null)
			element.setDocumenti(new ArrayList<Documento>());
		// vista di arrivo
		return editPage();
	}

	public String viewCurrent() {
		// fetch dei dati
		element = getSession().fetch(getId(element));
		if (element.getDocumenti() == null)
			element.setDocumenti(new ArrayList<Documento>());
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
		if (element.getDocumenti() == null)
			element.setDocumenti(new ArrayList<Documento>());
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
		if (element.getDocumenti() == null)
			element.setDocumenti(new ArrayList<Documento>());
		return editPage();
	}

	/*
	 * public String addPubblicazione() { this.pubblicazione = new
	 * Pubblicazione(); this.documenti = null; setEditMode(false); return
	 * NEW_OR_EDIT; }
	 * 
	 * public String savePubblicazione() {
	 * this.pubblicazione.setDataPubblicazione(new Date());
	 * this.pubblicazione.setDocumenti(getDocumenti()); this.pubblicazione =
	 * pubblicazioniSession.persist(this.pubblicazione); return VIEW; }
	 * 
	 * public String deletePubblicazione() {
	 * pubblicazioniSession.delete(this.pubblicazione.getId()); this.all = null;
	 * return LIST; }
	 * 
	 * public String modPubblicazione(Long id) { setEditMode(true);
	 * this.pubblicazione = pubblicazioniSession.find(id); this.documenti =
	 * this.pubblicazione.getDocumenti(); return NEW_OR_EDIT; }
	 * 
	 * public String updatePubblicazione() {
	 * this.pubblicazione.setDocumenti(getDocumenti()); this.pubblicazione =
	 * pubblicazioniSession.update(this.pubblicazione); return VIEW; }
	 * 
	 * public String detailPubblicazione(Long id) { this.pubblicazione =
	 * pubblicazioniSession.find(id); this.documenti =
	 * this.pubblicazione.getDocumenti(); return VIEW; }
	 * 
	 * public List<Documento> getDocumenti() { if (this.documenti == null)
	 * this.documenti = new ArrayList<Documento>(); return documenti; }
	 * 
	 * public void setDocumenti(List<Documento> documenti) { this.documenti =
	 * documenti; }
	 */
	public void handleFileUpload(FileUploadEvent event) {
		logger.info("Uploaded: {}", event.getFile().getFileName());
		logger.info("Uploaded: {}", event.getFile().getContentType());
		logger.info("Uploaded: {}", event.getFile().getSize());
		Documento doc = new Documento();
		doc.setUploadedData(event.getFile());
		doc.setData(event.getFile().getContents());
		doc.setType(event.getFile().getContentType());
		String filename = FileUtils.createFile_("docs", event.getFile()
				.getFileName(), event.getFile().getContents());
		doc.setFilename(filename);
		getElement().getDocumenti().add(doc);
	}

	public void removeDocument(int index) {
		getElement().getDocumenti().remove(index);
	}

	public String cerca() {
		refreshModel();
		return null;
	}
}
