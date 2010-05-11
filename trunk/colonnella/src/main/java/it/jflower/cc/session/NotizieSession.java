package it.jflower.cc.session;

import it.jflower.cc.par.Notizia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.seamframework.tx.Transactional;

@Named
@SessionScoped
public class NotizieSession implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	EntityManager em;

	@Transactional
	public Notizia update(Notizia notizia) {
		try {
			em.merge(notizia);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return notizia;
	}

	@Transactional
	public Notizia persist(Notizia notizia) {
		try {
			em.persist(notizia);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return notizia;
	}

	@Transactional
	public Notizia find(String id) {
		try {
			Notizia notizia = em.find(Notizia.class, id);
			return notizia;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Transactional
	public void delete(String id) {
		try {
			Notizia notizia = em.find(Notizia.class, id);
			em.remove(notizia);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<Notizia> getAll() {

		List<Notizia> all = new ArrayList<Notizia>();
		try {
			all = em.createQuery("select p from Notizia p order by p.id")
					.getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return all;
	}
}
