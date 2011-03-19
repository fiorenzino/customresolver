package it.jflower.cc.session;

import it.jflower.base.par.Ricerca;
import it.jflower.base.session.SuperSession;
import it.jflower.base.utils.JSFUtils;
import it.jflower.cc.par.Modulo;
import it.jflower.cc.par.attachment.Documento;
import it.jflower.cc.par.type.TipoModulo;
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
public class ModuliSession extends SuperSession<Modulo> implements Serializable {

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
	protected Class<Modulo> getEntityType() {
		return Modulo.class;
	}

	@Override
	protected String getOrderBy() {
		return "data desc";
	}

	@Override
	protected Query getRestrictions(Ricerca<Modulo> ricerca, String orderBy,
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
		if (ricerca.getOggetto() != null
				&& ricerca.getOggetto().getNome() != null
				&& ricerca.getOggetto().getNome().length() > 0) {
			sb.append(separator).append(alias).append(".nome LIKE :nome ");
			params.put("nome", likeParam(ricerca.getOggetto().getNome()));
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

	// @Override
	// @Transactional
	// public Modulo persist(Modulo m) {
	// return super.persist(m);
	// }
	// @Override
	// @Transactional
	// public Modulo update(Modulo m) {
	// return super.update(m);
	// }
	// @Override
	// @Transactional
	// public Modulo fetch(Object id) {
	// return super.fetch(id);
	// }
	// @Override
	// @Transactional
	// public Modulo find(Object id) {
	// return super.find(id);
	// }

	@Override
	protected Modulo prePersist(Modulo m) {
		m.setTipo(getEm().find(TipoModulo.class, m.getIdTipo()));
		if (m.getDocumento() != null && m.getDocumento().getFilename() == null) {
			m.setDocumento(null);
		}
		String idTitle = PageUtils.createPageId(m.getNome());
		String idFinal = testId(idTitle);
		m.setId(idFinal);
		m.setData(new Date());
		m.setAutore(JSFUtils.getUserName());
		return m;
	}

	@Override
	protected Modulo preUpdate(Modulo m) {
		m.setTipo(getEm().find(TipoModulo.class, m.getIdTipo()));
		if (m.getDocumento() != null && m.getDocumento().getFilename() == null) {
			m.setDocumento(null);
		}
		m.setData(new Date());
		m.setAutore(JSFUtils.getUserName());
		return m;
	}

	private String testId(String id) {
		String idFinal = id;
		boolean trovato = false;
		int i = 0;
		while (!trovato) {
			logger.info("id final: " + idFinal);
			Modulo moduloFind = getEm().find(Modulo.class, idFinal);
			logger.info("trovato_ " + moduloFind);
			if (moduloFind != null) {
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

}
