package by.giava.moduli.repository;


import java.io.Serializable;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import by.giava.base.common.ejb.SuperSession;
import by.giava.moduli.model.type.TipoModulo;

@Named
@Stateless
@LocalBean
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
		return "nome asc";
	}

}
