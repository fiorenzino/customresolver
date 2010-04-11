package it.flowercms.web.utils;

import it.flowercms.web.PagesHandler;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.naming.Context;
import javax.naming.InitialContext;

public class JSFUtils {

	public static <T> T getPageHandler(T handler) {
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
			e.printStackTrace();
		}
		return handler;
	}
}
