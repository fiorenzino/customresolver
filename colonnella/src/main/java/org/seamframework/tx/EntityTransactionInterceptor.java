package org.seamframework.tx;

import java.io.Serializable;

import javax.enterprise.inject.Any;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

/**
 * Declarative JPA EntityTransactions
 * 
 * @author Gavin King
 */
@Transactional
@Interceptor
public class EntityTransactionInterceptor implements Serializable {

	private static final long serialVersionUID = 1L;

	private Logger logger = Logger.getLogger(getClass());

	private @Inject
	@Any
	EntityManager em;

	@AroundInvoke
	public Object aroundInvoke(InvocationContext ic) throws Exception {
		logger.debug("Entity tx interceptor running...");
		boolean toManage = !em.getTransaction().isActive();
		if (toManage) {
			em.getTransaction().begin();
			logger.debug("...tx has begun...");
		}
		boolean isActive = em.getTransaction().isActive();
		try {
			Object result = ic.proceed();
			isActive = em.getTransaction().isActive();
			if (toManage && isActive) {
				em.getTransaction().commit();
				logger.debug("...tx has succeeded!");
			}
			return result;
		} catch (Exception e) {
			if (toManage && isActive) {
				em.getTransaction().rollback();
				logger.debug("...tx has failed!");
			}
			throw e;
		}
	}

}
