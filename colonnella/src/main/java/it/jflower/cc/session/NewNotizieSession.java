package it.jflower.cc.session;

import it.jflower.base.par.Ricerca;
import it.jflower.base.session.SuperSession;
import it.jflower.cc.par.Notizia;
import it.jflower.cc.par.attachment.Immagine;
import it.jflower.cc.par.type.TipoInformazione;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.seamframework.tx.Transactional;

@Named
@RequestScoped
public class NewNotizieSession extends SuperSession<Notizia> implements
		Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	EntityManager em;

	@Override
	public EntityManager getEm() {
		return em;
	}

	@Override
	protected Class<Notizia> getEntityType() {
		return Notizia.class;
	}

	@Override
	protected String getOrderBy() {
		return "data desc";
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
	protected Query getRestrictions(Ricerca<Notizia> ricerca, String orderBy,
			boolean count) {

		if (ricerca == null || ricerca.getOggetto() == null)
			return super.getRestrictions(ricerca, orderBy, count);

		Map<String, Object> params = new HashMap<String, Object>();

		String alias = "t";
		StringBuffer sb = new StringBuffer(getBaseList(getEntityType(), alias,
				count));
		sb.append(" where ").append(alias).append(".attivo = :attivo");
		params.put("attivo", true);

		String separator = " and ";

		if (ricerca.getOggetto().getTipo() != null
				&& ricerca.getOggetto().getTipo().getNome() != null
				&& ricerca.getOggetto().getTipo().getNome().length() > 0) {
			sb.append(separator).append(alias)
					.append(".tipo.nome = :nomeTipo ");
			params.put("nomeTipo", ricerca.getOggetto().getTipo().getNome());
		}
		if (ricerca.getOggetto().getTipo() != null
				&& ricerca.getOggetto().getTipo().getId() != null) {
			sb.append(separator).append(alias).append(".tipo.id = :idTipo ");
			params.put("idTipo", ricerca.getOggetto().getTipo().getId());
		}

		if (ricerca.getOggetto().getTitolo() != null
				&& !ricerca.getOggetto().getTitolo().isEmpty()) {
			sb.append(separator + " (").append(alias)
					.append(".titolo LIKE :titolo ");
			params.put("titolo", likeParam(ricerca.getOggetto().getTitolo()));
			sb.append(" or ").append(alias)
					.append(".contenuto LIKE :contenuto ");
			params.put("contenuto", likeParam(ricerca.getOggetto().getTitolo()));
			sb.append(" ) ");
		}

		if (!count) {
			sb.append(" order by ").append(alias).append(".").append(orderBy);
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
		return n;
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

	public Notizia findEvidenza() {
		Notizia ret = new Notizia();
		try {
			ret = (Notizia) em
					.createQuery(
							"select p from Notizia p where p.evidenza = :STATUS ")
					.setParameter("STATUS", true).setMaxResults(1)
					.getSingleResult();

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (ret == null)
			return findLast();
		return ret;
	}

	@Transactional
	public void refreshEvidenza(String id) {
		List<Notizia> ret = null;
		try {
			ret = (List<Notizia>) em
					.createQuery(
							"select p from Notizia p where p.id != :ID AND p.evidenza = :STATUS")
					.setParameter("ID", id).setParameter("STATUS", true)
					.getResultList();
			if (ret != null) {
				for (Notizia notizia : ret) {
					notizia.setEvidenza(false);
					update(notizia);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public Immagine findEvidenzaImmagine() {
		Immagine ret = null;
		try {
			List<Notizia> nl = em
					.createQuery(
							"select p from Notizia p where p.evidenza = :STATUS")
					.setParameter("STATUS", true).getResultList();
			if (nl == null || nl.size() == 0 || nl.get(0).getImmagini() == null
					|| nl.get(0).getImmagini().size() == 0) {
				return null;
			}
			return nl.get(0).getImmagini().get(0);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
