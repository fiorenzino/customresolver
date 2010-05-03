package weld.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import weld.model.Page;
import weld.session.PagesSession;
import weld.view.utils.PagesUtils;

@Named
@SessionScoped
public class PagesHandler implements Serializable {

	@Inject
	PagesSession pagesSession;
	private boolean editMode;

	private Page page;
	private List<Page> all;

	private String pageId;

	public PagesHandler() {
		System.out.println("NUOVO");
	}

	public String getData() {
		return "" + System.currentTimeMillis();
	}

	public String creaPagina() {
		this.page = new Page();
		setEditMode(false);
		return "/private/pagine/gestione-pagina?redirect=true";
	}

	public String modPage(String id) {
		this.page = pagesSession.find(id);
		setEditMode(true);
		return "/private/pagine/gestione-pagina?redirect=true";
	}

	public String updatePagina() {
		pagesSession.update(this.page);
		this.all = null;
		return "/private/pagine/lista-pagine?redirect=true";

	}

	public String deletePagina() {
		pagesSession.delete(this.page.getId());
		this.all = null;
		return "/private/pagine/lista-pagine?redirect=true";
	}

	public String salvaPagina() {
		try {
			String idTitle = PagesUtils.createPageId(page.getTitle());
			String idFinal = testId(idTitle);
			this.page.setId(idFinal);
			pagesSession.persist(this.page);
			this.all = null;
		} catch (Exception e) {

			e.printStackTrace();

		}
		return "/private/pagine/lista-pagine?redirect=true";

	}

	public void createPage(String title, String description, String content) {
		System.out.println("CREO PAGINA: " + title);
		Page page = new Page();
		page.setTitle(title);
		page.setDescription(description);
		page.setId(PagesUtils.createPageId(page.getTitle()));
		page.setContent(content);
		pagesSession.persist(page);
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

	public List<Page> getAll() {
		if (this.all == null) {
			this.all = new ArrayList<Page>();
			try {
				this.all = pagesSession.getAll();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return this.all;
	}

	public void setAll(List<Page> all) {
		this.all = all;
	}

	public String testId(String id) {
		String idFinal = id;
		boolean trovato = false;
		int i = 0;
		while (!trovato) {
			System.out.println("id final: " + idFinal);
			Page pageFind = pagesSession.find(idFinal);
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

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

}