package by.giava.gallerie.repository;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import by.giava.gallerie.model.Galleria;

@Named
@Stateless
@LocalBean
public class GallerieSession implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	EntityManager em;

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Galleria update(Galleria galleria) {
		try {
			closeHtmlTags(galleria);
			em.merge(galleria);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return galleria;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
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
		galleria.setDescrizione(galleria.getDescrizione() == null ? null
				: galleria
						.getDescrizione()
						.replaceAll("class=\"replaceMe \">",
								"class=\"replaceMe\"/>")
						.replaceAll("<br>", "<br/>"));
	}

	public Galleria find(String id) {
		try {
			Galleria galleria = em.find(Galleria.class, id);
			return galleria;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void delete(String id) {
		try {
			Galleria galleria = em.find(Galleria.class, id);
			em.remove(galleria);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
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
