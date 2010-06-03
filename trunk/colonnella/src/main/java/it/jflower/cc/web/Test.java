package it.jflower.cc.web;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class Test implements Serializable {
	@Inject
	ParamsHandler paramsHandler;

	public Test() {
		// System.out.println("creo Test: " + paramsHandler.getParam("id"));
	}

	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void loadEntry() {
		System.out.println("creo Test: " + paramsHandler.getParam("id"));
		FacesContext ctx = FacesContext.getCurrentInstance();
		if (ctx.isValidationFailed()) {
			ctx.getApplication().getNavigationHandler().handleNavigation(ctx,
					"#{test.id}", "invalid");
			return;
		}

		// load entry
	}

}
