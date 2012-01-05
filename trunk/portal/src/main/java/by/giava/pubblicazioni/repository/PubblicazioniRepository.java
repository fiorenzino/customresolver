package by.giava.pubblicazioni.repository;

import it.coopservice.commons2.domain.Search;
import it.coopservice.commons2.repository.AbstractRepository;
import it.coopservice.commons2.utils.JSFUtils;

import java.util.Calendar;
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
import by.giava.base.model.attachment.Documento;
import by.giava.pubblicazioni.model.Pubblicazione;
import by.giava.pubblicazioni.model.type.TipoPubblicazione;

@Named
@Stateless
@LocalBean
public class PubblicazioniRepository extends AbstractRepository<Pubblicazione> {

	private static final long serialVersionUID = 1L;

	@Inject
	EntityManager em;

	public EntityManager getEm() {
		return em;
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
	protected Query getRestrictions(Search<Pubblicazione> search,
			boolean justCount) {

		if (search == null || search.getObj() == null)
			return super.getRestrictions(search, justCount);

		Map<String, Object> params = new HashMap<String, Object>();

		String alias = "t";
		StringBuffer sb = new StringBuffer(getBaseList(getEntityType(), alias,
				justCount));
		String leftOuterJoinAlias = "doc";
		if (!justCount && search.getObj().isAllegati()) {
			sb.append(" left outer join "/* fetch " */).append(alias)
					.append(".documenti ").append(leftOuterJoinAlias)
					.append(" ");
			sb.append(" left join "/* fetch " */).append(alias)
					.append(".tipo tip ");
		}

		sb.append(" where ").append(alias).append(".attivo = :attivo");
		params.put("attivo", true);

		String separator = " and ";

		if (search.getObj().getValidoIl() != null) {

			Calendar cal = Calendar.getInstance();
			cal.setTime(search.getObj().getValidoIl());
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			Date inizioGiornata = cal.getTime();
			cal.set(Calendar.HOUR_OF_DAY, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
			Date fineGiornata = cal.getTime();

			sb.append(separator).append(alias)
					.append(".dal <= :FINEGIORNATA AND ").append(alias)
					.append(".al >= :INIZIOGIORNATA ");
			params.put("FINEGIORNATA", fineGiornata);
			params.put("INIZIOGIORNATA", inizioGiornata);
		}

		if (search.getObj().getTipo() != null
				&& search.getObj().getTipo().getNome() != null
				&& search.getObj().getTipo().getNome().length() > 0) {
			sb.append(separator).append(alias)
					.append(".tipo.nome = :nomeTipo ");
			params.put("nomeTipo", search.getObj().getTipo().getNome());
		}

		if (search.getObj().isArchivio()) {
			sb.append(separator).append(alias)
					.append(".tipo.nome != :nomeTipoSpecial ");
			params.put("nomeTipoSpecial", "Pubblicazioni di Matrimonio");
		}

		if (search.getObj().getTipo() != null
				&& search.getObj().getTipo().getId() != null) {
			sb.append(separator).append(alias).append(".tipo.id = :idTipo ");
			params.put("idTipo", search.getObj().getTipo().getId());
		}
		if (search.getObj() != null && search.getObj().getNome() != null
				&& search.getObj().getNome().length() > 0) {
			sb.append(separator + " ( UPPER(").append(alias)
					.append(".nome) LIKE :nome ");
			params.put("nome", likeParam(search.getObj().getNome()));
			sb.append(" or UPPER(").append(alias)
					.append(".titolo) LIKE :titolo ");
			params.put("titolo", likeParam(search.getObj().getNome()));
			sb.append(" or UPPER(").append(alias)
					.append(".descrizione) LIKE :descrizione ");
			params.put("descrizione", likeParam(search.getObj().getNome()));
			sb.append(" ) ");
		}
		if (search.getObj().getStampaDal() != null) {
			sb.append(separator).append(alias).append(".data >= :STAMPADAL");
			params.put("STAMPADAL", search.getObj().getStampaDal());
		}
		if (search.getObj().getStampaAl() != null) {
			sb.append(separator).append(alias).append(".data <= :STAMPAAL");
			params.put("STAMPAAL", search.getObj().getStampaAl());
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

		q.setHint("org.hibernate.cacheMode", "IGNORE");

		return q;
	}

	@Override
	protected String getBaseList(Class<? extends Object> clazz, String alias,
			boolean count) {
		if (count) {
			return "select count( distinct " + alias + ") from "
					+ clazz.getSimpleName() + " " + alias + " ";
		} else {
			return "select distinct " + alias + " from "
					+ clazz.getSimpleName() + " " + alias + " ";
		}
	}

	@Override
	protected String getDefaultOrderBy() {
		return "progressivoRegistro desc";
	}

}
