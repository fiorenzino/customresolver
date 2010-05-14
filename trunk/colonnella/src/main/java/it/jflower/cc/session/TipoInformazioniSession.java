package it.jflower.cc.session;

import it.jflower.base.session.SuperSession;
import it.jflower.cc.par.type.TipoInformazione;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

@Named
@SessionScoped
public class TipoInformazioniSession 
extends SuperSession<TipoInformazione>
implements Serializable {

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
	protected Class<TipoInformazione> getEntityType() {
		return TipoInformazione.class;
	}

	@Override
	protected String getOrderBy() {
		return "nome";
	}
	
}
