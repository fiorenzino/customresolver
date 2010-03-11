package weld.view.utils;

import java.util.Map;

import javax.enterprise.inject.spi.Bean;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.jboss.weld.manager.api.WeldManager;
import org.jboss.weld.servlet.ServletHelper;

import weld.model.Page;
import weld.view.PagesHandler;

public class JSFUtils {

	public static PagesHandler getPageHandler() {

		PagesHandler pagesHandler = null;
		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			ServletContext servletContext = (ServletContext) facesContext
					.getExternalContext().getContext();
			WeldManager beanManager = (WeldManager) ServletHelper
					.getModuleBeanManager(servletContext);
			Map<String, Object> mappa = FacesContext.getCurrentInstance()
					.getExternalContext().getApplicationMap();

			Bean phBean = beanManager.getBeans(PagesHandler.class).iterator()
					.next();
			pagesHandler = (PagesHandler) phBean.create(beanManager
					.createCreationalContext(phBean));
			Page page = pagesHandler.findPage("chi-siamo");
			if (page != null) {
				System.out.println(page.getContent());
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pagesHandler;
	}

	public static Object getManagedBean(String name) {
		FacesContext fc = FacesContext.getCurrentInstance();
		if (fc == null) {
			System.out.println("Faces Context Application NULLO");
		}
		return fc.getApplication().getELResolver().getValue(fc.getELContext(),
				null, name);
	}
}
