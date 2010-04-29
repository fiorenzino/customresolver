package weld.view;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;

import weld.model.Galleria;
import weld.model.attachment.Immagine;
import weld.session.GallerieSession;
import weld.view.utils.PagesUtils;

@Named
@SessionScoped
public class GallerieHandler implements Serializable {

	@Inject
	GallerieSession gallerieSession;
	private Galleria galleria;
	private boolean editMode;
	private List<Galleria> all;

	public String addGalleria() {
		this.galleria = new Galleria();
		return "/private/gallerie/gestione-galleria?redirect=true";
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
		this.galleria.setId(PagesUtils.createPageId(this.galleria.getTitolo()));
		gallerieSession.persist(this.galleria);
		this.all = null;
		return "/private/gallerie/scheda-galleria?redirect=true";
	}

	public String deleteGalleria() {
		gallerieSession.delete(this.galleria.getId());
		this.all = null;
		return "/private/gallerie/lista-notizie?redirect=true";
	}

	public String modGalleria(String id) {
		this.editMode = true;
		this.galleria = gallerieSession.find(id);
		return "/private/gallerie/gestione-galleria?redirect=true";
	}

	public String updateGalleria() {
		this.galleria = gallerieSession.update(this.galleria);
		this.all = null;
		return "/private/gallerie/scheda-galleria?redirect=true";
	}

	public String detailGalleria(String id) {
		this.galleria = gallerieSession.find(id);
		return "/private/gallerie/scheda-galleria?redirect=true";
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
}
