package it.jflower.session;

import it.jflower.par.Menu;
import it.jflower.par.Page;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.seamframework.tx.Transactional;

@Named
@RequestScoped
public class PageSession {

	@Inject
	EntityManager em;

	@Transactional
	public void create(Page page) {
		try {
			em.persist(page);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public void update(Page page) {
		try {
			em.merge(page);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public void delete(Page page) {
		try {
			Page ban = em.find(Page.class, page.getId());
			em.remove(page);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
