package it.flowercms.web;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.ocpsoft.pretty.PrettyContext;

@Named
@RequestScoped
public class UrlHandler implements Serializable {

	private static final long serialVersionUID = 1L;

	private String uri;
	
	@Inject
	DbPageHandler dbPageHandler;

	public UrlHandler() {
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String viewId() throws UnsupportedEncodingException {

		// es: /flowercms/private/amministrazione.jsf

		uri = PrettyContext.getCurrentInstance().getOriginalUri().substring(1);

		String[] str = uri.split("/");

		dbPageHandler.setPageId(str[str.length-1]);
		return "database-page.jsf";

	}
}
