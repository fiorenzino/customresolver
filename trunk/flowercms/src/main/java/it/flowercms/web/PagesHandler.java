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

@Named
@SessionScoped
public class PagesHandler implements Serializable {

	@Inject
	PageSession pageSession;

	private Page page;

	private List<Page> allpages;

	public PagesHandler() {
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
			pageSession.create(this.page);
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
			this.allpages = pageSession.getAll();
		return allpages;
	}

	public void setAllpages(List<Page> allpages) {
		this.allpages = allpages;
	}

}