package by.giava.base.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Transient;

@Entity
public class MenuGroup implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	boolean attivo = true;

	// ------------------------------------------------------------------------

	private String nome;
	private String descrizione;
	private String percorso;
	private Integer ordinamento = 1;

	private List<MenuItem> lista;

	private List<MenuItem> listaAttivi;

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

	@OneToMany( mappedBy="gruppo", cascade = CascadeType.ALL, fetch=FetchType.EAGER )
	@OrderBy( "ordinamento, nome" )
//	@OneToMany( mappedBy="gruppo", cascade = CascadeType.ALL )
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

	public Integer getOrdinamento() {
		return ordinamento;
	}

	public void setOrdinamento(Integer ordinamento) {
		this.ordinamento = ordinamento;
	}

	@Transient
	public int getListaSize() {
		return this.lista == null ? 0 : lista.size();
	}

	@Transient
	public List<MenuItem> getListaAttivi() {
		if ( listaAttivi == null )
			listaAttivi = new ArrayList<MenuItem>();
		return listaAttivi;
	}

	public void setListaAttivi(List<MenuItem> listaAttivi) {
		this.listaAttivi = listaAttivi;
	}
	
	@Transient
	public int getListaAttiviSize() {
		return this.listaAttivi == null ? 0 : lista.size();
	}

}
