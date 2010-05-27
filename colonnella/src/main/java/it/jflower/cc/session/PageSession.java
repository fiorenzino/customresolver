package it.jflower.cc.session;

import it.jflower.base.par.Ricerca;
import it.jflower.base.session.SuperSession;
import it.jflower.cc.par.Page;
import it.jflower.cc.utils.PageUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.seamframework.tx.Transactional;

@Named
@SessionScoped
public class PageSession extends SuperSession<Page> implements Serializable {

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

	@Override
	@Transactional
	protected Page prePersist(Page page) {
		closeHtmlTags(page);
		String idTitle = PageUtils.createPageId(page.getTitle());
		String idFinal = testId(idTitle);
		page.setId(idFinal);
		return page;
	}

	@Override
	@Transactional
	protected Page preUpdate(Page page) {
		closeHtmlTags(page);
		return page;
	}

	public String testId(String id) {
		String idFinal = id;
		boolean trovato = false;
		int i = 0;
		while (!trovato) {
			// System.out.println("id final: " + idFinal);
			Page pageFind = find(idFinal);
			// System.out.println("trovato_ " + pageFind);
			if (pageFind != null) {
				i++;
				idFinal = id + "-" + i;
			} else {
				trovato = true;
				return idFinal;
			}
		}
		return "";
	}

	@Override
	protected Query getRestrictions(Ricerca<Page> ricerca, String orderBy,
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

		if (ricerca.getOggetto().getTemplate() != null && ricerca.getOggetto().getTemplate().getTemplate() != null && ricerca.getOggetto().getTemplate().getTemplate().getSearchStatico() != null ) {
			sb.append(separator).append(alias).append(".template.template.statico = :statico ");
			params.put("statico", ricerca.getOggetto().getTemplate().getTemplate().getSearchStatico());
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

	private void closeHtmlTags(Page page) {
		page.getTemplate().setHeader( page.getTemplate().getHeader() == null ? null : page.getTemplate().getHeader().replaceAll("class=\"replaceMe \">", "class=\"replaceMe\"/>").replaceAll("<br>", "<br/>") );
		page.getTemplate().setCol1( page.getTemplate().getCol1() == null ? null : page.getTemplate().getCol1().replaceAll("class=\"replaceMe \">", "class=\"replaceMe\"/>").replaceAll("<br>", "<br/>") );
		page.getTemplate().setCol2( page.getTemplate().getCol2() == null ? null : page.getTemplate().getCol2().replaceAll("class=\"replaceMe \">", "class=\"replaceMe\"/>").replaceAll("<br>", "<br/>") );
		page.getTemplate().setCol3( page.getTemplate().getCol3() == null ? null : page.getTemplate().getCol3().replaceAll("class=\"replaceMe \">", "class=\"replaceMe\"/>").replaceAll("<br>", "<br/>") );
		page.getTemplate().setFooter( page.getTemplate().getFooter() == null ? null : page.getTemplate().getFooter().replaceAll("class=\"replaceMe \">", "class=\"replaceMe\"/>").replaceAll("<br>", "<br/>") );
	}

}
