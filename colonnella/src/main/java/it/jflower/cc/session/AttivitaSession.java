package it.jflower.cc.session;

import it.jflower.cc.par.Attivita;
import it.jflower.cc.par.type.CategoriaAttivita;

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
public class AttivitaSession implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	EntityManager em;

	@Transactional
	public Attivita update(Attivita attivita) {
		try {
			CategoriaAttivita cat = em.find(CategoriaAttivita.class, attivita
					.getCategoria().getId());
			attivita.setCategoria(cat);
			em.merge(attivita);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return attivita;
	}

	@Transactional
	public Attivita persist(Attivita attivita) {
		try {
			CategoriaAttivita cat = em.find(CategoriaAttivita.class, attivita
					.getCategoria().getId());
			attivita.setCategoria(cat);
			em.persist(attivita);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return attivita;
	}

	@Transactional
	public Attivita find(String id) {
		try {
			Attivita attivita = em.find(Attivita.class, id);
			return attivita;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Transactional
	public void delete(String id) {
		try {
			Attivita attivita = em.find(Attivita.class, id);
			em.remove(attivita);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
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
