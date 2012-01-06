package by.giava.news.repository;

import it.coopservice.commons2.domain.Search;
import it.coopservice.commons2.repository.AbstractRepository;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import by.giava.base.controller.util.PageUtils;
import by.giava.base.controller.util.TimeUtils;
import by.giava.base.model.attachment.Documento;
import by.giava.base.model.attachment.Immagine;
import by.giava.notizie.model.Notizia;
import by.giava.notizie.model.type.TipoInformazione;

@Named
@Stateless
@LocalBean
public class NotizieRepository extends AbstractRepository<Notizia> {

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

	/**
	 * criteri di default, comuni a tutti, ma specializzabili da ogni EJB
	 * tramite overriding
	 */
	@Override
	protected Query getRestrictions(Search<Notizia> search, boolean justCount) {
		if (search.getObj() == null)
			return super.getRestrictions(search, justCount);

		Map<String, Object> params = new HashMap<String, Object>();

		String alias = "t";
		StringBuffer sb = new StringBuffer(getBaseList(getEntityType(), alias,
				justCount));
		sb.append(" where ").append(alias).append(".attivo = :attivo");
		params.put("attivo", true);

		String separator = " and ";

		if (search.getObj().getTipo() != null
				&& search.getObj().getTipo().getNome() != null
				&& search.getObj().getTipo().getNome().length() > 0) {
			sb.append(separator).append(alias)
					.append(".tipo.nome = :nomeTipo ");
			params.put("nomeTipo", search.getObj().getTipo().getNome());
		}
		if (search.getObj().getTipo() != null
				&& search.getObj().getTipo().getId() != null) {
			sb.append(separator).append(alias).append(".tipo.id = :idTipo ");
			params.put("idTipo", search.getObj().getTipo().getId());
		}

		if (search.getObj().getTitolo() != null
				&& !search.getObj().getTitolo().isEmpty()) {
			sb.append(separator + " (").append(alias)
					.append(".titolo LIKE :titolo ");
			params.put("titolo", likeParam(search.getObj().getTitolo()));
			sb.append(" or ").append(alias)
					.append(".contenuto LIKE :contenuto ");
			params.put("contenuto", likeParam(search.getObj().getTitolo()));
			sb.append(" ) ");
		}

		if (!justCount) {
			sb.append(" order by ").append(alias).append(".")
					.append(getDefaultOrderBy());
		} else {
			logger.info("order by null");
		}

		logger.info(sb.toString());

		Query q = getEm().createQuery(sb.toString());

		for (String param : params.keySet()) {
			q.setParameter(param, params.get(param));
		}

		return q;
	}

	@Override
	protected Notizia prePersist(Notizia n) {
		String idTitle = PageUtils.createPageId(n.getTitolo());
		String idFinal = testId(idTitle);
		n.setId(idFinal);
		if (n.getData() == null)
			n.setData(new Date());
		if (n.getTipo() != null && n.getTipo().getId() != null)
			n.setTipo(getEm().find(TipoInformazione.class, n.getTipo().getId()));
		if (n.getDocumenti() != null && n.getDocumenti().size() == 0) {
			n.setDocumenti(null);
		}
		if (n.getImmagini() != null && n.getImmagini().size() == 0) {
			n.setImmagini(null);
		}
		n.setData(TimeUtils.correggiOraMinuti(n.getData()));
		return n;
	}

	@Override
	protected Notizia preUpdate(Notizia n) {
		if (n.getData() == null)
			n.setData(new Date());
		if (n.getTipo() != null && n.getTipo().getId() != null)
			n.setTipo(getEm().find(TipoInformazione.class, n.getTipo().getId()));
		if (n.getDocumenti() != null && n.getDocumenti().size() == 0) {
			n.setDocumenti(null);
		}
		if (n.getImmagini() != null && n.getImmagini().size() == 0) {
			n.setImmagini(null);
		}
		n.setData(TimeUtils.correggiOraMinuti(n.getData()));
		return n;
	}

	public String testId(String id) {
		String idFinal = id;
		boolean trovato = false;
		int i = 0;
		while (!trovato) {
			logger.info("id final: " + idFinal);
			Notizia notiziaFind = find(idFinal);
			logger.info("trovato_ " + notiziaFind);
			if (notiziaFind != null) {
				i++;
				idFinal = id + "-" + i;
			} else {
				trovato = true;
				return idFinal;

			}
		}
		return "";
	}

	public Notizia findLast() {
		Notizia ret = new Notizia();
		try {
			ret = (Notizia) em
					.createQuery("select p from Notizia p order by p.id desc ")
					.setMaxResults(1).getSingleResult();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	@Override
	public Notizia fetch(Object key) {
		if (key == null || key.toString().length() == 0) {
			return null;
		}
		Notizia notizia = find(key);
		for (Documento doc : notizia.getDocumenti()) {
			doc.getFilename();
			doc.getId();
		}
		for (Immagine img : notizia.getImmagini()) {
			img.getFilename();
			img.getId();
		}
		return notizia;
	}

	@Override
	protected String getDefaultOrderBy() {
		return "data desc";
	}
}
