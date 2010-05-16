package it.jflower.cc.web;

import it.jflower.base.utils.FileUtils;
import it.jflower.base.utils.JSFUtils;
import it.jflower.cc.par.Attivita;
import it.jflower.cc.par.attachment.Immagine;
import it.jflower.cc.par.type.CategoriaAttivita;
import it.jflower.cc.session.AttivitaSession;
import it.jflower.cc.session.CategorieSession;
import it.jflower.cc.utils.PageUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@SessionScoped
public class AttivitaHandler implements Serializable {

	private static final long serialVersionUID = 1L;

	// --------------------------------------------------------

	private static String FACES_REDIRECT = "?faces-redirect=true";

	public static String BACK = "/private/amministrazione.xhtml"
			+ FACES_REDIRECT;
	public static String VIEW = "/private/attivita/scheda-attivita.xhtml"
			+ FACES_REDIRECT;
	public static String LIST = "/private/notizie/lista-notizie.xhtml"
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

	private Logger logger = LoggerFactory.getLogger(AttivitaHandler.class);

	private boolean editMode;
	private Attivita attivita;
	private List<Attivita> all;
	private int tipoId;
	private int catId;
	private Immagine immagine;

	public AttivitaHandler() {
		// TODO Auto-generated constructor stub
	}

	public String addAttivita() {
		this.tipoId = 0;
		this.catId = 0;
		this.immagine = null;
		this.attivita = new Attivita();
		this.editMode = false;
		return NEW_OR_EDIT;
	}

	public String saveAttivita() {

		String idTitle = PageUtils.createPageId(this.attivita.getNome());
		String idFinal = testId(idTitle);
		this.attivita.setId(idFinal);

		this.attivita.setData(new Date());
		this.attivita.setAutore(JSFUtils.getUserName());
		CategoriaAttivita cat = categorieSession
				.findCategoriaAttivita(new Long(catId));
		this.attivita.setCategoria(cat);
		if (this.immagine.getData() != null)
			this.attivita.setImmagine(getImmagine());
		this.attivita = attivitaSession.persist(this.attivita);
		this.all = null;
		return VIEW;
	}

	public String modAttivita(String id) {
		this.attivita = attivitaSession.find(id);
		this.immagine = null;
		this.catId = this.attivita.getCategoria().getId().intValue();
		this.tipoId = this.attivita.getCategoria().getTipoAttivita().getId()
				.intValue();
		propertiesHandler.cambioTipoDirect(this.tipoId);
		this.editMode = true;
		return NEW_OR_EDIT;
	}

	public String deleteAttivita(String id) {
		attivitaSession.delete(id);
		this.all = null;
		return LIST;
	}

	public String updateAttivita() {
		CategoriaAttivita cat = categorieSession
				.findCategoriaAttivita(new Long(getCatId()));
		if (cat == null)
			return NEW_OR_EDIT;
		this.attivita.setCategoria(cat);
		if (getImmagine().getData() != null)
			this.attivita.setImmagine(getImmagine());
		attivitaSession.update(this.attivita);
		this.all = null;
		return VIEW;
	}

	public String detailAttivita(String id) {
		this.attivita = attivitaSession.find(id);
		return VIEW;
	}

	public Attivita getAttivita() {
		return attivita;
	}

	public void setAttivita(Attivita attivita) {
		this.attivita = attivita;
	}

	public boolean isEditMode() {
		return editMode;
	}

	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}

	public List<Attivita> getAll() {
		if (all == null)
			this.all = attivitaSession.getAll();
		return all;
	}

	public void setAll(List<Attivita> all) {
		this.all = all;
	}

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
		getImmagine().setFilename(event.getFile().getFileName());
		getImmagine().setType(event.getFile().getContentType());
		FileUtils.createImage("img", event.getFile().getFileName(), event
				.getFile().getContents());

	}

	public Immagine getImmagine() {
		if (immagine == null)
			this.immagine = new Immagine();
		return immagine;
	}

	public void setImmagine(Immagine immagine) {
		this.immagine = immagine;
	}
}
