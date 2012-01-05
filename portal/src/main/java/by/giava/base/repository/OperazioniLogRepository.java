package by.giava.base.repository;

import it.coopservice.commons2.domain.Search;
import it.coopservice.commons2.repository.AbstractRepository;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import by.giava.base.model.OperazioniLog;

@Named
@Stateless
@LocalBean
public class OperazioniLogRepository extends AbstractRepository<OperazioniLog> {

	private static final long serialVersionUID = 1L;

	@Inject
	EntityManager em;

	@Override
	public EntityManager getEm() {
		return em;
	}

	@Override
	public void setEm(EntityManager em) {
		this.em = em;
	}

	
	@Override
	protected Query getRestrictions(Search<OperazioniLog> search,
			boolean justCount) {

		if (search.getObj() == null) {
			return super.getRestrictions(search, justCount);
		}

		Map<String, Object> params = new HashMap<String, Object>();

		String alias = "c";
		StringBuffer sb = new StringBuffer(getBaseList(search.getObj()
				.getClass(), alias, justCount));

		String separator = " where ";

		// attivo
		sb.append(separator).append(" ").append(alias)
				.append(".attivo = :attivo ");
		// aggiunta alla mappa
		params.put("attivo", true);
		// separatore
		separator = " and ";

		if (search.getObj().getData() != null) {
			sb.append(separator).append(alias).append(".data = :data ");
			params.put("data", search.getObj().getData());
		}
		if (search.getObj().getUsername() != null
				&& !search.getObj().getUsername().isEmpty()) {
			sb.append(separator).append(alias).append(".username = :username ");
			params.put("username", search.getObj().getUsername());
		}
		if (search.getObj().getTipo() != null
				&& !search.getObj().getTipo().isEmpty()) {
			sb.append(separator).append(alias).append(".tipo = :type ");
			params.put("type", search.getObj().getTipo());
		}

		if (!justCount) {
			sb.append(getOrderBy(alias, search.getOrder()));
		}

		Query q = getEm().createQuery(sb.toString());
		for (String param : params.keySet()) {
			q.setParameter(param, params.get(param));
		}

		return q;
	}

	@Override
	protected OperazioniLog prePersist(OperazioniLog n) {
		if (n.getData() == null)
			n.setData(new Date());
		return n;
	}

	@Override
	protected String getDefaultOrderBy() {
		return "data desc";
	}
}
