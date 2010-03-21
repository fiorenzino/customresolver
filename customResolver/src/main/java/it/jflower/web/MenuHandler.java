package it.jflower.web;

import it.jflower.par.Menu;
import it.jflower.par.MenuGroup;

import java.io.Serializable;

import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;

@ConversationScoped
@Named
public class MenuHandler implements Serializable {
	
	private MenuGroup menuGroup;
	private Menu menu;

	public MenuGroup getMenuGroup() {
		return menuGroup;
	}

	public void setMenuGroup(MenuGroup menuGroup) {
		this.menuGroup = menuGroup;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}
}
