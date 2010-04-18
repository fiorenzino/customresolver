package it.flowercms.session;

import it.flowercms.par.MenuGroup;
import it.flowercms.session.base.SuperSession;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

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

}
