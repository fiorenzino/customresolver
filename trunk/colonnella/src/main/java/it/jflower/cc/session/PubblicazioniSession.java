package it.jflower.cc.session;

import it.jflower.base.par.Ricerca;
import it.jflower.base.session.SuperSession;
import it.jflower.base.utils.JSFUtils;
import it.jflower.cc.par.Pubblicazione;
import it.jflower.cc.par.attachment.Documento;
import it.jflower.cc.par.type.TipoPubblicazione;
import it.jflower.cc.utils.PageUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;

@Named
@RequestScoped
public class PubblicazioniSession extends SuperSession<Pubblicazione> implements
		Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	EntityManager em;

	public EntityManager getEm() {
		return em;
	}

	protected Class<Pubblicazione> getEntityType() {
		return Pubblicazione.class;
	}

	protected String getOrderBy() {
		return "progressivoRegistro desc";
	}

	@Override
	public void setEm(EntityManager em) {
		this.em = em;
	}

	@Override
	protected Pubblicazione prePersist(Pubblicazione p) {
		p.setTipo(getEm().find(TipoPubblicazione.class, p.getIdTipo()));
		if (p.getDocumenti() != null && p.getDocumenti().size() == 0) {
			p.setDocumenti(null);
		}
		String idTitle = PageUtils.createPageId(p.getTitolo());
		String idFinal = testId(idTitle);
		p.setId(idFinal);
		if (p.getData() == null)
			p.setData(new Date());
		p.setAutore(JSFUtils.getUserName());
		return p;
	}

	@Override
	protected Pubblicazione preUpdate(Pubblicazione p) {
		if (p.getIdTipo() != null) {
			TipoPubblicazione t = getEm().find(TipoPubblicazione.class,
					p.getIdTipo());
			p.setTipo(t);
		}
		if (p.getDocumenti() != null && p.getDocumenti().size() == 0) {
			p.setDocumenti(null);
		}
		if (p.getData() == null)
			p.setData(new Date());
		p.setAutore(JSFUtils.getUserName());
		return p;
	}

	private String testId(String id) {
		String idFinal = id;
		boolean trovato = false;
		int i = 0;
		while (!trovato) {
			logger.info("id final: " + idFinal);
			Pubblicazione pubblicazioneFind = getEm().find(Pubblicazione.class,
					idFinal);
			logger.info("trovato_ " + pubblicazioneFind);
			if (pubblicazioneFind != null) {
				i++;
				idFinal = id + "-" + i;
			} else {
				trovato = true;
				return idFinal;
			}
		}
		return "";
	}

	public void removeDocument(Documento documento) {
		Documento d = getEm().find(Documento.class, documento.getId());
		if (d != null)
			d.setAttivo(false);
	}

	public Pubblicazione findLast() {
		Pubblicazione ret = new Pubblicazione();
		try {
			ret = (Pubblicazione) em
					.createQuery(
							"select p from Pubblicazione p order by p.id desc ")
					.setMaxResults(1).getSingleResult();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	@Override
	protected Query getRestrictions(Ricerca<Pubblicazione> ricerca,
			String orderBy, boolean count) {

		if (ricerca == null || ricerca.getOggetto() == null)
			return super.getRestrictions(ricerca, orderBy, count);

		Map<String, Object> params = new HashMap<String, Object>();

		String alias = "t";
		StringBuffer sb = new StringBuffer(getBaseList(getEntityType(), alias,
				count));
		sb.append(" where ").append(alias).append(".attivo = :attivo");
		params.put("attivo", true);

		String separator = " and ";

		if (ricerca.getOggetto().getValidoIl() != null) {
			sb.append(separator).append(alias).append(".dal <= :VALIDO1 AND ")
					.append(alias).append(".al >= :VALIDO2 ");
			params.put("VALIDO1", ricerca.getOggetto().getValidoIl());
			params.put("VALIDO2", ricerca.getOggetto().getValidoIl());
		}

		if (ricerca.getOggetto().getTipo() != null
				&& ricerca.getOggetto().getTipo().getNome() != null
				&& ricerca.getOggetto().getTipo().getNome().length() > 0) {
			sb.append(separator).append(alias)
					.append(".tipo.nome = :nomeTipo ");
			params.put("nomeTipo", ricerca.getOggetto().getTipo().getNome());
		}

		if (ricerca.getOggetto().isArchivio()) {
			sb.append(separator).append(alias)
					.append(".tipo.nome != :nomeTipo ");
			params.put("nomeTipo", "Pubblicazioni di Matrimonio");
		}

		if (ricerca.getOggetto().getTipo() != null
				&& ricerca.getOggetto().getTipo().getId() != null) {
			sb.append(separator).append(alias).append(".tipo.id = :idTipo ");
			params.put("idTipo", ricerca.getOggetto().getTipo().getId());
		}
		if (ricerca.getOggetto() != null
				&& ricerca.getOggetto().getNome() != null
				&& ricerca.getOggetto().getNome().length() > 0) {
			sb.append(separator + " ( UPPER(").append(alias)
					.append(".nome) LIKE :nome ");
			params.put("nome", likeParam(ricerca.getOggetto().getNome()));
			sb.append(" or UPPER(").append(alias)
					.append(".titolo) LIKE :titolo ");
			params.put("titolo", likeParam(ricerca.getOggetto().getNome()));
			sb.append(" or UPPER(").append(alias)
					.append(".descrizione) LIKE :descrizione ");
			params.put("descrizione", likeParam(ricerca.getOggetto().getNome()));
			sb.append(" ) ");
		}
		if (ricerca.getOggetto().getStampaDal() != null) {
			sb.append(separator).append(alias).append(".data >= :STAMPADAL");
			params.put("STAMPADAL", ricerca.getOggetto().getStampaDal());
		}
		if (ricerca.getOggetto().getStampaAl() != null) {
			sb.append(separator).append(alias).append(".data <= :STAMPAAL");
			params.put("STAMPAAL", ricerca.getOggetto().getStampaAl());
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
}
