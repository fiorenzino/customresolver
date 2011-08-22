package it.jflower.cc.web;

import it.jflower.cc.par.Page;
import it.jflower.cc.session.PageSession;

import java.io.Serializable;
import java.util.Random;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class DbPageHandler implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final Random rnd = new Random();

	@Inject
	PageSession pageSession;

	@Inject
	NewsHandlerRequest newsHandlerRequest;

	@Inject
	AttivitaHandlerRequest attivitaHandlerRequest;

	private Page page;

	// private String pageName;

	public DbPageHandler() {
	}

	public Page getPage() {
		if (page == null)
			page = new Page();
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public String getRng() {
		return "" + rnd.nextLong();
	}

	public String getPageName() {
		if ((this.page != null) && (this.page.getId() != null)) {
			if ("leggiNews".equals(this.page.getId())) {
				if (newsHandlerRequest.getNotizia() != null)
					return newsHandlerRequest.getNotizia().getTitolo();
			}
			if ("leggiAttivita".equals(this.page.getId())) {
				if (attivitaHandlerRequest.getAttivita() != null)
					return attivitaHandlerRequest.getAttivita().getNome();
			}
			if ( this.page.getTitle() != null && this.page.getTitle().length() > 0 ) {
				return this.page.getTitle();
			}
			this.page = pageSession.fetchPage(this.page.getId());
			return this.page.getTitle();
		}
		return "";

	}
}