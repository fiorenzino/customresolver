package weld.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import weld.model.attachment.Documento;
import weld.model.type.TipoPubblicazione;

public class Pubblicazione implements Serializable {

	private Long id;
	private TipoPubblicazione tipoPubblicazione;
	private String oggetto;
	private String mittente;
	private Date dataPubblicazione;
	private Date dal;
	private Date al;
	private List<Documento> documenti;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TipoPubblicazione getTipoPubblicazione() {
		return tipoPubblicazione;
	}

	public void setTipoPubblicazione(TipoPubblicazione tipoPubblicazione) {
		this.tipoPubblicazione = tipoPubblicazione;
	}

	public String getOggetto() {
		return oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public String getMittente() {
		return mittente;
	}

	public void setMittente(String mittente) {
		this.mittente = mittente;
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

	public Date getDataPubblicazione() {
		return dataPubblicazione;
	}

	public void setDataPubblicazione(Date dataPubblicazione) {
		this.dataPubblicazione = dataPubblicazione;
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
}
