package weld.view.utils;

import java.security.Principal;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;

import weld.view.PagesHandler;

public class JSFUtils {
	/*
	 * public static PagesHandler getPageHandler() {
	 * 
	 * PagesHandler pagesHandler = null; try { Context initCtx = new
	 * InitialContext(); Context envCtx = (Context)
	 * initCtx.lookup("java:comp/env"); BeanManager beanManager = (BeanManager)
	 * envCtx .lookup("BeanManager");
	 * 
	 * Bean phBean = (Bean) beanManager.getBeans(PagesHandler.class)
	 * .iterator().next(); CreationalContext cc =
	 * beanManager.createCreationalContext(phBean); pagesHandler =
	 * (PagesHandler) beanManager.getReference(phBean, PagesHandler.class, cc);
	 * 
	 * } catch (Exception e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } return pagesHandler; }
	 */
	public static <T> T getHandler(T handler) {

		// PagesHandler pagesHandler = null;
		try {
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			BeanManager beanManager = (BeanManager) envCtx
					.lookup("BeanManager");

			Bean phBean = (Bean) beanManager.getBeans(handler.getClass())
					.iterator().next();
			CreationalContext cc = beanManager.createCreationalContext(phBean);
			handler = (T) beanManager.getReference(phBean, handler.getClass(),
					cc);
			return handler;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return handler;
	}

	public static String getUserName() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest req = (HttpServletRequest) context
				.getExternalContext().getRequest();
		String rem = req.getRemoteUser();
		// System.out.println("******************************");
		// System.out.println("REM USER: " + rem);
		Principal pr = req.getUserPrincipal();
		// System.out.println("PRINC USER: " + pr.getName());
		// System.out.println("******************************");

		if (pr == null)
			return "colonnella";
		return pr.getName();
	}
}
