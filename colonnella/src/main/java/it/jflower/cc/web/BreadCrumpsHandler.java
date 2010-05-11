package it.jflower.cc.web;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class BreadCrumpsHandler implements Serializable {

	private static final long serialVersionUID = 1L;
	
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
