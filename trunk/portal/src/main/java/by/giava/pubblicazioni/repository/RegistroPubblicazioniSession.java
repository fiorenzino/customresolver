package by.giava.pubblicazioni.repository;


import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import by.giava.base.common.ejb.SuperSession;
import by.giava.base.controller.util.DbUtils;
import by.giava.pubblicazioni.model.Pubblicazione;
import by.giava.pubblicazioni.model.RegistroPubblicazioni;

@Named
@Stateless
@LocalBean
public class RegistroPubblicazioniSession extends
		SuperSession<RegistroPubblicazioni> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	EntityManager em;

	@Override
	public EntityManager getEm() {
		if (em == null) {
			this.em = DbUtils.getEM();
		}
		return em;
	}

	@Override
	protected Class<RegistroPubblicazioni> getEntityType() {
		return RegistroPubblicazioni.class;
	}

	@Override
	protected String getOrderBy() {
		return "id";
	}

	@Override
	public void setEm(EntityManager em) {
		this.em = em;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public String getNext(String anno) {
		// faccio una prima query per verificare dove siamo arrivati per l'anno
		// corrente
		// recupero il max

		RegistroPubblicazioni ret = null;
		try {
			ret = (RegistroPubblicazioni) em
					.createQuery(
							"select p from RegistroPubblicazioni p where p.anno = :ANNO order by id desc")
					.setParameter("ANNO", anno).setMaxResults(1)
					.getSingleResult();

		} catch (Exception e) {
			// e.printStackTrace();
		}
		try {
			if (ret == null) {
				// stiamo inizializzando un nuovo anno
				ret = new RegistroPubblicazioni();
				ret.setAnno(anno);
				ret.setProgressivo(1L);
				persist(ret);
				// em.flush();
				return ret.getIndice();
			} else {
				// genero il progressivo dell'anno selezionato
				Long nextProgressivo = ret.getProgressivo() + 1;
				RegistroPubblicazioni next = new RegistroPubblicazioni();
				next.setAnno(anno);
				next.setProgressivo(nextProgressivo);
				persist(next);
				// em.flush();
				return next.getIndice();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public String getNext() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		return getNext("" + cal.get(Calendar.YEAR));
	}

	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean resetRegistro(String anno) {
		List<RegistroPubblicazioni> ret = null;
		try {
			ret = (List<RegistroPubblicazioni>) em
					.createQuery(
							"select p from RegistroPubblicazioni p where p.anno = :ANNO")
					.setParameter("ANNO", anno).getResultList();
			int i = 0;
			logger.info("devo eliminare " + ret.size() + "indici del registro");
			for (RegistroPubblicazioni registroPubblicazioni : ret) {
				logger.info(i + "elimino: " + registroPubblicazioni.getId());
				em.remove(registroPubblicazioni);
				i++;
			}
			em.flush();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean allineaRegistro(String anno) {

		resetRegistro(anno);
		try {
			@SuppressWarnings("unchecked")
			List<Pubblicazione> lista = (List<Pubblicazione>) em
					.createQuery(
							"select p from Pubblicazione p where year(p.data) = :ANNO order by p.data asc")
					.setParameter("ANNO", Integer.valueOf(anno))
					.getResultList();
			int i = 1;
			logger.info("devo riallineare per l'anno: " + anno
					+ " num pubblicazioni: " + lista.size());
			for (Pubblicazione pubblicazione : lista) {
				logger.info(i + ") allineo " + pubblicazione.getId());
				String indiceReg = getNext(anno);
				pubblicazione.setProgressivoRegistro(indiceReg);
				// em.merge(pubblicazione);
				em.createQuery(
						"UPDATE  Pubblicazione p SET p.progressivoRegistro = :IND WHERE p.id =  :ID ")
						.setParameter("IND", indiceReg)
						.setParameter("ID", pubblicazione.getId())
						.executeUpdate();
				++i;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
