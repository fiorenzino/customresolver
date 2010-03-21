package it.jflower.session;

import it.jflower.par.Menu;
import it.jflower.par.Template;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.seamframework.tx.Transactional;

@Named
@Dependent
public class TemplateSession {

	@Inject
	EntityManager em;

	@Transactional
	public void create(Template menu) {
		try {
			em.persist(menu);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public void update(Template template) {
		try {
			em.merge(template);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public void delete(Template template) {
		try {
			Template ban = em.find(Template.class, template.getId());
			em.remove(template);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
