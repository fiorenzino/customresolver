package weld.view;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import weld.model.type.CategoriaAttivita;
import weld.model.type.TipoAttivita;
import weld.model.type.TipoPubblicazione;
import weld.session.CategorieSession;

@Named
@SessionScoped
public class CategorieHandler implements Serializable {

	@Inject
	CategorieSession categorieSession;

	@Inject
	PropertiesHandler propertiesHandler;

	private List<CategoriaAttivita> allCategorie;
	private List<TipoAttivita> allTipi;
	private List<TipoPubblicazione> allTipiPubblicazioni;
	private TipoAttivita tipoAttivita;
	private TipoPubblicazione tipoPubblicazione;
	private CategoriaAttivita categoriaAttivita;
	private boolean editMode;

	public String addCategoriaAttivita() {
		setEditMode(false);
		this.categoriaAttivita = new CategoriaAttivita();
		return "/private/attivita/gestione-categorie-attivita.xhtml";
	}

	public String saveCategoriaAttivita() {
		TipoAttivita tipo = categorieSession
				.findTipoAttivita(this.categoriaAttivita.getTipoAttivita()
						.getId());
		categoriaAttivita.setTipoAttivita(tipo);
		categorieSession.persistCategoriaAttivita(categoriaAttivita);
		aggCategorie();
		return "/private/attivita/lista-categorie-attivita.xhtml";
	}

	public String modCategoriaAttivita(Long id) {
		setEditMode(true);
		this.categoriaAttivita = categorieSession.findCategoriaAttivita(id);
		return "/private/attivita/gestione-categorie-attivita.xhtml";
	}

	public String updateCategoriaAttivita() {
		System.out.println("ATT TIPO:"
				+ this.categoriaAttivita.getTipoAttivita().getId());

		categorieSession.updateCategoriaAttivita(categoriaAttivita);
		aggCategorie();
		return "/private/attivita/lista-categorie-attivita.xhtml";
	}

	public String addTipoPubblicazione() {
		setEditMode(false);
		this.tipoPubblicazione = new TipoPubblicazione();
		return "/private/pubblicazioni/gestione-tipi-pubblicazione.xhtml";
	}

	public String saveTipoPubblicazione() {
		categorieSession.persistTipoPubblicazione(tipoPubblicazione);
		aggTipiPubblicazioni();
		return "/private/pubblicazioni/lista-tipi-pubblicazione.xhtml";
	}

	public String modTipoPubblicazione(Long id) {
		setEditMode(true);
		this.tipoPubblicazione = categorieSession.findTipoPubblicazione(id);
		return "/private/pubblicazioni/gestione-tipi-pubblicazione.xhtml";
	}

	public String updateTipoPubblicazione() {
		categorieSession.updateTipoPubblicazione(tipoPubblicazione);
		aggTipiPubblicazioni();
		return "/private/pubblicazioni/lista-tipi-pubblicazione.xhtml";
	}

	public String addTipoAttivita() {
		this.tipoAttivita = new TipoAttivita();
		return "/private/attivita/gestione-tipi-attivita.xhtml";
	}

	public String saveTipoAttivita() {
		categorieSession.persistTipoAttivita(tipoAttivita);
		aggTipi();
		return "/private/attivita/lista-tipi-attivita.xhtml";
	}

	public String modTipoAttivita(Long id) {
		this.tipoAttivita = categorieSession.findTipoAttivita(id);
		return "/private/attivita/gestione-tipi-attivita.xhtml";
	}

	public String updateTipoAttivita() {
		categorieSession.updateTipoAttivita(tipoAttivita);
		aggTipi();
		return "/private/attivita/lista-tipi-attivita.xhtml";
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

	public void aggCategorie() {
		this.allCategorie = categorieSession.getAllCategoriaAttivita();
		propertiesHandler.resetCategorieAttivitaItems();
	}

	public List<CategoriaAttivita> getAllCategorie() {
		if (this.allCategorie == null)
			aggCategorie();
		return allCategorie;
	}

	public void setAllCategorie(List<CategoriaAttivita> allCategorie) {
		this.allCategorie = allCategorie;
	}

	public void aggTipi() {
		this.allTipi = categorieSession.getAllTipoAttivita();
		propertiesHandler.resetTipiAttivitaItems();
	}

	public List<TipoAttivita> getAllTipi() {
		if (allTipi == null)
			aggTipi();
		return allTipi;
	}

	public void setAllTipiPubblicazioni(
			List<TipoPubblicazione> allTipiPubblicazioni) {
		this.allTipiPubblicazioni = allTipiPubblicazioni;
	}

	public void aggTipiPubblicazioni() {
		this.allTipiPubblicazioni = categorieSession.getAllTipoPubblicazione();
		propertiesHandler.resetTipiPubblicazioniItems();
	}

	public List<TipoPubblicazione> getAllTipiPubblicazioni() {
		if (allTipiPubblicazioni == null)
			aggTipiPubblicazioni();
		return allTipiPubblicazioni;
	}

	public void setAllTipi(List<TipoAttivita> allTipi) {
		this.allTipi = allTipi;
	}

	public boolean isEditMode() {
		return editMode;
	}

	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}

	public TipoPubblicazione getTipoPubblicazione() {
		return tipoPubblicazione;
	}

	public void setTipoPubblicazione(TipoPubblicazione tipoPubblicazione) {
		this.tipoPubblicazione = tipoPubblicazione;
	}
}
