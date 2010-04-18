package weld.session;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.seamframework.tx.Transactional;

import weld.model.Galleria;

@Named
@SessionScoped
public class GallerieSession implements Serializable {

	@Inject
	EntityManager em;

	@Transactional
	public Galleria update(Galleria galleria) {
		try {
			em.merge(galleria);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return galleria;
	}

	@Transactional
	public Galleria persist(Galleria galleria) {
		try {
			em.persist(galleria);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return galleria;
	}

	@Transactional
	public Galleria find(String id) {
		try {
			Galleria galleria = em.find(Galleria.class, id);
			return galleria;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Transactional
	public void delete(String id) {
		try {
			Galleria galleria = em.find(Galleria.class, id);
			em.remove(galleria);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public List<Galleria> getAll() {

		List<Galleria> all = new ArrayList<Galleria>();
		try {
			all = em.createQuery("select p from Galleria p order by p.id")
					.getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return all;
	}
}
