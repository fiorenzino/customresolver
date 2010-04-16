package weld.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

	@Inject
	EntityManager em;
	private boolean editMode;

	private String page = "index";

	private Page currentPage;
	private List<Page> allpages;

	public PagesHandler() {
		System.out.println("NUOVO");
	}

	public String getData() {
		return "" + System.currentTimeMillis();
	}

	public String creaPagina() {
		this.currentPage = new Page();
		setEditMode(false);
		return "/crea-pagina?redirect=true";
	}

	public String modPage(String id) {
		this.currentPage = findPage(id);
		setEditMode(true);
		return "/crea-pagina?redirect=true";
	}

	@SuppressWarnings( { "unchecked" })
	@Transactional
	public String updatePagina() {
		try {
			em.merge(this.currentPage);
		} catch (Exception e) {

			e.printStackTrace();

		}
		return "/index?redirect=true";

	}

	@SuppressWarnings( { "unchecked" })
	@Transactional
	public String salvaPagina() {
		try {
			String idTitle = PagesUtils.createPageId(currentPage.getTitle());
			String idFinal = testId(idTitle);
			this.currentPage.setId(idFinal);
			em.persist(this.currentPage);
			System.out.println("ciao");
		} catch (Exception e) {

			e.printStackTrace();

		}
		return "/index?redirect=true";

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

	@Transactional
	public Page findPage(String pageName) {
		try {
			Page page = em.find(Page.class, pageName);
			return page;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings( { "unchecked" })
	@Transactional
	public List<Page> getAllpages() {
		System.out.println("get all pages");

		this.allpages = new ArrayList<Page>();
		try {
			this.allpages = em
					.createQuery("select p from Page p order by p.id")
					.getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.allpages;
	}

	public void setAllpages(List<Page> allpages) {
		this.allpages = allpages;
	}

	public String testId(String id) {
		String idFinal = id;
		boolean trovato = false;
		int i = 0;
		while (!trovato) {
			System.out.println("id final: " + idFinal);
			Page pageFind = findPage(idFinal);
			System.out.println("trovato_ " + pageFind);
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

	public boolean isEditMode() {
		return editMode;
	}

	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}

}