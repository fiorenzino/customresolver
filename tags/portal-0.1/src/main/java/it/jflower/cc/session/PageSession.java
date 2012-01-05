package it.jflower.cc.session;

import it.jflower.base.par.Ricerca;
import it.jflower.base.session.SuperSession;
import it.jflower.cc.par.Page;
import it.jflower.cc.utils.DbUtils;
import it.jflower.cc.utils.PageUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;

@Named
@Stateless
@LocalBean
public class PageSession extends SuperSession<Page> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	EntityManager em;

	@Override
	public EntityManager getEm() {
		if (em == null) {
			this.em = DbUtils.getEM();
		}
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
	protected Page prePersist(Page page) {
		// closeHtmlTags(page);
		String idTitle = PageUtils.createPageId(page.getTitle());
		String idFinal = testId(idTitle);
		page.setId(idFinal);
		return page;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	protected Page preUpdate(Page page) {
		// closeHtmlTags(page);
		return page;
	}

	public String testId(String id) {
		String idFinal = id;
		boolean trovato = false;
		int i = 0;
		while (!trovato) {
			// logger.info("id final: " + idFinal);
			Page pageFind = find(idFinal);
			// Page pageFind = find(new Long(1));
			// logger.info("trovato_ " + pageFind);
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

		if (ricerca == null || ricerca.getOggetto() == null)
			return super.getRestrictions(ricerca, orderBy, count);

		Map<String, Object> params = new HashMap<String, Object>();

		String alias = "t";
		StringBuffer sb = new StringBuffer(getBaseList(getEntityType(), alias,
				count));
		sb.append(" where ").append(alias).append(".attivo = :attivo");
		params.put("attivo", true);

		String separator = " and ";

		if (ricerca.getOggetto().getTemplate() != null
				&& ricerca.getOggetto().getTemplate().getTemplate() != null
				&& ricerca.getOggetto().getTemplate().getTemplate()
						.getSearchStatico() != null) {
			sb.append(separator).append(alias)
					.append(".template.template.statico = :statico ");
			params.put("statico", ricerca.getOggetto().getTemplate()
					.getTemplate().getSearchStatico());
		}

		if (ricerca.getOggetto().getTitle() != null
				&& !ricerca.getOggetto().getTitle().isEmpty()) {
			sb.append(separator).append(alias).append(".title like :title ");
			params.put("title", likeParam(ricerca.getOggetto().getTitle()));
		}

		if (!count) {
			sb.append(" order by ").append(alias).append(".").append(orderBy);
		} else {
			logger.info("order by null");
		}

		logger.info(sb.toString());

		Query q = getEm().createQuery(sb.toString());

		for (String param : params.keySet()) {
			q.setParameter(param, params.get(param));
		}

		return q;
	}

	public Page fetch(String id) {
		logger.info("ENTRO fetchPage: " + id);
		Page ret = null;
		try {
			ret = (Page) em
					.createQuery(
							"select p from Page p left join fetch p.template ti left join fetch ti.template t where p.id = :ID ")
					.setParameter("ID", id).setMaxResults(1).getSingleResult();
			em.refresh(ret);

		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		logger.info("ESCO fetchPage: " + id);
		return ret;
	}

}