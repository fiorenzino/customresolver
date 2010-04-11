package it.flowercms.web;

import it.flowercms.par.Template;
import it.flowercms.session.TemplateSession;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

@SessionScoped
@Named
public class TemplateHandler implements Serializable {

	@Inject
	TemplateSession templateSession;

	private Template template;

	private boolean editMode;

	private List<Template> allTemplates;

	private SelectItem[] templateItems = new SelectItem[] {};

	public TemplateHandler() {

	}

	public String add() {
		this.template = new Template();
		setEditMode(false);
		return "/private/templates/management?redirect=true";
	}

	public String create() {
		try {
			templateSession.create(this.template);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/private/templates/index?redirect=true";
	}

	public String management(Long id) {
		try {
			setEditMode(true);
			this.template = templateSession.find(id);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "/private/templates/management?redirect=true";
	}

	public String update() {
		try {
			templateSession.update(this.template);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/private/templates/index?redirect=true";
	}

	public Template getTemplate() {
		return template;
	}

	public void setTemplate(Template template) {
		this.template = template;
	}

	public List<Template> getAllTemplates() {
		if (this.allTemplates == null)
			this.allTemplates = templateSession.getAll();
		return allTemplates;
	}

	public void setAllTemplates(List<Template> allTemplates) {
		this.allTemplates = allTemplates;
	}

	public SelectItem[] getTemplateItems() {
		if (templateItems.length == 0) {
			templateItems = new SelectItem[1];
			templateItems[0] = new SelectItem(null, "nessun template");
			List<Template> templates = templateSession.getAll();
			if (templates != null && templates.size() > 0) {
				templateItems = new SelectItem[templates.size()];
				int i = 0;
				for (Template c : templates) {
					templateItems[i] = new SelectItem(c.getId(), c.getNome());
					i++;
				}
			}
		}
		return templateItems;
	}

	public boolean isEditMode() {
		return editMode;
	}

	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}
}
