package by.giava.gallerie.repository;

import it.coopservice.commons2.repository.AbstractRepository;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import by.giava.base.controller.util.PageUtils;
import by.giava.gallerie.model.Galleria;

@Named
@Stateless
@LocalBean
public class GallerieRepository extends AbstractRepository<Galleria> {

	private static final long serialVersionUID = 1L;

	@Inject
	EntityManager em;

	@Override
	public boolean update(Galleria galleria) {
		try {
			closeHtmlTags(galleria);
			em.merge(galleria);
			return true;
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		return false;
	}

	@Override
	protected Galleria prePersist(Galleria galleria) {
		String idTitle = PageUtils.createPageId(galleria.getTitolo());
		String idFinal = testId(idTitle);
		galleria.setId(idFinal);
		return galleria;
	}

	public String testId(String id) {
		String idFinal = id;
		boolean trovato = false;
		int i = 0;
		while (!trovato) {
			logger.info("id final: " + idFinal);
			Galleria galleriaFind = find(idFinal);
			logger.info("trovato_ " + galleriaFind);
			if (galleriaFind != null) {
				i++;
				idFinal = id + "-" + i;
			} else {
				trovato = true;
				return idFinal;

			}
		}

		return "";
	}

	@Override
	public Galleria persist(Galleria galleria) {
		try {
			closeHtmlTags(galleria);
			em.persist(galleria);
		} catch (Exception e) {
			logger.info(e.getMessage());
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

	@Override
	public Galleria find(Object id) {
		try {
			Galleria galleria = em.find(Galleria.class, id);
			return galleria;
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		return null;
	}

	@Override
	public boolean delete(Object key) {
		try {
			Galleria galleria = em.find(Galleria.class, key);
			em.remove(galleria);
			return true;
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		return false;
	}

	@Override
	public List<Galleria> getAllList() {
		List<Galleria> all = new ArrayList<Galleria>();
		try {
			all = em.createQuery("select p from Galleria p order by p.id")
					.getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return all;
	}

	@Override
	protected EntityManager getEm() {
		return em;
	}

	@Override
	public void setEm(EntityManager em) {
		this.em = em;
	}

	@Override
	protected String getDefaultOrderBy() {
		return "id";
	}
}
