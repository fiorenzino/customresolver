package weld.view;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import weld.model.type.CategoriaAttivita;
import weld.model.type.TipoAttivita;

@Named
@SessionScoped
public class CategorieHandler implements Serializable {

	private TipoAttivita tipoAttivita;
	private CategoriaAttivita categoriaAttivita;

	public String addCategoriaAttivita() {
		this.tipoAttivita = new TipoAttivita();
		return "";
	}

	public String saveCategoriaAttivita() {

		return "";
	}

	public String modCategoriaAttivita(Long id) {

		return "";
	}

	public String updateCategoriaAttivita() {

		return "";
	}

	public String detailCategoriaAttivita(Long id) {

		return "";
	}

	public String addTipoAttivita() {

		return "";
	}

	public String saveTipoAttivita() {

		return "";
	}

	public String modTipoAttivita(Long id) {

		return "";
	}

	public String updateTipoAttivita() {

		return "";
	}

	public String detailTipoAttivita(Long id) {

		return "";
	}

	public TipoAttivita getTipoAttivita() {
		return tipoAttivita;
	}

	public void setTipoAttivita(TipoAttivita tipoAttivita) {
		this.tipoAttivita = tipoAttivita;
	}

	public CategoriaAttivita getCategoriaAttivita() {
		return categoriaAttivita;
	}

	public void setCategoriaAttivita(CategoriaAttivita categoriaAttivita) {
		this.categoriaAttivita = categoriaAttivita;
	}
}
