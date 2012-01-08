package by.giava.base.controller.request;

import java.io.Serializable;
import java.util.Random;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import by.giava.attivita.controller.request.AttivitaRequestController;
import by.giava.base.model.Page;
import by.giava.base.repository.PageRepository;
import by.giava.news.controller.request.NewsRequestController;
import by.giava.pubblicazioni.controller.request.PubblicazioniRequestController;

@Named
@RequestScoped
public class DbPageHandler implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final Random rnd = new Random();

	@Inject
	PageRepository pageRepository;

	@Inject
	NewsRequestController newsRequestController;

	@Inject
	AttivitaRequestController attivitaRequestController;

	@Inject
	PubblicazioniRequestController pubblicazioniRequestController;

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
				if (newsRequestController.getElement() != null
						&& newsRequestController.getElement().getTitolo() != null)
					return newsRequestController.getElement().getTitolo();
			}
			if ("leggiAttivita".toLowerCase().equals(
					this.page.getId().toLowerCase())) {
				if (attivitaRequestController.getElement() != null
						&& attivitaRequestController.getElement().getNome() != null)
					return attivitaRequestController.getElement().getNome();
			}
			if ("leggiPubblicazione".toLowerCase().equals(
					this.page.getId().toLowerCase())) {
				if (pubblicazioniRequestController.getElement() != null
						&& pubblicazioniRequestController.getElement()
								.getNome() != null)
					return pubblicazioniRequestController.getElement()
							.getNome();
			}
			if (this.page.getTitle() != null
					&& this.page.getTitle().length() > 0) {
				return this.page.getTitle();
			}
			this.page = pageRepository.fetch(this.page.getId());
			return this.page.getTitle();
		}
		return "";

	}
}