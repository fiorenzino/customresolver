package it.jflower.cc.par;

import it.jflower.base.utils.HtmlUtils;
import it.jflower.cc.par.attachment.Documento;
import it.jflower.cc.par.attachment.Immagine;
import it.jflower.cc.par.type.TipoInformazione;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

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
	private boolean evidenza;

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

	@Transient
	public String getContenutoN() {
		return HtmlUtils.normalizeHtml(this.contenuto);
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

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "Notizia_Documento", joinColumns = @JoinColumn(name = "Notizia_id"), inverseJoinColumns = @JoinColumn(name = "documenti_id"))
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

	@Transient
	public int getDocSize() {
		return getDocumenti().size();
	}

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "Notizia_Immagine", joinColumns = @JoinColumn(name = "Notizia_id"), inverseJoinColumns = @JoinColumn(name = "immagini_id"))
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

	@Transient
	public int getImgSize() {
		return getImmagini().size();
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	@ManyToOne
	public TipoInformazione getTipo() {
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

	public boolean isEvidenza() {
		return evidenza;
	}

	public void setEvidenza(boolean evidenza) {
		this.evidenza = evidenza;
	}

}
