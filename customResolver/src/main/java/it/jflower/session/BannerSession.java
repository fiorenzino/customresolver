package it.jflower.session;

import it.jflower.par.Banner;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.seamframework.tx.Transactional;

@Named
@RequestScoped
public class BannerSession {

	@Inject
	EntityManager em;

	@Transactional
	public void create(Banner banner) {
		try {
			em.persist(banner);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public void update(Banner banner) {
		try {
			em.merge(banner);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public void delete(Banner banner) {
		try {
			Banner ban = em.find(Banner.class, banner.getId());
			em.remove(banner);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
