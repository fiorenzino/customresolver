package it.jflower.cc.session;

import it.jflower.cc.par.type.CategoriaAttivita;
import it.jflower.cc.par.type.TipoAttivita;
import it.jflower.cc.par.type.TipoModulo;
import it.jflower.cc.par.type.TipoPubblicazione;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.seamframework.tx.Transactional;

@Named
@RequestScoped
@SuppressWarnings("unchecked")
public class CategorieSession implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	EntityManager em;

	@Transactional
	public void updateCategoriaAttivita(CategoriaAttivita categoriaAttivita) {
		try {
			categoriaAttivita.setTipoAttivita(em.find(TipoAttivita.class,
					categoriaAttivita.getTipoAttivita().getId()));
			em.merge(categoriaAttivita);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public void updateTipoAttivita(TipoAttivita tipoAttivita) {
		try {
			em.merge(tipoAttivita);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public void updateTipoPubblicazione(TipoPubblicazione tipoPubblicazione) {
		try {
			em.merge(tipoPubblicazione);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public CategoriaAttivita persistCategoriaAttivita(
			CategoriaAttivita categoriaAttivita) {
		try {
			em.persist(categoriaAttivita);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return categoriaAttivita;
	}

	@Transactional
	public TipoAttivita persistTipoAttivita(TipoAttivita tipoAttivita) {
		try {
			em.persist(tipoAttivita);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tipoAttivita;
	}

	@Transactional
	public TipoPubblicazione persistTipoPubblicazione(
			TipoPubblicazione tipoPubblicazione) {
		try {
			em.persist(tipoPubblicazione);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tipoPubblicazione;
	}

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

	public TipoAttivita findTipoAttivita(Long id) {
		try {
			TipoAttivita tipoAttivita = em.find(TipoAttivita.class, id);
			return tipoAttivita;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public TipoPubblicazione findTipoPubblicazione(Long id) {
		try {
			TipoPubblicazione tipoPubblicazione = em.find(
					TipoPubblicazione.class, id);
			return tipoPubblicazione;
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
	public void deleteTipoPubblicazione(Long id) {
		try {
			TipoPubblicazione tipoPubblicazione = em.find(
					TipoPubblicazione.class, id);
			em.remove(tipoPubblicazione);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<CategoriaAttivita> getAllCategoriaAttivita() {

		List<CategoriaAttivita> all = new ArrayList<CategoriaAttivita>();
		try {
			all = em.createQuery(
					"select p from CategoriaAttivita p where p.attivo = :attivo order by p.categoria asc ")
					.setParameter("attivo", true).getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return all;
	}

	public List<CategoriaAttivita> getAllCategoriaAttivitaByTipo(Long id) {

		List<CategoriaAttivita> all = new ArrayList<CategoriaAttivita>();
		try {
			all = em.createQuery(
					"select p from CategoriaAttivita p where p.tipoAttivita.id = :TIPO and p.attivo = :attivo order by p.id")
					.setParameter("attivo", true).setParameter("TIPO", id)
					.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return all;
	}

	public List<TipoAttivita> getAllTipoAttivita() {

		List<TipoAttivita> all = new ArrayList<TipoAttivita>();
		try {
			all = em.createQuery(
					"select p from TipoAttivita p where p.attivo = :attivo order by p.tipo asc")
					.setParameter("attivo", true).getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return all;
	}

	public List<TipoPubblicazione> getAllTipoPubblicazione() {

		List<TipoPubblicazione> all = new ArrayList<TipoPubblicazione>();
		try {
			all = em.createQuery(
					"select p from TipoPubblicazione p where p.attivo = :attivo order by p.nome asc")
					.setParameter("attivo", true).getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return all;
	}

	public List<TipoModulo> getAllTipoModulo() {

		List<TipoModulo> all = new ArrayList<TipoModulo>();
		try {
			all = em.createQuery(
					"select p from TipoModulo p where p.attivo = :attivo order by p.id")
					.setParameter("attivo", true).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return all;
	}

}
