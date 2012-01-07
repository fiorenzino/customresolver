package by.giava.pubblicazioni.repository;

import it.coopservice.commons2.domain.Search;
import it.coopservice.commons2.repository.AbstractRepository;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;

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

	@Override
	protected Query getRestrictions(Search<TipoPubblicazione> search,
			boolean justCount) {

		if (search.getObj() == null) {
			return super.getRestrictions(search, justCount);
		}

		Map<String, Object> params = new HashMap<String, Object>();

		String alias = "c";
		StringBuffer sb = new StringBuffer(getBaseList(search.getObj()
				.getClass(), alias, justCount));

		String separator = " where ";

		// attivo
		sb.append(separator).append(" ").append(alias)
				.append(".attivo = :attivo ");
		// aggiunta alla mappa
		params.put("attivo", true);
		// separatore
		separator = " and ";

		if (!justCount) {
			sb.append(getOrderBy(alias, search.getOrder()));
		}

		Query q = getEm().createQuery(sb.toString());
		for (String param : params.keySet()) {
			q.setParameter(param, params.get(param));
		}

		return q;
	}

	// public List<TipoPubblicazione> getAllTipoPubblicazione() {
	//
	// List<TipoPubblicazione> all = new ArrayList<TipoPubblicazione>();
	// try {
	// all = (List<TipoPubblicazione>) em
	// .createQuery(
	// "select p from TipoPubblicazione p where p.attivo = :attivo order by p.nome asc")
	// .setParameter("attivo", true).getResultList();
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return all;
	// }

	// @TransactionAttribute(TransactionAttributeType.REQUIRED)
	// public void updateTipoPubblicazione(TipoPubblicazione tipoPubblicazione)
	// {
	// try {
	// em.merge(tipoPubblicazione);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }

	// @TransactionAttribute(TransactionAttributeType.REQUIRED)
	// public TipoPubblicazione persistTipoPubblicazione(
	// TipoPubblicazione tipoPubblicazione) {
	// try {
	// em.persist(tipoPubblicazione);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return tipoPubblicazione;
	// }
	//
	// public TipoPubblicazione findTipoPubblicazione(Long id) {
	// try {
	// TipoPubblicazione tipoPubblicazione = em.find(
	// TipoPubblicazione.class, id);
	// return tipoPubblicazione;
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return null;
	// }
	//
	// @TransactionAttribute(TransactionAttributeType.REQUIRED)
	// public void deleteTipoPubblicazione(Long id) {
	// try {
	// TipoPubblicazione tipoPubblicazione = em.find(
	// TipoPubblicazione.class, id);
	// em.remove(tipoPubblicazione);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
}
