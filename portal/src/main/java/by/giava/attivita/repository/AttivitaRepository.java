package by.giava.attivita.repository;

import it.coopservice.commons2.domain.Search;
import it.coopservice.commons2.repository.AbstractRepository;
import it.coopservice.commons2.utils.JSFUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import by.giava.attivita.model.Attivita;
import by.giava.attivita.model.type.CategoriaAttivita;
import by.giava.base.controller.util.PageUtils;

@Named
@Stateless
@LocalBean
public class AttivitaRepository extends AbstractRepository<Attivita> {

	private static final long serialVersionUID = 1L;

	@Inject
	CategorieAttivitaRepository categorieAttivitaRepository;

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
	protected Attivita prePersist(Attivita attivita) {
		String idTitle = PageUtils.createPageId(attivita.getNome());
		String idFinal = testId(idTitle);
		attivita.setId(idFinal);
		attivita.setData(new Date());
		attivita.setAutore(JSFUtils.getUserName());
		CategoriaAttivita cat = categorieAttivitaRepository.find(Long
				.valueOf(attivita.getCategoria().getId()));
		attivita.setCategoria(cat);
		return attivita;
	}

	@Override
	protected Attivita preUpdate(Attivita attivita) {
		CategoriaAttivita cat = categorieAttivitaRepository.find(Long
				.valueOf(attivita.getCategoria().getId()));
		attivita.setCategoria(cat);
		return attivita;
	}

	public String testId(String id) {
		String idFinal = id;
		boolean trovato = false;
		int i = 0;
		while (!trovato) {
			logger.info("id final: " + idFinal);
			Attivita attivitaFind = find(idFinal);
			logger.info("trovato_ " + attivitaFind);
			if (attivitaFind != null) {
				i++;
				idFinal = id + "-" + i;
			} else {
				trovato = true;
				return idFinal;

			}
		}

		return "";
	}

	@Override
	protected Query getRestrictions(Search<Attivita> search, boolean justCount) {

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

		if (search.getObj().getCategoria() != null
				&& search.getObj().getCategoria().getTipoAttivita() != null
				&& search.getObj().getCategoria().getTipoAttivita().getId() != null) {
			sb.append(separator).append(alias)
					.append(".categoria.tipoAttivita.id = :idAttivita ");
			params.put("idAttivita", search.getObj().getCategoria()
					.getTipoAttivita().getId());
		}
		if (search.getObj().getCategoria() != null
				&& search.getObj().getCategoria().getId() != null) {
			sb.append(separator).append(alias)
					.append(".categoria.id = :idCategoria ");
			params.put("idCategoria", search.getObj().getCategoria().getId());
		}
		if (search.getObj().getCategoria() != null
				&& search.getObj().getCategoria().getNome() != null
				&& !search.getObj().getCategoria().getNome().isEmpty()) {
			sb.append(separator).append("UPPER(" + alias)
					.append(".categoria.nome) LIKE :categoria ");
			params.put("categoria", likeParam(search.getObj().getCategoria()
					.getNome()));
		}
		if (search.getObj().getNome() != null
				&& search.getObj().getNome().length() > 0) {
			sb.append(separator).append(alias).append(".nome LIKE :nome ");
			params.put("nome", likeParam(search.getObj().getNome()));
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

	public Attivita findLast() {
		Attivita ret = new Attivita();
		try {
			ret = (Attivita) em
					.createQuery("select p from Attivita p order by p.id desc ")
					.setMaxResults(1).getSingleResult();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	@Override
	protected String getDefaultOrderBy() {
		return "nome asc";
	}

}