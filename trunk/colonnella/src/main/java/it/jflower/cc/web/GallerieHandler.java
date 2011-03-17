package it.jflower.cc.web;

import it.jflower.base.utils.JSFUtils;
import it.jflower.cc.par.Galleria;
import it.jflower.cc.par.OperazioniLog;
import it.jflower.cc.par.attachment.Immagine;
import it.jflower.cc.session.GallerieSession;
import it.jflower.cc.utils.PageUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@SessionScoped
public class GallerieHandler implements Serializable {

	private static final long serialVersionUID = 1L;

	// --------------------------------------------------------

	private static String FACES_REDIRECT = "?faces-redirect=true";

	public static String BACK = "/private/amministrazione.xhtml"
			+ FACES_REDIRECT;
	public static String VIEW = "/private/gallerie/scheda-galleria.xhtml"
			+ FACES_REDIRECT;
	public static String LIST = "/private/gallerie/lista-gallerie.xhtml"
			+ FACES_REDIRECT;
	public static String NEW_OR_EDIT = "/private/gallerie/gestione-galleria.xhtml"
			+ FACES_REDIRECT;

	// ------------------------------------------------

	@Inject
	GallerieSession gallerieSession;

	@Inject
	ResourceHandler resourceHandler;

	@Inject
	OperazioniLogHandler operazioniLogHandler;

	private Galleria galleria;
	private boolean editMode;
	private List<Galleria> all;

	public String addGalleria() {
		resourceHandler.setImgModel(null);
		this.galleria = new Galleria();

		return NEW_OR_EDIT;
	}

	public void addFoto() {
		this.galleria.addImmagine(new Immagine());
	}

	public String saveGalleria() {
		List<Immagine> immagini = new ArrayList<Immagine>();
		for (Immagine immagine : this.galleria.getImmagini()) {
			if (immagine.getUploadedData() != null)
				immagini.add(immagine);
		}
		this.galleria.setImmagini(immagini);

		String idTitle = PageUtils.createPageId(this.galleria.getTitolo());
		String idFinal = testId(idTitle);
		this.galleria.setId(idFinal);

		gallerieSession.persist(this.galleria);
		this.all = null;
		operazioniLogHandler.save(OperazioniLog.NEW, JSFUtils.getUserName(),
				"creazione galleria: " + this.galleria.getTitolo());
		return VIEW;
	}

	public String deleteGalleria() {
		gallerieSession.delete(this.galleria.getId());
		this.all = null;
		operazioniLogHandler.save(OperazioniLog.DELETE, JSFUtils.getUserName(),
				"eliminazione galleria: " + this.galleria.getTitolo());
		return LIST;
	}

	public String modGalleria(String id) {
		this.editMode = true;
		this.galleria = gallerieSession.find(id);
		return NEW_OR_EDIT;
	}

	public String updateGalleria() {
		this.galleria = gallerieSession.update(this.galleria);
		this.all = null;
		operazioniLogHandler.save(OperazioniLog.MODIFY, JSFUtils.getUserName(),
				"eliminazione galleria: " + this.galleria.getTitolo());
		return VIEW;
	}

	public String detailGalleria(String id) {
		this.galleria = gallerieSession.find(id);
		return VIEW;
	}

	public Galleria getGalleria() {
		return galleria;
	}

	public void setGalleria(Galleria galleria) {
		this.galleria = galleria;
	}

	public boolean isEditMode() {
		return editMode;
	}

	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}

	public List<Galleria> getAll() {
		if (this.all == null)
			this.all = gallerieSession.getAll();
		return all;
	}

	public void setAll(List<Galleria> all) {
		this.all = all;
	}

	public String testId(String id) {
		String idFinal = id;
		boolean trovato = false;
		int i = 0;
		while (!trovato) {
			System.out.println("id final: " + idFinal);
			Galleria galleriaFind = gallerieSession.find(idFinal);
			System.out.println("trovato_ " + galleriaFind);
			if (galleriaFind != null) {
				i++;
				idFinal = id + "-" + i;
			} else {
				trovato = true;
				return idFinal;

			}

		}

		return "";
	}
}
