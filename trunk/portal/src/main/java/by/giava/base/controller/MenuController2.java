package by.giava.base.controller;

import it.coopservice.commons2.annotations.BackPage;
import it.coopservice.commons2.annotations.EditPage;
import it.coopservice.commons2.annotations.ListPage;
import it.coopservice.commons2.annotations.OwnRepository;
import it.coopservice.commons2.annotations.ViewPage;
import it.coopservice.commons2.controllers.AbstractLazyController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.DualListModel;
import org.primefaces.model.TreeNode;

import by.giava.base.model.MenuGroup;
import by.giava.base.model.MenuItem;
import by.giava.base.model.Page;
import by.giava.base.repository.MenuRepository;
import by.giava.base.repository.PageRepository;

@Named
@SessionScoped
public class MenuController2 extends AbstractLazyController<MenuGroup> {

	private static final long serialVersionUID = 1L;

	// --------------------------------------------------------
	@BackPage
	public static String BACK = "/private/amministrazione.xhtml";
	@ViewPage
	public static String VIEW = "/private/menu/percorsi.xhtml";
	@ListPage
	public static String LIST = "/private/menu/percorsi.xhtml";
	@EditPage
	public static String NEW_OR_EDIT = "/private/menu/percorsi.xhtml";

	// --------------------------------------------------------

	@Inject
	@OwnRepository(MenuRepository.class)
	MenuRepository menuRepository;

	@Inject
	PageRepository pageRepository;

	@Inject
	PropertiesHandler propertiesHandler;

	private TreeNode root;
	private TreeNode selectedGroup;
	private DualListModel<MenuItem> dualListModel;
	private List<Page> pages;

	private TreeNode selectedItem;

	private List<MenuItem> itemList;

	// ------------------------------------------------

	/**
	 * Obbligatoria l'invocazione 'appropriata' di questo super construttore
	 * protetto da parte delle sottoclassi
	 */
	public MenuController2() {

	}

	// ------------------------------------------------

	/**
	 * Metodo da implementare per assicurare che i criteri di ricerca contengano
	 * sempre tutti i vincoli desiderati (es: identità utente, selezioni
	 * esterne, ecc...)
	 */
	@Override
	public void refreshModel() {
		super.refreshModel();
		this.pages = pageRepository.getAllList();
	}

	// -----------------------------------------------------------------

	@SuppressWarnings("unchecked")
	public TreeNode getModelTree() {

		root = new DefaultTreeNode("Root", null);

		for (MenuGroup mg : (List<MenuGroup>) getModel().getWrappedData()) {
			TreeNode mgn = new DefaultTreeNode("menuGroup", mg, root);
			if (mg.getLista() != null) {
				for (MenuItem mi : mg.getLista()) {
					if (mi.isAttivo()) {
						@SuppressWarnings("unused")
						TreeNode min = new DefaultTreeNode("menuItem", mi, mgn);
					}
				}
			}
		}

		return root;
	}

	public TreeNode getSelectedGroup() {
		return selectedGroup;
	}

	public void setSelectedGroup(TreeNode selectedGroup) {
		this.selectedGroup = selectedGroup;
		this.selectedItem = null;
		this.itemList = null;
	}

	public TreeNode getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(TreeNode selectedItem) {
		this.selectedItem = selectedItem;
		this.selectedGroup = null;
		this.itemList = null;
	}

	public List<MenuItem> getItemList() {
		return itemList;
	}

	public void setItemList(List<MenuItem> itemList) {
		this.itemList = itemList;
		this.selectedGroup = null;
		this.selectedItem = null;
	}

	public void onNodeSelect(NodeSelectEvent event) {
		TreeNode selected = event.getTreeNode();
		if ("menuGroup".equals(selected.getType())) {
			setSelectedGroup(selected);
			MenuGroup menuGroup = ((MenuGroup) selected.getData());
			Map<String, MenuItem> groupItems = new HashMap<String, MenuItem>();
			if (menuGroup.getLista() != null) {
				for (MenuItem item : menuGroup.getLista()) {
					if (item.isAttivo()) {
						groupItems.put(item.getPagina().getId(), item);
					}
				}
			}
			List<MenuItem> source = new ArrayList<MenuItem>();
			List<MenuItem> target = new ArrayList<MenuItem>();
			for (Page page : this.pages) {
				if (groupItems.get(page.getId()) != null) {
					target.add(groupItems.get(page.getId()));
				} else {
					source.add(new MenuItem(page, menuGroup));
				}
			}
			dualListModel = new DualListModel<MenuItem>(source, target);
			logger.debug("Selected:" + selectedGroup.getData());
		} else if ("menuItem".equals(selected.getType())) {
			setSelectedItem(selected);
			@SuppressWarnings("unused")
			MenuItem menuItem = ((MenuItem) selected.getData());
			logger.debug("Selected:" + selectedItem.getData());
		}
	}

	public DualListModel<MenuItem> getDualListModel() {
		if (dualListModel == null)
			dualListModel = new DualListModel<MenuItem>();
		return dualListModel;
	}

	public void setDualListModel(DualListModel<MenuItem> dualListModel) {
		this.dualListModel = dualListModel;
	}

	public String deleteGroup() {
		menuRepository.delete(((MenuGroup) this.selectedGroup.getData())
				.getId());
		this.selectedGroup = null;
		refreshModel();
		return null;
	}

	public String confirmPages() {
		// confermo quelli già presenti
		MenuGroup menuGroup = (MenuGroup) selectedGroup.getData();
		if (menuGroup.getLista() == null) {
			menuGroup.setLista(new ArrayList<MenuItem>());
		}
		for (MenuItem giaPresente : menuGroup.getLista()) {
			boolean mantenuto = false;
			for (MenuItem scelto : dualListModel.getTarget()) {
				if (giaPresente.getId().equals(scelto.getId()))
					mantenuto = true;
				break;
			}
			giaPresente.setAttivo(mantenuto);
		}
		// aggiungo i nuovi
		List<MenuItem> nuovi = new ArrayList<MenuItem>();
		for (MenuItem scelto : dualListModel.getTarget()) {
			boolean aggiunto = true;
			for (MenuItem giaPresente : menuGroup.getLista()) {
				if (giaPresente.getId().equals(scelto.getId()))
					aggiunto = true;
				break;
			}
			if (aggiunto)
				nuovi.add(scelto);
		}
		List<MenuItem> nuovaLista = new ArrayList<MenuItem>();
		for (MenuItem mantenutoOrimosso : menuGroup.getLista()) {
			nuovaLista.add(mantenutoOrimosso);
		}
		for (MenuItem nuovo : nuovi) {
			nuovo.setGruppo(menuGroup);
			nuovaLista.add(nuovo);
		}
		menuGroup.setLista(nuovaLista);
		menuRepository.update(menuGroup);

		List<MenuItem> listaAttivi = new ArrayList<MenuItem>();
		for (MenuItem i : nuovaLista) {
			if (i.isAttivo())
				listaAttivi.add(i);
		}

		setItemList(listaAttivi);
		refreshModel();

		return null;
	}

	@SuppressWarnings("unchecked")
	public boolean isGruppi() {
		return ((List<MenuGroup>) getModel().getWrappedData()).size() > 0 ? true
				: false;
	}

	public String confirmNames() {
		for (MenuItem mi : itemList) {
			menuRepository.updateItem(mi);
		}
		setItemList(null);
		return null;
	}

	public String updateItem() {
		menuRepository.updateItem((MenuItem) this.selectedItem.getData());
		// setSelectedItem(null);
		return null;
	}

}