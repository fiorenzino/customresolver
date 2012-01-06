package by.giava.base.repository;

import it.coopservice.commons2.domain.Search;
import it.coopservice.commons2.repository.AbstractRepository;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import by.giava.base.model.MenuGroup;
import by.giava.base.model.MenuItem;

@Named
@Stateless
@LocalBean
public class MenuRepository extends AbstractRepository<MenuGroup> {

	private static final long serialVersionUID = 1L;

	@Inject
	EntityManager em;

	public EntityManager getEm() {
		return em;
	}

	protected Class<MenuGroup> getEntityType() {
		return MenuGroup.class;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

	public MenuItem findItem(Long id) {
		return getEm().find(MenuItem.class, id);
	}

	public boolean updateItem(MenuItem mi) {
		try {
			getEm().merge(mi);
			return true;
		} catch (Exception e) {
			logger.info(e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Override this if needed
	 * 
	 * @param object
	 * @return
	 */

	protected MenuGroup preUpdate(MenuGroup mg) {
		for (MenuItem mi : mg.getLista()) {
			if (mi.getId() == null) {
				em.persist(mi);
			} else {
				em.merge(mi);
			}
		}
		processActiveMenuItems(mg);
		return mg;
	}

	public List<MenuGroup> getAllList() {
		List<MenuGroup> result = super.getAllList();
		processActiveMenuItems(result);
		return result;
	}

	@Override
	public List<MenuGroup> getList(Search<MenuGroup> ricerca, int startRow,
			int pageSize) {
		List<MenuGroup> result = super.getList(ricerca, startRow, pageSize);
		processActiveMenuItems(result);
		return result;
	}

	private void processActiveMenuItems(List<MenuGroup> result) {
		if (result != null) {
			for (MenuGroup mg : result) {
				processActiveMenuItems(mg);
			}
		}
	}

	private void processActiveMenuItems(MenuGroup mg) {
		mg.setListaAttivi(null);
		if (mg.getLista() != null) {
			for (MenuItem mi : mg.getLista()) {
				if (mi.isAttivo()) {
					mg.getListaAttivi().add(mi);
				}
			}
		}
		Collections.sort(mg.getListaAttivi(), new Comparator<MenuItem>() {
			public int compare(MenuItem o1, MenuItem o2) {
				return o1.getOrdinamento() == null ? -1 : o1.getOrdinamento()
						.compareTo(o2.getOrdinamento());
			}
		});
	}

	@Override
	protected String getDefaultOrderBy() {
		return "percorso asc";
	}

}
