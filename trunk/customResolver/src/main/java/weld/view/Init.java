package weld.view;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.seamframework.tx.PageManager;

@Named
@ApplicationScoped
public class Init implements Serializable {

	@Inject
	Instance<PagesHandler> pageHandlerInstance;

	@Produces
	@ApplicationScoped
	@PersistenceContext(unitName = "foo")
	EntityManager em;

	@RequestScoped
	@PageManager
	public PagesHandler retrievePH() {
		System.out.println("PRODUCO PAGEHANDLER");
		return pageHandlerInstance.get();
	}
}
