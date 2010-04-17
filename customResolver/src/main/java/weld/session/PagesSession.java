package weld.session;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.seamframework.tx.Transactional;

import weld.model.Page;

@Named
@SessionScoped
public class PagesSession implements Serializable {

	@Inject
	EntityManager em;

	@Transactional
	public Page update(Page page) {
		try {
			em.merge(page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}

	@Transactional
	public Page persist(Page page) {
		try {
			em.persist(page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}

	@Transactional
	public Page find(String id) {
		try {
			Page page = em.find(Page.class, id);
			return page;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Transactional
	public void delete(String id) {
		try {
			Page page = em.find(Page.class, id);
			em.remove(page);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public List<Page> getAll() {

		List<Page> all = new ArrayList<Page>();
		try {
			all = em.createQuery("select p from Page p order by p.id")
					.getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return all;
	}
}