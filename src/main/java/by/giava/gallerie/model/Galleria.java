package by.giava.gallerie.model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

import by.giava.base.model.attachment.Immagine;

@Entity
public class Galleria implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private String titolo;
	private String descrizione;
	private List<Immagine> immagini;
	private boolean attivo = true;

	@Id
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	@Lob
	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	@OneToMany
	public List<Immagine> getImmagini() {
		if (this.immagini == null)
			this.immagini = new ArrayList<Immagine>();
		return immagini;
	}

	public void setImmagini(List<Immagine> immagini) {
		this.immagini = immagini;
	}

	public void addImmagine(Immagine immagine) {
		getImmagini().add(immagine);
	}

	public boolean isAttivo() {
		return attivo;
	}

	public void setAttivo(boolean attivo) {
		this.attivo = attivo;
	}
}
