package by.giava.base.repository;

import it.coopservice.commons2.domain.Search;
import it.coopservice.commons2.repository.AbstractRepository;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import by.giava.base.controller.util.PageUtils;
import by.giava.base.model.Page;
import by.giava.base.model.Template;

@Named
@Stateless
@LocalBean
public class PageRepository extends AbstractRepository<Page> {

	private static final long serialVersionUID = 1L;

	@Inject
	EntityManager em;

	@Inject
	TemplateRepository templateRepository;

	@Override
	public EntityManager getEm() {
		return em;
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
		Template template = templateRepository.find(page.getTemplate()
				.getTemplate().getId());
		page.getTemplate().setTemplate(template);
		page.setId(idFinal);
		return page;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	protected Page preUpdate(Page page) {
		Template template = templateRepository.find(page.getTemplate()
				.getTemplate().getId());
		page.getTemplate().setTemplate(template);
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
	protected Query getRestrictions(Search<Page> search, boolean justCount) {

		if (search == null || search.getObj() == null)
			return super.getRestrictions(search, justCount);

		Map<String, Object> params = new HashMap<String, Object>();

		String alias = "t";
		StringBuffer sb = new StringBuffer(getBaseList(getEntityType(), alias,
				justCount));
		sb.append(" where ").append(alias).append(".attivo = :attivo");
		params.put("attivo", true);

		String separator = " and ";

		if (search.getObj().getTemplate() != null
				&& search.getObj().getTemplate().getTemplate() != null
				&& search.getObj().getTemplate().getTemplate()
						.getSearchStatico() != null) {
			sb.append(separator).append(alias)
					.append(".template.template.statico = :statico ");
			params.put("statico", search.getObj().getTemplate().getTemplate()
					.getSearchStatico());
		}

		if (search.getObj().getTitle() != null
				&& !search.getObj().getTitle().isEmpty()) {
			sb.append(separator).append(alias).append(".title like :title ");
			params.put("title", likeParam(search.getObj().getTitle()));
		}

		if (!justCount) {
			sb.append(getOrderBy(alias, search.getOrder()));
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

	@Override
	protected String getDefaultOrderBy() {
		return "title";
	}

}
