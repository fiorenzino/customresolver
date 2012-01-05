package by.giava.moduli.model;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import by.giava.base.model.attachment.Documento;
import by.giava.moduli.model.type.TipoModulo;

@Entity
public class Modulo implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String nome;
	private Date data;
	private String autore;
	private boolean attivo = true;
	private String oggetto;
	private Documento documento;
	private TipoModulo tipo;
	private Long idTipo;

	@Id
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getAutore() {
		return autore;
	}

	public void setAutore(String autore) {
		this.autore = autore;
	}

	public boolean isAttivo() {
		return attivo;
	}

	public void setAttivo(boolean attivo) {
		this.attivo = attivo;
	}

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	public Documento getDocumento() {
		return documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

	@Lob
	public String getOggetto() {
		return oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	@ManyToOne
	public TipoModulo getTipo() {
		return tipo;
	}

	public void setTipo(TipoModulo tipo) {
		this.tipo = tipo;
	}

	@Transient
	public String getOggettoBreve() {
		return oggetto == null || oggetto.length() == 0 ? "n.d." : oggetto
				.length() < 30 ? oggetto : oggetto.substring(0, 30) + "...";
	}

	@Transient
	public Long getIdTipo() {
		return idTipo;
	}

	public void setIdTipo(Long idTipo) {
		this.idTipo = idTipo;
	}

}
