package by.giava.notizie.repository;

import it.coopservice.commons2.repository.AbstractRepository;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import by.giava.pubblicazioni.model.type.TipoPubblicazione;

@Named
@Stateless
@LocalBean
public class TipoPubblicazioneRepository extends
		AbstractRepository<TipoPubblicazione> {
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
		return "nome asc";
	}

	public List<TipoPubblicazione> getAllTipoPubblicazione() {

		List<TipoPubblicazione> all = new ArrayList<TipoPubblicazione>();
		try {
			all = (List<TipoPubblicazione>) em
					.createQuery(
							"select p from TipoPubblicazione p where p.attivo = :attivo order by p.nome asc")
					.setParameter("attivo", true).getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return all;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateTipoPubblicazione(TipoPubblicazione tipoPubblicazione) {
		try {
			em.merge(tipoPubblicazione);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public TipoPubblicazione persistTipoPubblicazione(
			TipoPubblicazione tipoPubblicazione) {
		try {
			em.persist(tipoPubblicazione);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tipoPubblicazione;
	}

	public TipoPubblicazione findTipoPubblicazione(Long id) {
		try {
			TipoPubblicazione tipoPubblicazione = em.find(
					TipoPubblicazione.class, id);
			return tipoPubblicazione;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteTipoPubblicazione(Long id) {
		try {
			TipoPubblicazione tipoPubblicazione = em.find(
					TipoPubblicazione.class, id);
			em.remove(tipoPubblicazione);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
