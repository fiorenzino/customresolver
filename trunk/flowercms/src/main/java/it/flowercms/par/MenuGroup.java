package it.flowercms.par;

import java.io.Serializable;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class MenuGroup implements Serializable {

	private Long id;
	private String nome;
	private List<MenuItem> lista;

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

	public List<MenuItem> getLista() {
		return lista;
	}

	public void setLista(List<MenuItem> lista) {
		this.lista = lista;
	}

}
