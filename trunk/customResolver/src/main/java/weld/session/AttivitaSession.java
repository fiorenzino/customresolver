package weld.session;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

@Named
@SessionScoped
public class AttivitaSession implements Serializable {

	@Inject
	EntityManager em;
}
