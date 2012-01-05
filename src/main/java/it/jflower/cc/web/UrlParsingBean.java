package it.jflower.cc.web;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;

import com.ocpsoft.pretty.PrettyContext;
import com.ocpsoft.pretty.faces.url.QueryString;

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

	private String idPage;

	public UrlParsingBean() {
		// logger.info("OK UrlParsingBean");
	}

	public String parseComplexUrl() throws UnsupportedEncodingException {
		// String uri = PrettyContext.getCurrentInstance().getOriginalUri();
		// String uriPars = PrettyContext.getCurrentInstance()
		// .getOriginalRequestUrl();
		// logger.info("uri: " + uri);
		// // logger.info("uriPars: " + uriPars);
		//
		// @SuppressWarnings("unused")
		// List<String> categoryChain = new ArrayList<String>();
		//
		// if (uriPars.contains("?")) {
		// String params = uriPars.substring(uriPars.lastIndexOf("?") + 1);
		// if (!params.contains("&")) {
		// logger.info("param: " + URLDecoder.decode(params, "UTF-8"));
		// paramsHandler.addParam(params);
		// } else {
		// String[] pars = params.split("&");
		// for (String string : pars) {
		// if (!string.equals("")) {
		// logger.info("param: "
		// + URLDecoder.decode(string, "UTF-8"));
		// paramsHandler.addParam(string);
		// }
		// }
		// }
		//
		// } else {
		// logger.info("NON CI SONO PARAMETRI");
		// }
		// if (uri.contains("/")) {
		// String pageId = uri.substring(uri.lastIndexOf("/") + 1);
		// // String[] str = uri.split("/");
		// // for (String string : str) {
		// // if (!string.equals(""))
		// // logger.info("bb: " + URLDecoder.decode(string, "UTF-8"));
		// // }
		//
		// breadCrumpsHandler.setBreadCrump(uri);
		// // #{pagesHandler.pageId}
		// logger.info("pageId:" + pageId);
		// dbPageHandler.getPage().setId(pageId);
		// }
		//
		// return "/page.jsf";

		System.out.println("start uri****************");
		String uri = PrettyContext.getCurrentInstance().getRequestURL().toURL();
		System.out.println("uri: " + uri);
		System.out.println("stop uri****************");
		String pageId = uri.substring(uri.lastIndexOf("/") + 1);

		System.out.println("start pageId****************");
		System.out.println("pageId: " + pageId);
		System.out.println("stop pageId****************");
		if (!uri.startsWith("/pagine")) {
			return uri;
		}
//		if (uri.startsWith("/css") || uri.startsWith("/img")
//				|| uri.startsWith("/styles")) {
//			return uri;
//		}
		String contextPath = PrettyContext.getCurrentInstance()
				.getContextPath();
		logger.info("contextPath: " + contextPath);
		// parte del sito senza parametri
		// String uri =
		// PrettyContext.getCurrentInstance().getRequestURL().toURL();
		logger.info("uri: " + uri);
		// String uri = PrettyContext.getCurrentInstance().getOriginalUri();
		// requestQuery = ?
		// String uri =
		// PrettyContext.getCurrentInstance().getRequestURL().toURL();
		QueryString queryParams = PrettyContext.getCurrentInstance()
				.getRequestQueryString();
		// queryParams.getParameterMap()
		// if (uri.contains("?")) {
		// uri = uri.substring(0, uri.lastIndexOf("?"));
		// }
		// logger.info("uri: " + uri);

		if (!queryParams.isEmpty()) {
			// String params = uriPars.substring(uriPars.lastIndexOf("?") + 1);
			// if (!params.contains("&")) {
			// logger.info("param: "
			// + URLDecoder.decode(params, "UTF-8"));
			// paramsHandler.addParam(params);
			// } else {
			// String[] pars = params.split("&");
			// for (String string : pars) {
			// if (!string.equals("")) {
			// logger.info("param: "
			// + URLDecoder.decode(string, "UTF-8"));
			// paramsHandler.addParam(string);
			// }
			// }
			// }
			logger.info("start queryParams****************");
			Map<String, String[]> mappa = queryParams.getParameterMap();
			for (String key : mappa.keySet()) {
				String[] value = mappa.get(key);
				if (value != null && value.length > 0) {
					paramsHandler.addParam(key, value[0]);
					logger.info(key + ": " + value[0]);
					if (key.equals("lang")) {
						System.out
								.println("attenzione potrebbe essere un cambio lingua");
					}
				}
			}
			logger.info("stop queryParams****************");

		} else {
			logger.info("NO queryParams****************");
		}
		// String pageId = uri.substring(uri.lastIndexOf("/") + 1);
		// String[] str = uri.split("/");
		// for (String string : str) {
		// if (!string.equals(""))
		// logger.info("bb: " + URLDecoder.decode(string, "UTF-8"));
		// }

		breadCrumpsHandler.setBreadCrump(contextPath + uri);
		// #{pagesHandler.pageId}
		// logger.info("pageId:" + pageId);

		// prima di impostare la pagina
		// verifico che la lang tra i parametri sia la stessa della pagina
		// corrente, altrimenti cerco la pagina nella lingua specificata
		// altrimenti cerco la index nella lingua specificata
		dbPageHandler.getPage().setId(pageId);
		return "/page.jsf";

	}

	public String getIdPage() {
		return idPage;
	}

	public void setIdPage(String idPage) {
		this.idPage = idPage;
	}
}
