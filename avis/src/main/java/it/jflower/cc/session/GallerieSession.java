package it.jflower.cc.session;

import it.jflower.cc.par.Galleria;

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
public class GallerieSession implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	EntityManager em;

	@Transactional
	public Galleria update(Galleria galleria) {
		try {
			closeHtmlTags(galleria);
			em.merge(galleria);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return galleria;
	}

	@Transactional
	public Galleria persist(Galleria galleria) {
		try {
			closeHtmlTags(galleria);
			em.persist(galleria);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return galleria;
	}

	private void closeHtmlTags(Galleria galleria) {
		galleria.setDescrizione( galleria.getDescrizione() == null ? null : galleria.getDescrizione().replaceAll("class=\"replaceMe \">", "class=\"replaceMe\"/>").replaceAll("<br>", "<br/>") );
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

	@SuppressWarnings("unchecked")
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
