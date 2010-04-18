package it.flowercms.session;

import it.flowercms.par.Template;
import it.flowercms.session.base.SuperSession;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

@Named
@RequestScoped
public class TemplateSession 
extends SuperSession<Template>
implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	EntityManager em;

//	@Transactional
//	public void create(Template template) {
//		try {
//			em.persist(template);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Transactional
//	public Template find(Long id) {
//		try {
//			return em.find(Template.class, id);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//
//	}
//
//	@Transactional
//	public void update(Template template) {
//		try {
//			em.merge(template);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Transactional
//	public void delete(Template template) {
//		try {
//			Template temp = em.find(Template.class, template.getId());
//			em.remove(temp);
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
//	public List<Template> getAll() {
//		System.out.println("get all templates");
//
//		List<Template> lista = new ArrayList<Template>();
//		try {
//			lista = em.createQuery("select p from Template p order by p.id")
//					.getResultList();
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return lista;
//	}

	@Override
	public EntityManager getEm() {
		return em;
	}

	@Override
	protected Class<Template> getEntityType() {
		return Template.class;
	}

	@Override
	protected String getOrderBy() {
		return "nome";
	}

	@Override
	public void setEm(EntityManager em) {
		this.em = em;
	}
	
}
