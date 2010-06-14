package it.jflower.cc.web;

import it.jflower.base.par.Ricerca;
import it.jflower.base.session.SuperSession;
import it.jflower.base.web.model.LocalLazyDataModel;
import it.jflower.cc.par.MenuGroup;
import it.jflower.cc.par.MenuItem;
import it.jflower.cc.par.Page;
import it.jflower.cc.session.MenuSession;
import it.jflower.cc.session.PageSession;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.seamframework.tx.Transactional;

@Named
@SessionScoped
public class MenuHandler implements Serializable {

	private static final long serialVersionUID = 1L;

	// --------------------------------------------------------

	private static String FACES_REDIRECT = "?faces-redirect=true";

	public static String BACK = "/private/amministrazione.xhtml"
			+ FACES_REDIRECT;
	public static String VIEW = "/private/menu/scheda-menu.xhtml"
			+ FACES_REDIRECT;
	public static String LIST = "/private/menu/lista-menu.xhtml"
			+ FACES_REDIRECT;
	public static String NEW_OR_EDIT = "/private/menu/gestione-menu1.xhtml"
			+ FACES_REDIRECT;
	public static String NEW_OR_EDIT2 = "/private/menu/gestione-menu2.xhtml"
			+ FACES_REDIRECT;
	public static String NEW_OR_EDIT3 = "/private/menu/gestione-menu3.xhtml"
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

	// ------------------------------------------------

	/**
	 * Obbligatoria l'invocazione 'appropriata' di questo super construttore
	 * protetto da parte delle sottoclassi
	 */
	public MenuHandler() {

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

	@SuppressWarnings("unchecked")
	protected void refreshModel() {
		setModel(new LocalLazyDataModel<MenuGroup>(this.ricerca, session));
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
		return listPage();
	}

	public boolean getClear() {
		reset();
		return true;
	}

	public String cerca() {
		refreshModel();
		return null;
	}

	// -----------------------------------------------------

	public MenuGroup getElement() {
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
		// n.d.
		// settaggi nel super handler
		try {
			this.element = new MenuGroup();
			this.element.setLista(new ArrayList<MenuItem>());
		} catch (Exception e) {
			// logger.error(e.getMessage());
			e.printStackTrace();
		}
		// vista di destinazione
		return editPage();
	}

	public String viewElement() {
		// fetch dei dati
		MenuGroup m = (MenuGroup) getModel().getRowData();
		m = getSession().fetch(getId(m));
		// settaggi nel super handler
		this.element = m;
		// vista di destinazione
		return viewPage();
	}

	public String modElement() {
		// fetch dei dati;
		MenuGroup t = (MenuGroup) getModel().getRowData();
		t = getSession().fetch(getId(t));
		// settaggi nel super handler
		this.element = t;
		// vista di destinazione
		return editPage();
	}

	// -----------------------------------------------------

	@Transactional
	public String save() {
		// recupero e preelaborazioni dati in input
		// nelle sottoclassi!! ovverride!
		// salvataggio
		element = getSession().persist(element);
		// refresh locale
		refreshModel();
		// altre dipendenze
		// propertiesHandler.setMenuGroupItems(null);
		// vista di destinazione
		return viewPage();
	}

	@Transactional
	public String update() {
		// recupero dati in input
		// nelle sottoclassi!! ovverride!
		// salvataggio
		getSession().update(element);
		// refresh locale
		element = getSession().fetch(getId(element));
		refreshModel();
		// altre dipendenze
		// propertiesHandler.setMenuGroupItems(null);
		// vista di destinzione
		return viewPage();
	}

	@Transactional
	public String delete() {
		// operazione su db
		getSession().delete(getId(element));
		// refresh locale
		refreshModel();
		element = null;
		// altre dipendenze
		// propertiesHandler.setMenuGroupItems(null);
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

	public String viewElement(Object id) {
		this.element = session.fetch(id);
		return viewPage();
	}

	public String modElement(Object id) {
		this.element = session.fetch(id);
		return editPage();
	}

	// ----------------------------------------------------
	// ----------------------------------------------------
	// ----------------------------------------------------

	public String step2() {
		this.pagine = pageSession.getAllList();
		List<Page> pagineSelezionate = new ArrayList<Page>();
		for (Page pagina : pagine) {
			for (MenuItem menuItem : this.element.getLista()) {
				if (menuItem.getPagina().getId().equals(pagina.getId())) {
					pagineSelezionate.add(pagina);
				}
			}
		}
		this.pagineSelezionate = pagineSelezionate.toArray(new Page[] {});
		return NEW_OR_EDIT2;
	}

	private List<Page> pagine;

	private Page[] pagineSelezionate;

	public Page[] getPagineSelezionate() {
		return pagineSelezionate;
	}

	public void setPagineSelezionate(Page[] pagineSelezionate) {
		this.pagineSelezionate = pagineSelezionate;
	}

	public List<Page> getPagine() {
		return pagine;
	}

	public void setPagine(List<Page> pagine) {
		this.pagine = pagine;
	}

	public String step3() {
		List<Page> pagineDaAggiungere = new ArrayList<Page>();
		for (Page paginaSelezionata : this.pagineSelezionate) {
			boolean giaPresente = false;
			for (MenuItem menuItem : this.element.getLista()) {
				if (menuItem.getPagina().getId().equals(
						paginaSelezionata.getId())) {
					giaPresente = false;
				}
			}
			if (!giaPresente) {
				pagineDaAggiungere.add(paginaSelezionata);
			}
		}
		List<MenuItem> menuItemsDaRimuovere = new ArrayList<MenuItem>();
		for (MenuItem menuItem : this.element.getLista()) {
			boolean mantenuto = false;
			for (Page paginaSelezionata : this.pagineSelezionate) {
				if (paginaSelezionata.getId().equals(
						menuItem.getPagina().getId())) {
					mantenuto = true;
				}
			}
			if (!mantenuto) {
				menuItemsDaRimuovere.add(menuItem);
			}
		}
		for (Page paginaDaAggiungere : pagineDaAggiungere) {
			MenuItem menuItem = new MenuItem();
			menuItem.setNome(paginaDaAggiungere.getTitle());
			String percorso = getElement().getPercorso()
					+ (getElement().getPercorso().endsWith("/") ? "" : "/")
					+ paginaDaAggiungere.getId();
			menuItem.setPercorso(percorso);
			menuItem.setDescrizione(paginaDaAggiungere.getDescription());
			menuItem.setPagina(paginaDaAggiungere);
			menuItem.setGruppo(this.element);
			this.element.getLista().add(menuItem);
		}
		for (MenuItem menuItemDaRimuovere : menuItemsDaRimuovere) {
			menuItemDaRimuovere.setAttivo(false);
		}
		List<MenuItem> menuItem2dataModel = new ArrayList<MenuItem>();
		for (MenuItem menuItem : this.element.getLista()) {
			if (menuItem.isAttivo()) {
				menuItem2dataModel.add(menuItem);
			}
		}
		this.menuItemModel = new ListDataModel<MenuItem>(menuItem2dataModel);
		return NEW_OR_EDIT3;
	}

	private DataModel<MenuItem> menuItemModel;

	public DataModel<MenuItem> getMenuItemModel() {
		return menuItemModel;
	}

	public void setMenuItemModel(DataModel<MenuItem> menuItemModel) {
		this.menuItemModel = menuItemModel;
	}

}