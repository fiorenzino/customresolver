package it.flowercms.session;

import it.flowercms.par.Page;
import it.flowercms.session.base.SuperSession;
import it.flowercms.web.utils.PageUtils;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.seamframework.tx.Transactional;

@Named
@SessionScoped
public class PageSession
extends SuperSession<Page>
implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	EntityManager em;

	@Override
	public EntityManager getEm() {
		return em;
	}

	@Override
	protected Class<Page> getEntityType() {
		return Page.class;
	}

	@Override
	protected String getOrderBy() {
		return "title";
	}

	@Override
	public void setEm(EntityManager em) {
		this.em = em;
	}

//	@Transactional
//	public void create(Page page) {
//		try {
//			em.persist(page);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Transactional
//	public Page find(String id) {
//		try {
//			return em.find(Page.class, id);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//
//	}
//
//	@Transactional
//	public void update(Page page) {
//		try {
//			em.merge(page);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Transactional
//	public void delete(Page page) {
//		try {
//			Page ban = em.find(Page.class, page.getId());
//			em.remove(ban);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Transactional
//	public Page save(Page page) {
//		try {
//			page.setId(PagesUtils.createPageId(page.getTitle()));
//			em.persist(page);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return page;
//
//	}
//
//	@SuppressWarnings("unchecked")
//	@Transactional
//	public List<Page> getAll() {
//		System.out.println("get all pages");
//
//		List<Page> lista = new ArrayList<Page>();
//		try {
//			lista = em.createQuery("select p from Page p order by p.id")
//					.getResultList();
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return lista;
//	}

	@Override
	@Transactional
	public Page persist(Page page) {
		page.setId(PageUtils.createPageId(page.getTitle()));
		return super.persist(page);
	}

}
