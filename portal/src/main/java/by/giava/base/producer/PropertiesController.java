package by.giava.base.producer;

import it.coopservice.commons2.domain.Search;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import by.giava.base.model.Template;
import by.giava.base.repository.TemplateSession;

@Named
@SessionScoped
@SuppressWarnings("rawtypes")
public class PropertiesController implements Serializable {

	private static final long serialVersionUID = 1L;

	private Map<Class, SelectItem[]> items = null;

	@Inject
	TemplateSession templateSession;
	
	@PostConstruct
	public void reset() {
		items = new HashMap<Class, SelectItem[]>();
	}

	public void resetItemsForClass(Class clazz) {
		if (items.containsKey(clazz)) {
			items.remove(clazz);
		}
	}

	@Produces
	@Named
	public SelectItem[] getSiNoItems() {
		List<SelectItem> valori = new ArrayList<SelectItem>();
		valori.add(new SelectItem(true,"SI"));
		valori.add(new SelectItem(false,"NO"));
		return valori.toArray(new SelectItem[] {});
	}
	
	@Produces
	@Named
	public SelectItem[] getSiNoNullItems() {
		List<SelectItem> valori = new ArrayList<SelectItem>();
		valori.add(new SelectItem(null,"..."));
		valori.add(new SelectItem(true,"SI"));
		valori.add(new SelectItem(false,"NO"));
		return valori.toArray(new SelectItem[] {});
	}

	@Produces
	@Named
	public SelectItem[] getTemplateItems() {
		if ( items.get(Template.class) == null || items.get(Template.class).length  == 0 ) {
		List<SelectItem> valori = new ArrayList<SelectItem>();
		valori.add(new SelectItem(null,"template..."));
		for ( Template t : templateSession.getList(new Search<Template>(Template.class),0,0) ) {
			valori.add(new SelectItem(t.getId(), t.getNome()));
		}
		items.put(Template.class,valori.toArray(new SelectItem[]{}));
		}
		return items.get(Template.class);
	}

	@Produces
	@Named
	public SelectItem[] getApplicationItems() {
		List<SelectItem> valori = new ArrayList<SelectItem>();
		valori.add(new SelectItem(null,"applicazione..."));
		for ( Application s : Application.values() ) {
			valori.add(new SelectItem(s.name()));
		}
		return valori.toArray(new SelectItem[] {});
	}

	@Produces
	@Named
	public SelectItem[] getCanaliItems() {
		List<SelectItem> valori = new ArrayList<SelectItem>();
		valori.add(new SelectItem(null,"canale..."));
		for ( Channel s : Channel.values() ) {
			valori.add(new SelectItem(s.name()));
		}
		return valori.toArray(new SelectItem[] {});
	}

