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

	@Inject
	PubblicazioniHandlerRequest pubblicazioniHandlerRequest;

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
			if ("leggiNews".toLowerCase().equals(
					this.page.getId().toLowerCase())) {
				if (newsHandlerRequest.getNotizia() != null
						&& newsHandlerRequest.getNotizia().getTitolo() != null)
					return newsHandlerRequest.getNotizia().getTitolo();
			}
			if ("leggiAttivita".toLowerCase().equals(
					this.page.getId().toLowerCase())) {
				if (attivitaHandlerRequest.getAttivita() != null
						&& attivitaHandlerRequest.getAttivita().getNome() != null)
					return attivitaHandlerRequest.getAttivita().getNome();
			}
			if ("leggiPubblicazione".toLowerCase().equals(
					this.page.getId().toLowerCase())) {
				if (pubblicazioniHandlerRequest.getPubblicazione() != null
						&& pubblicazioniHandlerRequest.getPubblicazione()
								.getNome() != null)
					return pubblicazioniHandlerRequest.getPubblicazione()
							.getNome();
			}
			if (this.page.getTitle() != null
					&& this.page.getTitle().length() > 0) {
				return this.page.getTitle();
			}
			this.page = pageSession.fetch(this.page.getId());
			return this.page.getTitle();
		}
		return "";

	}
}