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
		if (em == null) {
			System.out.println("Renewing ETX's EM...");
			em = DbUtils.getEM();
		}
		System.out.println("Entity tx interceptor running...");
		boolean toManage = (em != null && em.isOpen() && !em.getTransaction()
				.isActive());
		if (toManage) {
			try {
				em.getTransaction().begin();
				System.out.println("...tx has begun...");
			} catch (Exception e) {
				System.out.println(e.getMessage());
				System.out.println("...provo a ricostruirla...");
				em.clear();
			}

		} else {
			System.out.println("Renewing ETX's EM...");
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
				System.out.println("...tx has succeeded!");
			}
			return result;
		} catch (Exception e) {
			if (toManage && isActive) {
				// em.getTransaction().rollback();
				if (em != null)
					em.clear();
				// em = DbUtils.getEM();

				System.out.println("...tx has failed!");
			}
			return null;
			// throw e;
		}
	}
}
