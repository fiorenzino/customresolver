package weld.view;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Named
@ApplicationScoped
public class InitHandler implements Serializable {

	@Produces
	@ApplicationScoped
	@PersistenceContext(unitName = "customresolver")
	EntityManager em;

}
