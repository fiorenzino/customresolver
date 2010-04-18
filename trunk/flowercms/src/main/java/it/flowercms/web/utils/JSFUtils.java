package it.flowercms.web.utils;

import javax.enterprise.context.spi.CreationalContext;

import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.naming.Context;
import javax.naming.InitialContext;


import it.flowercms.par.base.Ricerca;
import it.flowercms.session.base.SuperSession;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;

import javax.faces.model.SelectItem;

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

			Bean phBean = (Bean) beanManager.getBeans(beanClass)
					.iterator().next();
			CreationalContext cc = beanManager.createCreationalContext(phBean);
			T bean = (T) beanManager.getReference(phBean, beanClass,
					cc);
			return bean;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static int count(Collection collection) {
		return collection == null ? 0 : collection.size();
	}

	/**
	 * @param ricerca
	 * @param ejb
	 * @param idField il nome del campo del par il cui valore è da usare come selectItem.value
	 * @param valueField il nome del campo del par il cui valore è da usare selectItem.label
	 * @param emptyMessage messaggio da mettere in caso di no risultati: selectItem(null,"nessun entity trovato...")
	 * @param labelMessage messaggio da mettere nel primo selectitem in caso di no-selezione: select(null,"scegli l'entity....")
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

}
