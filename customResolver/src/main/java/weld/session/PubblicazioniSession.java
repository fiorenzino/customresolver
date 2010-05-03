package weld.session;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.seamframework.tx.Transactional;

import weld.model.Pubblicazione;

@Named
@SessionScoped
public class PubblicazioniSession implements Serializable {

	@Inject
	EntityManager em;

	@Transactional
	public Pubblicazione update(Pubblicazione pubblicazione) {
		try {
			em.merge(pubblicazione);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pubblicazione;
	}

	@Transactional
	public Pubblicazione persist(Pubblicazione pubblicazione) {
		try {
			em.persist(pubblicazione);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pubblicazione;
	}

	@Transactional
	public Pubblicazione find(Long id) {
		try {
			Pubblicazione pubblicazione = em.find(Pubblicazione.class, id);
			return pubblicazione;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Transactional
	public void delete(Long id) {
		try {
			Pubblicazione pubblicazione = em.find(Pubblicazione.class, id);
			em.remove(pubblicazione);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public List<Pubblicazione> getAll() {

		List<Pubblicazione> all = new ArrayList<Pubblicazione>();
		try {
			all = em.createQuery("select p from Pubblicazione p order by p.id")
					.getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return all;
	}
}
