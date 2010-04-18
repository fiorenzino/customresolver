package it.flowercms.web;

import it.flowercms.par.Page;
import it.flowercms.par.Template;
import it.flowercms.par.base.Ricerca;
import it.flowercms.session.PageSession;
import it.flowercms.session.TemplateSession;
import it.flowercms.session.base.SuperSession;
import it.flowercms.web.utils.JSFUtils;

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

	// --- constants -----------------------------------------

	private static final long serialVersionUID = 1L;

	// --- members -------------------------------------------

	@SuppressWarnings("unchecked")
	private Map<Class, SelectItem[]> items = null;

	@Inject
	private TemplateSession templateSession;

	@Inject
	private PageSession pageSession;

	// ==============================================================================

	public PropertiesHandler() {
		super();
		reset();
	}

	// ==============================================================================

	public SelectItem[] getTemplateItems() {
		Ricerca<Template> ricerca = new Ricerca<Template>(Template.class);
		return checkItems(ricerca, templateSession, "id", "nome",
				"nessun template disponibile", "seleziona template...");
	}

	public void setTemplateItems(SelectItem[] templateItems) {
		this.items.put(Template.class, templateItems);
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

}
