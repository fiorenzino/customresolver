package weld.view;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import weld.model.Attivita;
import weld.session.AttivitaSession;
import weld.view.utils.PagesUtils;

@Named
@SessionScoped
public class AttivitaHandler implements Serializable {

	@Inject
	AttivitaSession attivitaSession;
	private boolean editMode;
	private Attivita attivita;
	private List<Attivita> all;

	public String addAttivita() {
		this.attivita = new Attivita();
		return "/private/attivita/gestione-attivita.xhtml";
	}

	public String saveAttivita() {
		this.attivita.setId(PagesUtils.createPageId(this.attivita.getNome()));
		this.attivita = attivitaSession.persist(this.attivita);
		return "/private/attivita/scheda-attivita.xhtml";
	}

	public String modAttivita(String id) {
		this.attivita = attivitaSession.find(id);
		return "/private/attivita/gestione-attivita.xhtml";
	}

	public String updateAttivita() {
		attivitaSession.update(this.attivita);
		return "/private/attivita/gestione-attivita.xhtml";
	}

	public String detailAttivita(String id) {
		this.attivita = attivitaSession.find(id);
		return "";
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
}
