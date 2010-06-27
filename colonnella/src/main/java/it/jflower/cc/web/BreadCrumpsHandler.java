package it.jflower.cc.web;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named
@RequestScoped
public class BreadCrumpsHandler implements Serializable {

	private static final long serialVersionUID = 1L;

	private String breadCrump;

	private String pageName;

	public BreadCrumpsHandler() {
		// TODO Auto-generated constructor stub
	}

	public void setBreadCrump(String breadCrump) {
		this.breadCrump = breadCrump;
	}

	public String getBreadCrump() {
		String url = breadCrump;
		String[] crumbs = url.split("/");

		FacesContext fc = FacesContext.getCurrentInstance();
		String base = fc.getExternalContext().getRequestContextPath();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < crumbs.length; i++) {
			if (crumbs[i].trim().equals("")) {
				continue;
			} else if (crumbs[i].trim().equals(base.replaceAll("/", ""))) {
				String label = "home";
				sb.append("<a href=\"" + base + "\" title=\"" + crumbs[i]
						+ "\">" + label + "</a> ");
				sb.append("<span class=\"freccia\">&gt;</span> ");
			} else if (crumbs[i].trim().equals("pagine")) {
				base += "/" + crumbs[i];
			} else if (!crumbs[i].trim().equals("pagine")) {
				base += "/" + crumbs[i];
				String label = crumbs[i].replaceAll("-", " ");

				sb.append("<a href=\"" + base + "\" title=\"" + crumbs[i]
						+ "\">" + label + "</a> ");
				if (i != crumbs.length - 1)
					sb.append("<span class=\"freccia\">&gt;</span> ");
			}
		}
		return sb.toString();
	}

	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}
}
