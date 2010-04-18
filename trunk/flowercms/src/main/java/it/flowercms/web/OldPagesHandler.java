package it.flowercms.web;

import it.flowercms.par.Page;
import it.flowercms.par.Template;
import it.flowercms.par.TemplateImpl;
import it.flowercms.session.PageSession;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.seamframework.tx.Transactional;

@Named
@SessionScoped
public class OldPagesHandler implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	PageSession pageSession;

	private Page page;

	private List<Page> allpages;

	public OldPagesHandler() {
		System.out.println("PagesHandler");
	}

	public String add() {
		this.page = new Page();
		page.setTemplate(new TemplateImpl());
		page.getTemplate().setTemplate(new Template());
		return "/private/pages/management?redirect=true";
	}

	public String create() {
		try {
			pageSession.persist(this.page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/private/pages/index?redirect=true";
	}

	public String management(String id) {
		try {
			this.page = pageSession.find(id);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "/private/pages/management?redirect=true";
	}

	public String update() {
		try {
			pageSession.update(this.page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/private/pages/index?redirect=true";
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public List<Page> getAllpages() {
		if (this.allpages == null)
			this.allpages = pageSession.getAllList();
		return allpages;
	}

	public void setAllpages(List<Page> allpages) {
		this.allpages = allpages;
	}

	@Transactional
	public void createPage(String title, String description, String content) {
		System.out.println("CREO PAGINA: " + title);
		Page page = new Page();
		page.setTitle(title);
		page.setContent(content);
		pageSession.persist(page);
		System.out.println("ID: " + page.getId());
	}

	@Transactional
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

}