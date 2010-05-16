package it.jflower.cc.web;

import it.jflower.base.par.Ricerca;
import it.jflower.base.session.SuperSession;
import it.jflower.cc.par.Modulo;
import it.jflower.cc.par.Notizia;
import it.jflower.cc.par.type.TipoInformazione;
import it.jflower.cc.par.type.TipoModulo;
import it.jflower.cc.session.ModuliSession;
import it.jflower.cc.session.TipoInformazioniSession;
import it.jflower.cc.session.TipoModuloSession;
import it.jflower.cc.utils.PageUtils;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

@Named
@SessionScoped
public class ModuliHandler implements Serializable {

	private static final long serialVersionUID = 1L;

	// --------------------------------------------------------

	private static String FACES_REDIRECT = "?faces-redirect=true";

	public static String BACK = "/private/amministrazione.xhtml"
			+ FACES_REDIRECT;
	public static String VIEW = "/private/moduli/scheda-modulo.xhtml"
			+ FACES_REDIRECT;
	public static String LIST = "/private/moduli/lista-moduli.xhtml"
			+ FACES_REDIRECT;
	public static String NEW_OR_EDIT = "/private/moduli/gestione-modulo.xhtml"
			+ FACES_REDIRECT;

	// --------------------------------------------------------

	@Inject
	ModuliSession session;

	@Inject
	PropertiesHandler propertiesHandler;

	@Inject
	TipoModuloSession tipoModuloSession;

	private int idTipo;
	// ------------------------------------------------

	protected Logger logger = Logger.getLogger(getClass());

	// ------------------------------------------------

	private Ricerca<Modulo> ricerca;
	private Modulo element;
	private DataModel<Modulo> model;

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
	public ModuliHandler() {

	}

	// ------------------------------------------------

	/**
	 * Metodo da implementare per assicurare che i criteri di ricerca contengano
	 * sempre tutti i vincoli desiderati (es: identità utente, selezioni
	 * esterne, ecc...)
	 */
	@PostConstruct
	protected void gatherCriteria() {
		ricerca = new Ricerca<Modulo>(Modulo.class);
	}

	/**
	 * Metodo per ottenere l'id di ricerca
	 */
	protected Object getId(Modulo t) {
		return t.getId();
	}

	protected SuperSession<Modulo> getSession() {
		return session;
	}

	public Ricerca<Modulo> getRicerca() {
		return this.ricerca;
	}

	public DataModel<Modulo> getModel() {
		if (model == null)
			refreshModel();
		return model;
	}

	public void setModel(DataModel<Modulo> model) {
		this.model = model;
	}

	protected void refreshModel() {
		// setModel(new LocalDataModel<TipoInformazione>(pageSize, ricerca,
		// getSession()));
		setModel(new ListDataModel<Modulo>(session.getAllList()));
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

	// -----------------------------------------------------

	public Modulo getElement() {
		return element;
	}

	public void setElement(Modulo element) {
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
			this.element = (Modulo) ricerca.getOggetto().getClass()
					.newInstance();
			this.idTipo = 0;
		} catch (Exception e) {
			// logger.error(e.getMessage());
			e.printStackTrace();
		}
		// vista di destinazione
		return editPage();
	}

	public String viewElement() {
		// fetch dei dati
		Modulo t = (Modulo) getModel().getRowData();
		t = getSession().fetch(getId(t));
		// settaggi nel super handler
		this.element = t;
		// vista di destinazione
		return viewPage();
	}

	public String modElement() {
		// fetch dei dati;

		Modulo t = (Modulo) getModel().getRowData();
		t = getSession().fetch(getId(t));
		this.idTipo = t.getTipo().getId().intValue();
		// settaggi nel super handler
		this.element = t;
		// vista di destinazione
		return editPage();
	}

	// -----------------------------------------------------

	public String save() {
		// recupero e preelaborazioni dati in input
		// nelle sottoclassi!! ovverride!
		// salvataggio
		String idTitle = PageUtils.createPageId(this.element.getNome());
		String idFinal = testId(idTitle);
		this.element.setId(idFinal);
		this.element.setData(new Date());
		TipoModulo tipo = tipoModuloSession.find(new Long(idTipo));
		if (tipo != null)
			element.setTipo(tipo);
		element = getSession().persist(element);
		// refresh locale
		refreshModel();
		// altre dipendenze
		propertiesHandler.setTipoInformazioneItems(null);
		propertiesHandler.setTipoInformazioneItems(null);
		// vista di destinazione
		return viewPage();
	}

	public String update() {
		// recupero dati in input
		// nelle sottoclassi!! ovverride!
		// salvataggio
		TipoModulo tipo = tipoModuloSession.find(new Long(idTipo));
		if (tipo != null)
			element.setTipo(tipo);
		element = getSession().persist(element);
		getSession().update(element);
		// refresh locale
		element = getSession().fetch(getId(element));
		refreshModel();
		// altre dipendenze
		propertiesHandler.setTipoInformazioneItems(null);
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
		propertiesHandler.setTipoInformazioneItems(null);
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

	// public List<TipoInformazione> getList() {
	// return session.getAllList();
	// }

	public String viewElement(Object id) {
		// for (TipoInformazione t : session.getAllList() ) {
		// if ( t.getId().equals(id) ) {
		// this.element = t;
		// break;
		// }
		// }
		this.element = session.fetch(id);
		return viewPage();
	}

	public String modElement(Object id) {
		// for (TipoInformazione t : session.getAllList() ) {
		// if ( t.getId().equals(id) ) {
		// this.element = t;
		// break;
		// }
		// }
		this.element = session.fetch(id);
		return editPage();
	}

	public int getIdTipo() {
		return idTipo;
	}

	public void setIdTipo(int idTipo) {
		this.idTipo = idTipo;
	}

	public String testId(String id) {
		String idFinal = id;
		boolean trovato = false;
		int i = 0;
		while (!trovato) {
			System.out.println("id final: " + idFinal);
			Modulo moduloFind = session.find(idFinal);
			System.out.println("trovato_ " + moduloFind);
			if (moduloFind != null) {
				i++;
				idFinal = id + "-" + i;
			} else {
				trovato = true;
				return idFinal;
			}
		}

		return "";
	}
}