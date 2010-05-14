package it.jflower.cc.session;

import it.jflower.base.session.SuperSession;
import it.jflower.cc.par.Page;
import it.jflower.cc.utils.PageUtils;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.seamframework.tx.Transactional;

@Named
@SessionScoped
public class PageSession extends SuperSession<Page> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	EntityManager em;

	@Override
	public EntityManager getEm() {
		return em;
	}

	@Override
	protected Class<Page> getEntityType() {
		return Page.class;
	}

	@Override
	protected String getOrderBy() {
		return "title";
	}

	@Override
	public void setEm(EntityManager em) {
		this.em = em;
	}

	@Override
	@Transactional
	protected Page prePersist(Page page) {
		String idTitle = PageUtils.createPageId(page.getTitle());
		String idFinal = testId(idTitle);
		page.setId(idFinal);
		return page;
	}

	public String testId(String id) {
		String idFinal = id;
		boolean trovato = false;
		int i = 0;
		while (!trovato) {
			// System.out.println("id final: " + idFinal);
			Page pageFind = find(idFinal);
			// System.out.println("trovato_ " + pageFind);
			if (pageFind != null) {
				i++;
				idFinal = id + "-" + i;
			} else {
				trovato = true;
				return idFinal;
			}
		}
		return "";
	}

}
