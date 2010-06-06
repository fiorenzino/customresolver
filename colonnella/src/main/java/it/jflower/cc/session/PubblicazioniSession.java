package it.jflower.cc.session;

import it.jflower.base.session.SuperSession;
import it.jflower.base.utils.JSFUtils;
import it.jflower.cc.par.Pubblicazione;
import it.jflower.cc.par.attachment.Documento;
import it.jflower.cc.par.type.TipoPubblicazione;
import it.jflower.cc.utils.PageUtils;

import java.io.Serializable;
import java.util.Date;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

@Named
@SessionScoped
public class PubblicazioniSession extends SuperSession<Pubblicazione> implements
		Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	EntityManager em;

	// @Transactional
	// public Pubblicazione update(Pubblicazione pubblicazione) {
	// try {
	// em.merge(pubblicazione);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return pubblicazione;
	// }

	// @Transactional
	// public Pubblicazione persist(Pubblicazione pubblicazione) {
	// try {
	// em.persist(pubblicazione);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return pubblicazione;
	// }

	// @Transactional
	// public Pubblicazione find(Long id) {
	// try {
	// Pubblicazione pubblicazione = em.find(Pubblicazione.class, id);
	// return pubblicazione;
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return null;
	// }

	// @Transactional
	// public void delete(Long id) {
	// try {
	// Pubblicazione pubblicazione = em.find(Pubblicazione.class, id);
	// em.remove(pubblicazione);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }

	// @SuppressWarnings("unchecked")
	// @Transactional
	// public List<Pubblicazione> getAll() {
	//
	// List<Pubblicazione> all = new ArrayList<Pubblicazione>();
	// try {
	// all = em.createQuery("select p from Pubblicazione p order by p.id")
	// .getResultList();
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return all;
	// }

	public EntityManager getEm() {
		return em;
	}

	protected Class<Pubblicazione> getEntityType() {
		return Pubblicazione.class;
	}

	protected String getOrderBy() {
		return "id desc";
	}

	@Override
	public void setEm(EntityManager em) {
		this.em = em;
	}

	@Override
	protected Pubblicazione prePersist(Pubblicazione p) {
		p.setTipo(getEm().find(TipoPubblicazione.class, p.getTipo().getId()));
		if (p.getDocumenti() != null && p.getDocumenti().size() == 0) {
			p.setDocumenti(null);
		}
		String idTitle = PageUtils.createPageId(p.getNome());
		String idFinal = testId(idTitle);
		p.setId(idFinal);
		p.setData(new Date());
		p.setAutore(JSFUtils.getUserName());
		return p;
	}

	@Override
	protected Pubblicazione preUpdate(Pubblicazione p) {
		p.setTipo(getEm().find(TipoPubblicazione.class, p.getTipo().getId()));
		if (p.getDocumenti() != null && p.getDocumenti().size() == 0) {
			p.setDocumenti(null);
		}
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
			ret = (Pubblicazione) em.createQuery(
					"select p from Pubblicazione p order by p.id desc ")
					.setMaxResults(1).getSingleResult();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
}
