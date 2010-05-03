package weld.view;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import weld.model.Galleria;
import weld.model.Notizia;
import weld.session.NotizieSession;
import weld.view.utils.PagesUtils;

@Named
@SessionScoped
public class NotizieHandler implements Serializable {

	@Inject
	NotizieSession notizieSession;
	private Notizia notizia;
	private boolean editMode;
	private List<Notizia> all;

	public String addNotizia() {
		this.editMode = false;
		this.notizia = new Notizia();
		return "/private/notizie/gestione-notizia?redirect=true";
	}

	public String saveNotizia() {
		String idTitle = PagesUtils.createPageId(this.notizia.getTitolo());
		String idFinal = testId(idTitle);
		this.notizia.setId(idFinal);

		notizieSession.persist(this.notizia);
		this.all = null;
		return "/private/notizie/scheda-notizia?redirect=true";
	}

	public String deleteNotizia() {
		notizieSession.delete(this.notizia.getId());
		this.all = null;
		return "/private/notizie/lista-notizie?redirect=true";
	}

	public String modNotizia(String id) {
		this.editMode = true;
		this.notizia = notizieSession.find(id);
		return "/private/notizie/gestione-notizia?redirect=true";
	}

	public String updateNotizia() {
		this.notizia = notizieSession.update(this.notizia);
		this.all = null;
		return "/private/notizie/scheda-notizia?redirect=true";
	}

	public String detailNotizia(String id) {
		this.notizia = notizieSession.find(id);
		return "/private/notizie/scheda-notizia?redirect=true";
	}

	public Notizia getNotizia() {
		return notizia;
	}

	public void setNotizia(Notizia notizia) {
		this.notizia = notizia;
	}

	public boolean isEditMode() {
		return editMode;
	}

	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}

	public List<Notizia> getAll() {
		if (this.all == null)
			this.all = notizieSession.getAll();
		return all;
	}

	public void setAll(List<Notizia> all) {
		this.all = all;
	}

	public String testId(String id) {
		String idFinal = id;
		boolean trovato = false;
		int i = 0;
		while (!trovato) {
			System.out.println("id final: " + idFinal);
			Notizia notiziaFind = notizieSession.find(idFinal);
			System.out.println("trovato_ " + notiziaFind);
			if (notiziaFind != null) {
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
