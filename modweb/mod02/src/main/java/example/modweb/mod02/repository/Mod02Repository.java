package example.modweb.mod02.repository;

import java.io.Serializable;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import example.modweb.mod02.model.Mod02Model;

@Stateless
@LocalBean
public class Mod02Repository implements Serializable {

	private static final long serialVersionUID = 1L;

	@PersistenceContext
	private EntityManager em;

	public Mod02Model persist(Mod02Model testObj) {
		try {
			em.persist(testObj);
			return testObj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Mod02Model> getAll() {
		try {
			return em
					.createQuery("select t from Mod02Model t order by id desc")
					.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}
}
