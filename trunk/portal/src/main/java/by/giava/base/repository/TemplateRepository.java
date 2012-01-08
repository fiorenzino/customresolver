package by.giava.base.repository;

import it.coopservice.commons2.domain.Search;
import it.coopservice.commons2.repository.AbstractRepository;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import by.giava.base.model.Template;

@Named
@Stateless
@LocalBean
public class TemplateRepository extends AbstractRepository<Template> implements
		Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	EntityManager em;

	@Override
	public EntityManager getEm() {
		return em;
	}

	@Override
	protected String getDefaultOrderBy() {
		return "nome asc";
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
	protected Query getRestrictions(Search<Template> search, boolean justCount) {

		if (search == null || search.getObj() == null)
			return super.getRestrictions(search, justCount);

		Map<String, Object> params = new HashMap<String, Object>();

		String alias = "t";
		StringBuffer sb = new StringBuffer(getBaseList(getEntityType(), alias,
				justCount));
		sb.append(" where ").append(alias).append(".attivo = :attivo");
		params.put("attivo", true);

		String separator = " and ";

		if (search.getObj().getSearchStatico() != null) {
			sb.append(separator).append(alias).append(".statico = :statico ");
			params.put("statico", search.getObj().getSearchStatico());
		}

		if (search.getObj().getNome() != null
				&& !search.getObj().getNome().isEmpty()) {
			sb.append(separator).append(alias).append(".nome like :nome ");
			params.put("nome", likeParam(search.getObj().getNome()));
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

	@Override
	protected Template prePersist(Template t) {
		// closeHtmlTags(t);
		return t;
	}

	@Override
	protected Template preUpdate(Template t) {
		// closeHtmlTags(t);
		return t;
	}

}
