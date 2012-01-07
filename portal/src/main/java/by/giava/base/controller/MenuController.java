package by.giava.base.controller;

import it.coopservice.commons2.annotations.BackPage;
import it.coopservice.commons2.annotations.EditPage;
import it.coopservice.commons2.annotations.ListPage;
import it.coopservice.commons2.annotations.OwnRepository;
import it.coopservice.commons2.annotations.ViewPage;
import it.coopservice.commons2.controllers.AbstractLazyController;
import it.coopservice.commons2.utils.JSFUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;
import javax.inject.Named;

import by.giava.base.model.MenuGroup;
import by.giava.base.model.MenuItem;
import by.giava.base.model.OperazioniLog;
import by.giava.base.model.Page;
import by.giava.base.repository.MenuRepository;
import by.giava.base.repository.PageRepository;

@Named
@SessionScoped
public class MenuController extends AbstractLazyController<MenuGroup> {

	private static final long serialVersionUID = 1L;

	// --------------------------------------------------------
	@BackPage
	public static String BACK = "/private/amministrazione.xhtml";
	@ViewPage
	public static String VIEW = "/private/menu/scheda.xhtml";
	@ListPage
	public static String LIST = "/private/menu/lista.xhtml";
	@EditPage
	public static String NEW_OR_EDIT = "/private/menu/gestione1.xhtml";
	public static String NEW_OR_EDIT2 = "/private/menu/gestione2.xhtml";
	public static String NEW_OR_EDIT3 = "/private/menu/gestione3.xhtml";

	// --------------------------------------------------------

	@Inject
	MenuRepository menuRepository;

	@Inject
	@OwnRepository(PageRepository.class)
	PageRepository pageRepository;

	@Inject
	PropertiesHandler propertiesHandler;

	@Inject
	OperazioniLogController operazioniLogController;

	@Inject
	MenuHolder menuHolder;

	private DataModel<MenuItem> menuItemModel;
	private List<Page> pagine;
	private Page[] pagineSelezionate;

	// ------------------------------------------------

	/**
	 * Obbligatoria l'invocazione 'appropriata' di questo super construttore
	 * protetto da parte delle sottoclassi
	 */
	public MenuController() {

	}

	// -----------------------------------------------------

	@Override
	public String reset() {
		menuHolder.reset();
		return super.reset();
	}

	@Override
	public String addElement() {
		try {
			setElement(new MenuGroup());
			getElement().setPercorso("/pagine/");
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		return editPage();
	}

	// -----------------------------------------------------
	@Override
	public String save() {
		super.save();
		operazioniLogController.save(OperazioniLog.NEW, JSFUtils.getUserName(),
				"creazione menu: " + getElement().getNome());
		return viewPage();
	}

	public String update() {
		super.update();
		operazioniLogController.save(OperazioniLog.MODIFY,
				JSFUtils.getUserName(), "modifica menu: "
						+ getElement().getNome());
		return viewPage();
	}

	@Override
	public String delete() {
		super.delete();
		operazioniLogController.save(OperazioniLog.DELETE,
				JSFUtils.getUserName(), "eliminazione menu: "
						+ getElement().getNome());
		return listPage();
	}

	// ----------------------------------------------------
	// ----------------------------------------------------
	// ----------------------------------------------------

	public String step2() {
		this.pagine = pageRepository.getAllList();
		List<Page> pagineSelezionate = new ArrayList<Page>();
		for (Page pagina : pagine) {
			for (MenuItem menuItem : getElement().getLista()) {
				if (!menuItem.isAttivo()) {
					continue;
				}
				if (menuItem.getPagina().getId().equals(pagina.getId())) {
					pagineSelezionate.add(pagina);
				}
			}
		}
		this.pagineSelezionate = pagineSelezionate.toArray(new Page[] {});
		return NEW_OR_EDIT2;
	}

	public Page[] getPagineSelezionate() {
		return pagineSelezionate;
	}

	public void setPagineSelezionate(Page[] pagineSelezionate) {
		this.pagineSelezionate = pagineSelezionate;
	}

	public void aggiornaPagineSelezionate() {
		// evito doppioni, almeno... beccare le deselezioni ancora non ho capito
		// come..
		Map<String, Page> pagineSelezionateUniche = new HashMap<String, Page>();
		if (pagineSelezionate != null) {
			for (Page p : pagineSelezionate) {
				pagineSelezionateUniche.put(p.getId(), p);
			}
		}
		pagineSelezionate = pagineSelezionateUniche.values().toArray(
				new Page[] {});
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
			for (MenuItem menuItem : getElement().getLista()) {
				if (!menuItem.isAttivo()) {
					continue;
				}
				if (menuItem.getPagina().getId()
						.equals(paginaSelezionata.getId())) {
					giaPresente = true;
				}
			}
			if (!giaPresente) {
				pagineDaAggiungere.add(paginaSelezionata);
			}
		}
		List<MenuItem> menuItemsDaRimuovere = new ArrayList<MenuItem>();
		for (MenuItem menuItem : getElement().getLista()) {
			if (!menuItem.isAttivo()) {
				continue;
			}
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
			menuItem.setGruppo(getElement());
			getElement().getLista().add(menuItem);
		}
		for (MenuItem menuItemDaRimuovere : menuItemsDaRimuovere) {
			menuItemDaRimuovere.setAttivo(false);
		}
		int i = 0;
		List<MenuItem> menuItem2dataModel = new ArrayList<MenuItem>();
		for (MenuItem menuItem : getElement().getLista()) {
			if (menuItem.isAttivo()) {
				menuItem.setOrdinamento(++i);
				menuItem2dataModel.add(menuItem);
			}
		}
		this.menuItemModel = new ListDataModel<MenuItem>(menuItem2dataModel);
		return NEW_OR_EDIT3;
	}

	public String rimuoviItem(Long id) {
		int i = 0;
		for (MenuItem menuItem : getElement().getLista()) {
			if (menuItem.getId() != null && menuItem.getId().equals(id)) {
				menuItem.setAttivo(false);
			} else {
				menuItem.setOrdinamento(++i);
			}
		}
		List<MenuItem> menuItem2dataModel = new ArrayList<MenuItem>();
		for (MenuItem menuItem : getElement().getLista()) {
			if (menuItem.isAttivo()) {
				menuItem2dataModel.add(menuItem);
			}
		}
		this.menuItemModel = new ListDataModel<MenuItem>(menuItem2dataModel);
		return NEW_OR_EDIT3;
	}

	public DataModel<MenuItem> getMenuItemModel() {
		return menuItemModel;
	}

	public void setMenuItemModel(DataModel<MenuItem> menuItemModel) {
		this.menuItemModel = menuItemModel;
	}

}