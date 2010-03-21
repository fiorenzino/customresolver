package it.jflower.par;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Page implements Serializable {

	private String id;
	private String nome;
	private TemplateImpl template;
	private Menu menu;
	private Banner banner;

	@Id
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@OneToOne
	public TemplateImpl getTemplate() {
		return template;
	}

	public void setTemplate(TemplateImpl template) {
		this.template = template;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	public Banner getBanner() {
		return banner;
	}

	public void setBanner(Banner banner) {
		this.banner = banner;
	}
}
