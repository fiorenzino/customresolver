package it.jflower.cc.web;

import it.jflower.base.par.Ricerca;
import it.jflower.base.session.SuperSession;
import it.jflower.base.utils.JSFUtils;
import it.jflower.cc.par.Page;
import it.jflower.cc.par.Template;
import it.jflower.cc.par.type.CategoriaAttivita;
import it.jflower.cc.par.type.TipoAttivita;
import it.jflower.cc.par.type.TipoInformazione;
import it.jflower.cc.par.type.TipoModulo;
import it.jflower.cc.par.type.TipoPubblicazione;
import it.jflower.cc.session.CategorieSession;
import it.jflower.cc.session.PageSession;
import it.jflower.cc.session.TemplateSession;
import it.jflower.cc.session.TipoInformazioniSession;
import it.jflower.cc.session.TipoModuloSession;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

@Named
@SessionScoped
public class PropertiesHandler implements Serializable {

	private Logger logger = Logger.getLogger(getClass());

	private static final long serialVersionUID = 1L;

	@Inject
	CategorieSession categorieSession;

	@Inject
	AttivitaHandler attivitaHandler;

	@SuppressWarnings("rawtypes")
	private Map<Class, SelectItem[]> items = null;

	@Inject
	private TemplateSession templateSession;

	@Inject
	private PageSession pageSession;

	@Inject
	private TipoInformazioniSession tipoInformazioniSession;

	@Inject
	private TipoModuloSession tipoModuloSession;

	private SelectItem[] fileTypeItems = new SelectItem[] {};
	private SelectItem[] categorieAttivitaItems = new SelectItem[] {};
	private SelectItem[] tipiAttivitaItems = new SelectItem[] {};
	private SelectItem[] tipiPubblicazioneItems = new SelectItem[] {};
	private SelectItem[] tipiModuloItems = new SelectItem[] {};

	private SelectItem[] staticoDinamicoItems = new SelectItem[] {};

	private SelectItem[] ruoliItems = new SelectItem[] {};

	private SelectItem[] tipiOperazioniLogItems = new SelectItem[] {};

	public PropertiesHandler() {
		super();
		reset();
	}

	public SelectItem[] getRuoliItems() {
		if (ruoliItems == null || ruoliItems.length == 0) {
			List<SelectItem> ruoliList = new ArrayList<SelectItem>();
			// ruoliList.add( new SelectItem("admin", "amministratore") );
			// ruoliList.add ( new SelectItem("user", "utente ordinario") );
			ruoliList.add(new SelectItem("modelli", "gestione modelli"));
			ruoliList.add(new SelectItem("pagine", "gestione pagine"));
			ruoliList.add(new SelectItem("menu", "gestione menu"));
			ruoliList.add(new SelectItem("risorse", "gestione risorse"));
			ruoliList.add(new SelectItem("log", "visualizza log operazioni"));
			ruoliList.add(new SelectItem("tipi_informazione",
					"gestione tipi informazione"));
			ruoliList.add(new SelectItem("news", "gestione notizie"));
			ruoliList.add(new SelectItem("attivita", "gestione attivita'"));
			ruoliList.add(new SelectItem("tipi_attivita",
					"gestione tipi attivita'"));
			ruoliList.add(new SelectItem("categorie_attivita",
					"gestione categorie attivita'"));
			ruoliList.add(new SelectItem("pubblicazioni",
					"gestione albo pretorio"));
			ruoliList.add(new SelectItem("tipi_pubblicazione",
					"gestione tipi pubblicazione"));
			ruoliList.add(new SelectItem("moduli", "gestione modulistica"));
			ruoliList
					.add(new SelectItem("tipi_modulo", "gestione tipi modulo"));
			ruoliItems = ruoliList.toArray(new SelectItem[] {});
		}
		return ruoliItems;
	}

	// NEW
	// DELETE
	// MODIFY
	// LOGIN
	// LOGOUT
	public SelectItem[] getTipiOperazioniLogItems() {
		if (tipiOperazioniLogItems == null
				|| tipiOperazioniLogItems.length == 0) {
			tipiOperazioniLogItems = new SelectItem[6];
			tipiOperazioniLogItems[0] = new SelectItem(null, "operazione");
			tipiOperazioniLogItems[1] = new SelectItem("NEW", "NEW");
			tipiOperazioniLogItems[2] = new SelectItem("DELETE", "DELETE");
			tipiOperazioniLogItems[3] = new SelectItem("MODIFY", "MODIFY");
			tipiOperazioniLogItems[4] = new SelectItem("LOGIN", "LOGIN");
			tipiOperazioniLogItems[5] = new SelectItem("LOGOUT", "LOGOUT");
		}
		return tipiOperazioniLogItems;
	}

	public SelectItem[] getFileTypeItems() {
		if (fileTypeItems == null || fileTypeItems.length == 0) {
			fileTypeItems = new SelectItem[5];
			fileTypeItems[0] = new SelectItem(0, "css");
			fileTypeItems[1] = new SelectItem(1, "img");
			fileTypeItems[2] = new SelectItem(2, "flash");
			fileTypeItems[3] = new SelectItem(3, "javascript");
			fileTypeItems[4] = new SelectItem(4, "docs");
		}
		return fileTypeItems;
	}

	public SelectItem[] getStaticoDinamicoItems() {
		if (staticoDinamicoItems == null || staticoDinamicoItems.length == 0) {
			staticoDinamicoItems = new SelectItem[3];
			staticoDinamicoItems[0] = new SelectItem(null, "qualsiasi");
			staticoDinamicoItems[1] = new SelectItem(true, "statico");
			staticoDinamicoItems[2] = new SelectItem(false, "dinamico");
		}
		return staticoDinamicoItems;
	}

	public SelectItem[] getCategorieAttivitaItems() {
		if (categorieAttivitaItems == null
				|| categorieAttivitaItems.length == 0) {
			categorieAttivitaItems = new SelectItem[1];
			categorieAttivitaItems[0] = new SelectItem(null,
					"nessuna categoria");
			List<CategoriaAttivita> categorie = categorieSession
					.getAllCategoriaAttivita();
			if (categorie != null && categorie.size() > 0) {
				categorieAttivitaItems = new SelectItem[categorie.size() + 1];
				categorieAttivitaItems[0] = new SelectItem(null, "categoria");
				int i = 1;
				for (CategoriaAttivita c : categorie) {
					categorieAttivitaItems[i] = new SelectItem(c.getId(),
							c.getCategoria());
					i++;

				}
			}
		}
		return categorieAttivitaItems;
	}

	public void cambioTipo(ActionEvent event) {
		int tipo = attivitaHandler.getTipoId();
		cambioTipoDirect(tipo);
	}

	public void cambioTipoRicerca(ActionEvent event) {
		Long tipo = attivitaHandler.getRicerca().getOggetto().getCategoria()
				.getTipoAttivita().getId();
		if (tipo != null)
			cambioTipoDirect(tipo.intValue());
		else {

			categorieAttivitaItems = new SelectItem[1];
			categorieAttivitaItems[0] = new SelectItem(null, "categoria");
		}
	}

	public void cambioTipoDirect(int tipo) {
		logger.info("getCategorieByTipoItems: Tipo: " + tipo);
		List<CategoriaAttivita> categorie = categorieSession
				.getAllCategoriaAttivitaByTipo(new Long(tipo));
		if (categorie != null && categorie.size() > 0) {
			categorieAttivitaItems = new SelectItem[categorie.size() + 1];
			categorieAttivitaItems[0] = new SelectItem(null, "categoria");
			int i = 1;
			for (CategoriaAttivita c : categorie) {
				categorieAttivitaItems[i] = new SelectItem(c.getId(),
						c.getCategoria());
				i++;

			}
		} else {
			categorieAttivitaItems = new SelectItem[1];
			categorieAttivitaItems[0] = new SelectItem(null, "categoria");
		}

	}

	public SelectItem[] getCategorieByTipoItems() {
		if (categorieAttivitaItems.length == 0) {
			categorieAttivitaItems = new SelectItem[1];
			categorieAttivitaItems[0] = new SelectItem(null, "categoria");
		}
		return categorieAttivitaItems;
	}

	public void resetCategorieAttivitaItems() {
		this.categorieAttivitaItems = new SelectItem[] {};
	}

	public SelectItem[] getTipiAttivitaItems() {
		if (tipiAttivitaItems == null
				|| tipiAttivitaItems.length == 0) {
			tipiAttivitaItems = new SelectItem[1];
			tipiAttivitaItems[0] = new SelectItem(null, "nessun tipo");
			List<TipoAttivita> tipi = categorieSession.getAllTipoAttivita();
			if (tipi != null && tipi.size() > 0) {
				tipiAttivitaItems = new SelectItem[tipi.size() + 1];
				tipiAttivitaItems[0] = new SelectItem(null, "tipo");
				int i = 1;
				for (TipoAttivita c : tipi) {
					tipiAttivitaItems[i] = new SelectItem(c.getId(),
							c.getTipo());
					i++;

				}
			}
		}
		return tipiAttivitaItems;
	}

	public SelectItem[] getTipiPubblicazioneItems() {
		if (tipiPubblicazioneItems == null
				|| tipiPubblicazioneItems.length == 0) {
			tipiPubblicazioneItems = new SelectItem[1];
			tipiPubblicazioneItems[0] = new SelectItem(null, "nessun tipo");
			List<TipoPubblicazione> tipi = categorieSession
					.getAllTipoPubblicazione();
			if (tipi != null && tipi.size() > 0) {
				tipiPubblicazioneItems = new SelectItem[tipi.size() + 1];
				tipiPubblicazioneItems[0] = new SelectItem(null, "tipo");
				int i = 1;
				for (TipoPubblicazione c : tipi) {
					tipiPubblicazioneItems[i] = new SelectItem(c.getId(),
							c.getNome());
					i++;

				}
			}
		}
		return tipiPubblicazioneItems;
	}

	public SelectItem[] getTipiModuloItems() {
		if (tipiModuloItems == null || tipiModuloItems.length == 0) {
			tipiModuloItems = new SelectItem[1];
			tipiModuloItems[0] = new SelectItem(null, "nessun tipo");
			List<TipoModulo> tipi = tipoModuloSession.getAllList();
			if (tipi != null && tipi.size() > 0) {
				tipiModuloItems = new SelectItem[tipi.size() + 1];
				tipiModuloItems[0] = new SelectItem(null, "tipo");
				int i = 1;
				for (TipoModulo c : tipi) {
					tipiModuloItems[i] = new SelectItem(c.getId(), c.getNome());
					i++;

				}
			}
		}
		return tipiModuloItems;
	}

	public void resetTipiAttivitaItems() {
		tipiAttivitaItems = new SelectItem[] {};
	}

	public void resetTipiPubblicazioniItems() {
		tipiPubblicazioneItems = new SelectItem[] {};
	}

	// ==============================================================================
	// ==============================================================================
	// ==============================================================================
	// ==============================================================================
	// ==============================================================================

	// from FLOWERCMS
	// ===============================================================

	// ==============================================================================
	// ==============================================================================
	// ==============================================================================
	// ==============================================================================
	// ==============================================================================
	// ==============================================================================

	public SelectItem[] getTemplateStaticiItems() {
		Ricerca<Template> ricerca = new Ricerca<Template>(Template.class);
		ricerca.getOggetto().setSearchStatico(true);
		return JSFUtils.setupItems(ricerca, templateSession, "id", "nome",
				"nessun template per pagine statiche disponibile",
				"seleziona template per pagine statiche...");
	}

	public SelectItem[] getTemplateDinamiciItems() {
		Ricerca<Template> ricerca = new Ricerca<Template>(Template.class);
		ricerca.getOggetto().setSearchStatico(false);
		return JSFUtils.setupItems(ricerca, templateSession, "id", "nome",
				"nessun template per pagine dinamiche disponibile",
				"seleziona template per pagine dinamiche...");
	}

	public SelectItem[] getTemplateItems() {
		Ricerca<Template> ricerca = new Ricerca<Template>(Template.class);
		return checkItems(ricerca, templateSession, "id", "nome",
				"nessun template disponibile", "seleziona template...");
	}

	public void setTemplateItems(SelectItem[] templateItems) {
		this.items.put(Template.class, templateItems);
	}

	public SelectItem[] getTipoInformazioneItems() {
		Ricerca<TipoInformazione> ricerca = new Ricerca<TipoInformazione>(
				TipoInformazione.class);
		return checkItems(ricerca, tipoInformazioniSession, "id", "nome",
				"nessun tipo disponibile", "seleziona tipo...");
	}

	public void setTipoInformazioneItems(SelectItem[] tipiItems) {
		this.items.put(TipoInformazione.class, tipiItems);
	}

	public SelectItem[] getPageItems() {
		Ricerca<Page> ricerca = new Ricerca<Page>(Page.class);
		return checkItems(ricerca, pageSession, "id", "title",
				"nessuna pagina disponibile", "seleziona pagina...");
	}

	public void setPageItems(SelectItem[] pageItems) {
		this.items.put(Page.class, pageItems);
	}

	@SuppressWarnings("rawtypes")
	private SelectItem[] checkItems(Ricerca ricerca, SuperSession ejb,
			String idField, String valueField, String emptyMessage,
			String labelMessage) {
		Class clazz = ricerca.getOggetto().getClass();
		if (items.get(clazz) == null || items.get(clazz).length == 0) {
			items.put(clazz, JSFUtils.setupItems(ricerca, ejb, idField,
					valueField, emptyMessage, labelMessage));
		}
		return items.get(clazz);
	}

	// ==============================================================================

	@SuppressWarnings("rawtypes")
	public String reset() {
		items = new HashMap<Class, SelectItem[]>();
		return null;
	}

	public SelectItem[] getRisorseItems() {
		return new SelectItem[] { new SelectItem("img", "immagini"),
				new SelectItem("js", "funzioni javascript"),
				new SelectItem("css", "fogli di stile"),
				new SelectItem("swf", "contenuti flash"),
				new SelectItem("docs", "documenti") };
	}

	public void setTipiModuloItems(SelectItem[] tipiModuloItems) {
		this.tipiModuloItems = tipiModuloItems;
	}

}
