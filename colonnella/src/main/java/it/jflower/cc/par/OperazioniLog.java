package it.jflower.cc.par;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class OperazioniLog implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String tipo;
	private String username;
	private String descrizione;
	private boolean attivo = true;
	private Date data;

	public OperazioniLog() {

	}

	public OperazioniLog(String tipo, String username, String descrizione,
			Date data) {
		this.tipo = tipo;
		this.username = username;
		this.descrizione = descrizione;
		this.data = data;
		this.attivo = true;
	}

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	// NEW
	// DELETE
	// MODIFY
	// LOGIN
	// LOGOUT
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Lob
	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public boolean isAttivo() {
		return attivo;
	}

	public void setAttivo(boolean attivo) {
		this.attivo = attivo;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
}
