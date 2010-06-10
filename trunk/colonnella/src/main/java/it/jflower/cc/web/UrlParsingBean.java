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

	@Inject
	ParamsHandler paramsHandler;

	public UrlParsingBean() {
		System.out.println("OK UrlParsingBean");
	}

	public String parseComplexUrl() throws UnsupportedEncodingException {
		// String uri = PrettyContext.getCurrentInstance().getOriginalUri();
		// System.out.println("viewID: "
		// + PrettyContext.getCurrentInstance().getCurrentViewId());
		// System.out
		// .println("URL: "
		// + PrettyContext.getCurrentInstance().getRequestURL()
		// .toString());
		// System.out.println("query: "
		// + PrettyContext.getCurrentInstance().getRequestQueryString()
		// .toQueryString());
		String uri = PrettyContext.getCurrentInstance().getRequestURL()
				.toString();
		String uriPars = PrettyContext.getCurrentInstance()
				.getRequestQueryString().toQueryString();
		System.out.println("uri: " + uri);
		System.out.println("uriPars: " + uriPars);

		@SuppressWarnings("unused")
		List<String> categoryChain = new ArrayList<String>();

		if (uriPars.contains("?")) {
			String params = uriPars.substring(uriPars.lastIndexOf("?") + 1);
			if (!params.contains("&")) {
				System.out.println("param: "
						+ URLDecoder.decode(params, "UTF-8"));
				paramsHandler.addParam(params);
			} else {
				String[] pars = params.split("&");
				for (String string : pars) {
					if (!string.equals("")) {
						System.out.println("param: "
								+ URLDecoder.decode(string, "UTF-8"));
						paramsHandler.addParam(string);
					}
				}
			}

		} else {
			System.out.println("NON CI SONO PARAMETRI");
		}
		String pageId = uri.substring(uri.lastIndexOf("/") + 1);
		String[] str = uri.split("/");
		for (String string : str) {
			if (!string.equals(""))
				System.out.println("bb: " + URLDecoder.decode(string, "UTF-8"));
		}

		breadCrumpsHandler.setBreadCrump(uri);
		// #{pagesHandler.pageId}
		System.out.println("pageId:" + pageId);
		dbPageHandler.getPage().setId(pageId);
		return "/page.jsf";

	}
}
