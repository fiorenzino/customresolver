package it.jflower.cc.web;

import it.jflower.base.par.Ricerca;
import it.jflower.base.session.SuperSession;
import it.jflower.base.utils.JSFUtils;
import it.jflower.cc.par.Page;
import it.jflower.cc.par.Template;
import it.jflower.cc.par.type.CategoriaAttivita;
import it.jflower.cc.par.type.TipoAttivita;
import it.jflower.cc.par.type.TipoInformazione;
import it.jflower.cc.par.type.TipoPubblicazione;
import it.jflower.cc.session.CategorieSession;
import it.jflower.cc.session.PageSession;
import it.jflower.cc.session.TemplateSession;
import it.jflower.cc.session.TipoInformazioniSession;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@SessionScoped
public class PropertiesHandler implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	CategorieSession categorieSession;

	@Inject
	AttivitaHandler attivitaHandler;

	@SuppressWarnings("unchecked")
	private Map<Class, SelectItem[]> items = null;

	@Inject
	private TemplateSession templateSession;

	@Inject
	private PageSession pageSession;

	@Inject
	private TipoInformazioniSession tipoInformazioniSession;

	private SelectItem[] fileTypeItems = new SelectItem[] {};
	private SelectItem[] categorieAttivitaItems = new SelectItem[] {};
	private SelectItem[] tipiAttivitaItems = new SelectItem[] {};
	private SelectItem[] tipiPubblicazioneItems = new SelectItem[] {};

	public PropertiesHandler() {
		super();
		reset();
	}

	public SelectItem[] getFileTypeItems() {
		if (fileTypeItems == null || fileTypeItems.length == 0) {
			fileTypeItems = new SelectItem[4];
			fileTypeItems[0] = new SelectItem(0, "css");
			fileTypeItems[1] = new SelectItem(1, "img");
			fileTypeItems[2] = new SelectItem(2, "flash");
			fileTypeItems[3] = new SelectItem(3, "javascript");
		}
		return fileTypeItems;
	}

	public SelectItem[] getCategorieAttivitaItems() {
		if (categorieAttivitaItems.length == 0) {
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
					categorieAttivitaItems[i] = new SelectItem(c.getId(), c
							.getCategoria());
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

	public void cambioTipoDirect(int tipo) {
		System.out.println("getCategorieByTipoItems: Tipo: " + tipo);
		List<CategoriaAttivita> categorie = categorieSession
				.getAllCategoriaAttivitaByTipo(new Long(tipo));
		if (categorie != null && categorie.size() > 0) {
			categorieAttivitaItems = new SelectItem[categorie.size() + 1];
			categorieAttivitaItems[0] = new SelectItem(null, "categoria");
			int i = 1;
			for (CategoriaAttivita c : categorie) {
				categorieAttivitaItems[i] = new SelectItem(c.getId(), c
						.getCategoria());
				i++;

			}
		}

	}

	public SelectItem[] getCategorieByTipoItems() {
		return categorieAttivitaItems;
	}

	public void resetCategorieAttivitaItems() {
		this.categorieAttivitaItems = new SelectItem[] {};
	}

	public SelectItem[] getTipiAttivitaItems() {
		if (tipiAttivitaItems.length == 0) {
			tipiAttivitaItems = new SelectItem[1];
			tipiAttivitaItems[0] = new SelectItem(null, "nessun tipo");
			List<TipoAttivita> tipi = categorieSession.getAllTipoAttivita();
			if (tipi != null && tipi.size() > 0) {
				tipiAttivitaItems = new SelectItem[tipi.size() + 1];
				tipiAttivitaItems[0] = new SelectItem(null, "tipo");
				int i = 1;
				for (TipoAttivita c : tipi) {
					tipiAttivitaItems[i] = new SelectItem(c.getId(), c
							.getTipo());
					i++;

				}
			}
		}
		return tipiAttivitaItems;
	}

	public SelectItem[] getTipiPubblicazioneItems() {
		if (tipiPubblicazioneItems.length == 0) {
			tipiPubblicazioneItems = new SelectItem[1];
			tipiPubblicazioneItems[0] = new SelectItem(null, "nessun tipo");
			List<TipoPubblicazione> tipi = categorieSession
					.getAllTipoPubblicazione();
			if (tipi != null && tipi.size() > 0) {
				tipiPubblicazioneItems = new SelectItem[tipi.size() + 1];
				tipiPubblicazioneItems[0] = new SelectItem(null, "tipo");
				int i = 1;
				for (TipoPubblicazione c : tipi) {
					tipiPubblicazioneItems[i] = new SelectItem(c.getId(), c
							.getNome());
					i++;

				}
			}
		}
		return tipiPubblicazioneItems;
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
	
	// from FLOWERCMS ===============================================================

	// ==============================================================================
	// ==============================================================================
	// ==============================================================================
	// ==============================================================================
	// ==============================================================================
	// ==============================================================================

	public SelectItem[] getTemplateStaticiItems() {
		Ricerca<Template> ricerca = new Ricerca<Template>(Template.class);
		ricerca.getOggetto().setSearchStatico(true);
		return 
		JSFUtils.setupItems(ricerca, templateSession, "id", "nome",
				"nessun template per pagine statiche disponibile", "seleziona template per pagine statiche...");
	}

	public SelectItem[] getTemplateDinamiciItems() {
		Ricerca<Template> ricerca = new Ricerca<Template>(Template.class);
		ricerca.getOggetto().setSearchStatico(false);
		return 
		JSFUtils.setupItems(ricerca, templateSession, "id", "nome",
				"nessun template per pagine dinamiche disponibile", "seleziona template per pagine dinamiche...");
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
		Ricerca<TipoInformazione> ricerca = new Ricerca<TipoInformazione>(TipoInformazione.class);
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

	@SuppressWarnings("unchecked")
	private SelectItem[] checkItems(Ricerca ricerca, SuperSession ejb, String idField,
			String valueField, String emptyMessage, String labelMessage) {
		Class clazz = ricerca.getOggetto().getClass();
		if ( items.get(clazz) == null || items.get(clazz).length == 0 ) {
			items.put( clazz, JSFUtils.setupItems(ricerca, ejb, idField, valueField, emptyMessage, labelMessage) );
		}
		return items.get(clazz);
	}

	// ==============================================================================

	@SuppressWarnings("unchecked")
	public String reset() {
		items = new HashMap<Class, SelectItem[]>();
		return null;
	}

	public SelectItem[] getRisorseItems() {
		return new SelectItem[] {
			new SelectItem("img","immagini"),	
			new SelectItem("js","funzioni javascript"),	
			new SelectItem("css","fogli di stile"),	
			new SelectItem("swf","contenuti flash"),	
		};
	}

}
