package it.flowercms.par;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class MenuItem implements Serializable {

	private Long id;
	private String nome;
	private Page pagina;

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

	public Page getPagina() {
		return pagina;
	}

	public void setPagina(Page pagina) {
		this.pagina = pagina;
	}

}
