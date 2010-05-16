package it.jflower.cc.par;

import it.jflower.cc.par.attachment.Documento;
import it.jflower.cc.par.attachment.Immagine;
import it.jflower.cc.par.type.TipoInformazione;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Notizia implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private boolean attivo = true;
	private String titolo;
	private String anteprima;
	private String contenuto;
	private String autore;
	private Date data;
	private TipoInformazione tipo;
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

	@Lob
	public String getAnteprima() {
		return anteprima;
	}

	public void setAnteprima(String anteprima) {
		this.anteprima = anteprima;
	}

	@Lob
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

	@OneToMany
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

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	@ManyToOne
	public TipoInformazione getTipo() {
		if (tipo == null)
			this.tipo = new TipoInformazione();
		return tipo;
	}

	public void setTipo(TipoInformazione tipo) {
		this.tipo = tipo;
	}

	public boolean isAttivo() {
		return attivo;
	}

	public void setAttivo(boolean attivo) {
		this.attivo = attivo;
	}

}
