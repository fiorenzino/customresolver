package it.jflower.cc.par.type;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class CategoriaAttivita implements Serializable {

	private static final long serialVersionUID = 1L;

	// alberghi, ristoranti, pizzerie, vinerie, agriturismi, bed&breakfast, ecc
	// farmacie, studi medici, carabinieri, ecc
	private Long id;
	private TipoAttivita tipoAttivita;
	private String categoria;
	private boolean attivo = true;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	public TipoAttivita getTipoAttivita() {
		if (tipoAttivita == null)
			tipoAttivita = new TipoAttivita();
		return tipoAttivita;
	}

	public void setTipoAttivita(TipoAttivita tipoAttivita) {
		this.tipoAttivita = tipoAttivita;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public boolean isAttivo() {
		return attivo;
	}

	public void setAttivo(boolean attivo) {
		this.attivo = attivo;
	}
}
