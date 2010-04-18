package it.flowercms.web;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Named
@ApplicationScoped
public class Init implements Serializable {

	private static final long serialVersionUID = 1L;

	@Produces
	@ApplicationScoped
	@PersistenceContext(unitName = "foo")
	EntityManager em;

}
