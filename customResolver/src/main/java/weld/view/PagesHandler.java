package weld.view;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.seamframework.tx.Transactional;

import weld.model.Page;
import weld.view.utils.PagesUtils;

@Named
@SessionScoped
public class PagesHandler implements Serializable {

	private String page = "index";
	private Page currentPage;
	@Inject
	EntityManager em;

	PagesHandler() {
		System.out.println("NUOVO");
	}

	@SuppressWarnings( { "unchecked" })
	@Transactional
	public void createPage(String title, String description, String content) {
		System.out.println("CREO PAGINA: " + title);
		Page page = new Page();
		page.setTitle(title);
		page.setDescription(description);
		page.setId(PagesUtils.createPageId(page.getTitle()));
		page.setContent(content);
		em.persist(page);
		System.out.println("ID: " + page.getId());
	}

	public void createPages() {
		System.out.println("CREO PAGINE");

		createPage("Chi Siamo", "Chi siamo e cosa facciamo",
				"<br/>ciao siamo solo noi!<br/> ");

		createPage("Home Page", "benvenuti", "<br/>ciao benvenuti!<br/> ");

		createPage("Dove Siamo", "i nostri uffici",
				"<br/>ciao siamo ovunque!<br/> ");

		createPage("Cosa Facciamo", "la ns attivita'",
				"<br/>non facciamo un cazzo!<br/> ");

		System.out.println("FATTO");
	}

	@SuppressWarnings( { "unchecked" })
	@Transactional
	public void findPage() {
		try {
			this.currentPage = em.find(Page.class, getPage());

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (this.currentPage == null)
			this.currentPage = em.find(Page.class, PagesUtils
					.createPageId("Home Page"));

	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.currentPage = null;
		this.page = page;
	}

	public Page getCurrentPage() {
		if (this.currentPage == null)
			findPage();
		return currentPage;
	}

	public void setCurrentPage(Page currentPage) {
		this.currentPage = currentPage;
	}

	public Page findPage(String pageName) {
		try {
			return em.find(Page.class, getPage());
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

}