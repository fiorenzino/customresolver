package by.giava.base.repository;

import java.io.Serializable;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import by.giava.base.common.ejb.SuperSession;
import by.giava.base.controller.util.DbUtils;
import by.giava.base.model.Configurazione;


@Named
@Stateless
@LocalBean
public class ConfigurazioneSession extends SuperSession<Configurazione>
		implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	EntityManager em;

	@Override
	public EntityManager getEm() {
		if (em == null) {
			this.em = DbUtils.getEM();
		}
		return em;
	}

	@Override
	protected Class<Configurazione> getEntityType() {
		return Configurazione.class;
	}

	@Override
	protected String getOrderBy() {
		return "id";
	}

	@Override
	public void setEm(EntityManager em) {
		this.em = em;
	}

	public Configurazione caricaConfigurazione() {
		Configurazione c = null;
		try {
			c = em.find(Configurazione.class, 1L);
		} catch (Exception e) {
		}
		if (c == null) {
			c = new Configurazione();
			em.persist(c);
		}
		return c;
	}
}
