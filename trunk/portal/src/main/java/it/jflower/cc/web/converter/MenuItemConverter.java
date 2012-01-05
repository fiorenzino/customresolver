package it.jflower.cc.web.converter;

import it.jflower.cc.par.MenuItem;
import it.jflower.cc.session.MenuSession;
import it.jflower.cc.session.PageSession;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;

@Named
@RequestScoped
public class MenuItemConverter implements Converter {

	Logger logger = Logger.getLogger(getClass());

	public MenuItemConverter() {
	}

	@Inject
	MenuSession menuSession;
	@Inject
	PageSession pageSession;

	public Object getAsObject(FacesContext facesContextImpl,
			UIComponent pickList, String menuItemAsString) {
		if (menuItemAsString == null || menuItemAsString.equals(""))
			return null;
		// return ObjectUtils.deserialize(menuItemAsString);
		if (menuItemAsString.indexOf("mID") >= 0)
			return menuSession.findItem(Long.parseLong(menuItemAsString
					.substring(menuItemAsString.indexOf("mID") + 3)));
		else if (menuItemAsString.indexOf("pID") >= 0) {
			return new MenuItem(pageSession.find(menuItemAsString
					.substring(menuItemAsString.indexOf("pID") + 3)), null);
		} else {
			return null;
		}
	}

	public String getAsString(FacesContext facesContextImpl,
			UIComponent pickList, Object menuItemAsObject) {
		if (menuItemAsObject == null)
			return "";
		// return ObjectUtils.serialize(menuItemAsObject);
		MenuItem mi = (MenuItem) menuItemAsObject;
		if (mi.getId() != null)
			return "mID" + mi.getId();
		else
			return "pID" + mi.getPagina().getId();
	}

}
