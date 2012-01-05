package by.giava.base.controller;

import java.io.Serializable;

import javax.annotation.Resource;
import javax.enterprise.inject.Produces;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

@Named
// @ApplicationScoped
public class InitHandler implements Serializable {

	private static final long serialVersionUID = 1L;

	// @Produces
	// // @ApplicationScoped
	// @PersistenceContext(unitName = "colonnella")
	// EntityManager em;

	// @Produces
	// @PersistenceContext(unitName = "colonnella")
	// @RequestScoped
	// public EntityManager getEntityManager() {
	// return DbUtils.getEM();
	// }

	@Resource(mappedName = "java:jboss/datasources/colonnella")
	DataSource ds;

	@SuppressWarnings("unused")
	@Produces
	@PersistenceContext
	private EntityManager em;

	public DataSource getDs() {
		return ds;
	}

	public void setDs(DataSource ds) {
		this.ds = ds;
	}

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

}
