package it.jflower.base.utils;

import it.jflower.base.par.Ricerca;
import it.jflower.base.session.SuperSession;

import java.lang.reflect.Field;
import java.security.Principal;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

@SuppressWarnings("unchecked")
public class JSFUtils {

	static Logger logger = Logger.getLogger(JSFUtils.class);

	public static <T> T getBean(Class<T> beanClass) {
		try {
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			BeanManager beanManager = (BeanManager) envCtx
					.lookup("BeanManager");

			Bean phBean = (Bean) beanManager.getBeans(beanClass).iterator()
					.next();
			CreationalContext cc = beanManager.createCreationalContext(phBean);
			T bean = (T) beanManager.getReference(phBean, beanClass, cc);
			return bean;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static int count(Collection collection) {
		return collection == null ? 0 : collection.size();
	}

	/**
	 * @param ricerca
	 * @param ejb
	 * @param idField
	 *            il nome del campo del par il cui valore è da usare come
	 *            selectItem.value
	 * @param valueField
	 *            il nome del campo del par il cui valore è da usare
	 *            selectItem.label
	 * @param emptyMessage
	 *            messaggio da mettere in caso di no risultati:
	 *            selectItem(null,"nessun entity trovato...")
	 * @param labelMessage
	 *            messaggio da mettere nel primo selectitem in caso di
	 *            no-selezione: select(null,"scegli l'entity....")
	 * @return
	 */
	public static SelectItem[] setupItems(Ricerca ricerca, SuperSession ejb,
			String idField, String valueField, String emptyMessage,
			String labelMessage) {
		Class clazz = ricerca.getOggetto().getClass();
		SelectItem[] selectItems = new SelectItem[1];
		selectItems[0] = new SelectItem(null, emptyMessage);
		List entities = ejb.getList(ricerca, 0, 0);
		if (entities != null && entities.size() > 0) {
			selectItems = new SelectItem[entities.size() + 1];
			selectItems[0] = new SelectItem(null, labelMessage);
			int i = 1;
			for (Object o : entities) {
				try {
					Field ID_Field = clazz.getDeclaredField(idField);
					ID_Field.setAccessible(true);
					Field VALUE_Field = clazz.getDeclaredField(valueField);
					VALUE_Field.setAccessible(true);
					selectItems[i] = new SelectItem(ID_Field.get(o), ""
							+ VALUE_Field.get(o));
					i++;
				} catch (IllegalArgumentException e) {
					logger.error(e.getMessage(), e);
				} catch (IllegalAccessException e) {
					logger.error(e.getMessage(), e);
				} catch (SecurityException e) {
					logger.error(e.getMessage(), e);
				} catch (NoSuchFieldException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
		return selectItems;
	}

	public static String breadcrumbs() {
		HttpServletRequest hsr = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		String url = hsr.getRequestURL().toString();
		url = url.substring("http://".length());
		if (url.indexOf("/") >= 0)
			url = url.substring(url.indexOf("/") + 1);
		String[] crumbs = url.split("/");

		String base = "/";
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < crumbs.length; i++) {
			base += crumbs[i];
			String label = i == 0 ? "home" : crumbs[i];
			if (label.contains(".")) {
				label = label.substring(0, label.indexOf("."));
				sb.append("<b>" + label + "</b>");
			} else {
				sb.append("<a href=\"" + base + "\" title=\"" + crumbs[i]
						+ "\">" + label + "</a> ");
				sb.append("<span class=\"freccia\">&gt;</span> ");
			}
			base += "/";
		}
		return sb.toString();
	}

	public static String getUserName() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest req = (HttpServletRequest) context
				.getExternalContext().getRequest();
		// String rem = req.getRemoteUser();
		// System.out.println("******************************");
		// System.out.println("REM USER: " + rem);
		Principal pr = req.getUserPrincipal();
		// System.out.println("PRINC USER: " + pr.getName());
		// System.out.println("******************************");

		if (pr == null)
			return context.getExternalContext().getInitParameter(
					"jflower.DEFAULT_USER");
		return pr.getName();
	}

	public static boolean isUserInRole(String role) {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest req = (HttpServletRequest) context
				.getExternalContext().getRequest();
		return req.isUserInRole(role);
	}

}