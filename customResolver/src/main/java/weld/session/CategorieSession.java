package weld.session;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.seamframework.tx.Transactional;

import weld.model.Attivita;

@Named
@SessionScoped
public class CategorieSession implements Serializable {

	@Inject
	EntityManager em;

	@Transactional
	public Attivita update(Attivita attivita) {
		try {
			em.merge(attivita);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return attivita;
	}

	@Transactional
	public Attivita persist(Attivita Attivita) {
		try {
			em.persist(Attivita);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Attivita;
	}

	@Transactional
	public Attivita find(String id) {
		try {
			Attivita Attivita = em.find(Attivita.class, id);
			return Attivita;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Transactional
	public void delete(String id) {
		try {
			Attivita Attivita = em.find(Attivita.class, id);
			em.remove(Attivita);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public List<Attivita> getAll() {

		List<Attivita> all = new ArrayList<Attivita>();
		try {
			all = em.createQuery("select p from Attivita p order by p.id")
					.getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return all;
	}
}
