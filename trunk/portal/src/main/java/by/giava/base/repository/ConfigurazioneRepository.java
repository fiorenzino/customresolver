package by.giava.base.repository;

import it.coopservice.commons2.repository.AbstractRepository;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import by.giava.base.model.Configurazione;

@Named
@Stateless
@LocalBean
public class ConfigurazioneRepository extends
		AbstractRepository<Configurazione> {

	private static final long serialVersionUID = 1L;

	@Inject
	EntityManager em;

	@Override
	public void setEm(EntityManager em) {
		this.em = em;
	}

	public Configurazione caricaConfigurazione() {
		Configurazione c = null;
		try {
			c = find(1L);
		} catch (Exception e) {
		}
		if (c == null) {
			c = new Configurazione();
			persist(c);
		}
		return c;
	}

	@Override
	protected String getDefaultOrderBy() {

		return "id desc";
	}

	@Override
	protected EntityManager getEm() {

		return em;
	}
}
