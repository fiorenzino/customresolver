package by.giava.news.repository;


import java.io.Serializable;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import by.giava.base.common.ejb.SuperSession;
import by.giava.notizie.model.type.TipoInformazione;

@Named
@Stateless
@LocalBean
public class TipoInformazioniSession extends SuperSession<TipoInformazione>
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
		return "nome asc";
	}

}
