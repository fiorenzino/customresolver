package it.jflower.cc.web;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

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

	protected Logger logger = Logger.getLogger(getClass().getName());

	public UrlParsingBean() {
		// logger.info("OK UrlParsingBean");
	}

	public String parseComplexUrl() throws UnsupportedEncodingException {
		String uri = PrettyContext.getCurrentInstance().getOriginalUri();
		String uriPars = PrettyContext.getCurrentInstance()
				.getOriginalRequestUrl();
		logger.info("uri: " + uri);
		// logger.info("uriPars: " + uriPars);

		@SuppressWarnings("unused")
		List<String> categoryChain = new ArrayList<String>();

		if (uriPars.contains("?")) {
			String params = uriPars.substring(uriPars.lastIndexOf("?") + 1);
			if (!params.contains("&")) {
				logger.info("param: " + URLDecoder.decode(params, "UTF-8"));
				paramsHandler.addParam(params);
			} else {
				String[] pars = params.split("&");
				for (String string : pars) {
					if (!string.equals("")) {
						logger.info("param: "
								+ URLDecoder.decode(string, "UTF-8"));
						paramsHandler.addParam(string);
					}
				}
			}

		} else {
			logger.info("NON CI SONO PARAMETRI");
		}
		String pageId = uri.substring(uri.lastIndexOf("/") + 1);
		String[] str = uri.split("/");
		for (String string : str) {
			if (!string.equals(""))
				logger.info("bb: " + URLDecoder.decode(string, "UTF-8"));
		}

		breadCrumpsHandler.setBreadCrump(uri);
		// #{pagesHandler.pageId}
		logger.info("pageId:" + pageId);
		dbPageHandler.getPage().setId(pageId);
		return "/page.jsf";

	}
}
