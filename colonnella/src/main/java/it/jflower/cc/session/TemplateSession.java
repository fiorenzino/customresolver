package it.jflower.cc.session;

import it.jflower.base.par.Ricerca;
import it.jflower.base.session.SuperSession;
import it.jflower.cc.par.Template;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;

@Named
@SessionScoped
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
	
	/**
	 * criteri di default, comuni a tutti, ma specializzabili da ogni EJB
	 * tramite overriding
	 */
	@Override
	protected Query getRestrictions(Ricerca<Template> ricerca, String orderBy,
			boolean count) {
		
		if ( ricerca == null || ricerca.getOggetto() == null )
			return super.getRestrictions(ricerca, orderBy, count);

		Map<String,Object> params = new HashMap<String, Object>();
		
		String alias = "t";
		StringBuffer sb = new StringBuffer(getBaseList(getEntityType(), alias,
				count));
		sb.append(" where ").append(alias).append(".attivo = :attivo");
		params.put("attivo", true);
		
		String separator = " and ";

		if (ricerca.getOggetto().getSearchStatico() != null) {
			sb.append(separator).append(alias).append(".statico = :statico ");
			params.put("statico", ricerca.getOggetto().getSearchStatico());
		}
		
		if (!count) {
			sb.append(" order by ").append(alias).append(".").append(orderBy);
		} else {
			logger.info("order by null");
		}
		
		logger.info(sb.toString());
		
		Query q = getEm().createQuery(sb.toString());
		
		for ( String param : params.keySet() ) {
			q.setParameter(param, params.get(param));
		}

		return q;
	}

	@Override
	protected Template prePersist(Template t) {
		closeHtmlTags(t);
		return t;
	}

	@Override
	protected Template preUpdate(Template t) {
		closeHtmlTags(t);
		return t;
	}

	private void closeHtmlTags(Template t) {
		t.setHeader_start( t.getHeader_start() == null ? null : t.getHeader_start().replaceAll("class=\"replaceMe \">", "class=\"replaceMe\"/>").replaceAll("<br>", "<br/>") );
		t.setHeader_stop( t.getHeader_stop() == null ? null : t.getHeader_stop().replaceAll("class=\"replaceMe \">", "class=\"replaceMe\"/>").replaceAll("<br>", "<br/>") );
		t.setCol1_start( t.getCol1_start() == null ? null : t.getCol1_start().replaceAll("class=\"replaceMe \">", "class=\"replaceMe\"/>").replaceAll("<br>", "<br/>") );
		t.setCol1_stop( t.getCol1_stop() == null ? null : t.getCol1_stop().replaceAll("class=\"replaceMe \">", "class=\"replaceMe\"/>").replaceAll("<br>", "<br/>") );
		t.setCol2_start( t.getCol2_start() == null ? null : t.getCol2_start().replaceAll("class=\"replaceMe \">", "class=\"replaceMe\"/>").replaceAll("<br>", "<br/>") );
		t.setCol2_stop( t.getCol2_stop() == null ? null : t.getCol2_stop().replaceAll("class=\"replaceMe \">", "class=\"replaceMe\"/>").replaceAll("<br>", "<br/>") );
		t.setCol3_start( t.getCol3_start() == null ? null : t.getCol3_start().replaceAll("class=\"replaceMe \">", "class=\"replaceMe\"/>").replaceAll("<br>", "<br/>") );
		t.setCol3_stop( t.getCol3_stop() == null ? null : t.getCol3_stop().replaceAll("class=\"replaceMe \">", "class=\"replaceMe\"/>").replaceAll("<br>", "<br/>") );
		t.setFooter_start( t.getFooter_start() == null ? null : t.getFooter_start().replaceAll("class=\"replaceMe \">", "class=\"replaceMe\"/>").replaceAll("<br>", "<br/>") );
		t.setFooter_stop( t.getFooter_stop() == null ? null : t.getFooter_stop().replaceAll("class=\"replaceMe \">", "class=\"replaceMe\"/>").replaceAll("<br>", "<br/>") );
	}
}
