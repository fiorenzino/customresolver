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
	protected Query getRestrictions(Search<Template> ricerca, boolean count) {

		if (ricerca == null || ricerca.getObj() == null)
			return super.getRestrictions(ricerca, count);

		Map<String, Object> params = new HashMap<String, Object>();

		String alias = "t";
		StringBuffer sb = new StringBuffer(getBaseList(getEntityType(), alias,
				count));
		sb.append(" where ").append(alias).append(".attivo = :attivo");
		params.put("attivo", true);

		String separator = " and ";

		if (ricerca.getObj().getSearchStatico() != null) {
			sb.append(separator).append(alias).append(".statico = :statico ");
			params.put("statico", ricerca.getObj().getSearchStatico());
		}

		if (ricerca.getObj().getNome() != null
				&& !ricerca.getObj().getNome().isEmpty()) {
			sb.append(separator).append(alias).append(".nome like :nome ");
			params.put("nome", likeParam(ricerca.getObj().getNome()));
		}

		if (!count) {
			sb.append(" order by ").append(alias).append(".").append(ricerca.getOrder());
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
