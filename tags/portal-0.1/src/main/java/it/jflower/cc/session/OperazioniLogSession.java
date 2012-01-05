package it.jflower.cc.session;

import it.jflower.base.par.Ricerca;
import it.jflower.base.session.SuperSession;
import it.jflower.cc.par.OperazioniLog;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;

@Named
@Stateless
@LocalBean
public class OperazioniLogSession extends SuperSession<OperazioniLog> implements
		Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	EntityManager em;

	@Override
	public EntityManager getEm() {
		return em;
	}

	@Override
	protected Class<OperazioniLog> getEntityType() {
		return OperazioniLog.class;
	}

	@Override
	protected String getOrderBy() {
		return "data desc";
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
	protected Query getRestrictions(Ricerca<OperazioniLog> ricerca,
			String orderBy, boolean count) {

		if (ricerca == null || ricerca.getOggetto() == null)
			return super.getRestrictions(ricerca, orderBy, count);

		Map<String, Object> params = new HashMap<String, Object>();

		String alias = "t";
		StringBuffer sb = new StringBuffer(getBaseList(getEntityType(), alias,
				count));
		sb.append(" where ").append(alias).append(".attivo = :attivo");
		params.put("attivo", true);

		String separator = " and ";

		if (ricerca.getOggetto().getData() != null) {
			sb.append(separator).append(alias).append(".data = :data ");
			params.put("data", ricerca.getOggetto().getData());
		}
		if (ricerca.getOggetto().getUsername() != null
				&& !ricerca.getOggetto().getUsername().isEmpty()) {
			sb.append(separator).append(alias).append(".username = :username ");
			params.put("username", ricerca.getOggetto().getUsername());
		}
		if (ricerca.getOggetto().getTipo() != null
				&& !ricerca.getOggetto().getTipo().isEmpty()) {
			sb.append(separator).append(alias).append(".tipo = :type ");
			params.put("type", ricerca.getOggetto().getTipo());
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

	@Override
	protected OperazioniLog prePersist(OperazioniLog n) {
		if (n.getData() == null)
			n.setData(new Date());
		return n;
	}
}
