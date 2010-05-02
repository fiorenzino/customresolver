package weld.session;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.seamframework.tx.Transactional;

import weld.model.type.CategoriaAttivita;
import weld.model.type.TipoAttivita;

@Named
@SessionScoped
public class CategorieSession implements Serializable {

	@Inject
	EntityManager em;

	@Transactional
	public CategoriaAttivita update(CategoriaAttivita categoriaAttivita) {
		try {
			em.merge(categoriaAttivita);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return categoriaAttivita;
	}

	@Transactional
	public TipoAttivita update(TipoAttivita tipoAttivita) {
		try {
			em.merge(tipoAttivita);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tipoAttivita;
	}

	@Transactional
	public CategoriaAttivita persist(CategoriaAttivita categoriaAttivita) {
		try {
			em.persist(categoriaAttivita);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return categoriaAttivita;
	}

	@Transactional
	public TipoAttivita persist(TipoAttivita tipoAttivita) {
		try {
			em.persist(tipoAttivita);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tipoAttivita;
	}

	@Transactional
	public CategoriaAttivita findCategoriaAttivita(Long id) {
		try {
			CategoriaAttivita categoriaAttivita = em.find(
					CategoriaAttivita.class, id);
			return categoriaAttivita;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Transactional
	public TipoAttivita findTipoAttivita(Long id) {
		try {
			TipoAttivita tipoAttivita = em.find(TipoAttivita.class, id);
			return tipoAttivita;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Transactional
	public void deleteCategoriaAttivita(Long id) {
		try {
			CategoriaAttivita categoriaAttivita = em.find(
					CategoriaAttivita.class, id);
			em.remove(categoriaAttivita);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public void deleteTipoAttivita(Long id) {
		try {
			TipoAttivita tipoAttivita = em.find(TipoAttivita.class, id);
			em.remove(tipoAttivita);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public List<CategoriaAttivita> getAllCategoriaAttivita() {

		List<CategoriaAttivita> all = new ArrayList<CategoriaAttivita>();
		try {
			all = em.createQuery(
					"select p from CategoriaAttivita p order by p.id")
					.getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return all;
	}

	@Transactional
	public List<CategoriaAttivita> getAllCategoriaAttivitaByTipo(
			TipoAttivita tipoAttivita) {

		List<CategoriaAttivita> all = new ArrayList<CategoriaAttivita>();
		try {
			all = em
					.createQuery(
							"select p from CategoriaAttivita p where p.tipoAttivita.id = :TIPO order by p.id")
					.setParameter("TIPO", tipoAttivita.getId()).getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return all;
	}

	@Transactional
	public List<TipoAttivita> getAllTipoAttivita() {

		List<TipoAttivita> all = new ArrayList<TipoAttivita>();
		try {
			all = em.createQuery("select p from TipoAttivita p order by p.id")
					.getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return all;
	}
}
