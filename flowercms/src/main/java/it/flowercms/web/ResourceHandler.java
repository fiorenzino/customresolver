package it.flowercms.web;

import it.flowercms.par.Resource;
import it.flowercms.par.base.Ricerca;
import it.flowercms.session.ResourceSession;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.UploadedFile;
import org.seamframework.tx.Transactional;

@Named
@SessionScoped
public class ResourceHandler implements Serializable {

	private static final long serialVersionUID = 1L;

	// --------------------------------------------------------

	private static String FACES_REDIRECT = "?faces-redirect=true";
	
	public static String BACK = "/private/amministrazione.xhtml"+FACES_REDIRECT;
	public static String VIEW = "/private/risorse/scheda-risorsa.xhtml"+FACES_REDIRECT;
	public static String LIST = "/private/risorse/lista-risorse.xhtml"+FACES_REDIRECT;
	public static String NEW_OR_EDIT = "/private/risorse/gestione-risorsa.xhtml"+FACES_REDIRECT;

	// ------------------------------------------------

	protected Logger logger = Logger.getLogger(getClass());

	// --------------------------------------------------------

	@Inject
	ResourceSession session;

	@Inject
	PropertiesHandler propertiesHandler;

	// --------------------------------------------------------

	private Ricerca<Resource> ricerca;
	private Resource element;
	private DataModel<Resource> model;

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
	public ResourceHandler() {
	}

	// ------------------------------------------------

	/**
	 * Metodo da implementare per assicurare che i criteri di ricerca contengano
	 * sempre tutti i vincoli desiderati (es: identità utente, selezioni
	 * esterne, ecc...)
	 */
	@PostConstruct
	protected void gatherCriteria() {
		ricerca = new Ricerca<Resource>(Resource.class);
		ricerca.getOggetto().setTipo( propertiesHandler.getRisorseItems()[0].getValue().toString() );
	}

	public Ricerca<Resource> getRicerca() {
		return this.ricerca;
	}

	public DataModel<Resource> getModel() {
		if (model == null)
			refreshModel();
		return model;
	}

	public void setModel(DataModel<Resource> model) {
		this.model = model;
	}

	@SuppressWarnings("unchecked")
	protected void refreshModel() {
		boolean lazy = false;
//		setModel(new LocalDataModel<Page>(pageSize, ricerca, getSession()));
		if ( ! lazy )
			setModel( new ListDataModel<Resource>( session.getList(ricerca, 0, pageSize) ));
		else
		setModel( 
				new LazyDataModel<Resource>( session.getListSize(ricerca) ) {
					private static final long serialVersionUID = 1L;
					@Override
					public List<Resource> fetchLazyData(int first, int pageSize) {
						return session.getList(ricerca, first, pageSize);
					}
				}
		);
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

	// -----------------------------------------------------

	public Resource getElement() {
		return element;
	}

	public void setElement(Resource element) {
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
		return this.backPage == null ? BACK : this.backPage;
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

	public boolean getClear() {
		reset();
		return true;
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
		Resource r = new Resource();
		// settaggi 
		this.element = r;
		// vista di destinazione
		return editPage();
	}

	// -----------------------------------------------------

	@Transactional
	public String save() {
		// recupero e preelaborazioni dati in input
		if ( element.getTipo().equals("img") ) {
			((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getParameter("file");
		}
		// TODO
		// salvataggio
		element = session.persist(element);
		// refresh locale
		refreshModel();
		// altre dipendenze
		//
		// vista di destinazione
		return viewPage();
	}

	@Transactional
	public String update() {
		// recupero dati in input
		// TODO
		// salvataggio
		session.update(element);
		// refresh locale
		refreshModel();
		// altre dipendenze
		//
		// vista di destinzione
		return viewPage();
	}

	@Transactional
	public String delete() {
		// operazione su db
		session.delete(element);
		// refresh locale
		refreshModel();
		element = null;
		// altre dipendenze
		//
		// visat di destinazione
		return listPage();
	}

	// -----------------------------------------------------

	public String modCurrent() {
		// fetch dei dati
		//
		// vista di arrivo
		return editPage();
	}

	public String viewCurrent() {
		// fetch dei dati
		//
		// vista di arrivo
		return viewPage();
	}
	
	// -----------------------------------------------------

	
//	public List<Page> getList() {
//		return session.getAllList();
//	}

	public String viewElement(String tipo, String id) {
//		for (Page t : session.getAllList() ) {
//			if ( t.getId().equals(id) ) {
//				this.element = t;
//				break;
//			}
//		}
		this.element = session.find(tipo,id);
		return viewPage();
	}

	public String modElement(String tipo, String id) {
//		for (Page t : session.getAllList() ) {
//			if ( t.getId().equals(id) ) {
//				this.element = t;
//				break;
//			}
//		}
		this.element = session.find(tipo,id);
		return editPage();
	}

	public void handleFileUpload(FileUploadEvent event) {
		UploadedFile file = event.getFile();
		String filename = file.getFileName();
		if ( filename.contains("\\") )
			filename = filename.substring(filename.lastIndexOf("\\")+1);
		this.element.setNome( filename );
		this.element.setBytes( file.getContents() );
	}

}