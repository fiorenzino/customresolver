package it.jflower.cc.web;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

@Named
@SessionScoped
public class InitHandler implements Serializable {

	private static final long serialVersionUID = 1L;

	@Produces
	@SessionScoped
	@PersistenceContext(unitName = "colonnella", type = PersistenceContextType.TRANSACTION)
	EntityManager em;

}
