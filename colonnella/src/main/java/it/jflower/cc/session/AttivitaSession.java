package it.jflower.cc.session;

import it.jflower.base.par.Ricerca;
import it.jflower.base.session.SuperSession;
import it.jflower.cc.par.Attivita;
import it.jflower.cc.par.type.CategoriaAttivita;

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
public class AttivitaSession 
extends SuperSession<Attivita> implements Serializable {

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
	protected Class<Attivita> getEntityType() {
		return Attivita.class;
	}

	@Override
	protected String getOrderBy() {
		return "nome";
	}

	@Override
	protected Attivita prePersist(Attivita a) {
		a.setCategoria( getEm().find(CategoriaAttivita.class, a.getCategoria().getId() ));
		return a;
	}

	@Override
	protected Attivita preUpdate(Attivita a) {
		a.setCategoria( getEm().find(CategoriaAttivita.class, a.getCategoria().getId() ));
		return a;
	}

	@Override
	protected Query getRestrictions(Ricerca<Attivita> ricerca, String orderBy,
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

		if (ricerca.getOggetto().getCategoria() != null && ricerca.getOggetto().getCategoria().getTipoAttivita() != null && ricerca.getOggetto().getCategoria().getTipoAttivita().getId() != null ) {
			sb.append(separator).append(alias).append(".categoria.tipoAttivita.id = :idAttivita ");
			params.put("idAttivita", ricerca.getOggetto().getCategoria().getTipoAttivita().getId());
		}
		if (ricerca.getOggetto().getCategoria() != null && ricerca.getOggetto().getCategoria().getId() != null ) {
			sb.append(separator).append(alias).append(".categoria.id = :idCategoria ");
			params.put("idCategoria", ricerca.getOggetto().getCategoria().getId());
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