package example.modweb.mod02.controller;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import example.modweb.mod02.model.Mod02Model;
import example.modweb.mod02.repository.Mod02Repository;

@Model
public class Mod02Controller implements Serializable {

	private static final long serialVersionUID = 1L;
	private Mod02Model element;
	@Inject
	Mod02Repository mod02Repository;

	public Mod02Controller() {
		// TODO Auto-generated constructor stub
	}

	public void save() {
		element = mod02Repository.persist(getElement());
		System.out.println("SALVATO ELEMENTO Mod02Model: " + element.getId());
	}

	public Mod02Model getElement() {
		if (element == null)
			element = new Mod02Model();
		return element;
	}

	public void setElement(Mod02Model element) {
		this.element = element;
	}

	public List<Mod02Model> getList() {
		return mod02Repository.getAll();
	}
}