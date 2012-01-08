package by.giava.base.common.web.controller;

import it.coopservice.commons2.annotations.OwnRepository;
import it.coopservice.commons2.domain.Search;
import it.coopservice.commons2.repository.Repository;
import it.coopservice.commons2.utils.JSFUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

import org.apache.commons.lang.StringUtils;
import org.jboss.logging.Logger;

import by.giava.base.common.annotation.HttpParam;

public abstract class AbstractRequestController<T> implements
		UiRepeatInterface<T>, Serializable {

	private static final long serialVersionUID = 1L;

	// ------------------------------------------------
	// --- Logger -------------------------------------
	// ------------------------------------------------

	protected final Logger logger = Logger.getLogger(getClass()
			.getCanonicalName());

	/**
	 * Entity class
	 */
	private Class<T> entityClass;

	/**
	 * Search object
	 */
	protected Search<T> search;

	/**
	 * List elements from repository search
	 */
	private List<T> elements;

	// ------------------------------------------------
	// --- Stato dell'handler -------------------------
	// ------------------------------------------------

	public static final String REDIRECT_PARAM = "?faces-redirect=true";

	/**
	 * Risultato del caricamento diretto per parametro id
	 */
	private T element;

	/**
	 * Repository per fare query su db
	 */
	private Repository<T> repository;

	/**
	 * current page
	 */
	private int currentpage;

	// ------------------------------------------------
	// --- Costruttore interno ------------------------
	// ------------------------------------------------

	/**
	 * Costruttore con parametri da invocare obbligatoriamente nel costruttore
	 * senza argomenti dei sotto-handler: inizializza i parametri di ricerca e
	 * colleziona eventuali vincoli session-wide come quelli che discendono
	 * dalla identita' dell'utente loggato
	 */
	/**
		 * 
		 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public AbstractRequestController() {
		this.entityClass = getClassType();
		// defaultCriteria();
		search = new Search(this.entityClass);

	}

	@SuppressWarnings("unused")
	@PostConstruct
	private void init() {
		injectRepository();
		initController();
		defaultCriteria();
	}

	/**
	 * Metodo per inizializzare i controller
	 */
	public void initController() {

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void injectRepository() {

		Field[] fields = getClass().getDeclaredFields();
		for (Field field : fields) {
			try {

				OwnRepository repository_anno = field
						.getAnnotation(OwnRepository.class);

				HttpParam currentField = field.getAnnotation(HttpParam.class);
				try {
					if (repository_anno != null) {
						Class clazz = repository_anno.value();
						this.repository = (Repository<T>) JSFUtils
								.getBean(clazz);
					}
				} catch (Exception e) {
					logger.info(e.getMessage());
				}
				try {
					if (currentField != null) {
						field.setAccessible(true);
						String current = (String) field.get(null);
						currentpage = Integer.parseInt(current);
					}
				} catch (Exception e) {
					logger.info(e.getMessage());
				}

			} catch (IllegalArgumentException e) {
				logger.info(e.getMessage());
			}
		}
	}

	/**
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Class<T> getClassType() {
		Class clazz = getClass();
		while (!(clazz.getGenericSuperclass() instanceof ParameterizedType)) {
			clazz = clazz.getSuperclass();
		}
		ParameterizedType parameterizedType = (ParameterizedType) clazz
				.getGenericSuperclass();
		// ParameterizedType parameterizedType = (ParameterizedType) getClass()
		// .getSuperclass().getGenericSuperclass();
		return (Class<T>) parameterizedType.getActualTypeArguments()[0];
	}

	public int totalSize(String tipo, String filtro) {
		return getRepository().getListSize(getSearch());

	}

	public List<T> loadPage(String tipo, String filtro, int startRow,
			int pageSize) {
		return getRepository().getList(getSearch(), startRow, pageSize);
	}

	// ------------------------------------------------
	// --- getter/setter-------------------------------
	// ------------------------------------------------

	/**
	 * @return
	 */
	public Search<T> getSearch() {
		return this.search;
	}

	/**
	 * @return
	 */
	public T getElement() {
		return element;
	}

	/**
	 * @param element
	 */
	public void setElement(T element) {
		this.element = element;
	}

	/**
	 * @return
	 */
	public List<T> getElements() {
		return elements;
	}

	/**
	 * @param elements
	 */
	public void setElements(List<T> elements) {
		this.elements = elements;
	}

	public int getCurrentpage() {
		return currentpage;
	}

	public void setCurrentpage(int currentpage) {
		this.currentpage = currentpage;
	}

	/**
	 * Metodo per ottenere l'id di ricerca (il nome del campo non è noto a
	 * priori e uguale per tutti gli entity... almeno finché non introduciamo
	 * interfacce a questo scopo... ;) )
	 * 
	 * Serve per poter fornire una implementazione a default del metodo
	 * refreshModel(), senza doverlo riscrivere in ogni sotto-handler, come
	 * invece fatto in passato
	 */
	public Object getId(T t) {
		try {
			Field f = t.getClass().getDeclaredField("id");
			f.setAccessible(true);
			return f.get(t);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Metodo per ottenere l'ejb generico che effettua la ricerca
	 * 
	 * Serve per poter fornire una implementazione a default del metodo
	 * refreshModel(), senza doverlo riscrivere in ogni sotto-handler, come
	 * invece fatto in passato
	 * 
	 * @return
	 */
	public Repository<T> getRepository() {
		return repository;
	}

	// ============================================================================================
	// === LOGICA DI GESTIONE DELLO STATO INTERNO DELL'HANDLER
	// ============================================================================================

	/**
	 * Metodo interno (protected) da overriddare per assicurare che i criteri di
	 * ricerca contengano sempre tutti i vincoli desiderati (es: identità
	 * utente, selezioni esterne, oggetti figli non nulli, ecc...)
	 * 
	 * Qui ne viene fornita una implementazione di default che non fa nulla, per
	 * evitare di scrivere un metodo vuoto nei sotto-handler in caso non ce ne
	 * sia bisogno
	 */
	public void defaultCriteria() {

	}

	// ============================================================================================
	// === LOGICA DI NAVIGAZIONE
	// ==================================================================
	// ============================================================================================

	/*
	 * Questi metodi non iniziano per get perché sono da usare nelle action dei
	 * componenti ui (method binding expression), non come valori (value binding
	 * expression... get/set.. dot notation... you know...)
	 */

	public void getOrderByParameter() {
		String orderBy = (String) JSFUtils.getParameter("orderBy");
		if (!StringUtils.isEmpty(orderBy)) {
			search.setOrder(orderBy);
		}
	}

	// --------------------------------------------------------------------------
	// Varie
	// --------------------------------------------------------------------------

	protected void addFacesMessage(String summary, String message) {
		addFacesMessage(null, summary, message, "");
	}

	protected void addFacesMessage(String summary) {
		addFacesMessage(null, summary, summary, "");
	}

	protected void addFacesMessage(Severity severity, String summary,
			String message, String forComponentId) {
		FacesMessage fm = new FacesMessage(message);
		fm.setSummary(summary);
		if (severity != null) {
			fm.setSeverity(severity);
		} else {
			fm.setSeverity(FacesMessage.SEVERITY_ERROR);
		}
		FacesContext.getCurrentInstance().addMessage(forComponentId, fm);
	}

}
