package it.flowercms.web;

import it.flowercms.par.Page;
import it.flowercms.session.PageSession;

import java.io.Serializable;
import java.util.Random;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@SessionScoped
public class DbPageHandler implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final Random rnd = new Random();

	@Inject
	PageSession pageSession;

	private Page page;

	public DbPageHandler() {
	}

	public Page getPage() {
		if ( page == null )
			page = new Page();
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public String getPageId() {
		return getPage().getId();
	}
	public void setPageId(String pageId) {
		getPage().setId(pageId);
	}

	public String getRng() {
		return "" + rnd.nextLong();
	}
}