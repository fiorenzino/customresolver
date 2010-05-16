package it.jflower.cc.session;

import it.jflower.base.session.SuperSession;
import it.jflower.cc.par.Attivita;
import it.jflower.cc.par.type.CategoriaAttivita;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

@Named
@SessionScoped
public class AttivitaSession 
extends SuperSession<Attivita> implements Serializable {

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
	protected Class<Attivita> getEntityType() {
		return Attivita.class;
	}

	@Override
	protected String getOrderBy() {
		return "nome";
	}

	@Override
	protected Attivita prePersist(Attivita a) {
		a.setCategoria( getEm().find(CategoriaAttivita.class, a.getCategoria().getId() ));
		return a;
	}

	@Override
	protected Attivita preUpdate(Attivita a) {
		a.setCategoria( getEm().find(CategoriaAttivita.class, a.getCategoria().getId() ));
		return a;
	}

}
