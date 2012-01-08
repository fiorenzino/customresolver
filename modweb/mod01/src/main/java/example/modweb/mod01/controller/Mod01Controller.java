package example.modweb.mod01.controller;

import java.io.Serializable;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import example.modweb.mod01.model.Mod01Model;
import example.modweb.mod01.repository.Mod01Repository;

@Model
public class Mod01Controller implements Serializable {

	private static final long serialVersionUID = 1L;
	private Mod01Model element;
	@Inject
	Mod01Repository mod01Repository;

	public Mod01Controller() {
		// TODO Auto-generated constructor stub
	}

	public void save() {
		element = mod01Repository.persist(getElement());
		System.out.println(element.getId());
	}

	public Mod01Model getElement() {
		if (element == null)
			element = new Mod01Model();
		return element;
	}

	public void setElement(Mod01Model element) {
		this.element = element;
	}
}