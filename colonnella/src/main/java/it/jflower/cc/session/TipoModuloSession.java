package it.jflower.cc.session;

import it.jflower.base.session.SuperSession;
import it.jflower.cc.par.type.TipoModulo;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

@Named
@RequestScoped
public class TipoModuloSession extends SuperSession<TipoModulo> implements
		Serializable {

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
	protected Class<TipoModulo> getEntityType() {
		return TipoModulo.class;
	}

	@Override
	protected String getOrderBy() {
		return "nome";
	}

}
