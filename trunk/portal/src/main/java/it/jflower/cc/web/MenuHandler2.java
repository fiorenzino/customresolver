package it.jflower.cc.web;

import it.jflower.base.par.Ricerca;
import it.jflower.base.session.SuperSession;
import it.jflower.cc.par.MenuGroup;
import it.jflower.cc.par.MenuItem;
import it.jflower.cc.par.Page;
import it.jflower.cc.session.MenuSession;
import it.jflower.cc.session.PageSession;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DualListModel;
import org.primefaces.model.TreeNode;

@Named
@SessionScoped
public class MenuHandler2 implements Serializable {

	private static final long serialVersionUID = 1L;

	// --------------------------------------------------------

	private static String FACES_REDIRECT = "?faces-redirect=true";

	public static String BACK = "/private/amministrazione.xhtml"
			+ FACES_REDIRECT;
	public static String VIEW = "/private/menu/percorsi.xhtml" + FACES_REDIRECT;
	public static String LIST = "/private/menu/percorsi.xhtml" + FACES_REDIRECT;
	public static String NEW_OR_EDIT = "/private/menu/percorsi.xhtml"
			+ FACES_REDIRECT;

	// --------------------------------------------------------

	@Inject
	MenuSession session;

	@Inject
	PageSession pageSession;

	@Inject
	PropertiesHandler propertiesHandler;

	// ------------------------------------------------

	protected Logger logger = Logger.getLogger(getClass());

	// ------------------------------------------------

	private Ricerca<MenuGroup> ricerca;
	private MenuGroup element;
	private DataModel<MenuGroup> model;

	private int rowCount;
	private int pageSize = 10;
	private int rowsPerPage = 10;
	private int scrollerPage = 1;

	private String backPage = BACK;

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
	public MenuHandler2() {

	}

	// ------------------------------------------------

	/**
	 * Metodo da implementare per assicurare che i criteri di ricerca contengano
	 * sempre tutti i vincoli desiderati (es: identità utente, selezioni
	 * esterne, ecc...)
	 */
	@PostConstruct
	protected void gatherCriteria() {
		ricerca = new Ricerca<MenuGroup>(MenuGroup.class);
	}

	/**
	 * Metodo per ottenere l'id di ricerca
	 */
	protected Object getId(MenuGroup t) {
		return t.getId();
	}

	protected SuperSession<MenuGroup> getSession() {
		return session;
	}

	public Ricerca<MenuGroup> getRicerca() {
		return this.ricerca;
	}

	public DataModel<MenuGroup> getModel() {
		if (model == null)
			refreshModel();
		return model;
	}

	public void setModel(DataModel<MenuGroup> model) {
		this.model = model;
	}

	protected void refreshModel() {
		// setModel(new LocalDataModel<Template>(pageSize, ricerca,
		// getSession()));
		setModel(new ListDataModel<MenuGroup>(session.getAllList()));
		this.pages = pageSession.getAllList();
	}

	/**
	 * Metodo di navigazione per resettare lo stato interno e tornare alla
	 * pagina dell'elenco generale
	 * 
	 * @return
	 */
	public String reset() {
		this.element = null;
		this.model = null;
		return backPage();
	}

	public boolean getClear() {
		reset();
		return true;
	}

	// -----------------------------------------------------

	public MenuGroup getElement() {
		if (element == null)
			element = new MenuGroup();
		return element;
	}

	public void setElement(MenuGroup element) {
		this.element = element;
	}

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getRowsPerPage() {
		return rowsPerPage;
	}

	public void setRowsPerPage(int rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}

	public int getScrollerPage() {
		return scrollerPage;
	}

	public void setScrollerPage(int scrollerPage) {
		this.scrollerPage = scrollerPage;
	}

	// -----------------------------------------------------

	public void backPage(String backPage) {
		this.backPage = backPage;
	}

	public String backPage() {
		return this.backPage;
	}

	public String viewPage() {
		return VIEW;
	}

	public String listPage() {
		return LIST;
	}

	public String editPage() {
		return NEW_OR_EDIT;
	}

	// -----------------------------------------------------

	/**
	 * action che carica il modello dei dati, se inizialmente vuoto, tenendo
	 * conto dei vari criteri esterni
	 */
	public String loadModel() {
		gatherCriteria();
		refreshModel();
		return listPage();
	}

	// -----------------------------------------------------

	public String addElement() {
		// impostazioni locali
		MenuGroup m = new MenuGroup();
		m.setLista(new ArrayList<MenuItem>());
		// n.d.
		// settaggi nel super handler
		this.element = m;
		// vista di destinazione
		return editPage();
	}

	public String viewElement() {
		// fetch dei dati
		this.element = (MenuGroup) getModel().getRowData();
		this.element = getSession().fetch(getId(this.element));
		// vista di destinazione
		return viewPage();
	}

	public String modElement() {
		// fetch dei dati;
		this.element = (MenuGroup) getModel().getRowData();
		this.element = getSession().fetch(getId(this.element));
		// vista di destinazione
		return editPage();
	}

	// -----------------------------------------------------

	public String save() {
		// recupero e preelaborazioni dati in input
		// nelle sottoclassi!! ovverride!
		// salvataggio
		element = getSession().persist(element);
		// refresh locale
		refreshModel();
		// altre dipendenze
		// n.d.
		// vista di destinazione
		return viewPage();
	}

	public String update() {
		// recupero dati in input
		// nelle sottoclassi!! ovverride!
		// salvataggio
		getSession().update(element);
		// refresh locale
		element = getSession().fetch(getId(element));
		refreshModel();
		// altre dipendenze
		// n.d.
		// vista di destinzione
		return viewPage();
	}

	public String delete() {
		// operazione su db
		getSession().delete(getId(element));
		// refresh locale
		refreshModel();
		element = null;
		// altre dipendenze
		// n.d.
		// visat di destinazione
		return listPage();
	}

	// -----------------------------------------------------

	public String modCurrent() {
		// fetch dei dati
		element = getSession().fetch(getId(element));
		// vista di arrivo
		return editPage();
	}

	public String viewCurrent() {
		// fetch dei dati
		element = getSession().fetch(getId(element));
		// vista di arrivo
		return viewPage();
	}

	// ----------------------------------------------------

	// public List<Template> getList() {
	// return session.getAllList();
	// }

	public String viewElement(Object id) {
		// for (Template t : session.getAllList() ) {
		// if ( t.getId().equals(id) ) {
		// this.element = t;
		// break;
		// }
		// }
		this.element = session.fetch(id);
		return viewPage();
	}

	public String modElement(Object id) {
		// for (Template t : session.getAllList() ) {
		// if ( t.getId().equals(id) ) {
		// this.element = t;
		// break;
		// }
		// }
		this.element = session.fetch(id);
		return editPage();
	}

	// -----------------------------------------------------------------

	@SuppressWarnings("unchecked")
	public TreeNode getModelTree() {

		root = new TreeNode("root", null);

		for (MenuGroup mg : (List<MenuGroup>) getModel().getWrappedData()) {
			TreeNode mgn = new TreeNode("menuGroup", mg, root);
			if (mg.getLista() != null) {
				for (MenuItem mi : mg.getLista()) {
					if (mi.isAttivo()) {
						@SuppressWarnings("unused")
						TreeNode min = new TreeNode("menuItem", mi, mgn);
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
		session.delete(((MenuGroup) this.selectedGroup.getData()).getId());
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
		session.update(menuGroup);

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
			session.updateItem(mi);
		}
		setItemList(null);
		return null;
	}

	public String updateItem() {
		session.updateItem((MenuItem) this.selectedItem.getData());
		// setSelectedItem(null);
		return null;
	}

}