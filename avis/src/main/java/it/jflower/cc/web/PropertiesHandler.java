package it.jflower.cc.web;

import it.jflower.base.par.Ricerca;
import it.jflower.base.session.SuperSession;
import it.jflower.base.utils.JSFUtils;
import it.jflower.cc.par.Page;
import it.jflower.cc.par.Template;
import it.jflower.cc.par.type.TipoInformazione;
import it.jflower.cc.session.PageSession;
import it.jflower.cc.session.TemplateSession;
import it.jflower.cc.session.TipoInformazioniSession;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@SessionScoped
public class PropertiesHandler implements Serializable {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	private Map<Class, SelectItem[]> items = null;

	@Inject
	private TemplateSession templateSession;

	@Inject
	private PageSession pageSession;

	@Inject
	private TipoInformazioniSession tipoInformazioniSession;

	private SelectItem[] fileTypeItems = new SelectItem[] {};

	private SelectItem[] staticoDinamicoItems = new SelectItem[] {};

	private SelectItem[] ruoliItems = new SelectItem[] {};

	public PropertiesHandler() {
		super();
		reset();
	}

	public SelectItem[] getRuoliItems() {
		if (ruoliItems == null || ruoliItems.length == 0) {
			ruoliItems = new SelectItem[2];
			ruoliItems[0] = new SelectItem(0, "admin");
			ruoliItems[1] = new SelectItem(1, "user");
		}
		return ruoliItems;
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

	public SelectItem[] getStaticoDinamicoItems() {
		if (staticoDinamicoItems == null || staticoDinamicoItems.length == 0) {
			staticoDinamicoItems = new SelectItem[3];
			staticoDinamicoItems[0] = new SelectItem(null, "qualsiasi");
			staticoDinamicoItems[1] = new SelectItem(true, "statico");
			staticoDinamicoItems[2] = new SelectItem(false, "dinamico");
		}
		return staticoDinamicoItems;
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

	@SuppressWarnings("unchecked")
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

	@SuppressWarnings("unchecked")
	public String reset() {
		items = new HashMap<Class, SelectItem[]>();
		return null;
	}

	public SelectItem[] getRisorseItems() {
		return new SelectItem[] { new SelectItem("img", "immagini"),
				new SelectItem("js", "funzioni javascript"),
				new SelectItem("css", "fogli di stile"),
				new SelectItem("swf", "contenuti flash"), };
	}
}
