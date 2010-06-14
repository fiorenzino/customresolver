package it.jflower.cc.web;

import it.jflower.base.par.Ricerca;
import it.jflower.base.session.SuperSession;
import it.jflower.base.utils.FileUtils;
import it.jflower.base.utils.JSFUtils;
import it.jflower.cc.par.Attivita;
import it.jflower.cc.par.Page;
import it.jflower.cc.par.Resource;
import it.jflower.cc.par.attachment.Immagine;
import it.jflower.cc.par.type.CategoriaAttivita;
import it.jflower.cc.par.type.TipoAttivita;
import it.jflower.cc.session.AttivitaSession;
import it.jflower.cc.session.CategorieSession;
import it.jflower.cc.session.ResourceSession;
import it.jflower.cc.utils.PageUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.LazyDataModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@SessionScoped
public class AttivitaHandler implements Serializable {

	private static final long serialVersionUID = 1L;

	// --------------------------------------------------------

	// ------------------------------------------------

	private static String FACES_REDIRECT = "?faces-redirect=true";

	public static String BACK = "/private/amministrazione.xhtml"
			+ FACES_REDIRECT;
	public static String VIEW = "/private/attivita/scheda-attivita.xhtml"
			+ FACES_REDIRECT;
	public static String LIST = "/private/attivita/lista-attivita.xhtml"
			+ FACES_REDIRECT;
	public static String NEW_OR_EDIT = "/private/attivita/gestione-attivita.xhtml"
			+ FACES_REDIRECT;

	// --------------------------------------------------------

	@Inject
	AttivitaSession attivitaSession;

	@Inject
	PropertiesHandler propertiesHandler;

	@Inject
	CategorieSession categorieSession;

	@Inject
	ResourceSession resourceSession;

	@Inject
	OperazioniLogHandler operazioniLogHandler;

	private Logger logger = LoggerFactory.getLogger(AttivitaHandler.class);

	private Ricerca<Attivita> ricerca;
	private boolean editMode;
	private Attivita element;
	private DataModel<Attivita> model;
	private int tipoId;
	private int catId;
	private Immagine immagine;

	private int rowCount;
	private int pageSize = 10;
	private int rowsPerPage = 10;
	private int scrollerPage = 1;

	private String backPage = BACK;

	public AttivitaHandler() {
	}

	/**
	 * Metodo da implementare per assicurare che i criteri di ricerca contengano
	 * sempre tutti i vincoli desiderati (es: identità utente, selezioni
	 * esterne, ecc...)
	 */
	@PostConstruct
	protected void gatherCriteria() {
		ricerca = new Ricerca<Attivita>(Attivita.class);
		ricerca.getOggetto().setCategoria(new CategoriaAttivita());
		ricerca.getOggetto().getCategoria().setTipoAttivita(new TipoAttivita());
	}

	/**
	 * Metodo per ottenere l'id di ricerca
	 */
	protected Object getId(Page t) {
		return t.getId();
	}

	protected SuperSession<Attivita> getSession() {
		return attivitaSession;
	}

	public Ricerca<Attivita> getRicerca() {
		return this.ricerca;
	}

	public DataModel<Attivita> getModel() {
		if (model == null)
			refreshModel();
		return model;
	}

	public void setModel(DataModel<Attivita> model) {
		this.model = model;
	}

	public String cerca() {
		refreshModel();
		return listPage();
	}

	@SuppressWarnings("unchecked")
	protected void refreshModel() {
		// setModel(new LocalDataModel<Attivita>(pageSize, ricerca,
		// getSession()));
		boolean lazy = true;
		if (!lazy)
			setModel(new ListDataModel<Attivita>(attivitaSession.getAllList()));
		else
			setModel(new LazyDataModel<Attivita>(attivitaSession
					.getListSize(ricerca)) {
				private static final long serialVersionUID = 1L;

				@Override
				public List<Attivita> fetchLazyData(int first, int pageSize) {
					return attivitaSession.getList(ricerca, first, pageSize);
				}
			});
	}

	public String addAttivita() {
		this.tipoId = 0;
		this.catId = 0;
		this.immagine = null;
		this.element = new Attivita();
		this.element.setCategoria(new CategoriaAttivita());
		this.element.getCategoria().setTipoAttivita(new TipoAttivita());
		this.editMode = false;
		return NEW_OR_EDIT;
	}

	public String saveAttivita() {
		String idTitle = PageUtils.createPageId(this.element.getNome());
		String idFinal = testId(idTitle);
		this.element.setId(idFinal);
		this.element.setData(new Date());
		this.element.setAutore(JSFUtils.getUserName());
		CategoriaAttivita cat = categorieSession
				.findCategoriaAttivita(new Long(catId));
		this.element.setCategoria(cat);
		if (this.immagine != null && this.immagine.getData() != null)
			this.element.setImmagine(getImmagine());
		this.element = attivitaSession.persist(this.element);
		this.model = null;
		operazioniLogHandler.save("NEW", JSFUtils.getUserName(),
				"creazione attivita': " + this.element.getNome());
		return VIEW;
	}

	public String modAttivita(String id) {
		this.element = attivitaSession.find(id);
		this.immagine = null;
		this.catId = this.element.getCategoria().getId().intValue();
		this.tipoId = this.element.getCategoria().getTipoAttivita().getId()
				.intValue();
		propertiesHandler.cambioTipoDirect(this.tipoId);
		this.immagine = new Immagine();
		if (this.element.getImmagine() != null) {
			this.immagine.setFilename(this.element.getImmagine().getFilename());
		}
		this.editMode = true;
		return NEW_OR_EDIT;
	}

	public String deleteAttivita(String id) {
		operazioniLogHandler.save("DELETE", JSFUtils.getUserName(),
				"emilinazione attivita': " + this.element.getNome());
		attivitaSession.delete(id);
		this.model = null;
		return LIST;
	}

	public String updateAttivita() {
		CategoriaAttivita cat = categorieSession
				.findCategoriaAttivita(new Long(getCatId()));
		if (cat == null)
			return NEW_OR_EDIT;
		this.element.setCategoria(cat);
		if (getImmagine().getData() != null)
			this.element.setImmagine(getImmagine());
		attivitaSession.update(this.element);
		operazioniLogHandler.save("MODIFY", JSFUtils.getUserName(),
				"modifica attivita': " + this.element.getNome());
		this.model = null;
		return VIEW;
	}

	public String detailAttivita(String id) {
		this.element = attivitaSession.find(id);
		return VIEW;
	}

	public Attivita getAttivita() {
		return element;
	}

	public void setAttivita(Attivita attivita) {
		this.element = attivita;
	}

	public boolean isEditMode() {
		return editMode;
	}

	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}

	// -----------------------------------------------------

	public Attivita getElement() {
		return element;
	}

	public void setElement(Attivita element) {
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
		return this.backPage == null ? BACK : this.backPage;
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

	public boolean getClear() {
		reset();
		return true;
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

	public int getTipoId() {

		return tipoId;
	}

	public void setTipoId(int tipoId) {
		this.tipoId = tipoId;
	}

	public String testId(String id) {
		String idFinal = id;
		boolean trovato = false;
		int i = 0;
		while (!trovato) {
			System.out.println("id final: " + idFinal);
			Attivita attivitaFind = attivitaSession.find(idFinal);
			System.out.println("trovato_ " + attivitaFind);
			if (attivitaFind != null) {
				i++;
				idFinal = id + "-" + i;
			} else {
				trovato = true;
				return idFinal;

			}

		}

		return "";
	}

	public int getCatId() {
		return catId;
	}

	public void setCatId(int catId) {
		this.catId = catId;
	}

	public void handleFileUpload(FileUploadEvent event) {
		logger.info("Uploaded: {}", event.getFile().getFileName());
		logger.info("Uploaded: {}", event.getFile().getContentType());
		logger.info("Uploaded: {}", event.getFile().getSize());

		getImmagine().setUploadedData(event.getFile());
		getImmagine().setData(event.getFile().getContents());
		getImmagine().setType(event.getFile().getContentType());
		String filename = FileUtils.createImage_("img", event.getFile()
				.getFileName(), event.getFile().getContents());
		this.element.setImmagine(new Immagine());
		this.getImmagine().setFilename(filename);
		this.element.getImmagine().setFilename(getImmagine().getFilename());
	}

	public Immagine getImmagine() {
		if (immagine == null)
			this.immagine = new Immagine();
		return immagine;
	}

	public void setImmagine(Immagine immagine) {
		this.immagine = immagine;
	}

	public String reset() {
		this.element = null;
		this.model = null;
		return listPage();
	}

	public String discardImage() {
		if (this.element.getImmagine() != null
				&& this.element.getImmagine().getFilename() != null) {
			Resource r = this.resourceSession.find("img", this.element
					.getImmagine().getFilename());
			if (r != null)
				resourceSession.delete(r);
		}
		if (this.getImmagine() != null
				&& this.getImmagine().getFilename() != null) {
			Resource r = this.resourceSession.find("img", this.getImmagine()
					.getFilename());
			if (r != null)
				resourceSession.delete(r);
		}
		this.element.setImmagine(new Immagine());
		this.immagine = new Immagine();
		return null;
	}

}
