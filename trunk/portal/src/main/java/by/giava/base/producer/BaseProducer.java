package by.giava.base.producer;

import it.coopservice.commons2.domain.Search;
import it.coopservice.commons2.repository.AbstractRepository;
import it.coopservice.commons2.utils.JSFUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;

import by.giava.attivita.controller.AttivitaController;
import by.giava.attivita.model.type.CategoriaAttivita;
import by.giava.attivita.model.type.TipoAttivita;
import by.giava.attivita.repository.CategorieAttivitaRepository;
import by.giava.attivita.repository.TipoAttivitaRepository;
import by.giava.base.model.Page;
import by.giava.base.model.Template;
import by.giava.base.repository.PageRepository;
import by.giava.moduli.model.type.TipoModulo;
import by.giava.moduli.repository.TipoModuloRepository;
import by.giava.news.repository.TipoInformazioniRepository;
import by.giava.notizie.model.type.TipoInformazione;
import by.giava.pubblicazioni.model.type.TipoPubblicazione;
import by.giava.pubblicazioni.repository.TipoPubblicazioneRepository;

@SessionScoped
@Named
public class BaseProducer implements Serializable {

	private Logger logger = Logger.getLogger(getClass());

	private static final long serialVersionUID = 1L;

	@Inject
	CategorieAttivitaRepository categorieAttivitaRepository;

	@Inject
	AttivitaController attivitaController;

	@SuppressWarnings("rawtypes")
	private Map<Class, SelectItem[]> items = null;

	@Inject
	private PageRepository pageRepository;

	

	@Inject
	private TipoModuloRepository tipoModuloRepository;

	@Inject
	TipoAttivitaRepository tipoAttivitaRepository;

	@Inject
	TipoPubblicazioneRepository tipoPubblicazioneRepository;

	private SelectItem[] fileTypeItems = new SelectItem[] {};
	private SelectItem[] categorieAttivitaItems = new SelectItem[] {};
	private SelectItem[] tipiAttivitaItems = new SelectItem[] {};
	private SelectItem[] tipiPubblicazioneItems = new SelectItem[] {};
	private SelectItem[] tipiModuloItems = new SelectItem[] {};

	private SelectItem[] staticoDinamicoItems = new SelectItem[] {};

	private SelectItem[] ruoliItems = new SelectItem[] {};

	private SelectItem[] tipiOperazioniLogItems = new SelectItem[] {};

	public BaseProducer() {
		// TODO Auto-generated constructor stub
	}

	@Produces
	@Named
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
	@Produces
	@Named
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

	@Produces
	@Named
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

	@Produces
	@Named
	public SelectItem[] getCategorieAttivitaItems() {
		if (categorieAttivitaItems == null
				|| categorieAttivitaItems.length == 0) {
			categorieAttivitaItems = new SelectItem[1];
			categorieAttivitaItems[0] = new SelectItem(null,
					"nessuna categoria");
			List<CategoriaAttivita> categorie = categorieAttivitaRepository
					.getAllList();
			if (categorie != null && categorie.size() > 0) {
				categorieAttivitaItems = new SelectItem[categorie.size() + 1];
				categorieAttivitaItems[0] = new SelectItem(null, "categoria");
				int i = 1;
				for (CategoriaAttivita c : categorie) {
					categorieAttivitaItems[i] = new SelectItem(c.getId(),
							c.getNome());
					i++;

				}
			}
		}
		return categorieAttivitaItems;
	}

	public void cambioTipoRicerca() {
		Long tipo = attivitaController.getSearch().getObj().getCategoria()
				.getTipoAttivita().getId();
		if (tipo != null)
			cambioTipoDirect(tipo);
		else {

			categorieAttivitaItems = new SelectItem[1];
			categorieAttivitaItems[0] = new SelectItem(null, "categoria");
		}
	}

	public void cambioTipoDirect(Long tipo) {
		logger.info("getCategorieByTipoItems: Tipo: " + tipo);
		CategoriaAttivita categoriaAttivita = new CategoriaAttivita();
		categoriaAttivita.getTipoAttivita().setId(new Long(tipo));
		Search<CategoriaAttivita> search = new Search<CategoriaAttivita>(
				categoriaAttivita);
		List<CategoriaAttivita> categorie = categorieAttivitaRepository
				.getList(search, 0, 0);

		if (categorie != null && categorie.size() > 0) {
			categorieAttivitaItems = new SelectItem[categorie.size() + 1];
			categorieAttivitaItems[0] = new SelectItem(null, "categoria");
			int i = 1;
			for (CategoriaAttivita c : categorie) {
				categorieAttivitaItems[i] = new SelectItem(c.getId(),
						c.getNome());
				i++;

			}
		} else {
			categorieAttivitaItems = new SelectItem[1];
			categorieAttivitaItems[0] = new SelectItem(null, "categoria");
		}

	}

	@Produces
	@Named
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

	@Produces
	@Named
	public SelectItem[] getTipiAttivitaItems() {
		if (tipiAttivitaItems == null || tipiAttivitaItems.length == 0) {
			tipiAttivitaItems = new SelectItem[1];
			tipiAttivitaItems[0] = new SelectItem(null, "nessun tipo");
			List<TipoAttivita> tipi = tipoAttivitaRepository.getAllList();
			if (tipi != null && tipi.size() > 0) {
				tipiAttivitaItems = new SelectItem[tipi.size() + 1];
				tipiAttivitaItems[0] = new SelectItem(null, "tipo");
				int i = 1;
				for (TipoAttivita c : tipi) {
					tipiAttivitaItems[i] = new SelectItem(c.getId(),
							c.getNome());
					i++;

				}
			}
		}
		return tipiAttivitaItems;
	}

	@Produces
	@Named
	public SelectItem[] getTipiPubblicazioneItems() {
		if (tipiPubblicazioneItems == null
				|| tipiPubblicazioneItems.length == 0) {
			tipiPubblicazioneItems = new SelectItem[1];
			tipiPubblicazioneItems[0] = new SelectItem(null, "nessun tipo");
			List<TipoPubblicazione> tipi = tipoPubblicazioneRepository
					.getAllList();
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

	@Produces
	@Named
	public SelectItem[] getTipiModuloItems() {
		if (tipiModuloItems == null || tipiModuloItems.length == 0) {
			tipiModuloItems = new SelectItem[1];
			tipiModuloItems[0] = new SelectItem(null, "nessun tipo");
			List<TipoModulo> tipi = tipoModuloRepository.getAllList();
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

	@Produces
	@Named
	public SelectItem[] getRisorseItems() {
		return new SelectItem[] { new SelectItem("img", "immagini"),
				new SelectItem("js", "funzioni javascript"),
				new SelectItem("css", "fogli di stile"),
				new SelectItem("swf", "contenuti flash"),
				new SelectItem("docs", "documenti") };
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


	public SelectItem[] getPageItems() {
		Search<Page> ricerca = new Search<Page>(Page.class);
		return checkItems(ricerca, pageRepository, "id", "title",
				"nessuna pagina disponibile", "seleziona pagina...");
	}

	public void setPageItems(SelectItem[] pageItems) {
		this.items.put(Page.class, pageItems);
	}

	@SuppressWarnings("rawtypes")
	private SelectItem[] checkItems(Search ricerca, AbstractRepository ejb,
			String idField, String valueField, String emptyMessage,
			String labelMessage) {
		Class clazz = ricerca.getObj().getClass();
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

	public void setTipiModuloItems(SelectItem[] tipiModuloItems) {
		this.tipiModuloItems = tipiModuloItems;
	}

}
