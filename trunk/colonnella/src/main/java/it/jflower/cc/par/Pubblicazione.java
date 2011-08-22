package it.jflower.cc.par;

import it.jflower.cc.par.attachment.Documento;
import it.jflower.cc.par.type.TipoPubblicazione;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.persistence.JoinColumn;

@Entity
public class Pubblicazione implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private String nome;
	private String descrizione;
	private TipoPubblicazione tipo;
	private Long idTipo;
	private String titolo;
	private String autore;
	private Date data;
	private Date dal;
	private Date al;
	private Date stampaDal;
	private Date stampaAl;
	private List<Documento> documenti;
	private boolean attivo = true;
	private String progressivoRegistro;
	private String enteEmittente;
	private boolean archivio;

	private Date validoIl;

	@Id
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@ManyToOne
	public TipoPubblicazione getTipo() {
		if (tipo == null)
			tipo = new TipoPubblicazione();
		return tipo;
	}

	public void setTipo(TipoPubblicazione tipo) {
		this.tipo = tipo;
	}

	@Lob
	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getAutore() {
		return autore;
	}

	public void setAutore(String autore) {
		this.autore = autore;
	}

	public Date getDal() {
		return dal;
	}

	public void setDal(Date dal) {
		this.dal = dal;
	}

	public Date getAl() {
		return al;
	}

	public void setAl(Date al) {
		this.al = al;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "Pubblicazione_Documento", joinColumns = @JoinColumn(name = "Pubblicazione_id"), inverseJoinColumns = @JoinColumn(name = "documenti_id"))
	public List<Documento> getDocumenti() {
		if (this.documenti == null)
			this.documenti = new ArrayList<Documento>();
		return documenti;
	}

	public void setDocumenti(List<Documento> documenti) {
		this.documenti = documenti;
	}

	@Transient
	public int getDocSize() {
		return getDocumenti().size();
	}

	public void addDocumento(Documento documento) {
		getDocumenti().add(documento);
	}

	public boolean isAttivo() {
		return attivo;
	}

	public void setAttivo(boolean attivo) {
		this.attivo = attivo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Transient
	public Date getValidoIl() {
		return validoIl;
	}

	public void setValidoIl(Date validoIl) {
		this.validoIl = validoIl;
	}

	@Lob
	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	@Transient
	public Long getIdTipo() {
		return idTipo;
	}

	public void setIdTipo(Long idTipo) {
		this.idTipo = idTipo;
	}

	@Transient
	public Date getStampaDal() {
		return stampaDal;
	}

	public void setStampaDal(Date stampaDal) {
		this.stampaDal = stampaDal;
	}

	@Transient
	public Date getStampaAl() {
		return stampaAl;
	}

	public void setStampaAl(Date stampaAl) {
		this.stampaAl = stampaAl;
	}

	public String getProgressivoRegistro() {
		return progressivoRegistro;
	}

	public void setProgressivoRegistro(String progressivoRegistro) {
		this.progressivoRegistro = progressivoRegistro;
	}

	public String getEnteEmittente() {
		return enteEmittente;
	}

	public void setEnteEmittente(String enteEmittente) {
		this.enteEmittente = enteEmittente;
	}

	@Transient
	public boolean isArchivio() {
		return archivio;
	}

	public void setArchivio(boolean archivio) {
		this.archivio = archivio;
	}
}
