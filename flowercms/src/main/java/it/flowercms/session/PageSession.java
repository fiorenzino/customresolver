package it.flowercms.session;

import java.util.ArrayList;
import java.util.List;

import it.flowercms.par.Page;
import it.flowercms.web.utils.PagesUtils;

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
	public Page find(String id) {
		try {
			return em.find(Page.class, id);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

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
			em.remove(ban);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public Page save(Page page) {
		try {
			page.setId(PagesUtils.createPageId(page.getTitle()));
			em.persist(page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;

	}

	@Transactional
	public List<Page> getAll() {
		System.out.println("get all pages");

		List<Page> lista = new ArrayList<Page>();
		try {
			lista = em.createQuery("select p from Page p order by p.id")
					.getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}
}
