package it.jflower.cc.session;

import it.jflower.base.par.Ricerca;
import it.jflower.base.session.SuperSession;
import it.jflower.cc.par.Notizia;

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
public class NotizieSession extends SuperSession<Notizia> 
implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	EntityManager em;

	@Override
	public EntityManager getEm() {
		return em;
	}

	@Override
	protected Class<Notizia> getEntityType() {
		return Notizia.class;
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
	protected Query getRestrictions(Ricerca<Notizia> ricerca, String orderBy,
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

		if (ricerca.getOggetto().getTipo() != null && ricerca.getOggetto().getTipo().getNome() != null && ricerca.getOggetto().getTipo().getNome().length() > 0 ) {
			sb.append(separator).append(alias).append(".tipo.nome = :nomeTipo ");
			params.put("nomeTipo", ricerca.getOggetto().getTipo().getNome());
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

}
