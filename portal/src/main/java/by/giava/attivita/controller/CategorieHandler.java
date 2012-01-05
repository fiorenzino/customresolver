package by.giava.attivita.controller;


import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;

import by.giava.attivita.model.type.CategoriaAttivita;
import by.giava.attivita.model.type.TipoAttivita;
import by.giava.attivita.repository.CategorieSession;
import by.giava.base.common.util.JSFUtils;
import by.giava.base.controller.OperazioniLogHandler;
import by.giava.base.controller.PropertiesHandler;
import by.giava.base.model.OperazioniLog;
import by.giava.pubblicazioni.model.type.TipoPubblicazione;

@Named
@SessionScoped
public class CategorieHandler implements Serializable {

	private Logger logger = Logger.getLogger(getClass());

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

	@Inject
	OperazioniLogHandler operazioniLogHandler;

	private List<CategoriaAttivita> allCategorie;
	private List<TipoAttivita> allTipi;
	private List<TipoPubblicazione> allTipiPubblicazioni;
	private TipoAttivita tipoAttivita;
	private TipoPubblicazione tipoPubblicazione;
	private CategoriaAttivita categoriaAttivita;
	private boolean editMode;

	private Long idTipo;

	public String addCategoriaAttivita() {
		this.idTipo = null;
		setEditMode(false);
		this.categoriaAttivita = new CategoriaAttivita();
		return NEW_OR_EDIT_CAT;
	}

	public String saveCategoriaAttivita() {
		if (getIdTipo() == null)
			return "";
		TipoAttivita tipo = categorieSession.findTipoAttivita(new Long(
				getIdTipo()));
		if (tipo != null)
			this.categoriaAttivita.setTipoAttivita(tipo);
		categoriaAttivita.setTipoAttivita(tipo);
		categorieSession.persistCategoriaAttivita(categoriaAttivita);
		aggCategorie();
		operazioniLogHandler.save(
				OperazioniLog.NEW,
				JSFUtils.getUserName(),
				"creazione categoria attivita'': "
						+ this.categoriaAttivita.getCategoria());
		return LIST_CAT;
	}

	public String modCategoriaAttivita(Long id) {
		setEditMode(true);
		this.categoriaAttivita = categorieSession.findCategoriaAttivita(id);
		this.idTipo = this.categoriaAttivita.getTipoAttivita().getId();
		return NEW_OR_EDIT_CAT;
	}

	public String updateCategoriaAttivita() {
		if (getIdTipo() == null)
			return "";
		logger.info("ATT TIPO:"
				+ this.categoriaAttivita.getTipoAttivita().getId());
		TipoAttivita tipo = categorieSession.findTipoAttivita(new Long(
				getIdTipo()));
		if (tipo != null)
			this.categoriaAttivita.setTipoAttivita(tipo);
		categorieSession.updateCategoriaAttivita(categoriaAttivita);
		aggCategorie();
		operazioniLogHandler.save(OperazioniLog.MODIFY, JSFUtils.getUserName(),
				"modifica attivita': " + this.categoriaAttivita.getCategoria());
		return LIST_CAT;
	}

	public String deleteCategoriaAttivita() {
		categorieSession.deleteCategoriaAttivita(categoriaAttivita.getId());
		aggCategorie();
		operazioniLogHandler.save(
				OperazioniLog.DELETE,
				JSFUtils.getUserName(),
				"eliminazione attivita': "
						+ this.categoriaAttivita.getCategoria());
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
		operazioniLogHandler.save(
				OperazioniLog.NEW,
				JSFUtils.getUserName(),
				"creazione tipo pubblicazione': "
						+ this.tipoPubblicazione.getNome());
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
		operazioniLogHandler.save(
				OperazioniLog.MODIFY,
				JSFUtils.getUserName(),
				"modifica tipo pubblicazione': "
						+ this.tipoPubblicazione.getNome());
		return LIST_TIP_PUB;
	}

	public String deleteTipoPubblicazione() {
		categorieSession.deleteTipoPubblicazione(tipoPubblicazione.getId());
		aggTipiPubblicazioni();
		operazioniLogHandler.save(
				OperazioniLog.DELETE,
				JSFUtils.getUserName(),
				"eliminazione tipo pubblicazione': "
						+ this.tipoPubblicazione.getNome());
		return LIST_TIP_PUB;
	}

	public String addTipoAttivita() {
		this.tipoAttivita = new TipoAttivita();
		return NEW_OR_EDIT_TIP_ATT;
	}

	public String saveTipoAttivita() {
		categorieSession.persistTipoAttivita(tipoAttivita);
		aggTipi();
		operazioniLogHandler.save(OperazioniLog.NEW, JSFUtils.getUserName(),
				"creazione attivita': " + this.tipoAttivita.getTipo());
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
		operazioniLogHandler.save(OperazioniLog.MODIFY, JSFUtils.getUserName(),
				"modifica tipo attivita': " + this.tipoAttivita.getTipo());
		return LIST_TIP_ATT;
	}

	public String deleteTipoAttivita() {
		categorieSession.deleteTipoAttivita(tipoAttivita.getId());
		aggTipi();
		operazioniLogHandler.save(OperazioniLog.DELETE, JSFUtils.getUserName(),
				"eliminazione tipo attivita': " + this.tipoAttivita.getTipo());
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

	public Long getIdTipo() {
		return idTipo;
	}

	public void setIdTipo(Long idTipo) {
		this.idTipo = idTipo;
	}
}
