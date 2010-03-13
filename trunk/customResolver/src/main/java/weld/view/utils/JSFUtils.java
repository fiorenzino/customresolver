package weld.view.utils;

import java.util.Enumeration;
import java.util.Map;

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
			// FacesContext facesContext = FacesContext.getCurrentInstance();
			// ServletContext servletContext = (ServletContext) facesContext
			// .getExternalContext().getContext();
			// WeldManager beanManager = (WeldManager) ServletHelper
			// .getModuleBeanManager(servletContext);

			// Enumeration names = new InitialContext().list("java:comp/");
			// while (names.hasMoreElements()) {
			// NameClassPair pair = (NameClassPair) names.nextElement();
			// Object boundObject = new InitialContext()
			// .lookup(pair.getName());
			// System.out.println(pair.getName());
			// // look for implementation
			// // look at pair.getName for some fancy logic in case of multiple
			// // impls of same interface
			// }
			Map<String, Object> mappa = FacesContext.getCurrentInstance()
					.getExternalContext().getApplicationMap();
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			BeanManager beanManager = (BeanManager) envCtx
					.lookup("BeanManager");

			// BeanManager beanManager = (BeanManager) new InitialContext()
			// .lookup("java:comp/BeanManager");
			Bean phBean = beanManager.getBeans(PagesHandler.class).iterator()
					.next();
			pagesHandler = (PagesHandler) phBean.create(beanManager
					.createCreationalContext(phBean));

			phBean = beanManager.getBeans(Init.class).iterator().next();
			Init init = (Init) phBean.create(beanManager
					.createCreationalContext(phBean));

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
