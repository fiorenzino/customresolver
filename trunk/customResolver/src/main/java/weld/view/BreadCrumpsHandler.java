package weld.view;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class BreadCrumpsHandler implements Serializable {
	private String breadCrump;

	public BreadCrumpsHandler() {
		// TODO Auto-generated constructor stub
	}

	public String getBreadCrump() {
		return breadCrump;
	}

	public void setBreadCrump(String breadCrump) {
		this.breadCrump = breadCrump;
	}
}
