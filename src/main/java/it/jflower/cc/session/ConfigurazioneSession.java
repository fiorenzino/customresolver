package it.jflower.cc.session;

import it.jflower.base.session.SuperSession;
import it.jflower.cc.par.Configurazione;
import it.jflower.cc.utils.DbUtils;

import java.io.Serializable;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

@Named
@Stateless
@LocalBean
public class ConfigurazioneSession extends SuperSession<Configurazione> implements Serializable {

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
		}
		catch (Exception e) {
		}
		if ( c == null ) {
			c = new Configurazione();
			em.persist(c);
		}
		return c;
	}
}
