package by.giava.news.repository;

import it.coopservice.commons2.repository.AbstractRepository;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import by.giava.notizie.model.type.TipoInformazione;

@Named
@Stateless
@LocalBean
public class TipoInformazioniRepository extends
		AbstractRepository<TipoInformazione> {

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
	protected String getDefaultOrderBy() {
		return "nome asc";
	}

}
