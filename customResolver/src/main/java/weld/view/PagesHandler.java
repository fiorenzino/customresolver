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

	}

	@SuppressWarnings( { "unchecked" })
	@Transactional
	public void createPages() {
		System.out.println("CREO PAGINE");
		Page page = new Page();
		page.setTitle("Chi Siamo");
		page.setDescription("Chi siamo e cosa facciamo");
		page.setId(PagesUtils.createPageId(page.getTitle()));
		page.setContent("<br/>ciao siamo solo noi!<br/> ");
		em.persist(page);
		System.out.println("ID: " + page.getId());

		page = new Page();
		page.setTitle("Home Page");
		page.setDescription("benvenuti");
		page.setId(PagesUtils.createPageId(page.getTitle()));
		page.setContent("<br/>ciao benvenuti!<br/> ");
		em.persist(page);
		System.out.println("ID: " + page.getId());

		page = new Page();
		page.setTitle("Dove Siamo");
		page.setDescription("i nostri uffici");
		page.setId(PagesUtils.createPageId(page.getTitle()));
		page.setContent("<br/>ciao siamo ovunque!<br/> ");
		em.persist(page);
		System.out.println("ID: " + page.getId());

		page = new Page();
		page.setTitle("Cosa Facciamo");
		page.setDescription("la ns attivita'");
		page.setId(PagesUtils.createPageId(page.getTitle()));
		page.setContent("<br/>non facciamo un cazzo!<br/> ");
		em.persist(page);
		System.out.println("ID: " + page.getId());
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