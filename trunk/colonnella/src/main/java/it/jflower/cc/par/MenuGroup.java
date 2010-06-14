package it.jflower.cc.par;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class MenuGroup implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	boolean attivo = true;

	// ------------------------------------------------------------------------

	private String nome;
	private String descrizione;
	private String percorso;

	private List<MenuItem> lista;

	// ------------------------------------------------------------------------

	public MenuGroup() {
	}

	// ------------------------------------------------------------------------

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

//	@OneToMany( mappedBy="gruppo", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@OneToMany( mappedBy="gruppo", cascade = CascadeType.ALL )
	public List<MenuItem> getLista() {
		return lista;
	}

	public void setLista(List<MenuItem> lista) {
		this.lista = lista;
	}

	public boolean isAttivo() {
		return attivo;
	}

	public void setAttivo(boolean attivo) {
		this.attivo = attivo;
	}

	public String getPercorso() {
		return percorso;
	}

	public void setPercorso(String percorso) {
		this.percorso = percorso;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

}
