package by.giava.base.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;

import by.giava.base.model.MenuGroup;
import by.giava.base.model.MenuItem;
import by.giava.base.repository.MenuSession;

@Named
@ApplicationScoped
public class MenuHolder implements Serializable {

	private static final long serialVersionUID = 1L;

	// --------------------------------------------------------

	@Inject
	MenuSession session;

	// ------------------------------------------------

	protected Logger logger = Logger.getLogger(getClass());

	// ------------------------------------------------

	public MenuHolder() {
		// TODO Auto-generated constructor stub
	}

	private List<MenuGroup> lista = null;
	private Map<String, MenuGroup> mappa = null;

	public List<MenuGroup> getGruppi() {
		if (lista == null)
			init();
		return lista;
	}

	public MenuGroup get(String nome) {
		if (mappa == null)
			init();
		MenuGroup g = mappa.get(nome);
		if (g == null) {
			g = new MenuGroup();
			g.setNome(nome);
			g.setDescrizione(nome);
			g.setLista(new ArrayList<MenuItem>());
			g.setOrdinamento(1);
			g.setPercorso("");
		}
		return g;
	}

	public void init() {
		if (lista == null) {
			lista = session.getAllList();
			Collections.sort(lista, new Comparator<MenuGroup>() {

				public int compare(MenuGroup mg1, MenuGroup mg2) {
					return mg1.getOrdinamento() != null ? (mg2.getOrdinamento() != null ? mg1
							.getOrdinamento().compareTo(mg2.getOrdinamento())
							: 1)
							: (mg2.getOrdinamento() != null ? -1 : mg1
									.getNome().compareTo(mg2.getNome()));
				}
			});
			mappa = new HashMap<String, MenuGroup>();
			for (MenuGroup mg : lista) {
				mappa.put(mg.getNome(), mg);
			}
		}
	}

	public void reset() {
		this.lista = null;
		this.mappa = null;
	}

}