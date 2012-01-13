package by.giava.catalogo.repository;

import it.coopservice.commons2.domain.Search;
import it.coopservice.commons2.repository.AbstractRepository;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import by.giava.catalogo.model.Realizzazione;

@Named
@Stateless
@LocalBean
public class RealizzazioniRepository extends AbstractRepository<Realizzazione> {

	private static final long serialVersionUID = 1L;

	@Inject
	EntityManager em;

	@Override
	protected EntityManager getEm() {
		// TODO Auto-generated method stub
		return em;
	}

	@Override
	public void setEm(EntityManager em) {
		this.em = em;
	}

	@Override
	protected String getDefaultOrderBy() {
		// TODO Auto-generated method stub
		return "nome asc";
	}

	@Override
	protected Query getRestrictions(Search<Realizzazione> search,
			boolean justCount) {
		// TODO Auto-generated method stub
		return super.getRestrictions(search, justCount);
	}

}
