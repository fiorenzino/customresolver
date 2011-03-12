package it.jflower.cc.session;

import it.jflower.base.par.Ricerca;
import it.jflower.base.session.SuperSession;
import it.jflower.cc.par.MenuGroup;
import it.jflower.cc.par.MenuItem;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.seamframework.tx.Transactional;

@Named
@SessionScoped
public class MenuSession
extends SuperSession<MenuGroup>
implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	EntityManager em;

	@Override
	public EntityManager getEm() {
		return em;
	}

	@Override
	protected Class<MenuGroup> getEntityType() {
		return MenuGroup.class;
	}

	@Override
	protected String getOrderBy() {
		return "percorso";
	}

	@Override
	public void setEm(EntityManager em) {
		this.em = em;
	}

	public MenuItem findItem(Long id) {
		return getEm().find(MenuItem.class, id);
	}

	public boolean updateItem(MenuItem mi) {
		try{
			getEm().merge(mi);
			return true;
		}
		catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Override this if needed
	 * @param object
	 * @return
	 */
	@Override
	protected MenuGroup preUpdate(MenuGroup mg) {
		for ( MenuItem mi : mg.getLista() ) {
			if ( mi.getId() == null ) {
				em.persist(mi);
			}
			else {
				em.merge(mi);
			}
		}
		return mg;
	}
	

	
	
	
	
	@Transactional
	@Override
	public List<MenuGroup> getAllList() {
		List<MenuGroup> result = super.getAllList();
		processActiveMenuItems(result);
		return result;
	}

	@Transactional
	@Override
	public List<MenuGroup> getList(Ricerca<MenuGroup> ricerca, int startRow, int pageSize) {
		List<MenuGroup> result = super.getList(ricerca,startRow,pageSize);
		processActiveMenuItems(result);
		return result;
	}

	private void processActiveMenuItems(List<MenuGroup> result) {
		if ( result != null ) {
			for ( MenuGroup mg : result ) {
				if ( mg.getLista() != null ) {
					for ( MenuItem mi : mg.getLista() ) {
						if ( mi.isAttivo() ) {
							mg.getListaAttivi().add(mi);
						}
					}
				}
			}
		}
	}


	

	
}
