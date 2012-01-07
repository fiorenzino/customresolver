package by.giava.attivita.repository;

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

import by.giava.attivita.model.type.TipoAttivita;

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

	@Override
	protected Query getRestrictions(Search<TipoAttivita> search,
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

		// id tipoAttivita
		if (search.getObj().getId() != null) {
			sb.append(separator).append(alias).append(".id = :idTipo ");
			params.put("idTipo", search.getObj().getId());
		}

		if (!justCount) {
			sb.append(getOrderBy(alias, search.getOrder()));
		}

		Query q = getEm().createQuery(sb.toString());
		for (String param : params.keySet()) {
			q.setParameter(param, params.get(param));
		}

		return q;
	}

	// public List<TipoAttivita> getAllTipoAttivita() {
	// List<TipoAttivita> all = new ArrayList<TipoAttivita>();
	// try {
	// all = (List<TipoAttivita>) em
	// .createQuery(
	// "select p from TipoAttivita p where p.attivo = :attivo order by p.tipo asc")
	// .setParameter("attivo", true).getResultList();
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return all;
	// }

	// @TransactionAttribute(TransactionAttributeType.REQUIRED)
	// public void updateTipoAttivita(TipoAttivita tipoAttivita) {
	// try {
	// em.merge(tipoAttivita);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	//
	// @TransactionAttribute(TransactionAttributeType.REQUIRED)
	// public TipoAttivita persistTipoAttivita(TipoAttivita tipoAttivita) {
	// try {
	// em.persist(tipoAttivita);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return tipoAttivita;
	// }
	//
	// public TipoAttivita findTipoAttivita(Long id) {
	// try {
	// TipoAttivita tipoAttivita = em.find(TipoAttivita.class, id);
	// return tipoAttivita;
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return null;
	// }
	//
	// @TransactionAttribute(TransactionAttributeType.REQUIRED)
	// public void deleteTipoAttivita(Long id) {
	// try {
	// TipoAttivita tipoAttivita = em.find(TipoAttivita.class, id);
	// em.remove(tipoAttivita);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }

}
