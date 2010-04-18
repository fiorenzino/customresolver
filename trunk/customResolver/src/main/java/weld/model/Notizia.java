package weld.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import weld.model.attachment.Documento;
import weld.model.attachment.Immagine;

@Entity
public class Notizia implements Serializable {

	private String id;
	private String titolo;
	private String anteprima;
	private String contenuto;
	private String autore;
	private List<Documento> documenti;
	private List<Immagine> immagini;

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

	public String getAnteprima() {
		return anteprima;
	}

	public void setAnteprima(String anteprima) {
		this.anteprima = anteprima;
	}

	public String getContenuto() {
		return contenuto;
	}

	public void setContenuto(String contenuto) {
		this.contenuto = contenuto;
	}

	public String getAutore() {
		return autore;
	}

	public void setAutore(String autore) {
		this.autore = autore;
	}

	public List<Documento> getDocumenti() {
		if (this.documenti == null)
			this.documenti = new ArrayList<Documento>();
		return documenti;
	}

	public void setDocumenti(List<Documento> documenti) {
		this.documenti = documenti;
	}

	public void addDocumento(Documento documento) {
		getDocumenti().add(documento);
	}

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
}
