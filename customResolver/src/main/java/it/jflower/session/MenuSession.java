package it.jflower.session;

import it.jflower.par.Banner;
import it.jflower.par.Menu;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.seamframework.tx.Transactional;

@Named
@RequestScoped
public class MenuSession {

	@Inject
	EntityManager em;

	@Transactional
	public void create(Menu menu) {
		try {
			em.persist(menu);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public void update(Menu menu) {
		try {
			em.merge(menu);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public void delete(Menu menu) {
		try {
			Menu ban = em.find(Menu.class, menu.getId());
			em.remove(menu);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
