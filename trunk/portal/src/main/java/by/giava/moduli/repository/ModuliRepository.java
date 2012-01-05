package by.giava.moduli.repository;

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

import by.giava.base.common.util.JSFUtils;
import by.giava.base.controller.util.PageUtils;
import by.giava.base.model.attachment.Documento;
import by.giava.moduli.model.Modulo;
import by.giava.moduli.model.type.TipoModulo;

@Named
@Stateless
@LocalBean
public class ModuliRepository extends AbstractRepository<Modulo> {

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
	protected Query getRestrictions(Search<Modulo> search, boolean justCount) {

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
		if (search.getObj() != null && search.getObj().getNome() != null
				&& search.getObj().getNome().length() > 0) {
			sb.append(separator + " ( UPPER(").append(alias)
					.append(".nome) LIKE :nome ");
			params.put("nome", likeParam(search.getObj().getNome()));
			sb.append(" or UPPER(").append(alias)
					.append(".oggetto) LIKE :oggetto ");
			params.put("oggetto", likeParam(search.getObj().getNome()));
			sb.append(" ) ");
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

	@Override
	protected String getDefaultOrderBy() {
		return "data desc";
	}

}