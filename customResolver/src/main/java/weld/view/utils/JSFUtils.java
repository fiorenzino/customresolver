package weld.view.utils;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.naming.Context;
import javax.naming.InitialContext;

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

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pagesHandler;
	}

	public static <T> T getHandler(T pagesHandler) {

		// PagesHandler pagesHandler = null;
		try {
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			BeanManager beanManager = (BeanManager) envCtx
					.lookup("BeanManager");

			Bean phBean = (Bean) beanManager.getBeans(PagesHandler.class)
					.iterator().next();
			CreationalContext cc = beanManager.createCreationalContext(phBean);
			pagesHandler = (T) beanManager.getReference(phBean, pagesHandler
					.getClass(), cc);
			return pagesHandler;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pagesHandler;
	}
}
