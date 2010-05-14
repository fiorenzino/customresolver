package it.jflower.cc.par.type;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TipoInformazione implements Serializable {

	private static final long serialVersionUID = 1L;

	// qualunque, Ordinanze Polizia Municipale, Licenze Commerciali
	// Pubblicazioni di matrimonio, Determine dirigenziali, Concorsi
	// Bandi ,Delibere di consiglio , Delibere di giunta

	private Long id;
	private boolean attivo = true;
	private String nome;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public boolean isAttivo() {
		return attivo;
	}

	public void setAttivo(boolean attivo) {
		this.attivo = attivo;
	}

	
}
