package by.giava.catalogo.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Prodotto implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private String anteprima;
	private String description;
	private Categoria categoria;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAnteprima() {
		return anteprima;
	}

	public void setAnteprima(String anteprima) {
		this.anteprima = anteprima;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@ManyToOne
	public Categoria getCategoria() {
		if (this.categoria == null)
			this.categoria = new Categoria();
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
}
