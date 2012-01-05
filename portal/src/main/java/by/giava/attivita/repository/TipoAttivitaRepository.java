package by.giava.attivita.repository;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import by.giava.attivita.model.type.TipoAttivita;

import it.coopservice.commons2.repository.AbstractRepository;

@Named
@Stateless
@LocalBean
public class TipoAttivitaRepository extends AbstractRepository<TipoAttivita> {
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
		// TODO Auto-generated method stub
		return "tipo asc";
	}

	public List<TipoAttivita> getAllTipoAttivita() {
		List<TipoAttivita> all = new ArrayList<TipoAttivita>();
		try {
			all = (List<TipoAttivita>) em
					.createQuery(
							"select p from TipoAttivita p where p.attivo = :attivo order by p.tipo asc")
					.setParameter("attivo", true).getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return all;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateTipoAttivita(TipoAttivita tipoAttivita) {
		try {
			em.merge(tipoAttivita);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public TipoAttivita persistTipoAttivita(TipoAttivita tipoAttivita) {
		try {
			em.persist(tipoAttivita);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tipoAttivita;
	}

	public TipoAttivita findTipoAttivita(Long id) {
		try {
			TipoAttivita tipoAttivita = em.find(TipoAttivita.class, id);
			return tipoAttivita;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteTipoAttivita(Long id) {
		try {
			TipoAttivita tipoAttivita = em.find(TipoAttivita.class, id);
			em.remove(tipoAttivita);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
