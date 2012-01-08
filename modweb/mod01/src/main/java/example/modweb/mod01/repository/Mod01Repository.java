package example.modweb.mod01.repository;

import java.io.Serializable;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import example.modweb.mod01.model.Mod01Model;

@Stateless
@LocalBean
public class Mod01Repository implements Serializable {

	private static final long serialVersionUID = 1L;

	@PersistenceContext
	private EntityManager em;

	public Mod01Model persist(Mod01Model testObj) {
		try {
			em.persist(testObj);
			return testObj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Mod01Model> getAll() {
		try {
			return em
					.createQuery("select t from Mod01Model t order by id desc")
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
