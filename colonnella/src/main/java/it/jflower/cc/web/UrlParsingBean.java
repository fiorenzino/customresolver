package it.jflower.cc.web;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.ocpsoft.pretty.PrettyContext;

@Named
@RequestScoped
public class UrlParsingBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	DbPageHandler dbPageHandler;

	@Inject
	BreadCrumpsHandler breadCrumpsHandler;

	public UrlParsingBean() {
		System.out.println("OK UrlParsingBean");
	}

	public String parseComplexUrl() throws UnsupportedEncodingException {
		String uri = PrettyContext.getCurrentInstance().getOriginalUri();
		System.out.println("uri: " + uri);

		@SuppressWarnings("unused")
		List<String> categoryChain = new ArrayList<String>();
		
		String pageId = uri.substring(uri.lastIndexOf("/") + 1);
		String[] str = uri.split("/");
		for (String string : str) {
			System.out.println("bb: " + URLDecoder.decode(string, "UTF-8"));
		}
		breadCrumpsHandler.setBreadCrump(uri);
		// #{pagesHandler.pageId}
		System.out.println("pageId:" + pageId);
		dbPageHandler.getPage().setId(pageId);
		return "/page.jsf";

	}
}
