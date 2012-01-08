package by.giava.moduli.repository;

import it.coopservice.commons2.repository.AbstractRepository;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import by.giava.moduli.model.type.TipoModulo;

@Named
@Stateless
@LocalBean
public class TipoModuloRepository extends AbstractRepository<TipoModulo> {
	private static final long serialVersionUID = 1L;

	@Inject
	EntityManager em;

	@Override
	public EntityManager getEm() {
		return em;
	}

	@Override
	public void setEm(EntityManager em) {
		this.em = em;
	}

	@Override
	protected String getDefaultOrderBy() {
		// TODO Auto-generated method stub
		return "nome asc";
	}

	public List<TipoModulo> getAllTipoModulo() {

		List<TipoModulo> all = new ArrayList<TipoModulo>();
		try {
			all = (List<TipoModulo>) em
					.createQuery(
							"select p from TipoModulo p where p.attivo = :attivo order by p.id")
					.setParameter("attivo", true).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return all;
	}

}
