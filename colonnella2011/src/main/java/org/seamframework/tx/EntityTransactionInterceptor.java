package org.seamframework.tx;

import it.jflower.cc.utils.DbUtils;

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

	protected Logger logger = Logger.getLogger(getClass());

	private @Inject
	@Any
	EntityManager em;

	@AroundInvoke
	public Object aroundInvoke(InvocationContext ic)
	// throws Exception
	{
//		if ( true ) {
//			return null;
//		}

		if (em == null) {
			logger.info("Renewing ETX's EM...");
			em = DbUtils.getEM();
		}
		logger.info("Entity tx interceptor running...");
		boolean toManage = (em != null && em.isOpen() && !em.getTransaction()
				.isActive());
		if (toManage) {
			try {
				em.getTransaction().begin();
				logger.info("...tx has begun...");
			} catch (Exception e) {
				logger.info(e.getMessage());
				logger.info("...provo a ricostruirla..." + e.getMessage());
				em.clear();
			}

		} else {
//			return null;
			logger.info("Renewing ETX's EM...");
			em = DbUtils.getEM();
			em.getTransaction().begin();
		}
		boolean isActive = (em != null && em.isOpen() && em.getTransaction()
				.isActive());
		try {
			Object result = ic.proceed();
			isActive = em.getTransaction().isActive();
			if (toManage && isActive) {
				em.getTransaction().commit();
				logger.info("...tx has succeeded!");
			}
			return result;
		} catch (Exception e) {
			if (toManage && isActive) {
				// em.getTransaction().rollback();
				if (em != null)
					em.clear();
				// em = DbUtils.getEM();

				logger.info("...tx has failed!" + e.getMessage());
			}
			return null;
			// throw e;
		}
	}
}
