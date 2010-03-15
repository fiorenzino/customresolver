package weld.view.utils;

import java.util.Enumeration;
import java.util.Map;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.servlet.ServletContext;

import org.jboss.weld.manager.api.WeldManager;
import org.jboss.weld.servlet.ServletHelper;

import weld.model.Page;
import weld.view.Init;
import weld.view.PagesHandler;

public class JSFUtils {

	public static PagesHandler getPageHandler() {

		PagesHandler pagesHandler = null;
		try {
	
			
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			BeanManager beanManager = (BeanManager) envCtx
					.lookup("BeanManager");


			Bean phBean = (Bean) beanManager.getBeans(PagesHandler.class)
					.iterator().next();
			CreationalContext cc = beanManager.createCreationalContext(phBean);
			pagesHandler = (PagesHandler) beanManager.getReference(phBean,
					PagesHandler.class, cc);
			Page page2 = pagesHandler.findPage("chi-siamo");
			if (page2 != null) {
				System.out.println(page2.getContent());
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
			System.out.println("Faces Context Application null");
		}
		return fc.getApplication().getELResolver().getValue(fc.getELContext(),
				null, name);
	}
}
