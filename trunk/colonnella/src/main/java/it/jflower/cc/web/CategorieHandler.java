package it.jflower.cc.web;

import it.jflower.cc.par.type.CategoriaAttivita;
import it.jflower.cc.par.type.TipoAttivita;
import it.jflower.cc.par.type.TipoPubblicazione;
import it.jflower.cc.session.CategorieSession;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@SessionScoped
public class CategorieHandler implements Serializable {

	// --------------------------------------------------------

	private static String FACES_REDIRECT = "?faces-redirect=true";
	public static String BACK = "/private/amministrazione.xhtml"
			+ FACES_REDIRECT;

	public static String LIST_CAT = "/private/attivita/lista-categorie-attivita.xhtml"
			+ FACES_REDIRECT;
	public static String NEW_OR_EDIT_CAT = "/private/attivita/gestione-categorie-attivita.xhtml"
			+ FACES_REDIRECT;

	public static String LIST_TIP_PUB = "/private/tipi-pubblicazione/lista-tipi-pubblicazione.xhtml"
			+ FACES_REDIRECT;
	public static String NEW_OR_EDIT_TIP_PUB = "/private/tipi-pubblicazione/gestione-tipi-pubblicazione.xhtml"
			+ FACES_REDIRECT;

	public static String LIST_TIP_ATT = "/private/attivita/lista-tipi-attivita.xhtml"
			+ FACES_REDIRECT;
	public static String NEW_OR_EDIT_TIP_ATT = "/private/attivita/gestione-tipi-attivita.xhtml"
			+ FACES_REDIRECT;

	// --------------------------------------------------------

	private static final long serialVersionUID = 1L;

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

	private int idTipo;

	public String addCategoriaAttivita() {
		this.idTipo = 0;
		setEditMode(false);
		this.categoriaAttivita = new CategoriaAttivita();
		return NEW_OR_EDIT_CAT;
	}

	public String saveCategoriaAttivita() {
		TipoAttivita tipo = categorieSession.findTipoAttivita(new Long(
				getIdTipo()));
		if (tipo != null)
			this.categoriaAttivita.setTipoAttivita(tipo);
		categoriaAttivita.setTipoAttivita(tipo);
		categorieSession.persistCategoriaAttivita(categoriaAttivita);
		aggCategorie();
		return LIST_CAT;
	}

	public String modCategoriaAttivita(Long id) {
		setEditMode(true);
		this.categoriaAttivita = categorieSession.findCategoriaAttivita(id);
		this.idTipo = this.categoriaAttivita.getTipoAttivita().getId()
				.intValue();
		return NEW_OR_EDIT_CAT;
	}

	public String updateCategoriaAttivita() {
		System.out.println("ATT TIPO:"
				+ this.categoriaAttivita.getTipoAttivita().getId());
		TipoAttivita tipo = categorieSession.findTipoAttivita(new Long(
				getIdTipo()));
		if (tipo != null)
			this.categoriaAttivita.setTipoAttivita(tipo);
		categorieSession.updateCategoriaAttivita(categoriaAttivita);
		aggCategorie();
		return LIST_CAT;
	}

	public String deleteCategoriaAttivita() {
		categorieSession.deleteCategoriaAttivita(categoriaAttivita.getId());
		aggCategorie();
		return LIST_CAT;
	}

	public String addTipoPubblicazione() {
		setEditMode(false);
		this.tipoPubblicazione = new TipoPubblicazione();
		return NEW_OR_EDIT_TIP_PUB;
	}

	public String saveTipoPubblicazione() {
		categorieSession.persistTipoPubblicazione(tipoPubblicazione);
		aggTipiPubblicazioni();
		return LIST_TIP_PUB;
	}

	public String modTipoPubblicazione(Long id) {
		setEditMode(true);
		this.tipoPubblicazione = categorieSession.findTipoPubblicazione(id);
		return NEW_OR_EDIT_TIP_PUB;
	}

	public String updateTipoPubblicazione() {
		categorieSession.updateTipoPubblicazione(tipoPubblicazione);
		aggTipiPubblicazioni();
		return LIST_TIP_PUB;
	}

	public String deleteTipoPubblicazione() {
		categorieSession.deleteTipoPubblicazione(tipoPubblicazione.getId());
		aggTipiPubblicazioni();
		return LIST_TIP_PUB;
	}

	public String addTipoAttivita() {
		this.tipoAttivita = new TipoAttivita();
		return NEW_OR_EDIT_TIP_ATT;
	}

	public String saveTipoAttivita() {
		categorieSession.persistTipoAttivita(tipoAttivita);
		aggTipi();
		return LIST_TIP_ATT;
	}

	public String modTipoAttivita(Long id) {
		this.setEditMode(true);
		this.tipoAttivita = categorieSession.findTipoAttivita(id);
		return NEW_OR_EDIT_TIP_ATT;
	}

	public String updateTipoAttivita() {
		categorieSession.updateTipoAttivita(tipoAttivita);
		aggTipi();
		return LIST_TIP_ATT;
	}

	public String deleteTipoAttivita() {
		categorieSession.deleteTipoAttivita(tipoAttivita.getId());
		aggTipi();
		return LIST_TIP_ATT;
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

	public int getIdTipo() {
		return idTipo;
	}

	public void setIdTipo(int idTipo) {
		this.idTipo = idTipo;
	}
}
