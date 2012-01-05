package by.giava.attivita.repository;

import it.coopservice.commons2.domain.Search;
import it.coopservice.commons2.repository.AbstractRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import by.giava.attivita.model.type.CategoriaAttivita;
import by.giava.attivita.model.type.TipoAttivita;
import by.giava.moduli.model.Modulo;

@Named
@Stateless
@LocalBean
public class CategorieAttivitaRepository extends
		AbstractRepository<CategoriaAttivita> {

	private static final long serialVersionUID = 1L;

	@Inject
	EntityManager em;

	@Inject
	TipoAttivitaRepository tipoAttivitaRepository;

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
		return "categoria asc";
	}

	@Override
	protected Query getRestrictions(Search<CategoriaAttivita> search,
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

		// tipoAttivita
		if (search.getObj().getTipoAttivita() != null
				&& search.getObj().getTipoAttivita().getNome() != null
				&& !search.getObj().getTipoAttivita().getNome().isEmpty()) {
			sb.append(separator).append(alias)
					.append(".tipoAttivita.nome = :nomeTipo ");
			params.put("nomeTipo", search.getObj().getTipoAttivita().getNome());
		}

		// id tipoAttivita
		if (search.getObj().getTipoAttivita() != null
				&& search.getObj().getTipoAttivita().getId() != null) {
			sb.append(separator).append(alias)
					.append(".tipoAttivita.id = :idTipo ");
			params.put("idTipo", search.getObj().getTipoAttivita().getId());
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

	public List<CategoriaAttivita> getAllCategoriaAttivitaByTipo(Long id) {
		List<CategoriaAttivita> all = new ArrayList<CategoriaAttivita>();
		try {
			CategoriaAttivita categoriaAttivita = new CategoriaAttivita();
			categoriaAttivita.getTipoAttivita().setId(id);
			Search<CategoriaAttivita> search = new Search<CategoriaAttivita>(
					categoriaAttivita);
			all = getList(search, 0, 0);
			// all = (List<CategoriaAttivita>) em
			// .createQuery(
			// "select p from CategoriaAttivita p where p.tipoAttivita.id = :TIPO and p.attivo = :attivo order by p.id")
			// .setParameter("attivo", true).setParameter("TIPO", id)
			// .getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return all;
	}

}
