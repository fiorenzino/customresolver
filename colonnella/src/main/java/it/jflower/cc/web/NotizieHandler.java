package it.jflower.cc.web;

import it.jflower.base.par.Ricerca;
import it.jflower.base.utils.JSFUtils;
import it.jflower.base.web.model.LocalDataModel;
import it.jflower.base.web.model.LocalLazyDataModel;
import it.jflower.cc.par.Notizia;
import it.jflower.cc.par.type.TipoInformazione;
import it.jflower.cc.session.NotizieSession;
import it.jflower.cc.session.TipoInformazioniSession;
import it.jflower.cc.utils.PageUtils;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@SessionScoped
public class NotizieHandler implements Serializable {

	private static final long serialVersionUID = 1L;

	// --------------------------------------------------------

	private static String FACES_REDIRECT = "?faces-redirect=true";

	public static String BACK = "/private/amministrazione.xhtml"
			+ FACES_REDIRECT;
	public static String VIEW = "/private/notizie/scheda-notizia.xhtml"
			+ FACES_REDIRECT;
	public static String LIST = "/private/notizie/lista-notizie.xhtml"
			+ FACES_REDIRECT;
	public static String NEW_OR_EDIT = "/private/notizie/gestione-notizia.xhtml"
			+ FACES_REDIRECT;

	// --------------------------------------------------------

	@Inject
	NotizieSession notizieSession;

	@Inject
	TipoInformazioniSession tipoInformazioniSession;

	private int idTipo;

	private Notizia element;
	private Ricerca<Notizia> ricerca;
	private boolean editMode;
	private DataModel<Notizia> model;

	private int rowCount;
	private int pageSize = 10;
	private int rowsPerPage = 10;
	private int scrollerPage = 1;

	private Notizia evidenza;

	private String backPage = BACK;

	@Inject
	OperazioniLogHandler operazioniLogHandler;

	// --------------------------------------------------------

	public NotizieHandler() {
	}

	// --------------------------------------------------------

	/**
	 * Metodo da implementare per assicurare che i criteri di ricerca contengano
	 * sempre tutti i vincoli desiderati (es: identità utente, selezioni
	 * esterne, ecc...)
	 */
	@PostConstruct
	protected void gatherCriteria() {
		ricerca = new Ricerca<Notizia>(Notizia.class);
		ricerca.getOggetto().setTipo(new TipoInformazione());
	}

	// --------------------------------------------------------

	public Ricerca<Notizia> getRicerca() {
		return this.ricerca;
	}

	public DataModel<Notizia> getModel() {
		if (model == null)
			refreshModel();
		return model;
	}

	public void setModel(DataModel<Notizia> model) {
		this.model = model;
	}

	@SuppressWarnings("unchecked")
	protected void refreshModel() {
		setModel(new LocalLazyDataModel<Notizia>(this.ricerca, this.notizieSession));
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

	public Notizia getElement() {
		return element;
	}

	public void setElement(Notizia element) {
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

	public String cerca() {
		refreshModel();
		return null;
	}

	// --------------------------------------------------------

	public String addNotizia() {
		this.editMode = false;
		this.element = new Notizia();
		this.idTipo = 0;
		return NEW_OR_EDIT;
	}

	public String saveNotizia() {
		String idTitle = PageUtils.createPageId(this.element.getTitolo());
		String idFinal = testId(idTitle);
		this.element.setId(idFinal);
		TipoInformazione tipo = tipoInformazioniSession.find(new Long(idTipo));
		if (tipo != null)
			this.element.setTipo(tipo);
		notizieSession.persist(this.element);
		this.model = null;
		operazioniLogHandler.save("NEW", JSFUtils.getUserName(),
				"creazione notizia: " + this.element.getTitolo());
		return VIEW;
	}

	public String deleteNotizia() {
		notizieSession.delete(this.element.getId());
		this.model = null;
		operazioniLogHandler.save("DELETE", JSFUtils.getUserName(),
				"eliminazione menu: " + this.element.getTitolo());
		return LIST;
	}

	public String modNotizia(String id) {
		this.editMode = true;
		this.element = notizieSession.find(id);
		if (this.element.getTipo() != null)
			this.idTipo = this.element.getTipo().getId().intValue();
		return NEW_OR_EDIT;
	}

	public String updateNotizia() {
		TipoInformazione tipo = tipoInformazioniSession.find(new Long(idTipo));
		if (tipo != null)
			this.element.setTipo(tipo);
		this.element = notizieSession.update(this.element);
		this.model = null;
		operazioniLogHandler.save("MODIFY", JSFUtils.getUserName(),
				"modifica notizia: " + this.element.getTitolo());
		return VIEW;
	}

	public String detailNotizia(String id) {
		this.element = notizieSession.find(id);
		return VIEW;
	}

	public Notizia getNotizia() {
		return element;
	}

	public void setNotizia(Notizia notizia) {
		this.element = notizia;
	}

	public boolean isEditMode() {
		return editMode;
	}

	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}

	public String testId(String id) {
		String idFinal = id;
		boolean trovato = false;
		int i = 0;
		while (!trovato) {
			System.out.println("id final: " + idFinal);
			Notizia notiziaFind = notizieSession.find(idFinal);
			System.out.println("trovato_ " + notiziaFind);
			if (notiziaFind != null) {
				i++;
				idFinal = id + "-" + i;
			} else {
				trovato = true;
				return idFinal;

			}

		}

		return "";
	}

	// -----------------------------------------------------------

	private LocalDataModel<Notizia> latestNewsModel;
	private String latestTipo;
	private Integer latestPageSize;

	public LocalDataModel<Notizia> ultimeNotizie(String tipo, int pageSize) {
		if (latestNewsModel == null || this.latestTipo == null
				|| this.latestPageSize == null || !this.latestTipo.equals(tipo)
				|| this.latestPageSize != pageSize) {
			Ricerca<Notizia> ricerca = new Ricerca<Notizia>(Notizia.class);
			ricerca.getOggetto().setTipo(new TipoInformazione());
			ricerca.getOggetto().getTipo().setNome(tipo);
			latestNewsModel = new LocalDataModel<Notizia>(pageSize, ricerca,
					notizieSession);
		}
		return latestNewsModel;
	}

	// -----------------------------------------------------------

	public int getIdTipo() {
		return idTipo;
	}

	public void setIdTipo(int idTipo) {
		this.idTipo = idTipo;
	}

	public Notizia getEvidenza() {
		return evidenza;
	}

	public void setEvidenza(Notizia evidenza) {
		this.evidenza = evidenza;
	}

}
