package by.giava.catalogo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Realizzazione implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private String anteprima;
	private String description;
	private List<Prodotto> prodotti;

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

	@ManyToMany
	public List<Prodotto> getProdotti() {
		if (prodotti == null)
			this.prodotti = new ArrayList<Prodotto>();
		return prodotti;
	}

	public void setProdotti(List<Prodotto> prodotti) {
		this.prodotti = prodotti;
	}

	public void addProdotti(Prodotto prodotto) {
		getProdotti().add(prodotto);
	}

}
