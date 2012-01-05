package by.giava.attivita.controller;

import it.coopservice.commons2.annotations.BackPage;
import it.coopservice.commons2.annotations.EditPage;
import it.coopservice.commons2.annotations.ListPage;
import it.coopservice.commons2.annotations.ViewPage;
import it.coopservice.commons2.controllers.AbstractLazyController;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.LazyDataModel;

import by.giava.attivita.model.Attivita;
import by.giava.attivita.model.type.CategoriaAttivita;
import by.giava.attivita.model.type.TipoAttivita;
import by.giava.attivita.repository.AttivitaSession;
import by.giava.attivita.repository.CategorieSession;
import by.giava.base.common.util.FileUtils;
import by.giava.base.common.util.JSFUtils;
import by.giava.base.controller.OperazioniLogController;
import by.giava.base.controller.PropertiesHandler;
import by.giava.base.controller.util.PageUtils;
import by.giava.base.model.OperazioniLog;
import by.giava.base.model.Page;
import by.giava.base.model.Resource;
import by.giava.base.model.attachment.Immagine;
import by.giava.base.repository.ResourceSession;

@Named
@SessionScoped
public class AttivitaController extends AbstractLazyController<Attivita> {

	private static final long serialVersionUID = 1L;

	// --------------------------------------------------------

	@BackPage
	public static String BACK = "/private/amministrazione.xhtml";
	@ViewPage
	public static String VIEW = "/private/attivita/scheda-attivita.xhtml";
	@ListPage
	public static String LIST = "/private/attivita/lista-attivita.xhtml";
	@EditPage
	public static String NEW_OR_EDIT = "/private/attivita/gestione-attivita.xhtml";

	// --------------------------------------------------------

	@Inject
	AttivitaSession attivitaSession;

	@Inject
	PropertiesHandler propertiesHandler;

	@Inject
	CategorieSession categorieSession;

	@Inject
	ResourceSession resourceSession;

	@Inject
	OperazioniLogController operazioniLogController;

	private int tipoId;
	private int catId;
	private Immagine immagine;

	public AttivitaController() {
	}

	public String addAttivita() {
		this.tipoId = 0;
		this.catId = 0;
		this.immagine = null;
		this.element = new Attivita();
		this.element.setCategoria(new CategoriaAttivita());
		this.element.getCategoria().setTipoAttivita(new TipoAttivita());
		this.editMode = false;
		return NEW_OR_EDIT;
	}

	public String saveAttivita() {
		if (catId == 0) {
			FacesContext.getCurrentInstance().addMessage(
					"",
					new FacesMessage("Categoria non valida",
							"Selezionare la categoria."));
			return null;
		}
		String idTitle = PageUtils.createPageId(this.element.getNome());
		String idFinal = testId(idTitle);
		this.element.setId(idFinal);
		this.element.setData(new Date());
		this.element.setAutore(JSFUtils.getUserName());
		CategoriaAttivita cat = categorieSession
				.findCategoriaAttivita(new Long(catId));
		this.element.setCategoria(cat);
		if (this.immagine != null && this.immagine.getData() != null)
			this.element.setImmagine(getImmagine());
		this.element = attivitaSession.persist(this.element);
		this.model = null;
		operazioniLogHandler.save(OperazioniLog.NEW, JSFUtils.getUserName(),
				"creazione attivita': " + this.element.getNome());
		return VIEW;
	}

	public String modAttivita(String id) {
		this.element = attivitaSession.find(id);
		this.immagine = null;
		this.catId = this.element.getCategoria().getId().intValue();
		this.tipoId = this.element.getCategoria().getTipoAttivita().getId()
				.intValue();
		propertiesHandler.cambioTipoDirect(this.tipoId);
		this.immagine = new Immagine();
		if (this.element.getImmagine() != null) {
			this.immagine.setFilename(this.element.getImmagine().getFilename());
		}
		this.editMode = true;
		return NEW_OR_EDIT;
	}

	public String deleteAttivita(String id) {
		operazioniLogHandler.save(OperazioniLog.DELETE, JSFUtils.getUserName(),
				"emilinazione attivita': " + this.element.getNome());
		attivitaSession.delete(id);
		this.model = null;
		return LIST;
	}

	public String updateAttivita() {
		CategoriaAttivita cat = categorieSession
				.findCategoriaAttivita(new Long(getCatId()));
		if (cat == null)
			return NEW_OR_EDIT;
		this.element.setCategoria(cat);
		if (getImmagine().getData() != null)
			this.element.setImmagine(getImmagine());
		attivitaSession.update(this.element);
		operazioniLogHandler.save(OperazioniLog.MODIFY, JSFUtils.getUserName(),
				"modifica attivita': " + this.element.getNome());
		this.model = null;
		return VIEW;
	}

	// -----------------------------------------------------

	public int getTipoId() {

		return tipoId;
	}

	public void setTipoId(int tipoId) {
		this.tipoId = tipoId;
	}

	public String testId(String id) {
		String idFinal = id;
		boolean trovato = false;
		int i = 0;
		while (!trovato) {
			logger.info("id final: " + idFinal);
			Attivita attivitaFind = attivitaSession.find(idFinal);
			logger.info("trovato_ " + attivitaFind);
			if (attivitaFind != null) {
				i++;
				idFinal = id + "-" + i;
			} else {
				trovato = true;
				return idFinal;

			}

		}

		return "";
	}

	public int getCatId() {
		return catId;
	}

	public void setCatId(int catId) {
		this.catId = catId;
	}

	public void handleFileUpload(FileUploadEvent event) {
		logger.info("Uploaded: {}" + event.getFile().getFileName());
		logger.info("Uploaded: {}" + event.getFile().getContentType());
		logger.info("Uploaded: {}" + event.getFile().getSize());

		getImmagine().setUploadedData(event.getFile());
		getImmagine().setData(event.getFile().getContents());
		getImmagine().setType(event.getFile().getContentType());
		String filename = FileUtils.createImage_("img", event.getFile()
				.getFileName(), event.getFile().getContents());
		getElement().setImmagine(new Immagine());
		getElement().getImmagine().setFilename(filename);
		getElement().getImmagine().setFilename(getImmagine().getFilename());
	}

	public Immagine getImmagine() {
		if (immagine == null)
			this.immagine = new Immagine();
		return immagine;
	}

	public void setImmagine(Immagine immagine) {
		this.immagine = immagine;
	}

	public String discardImage() {
		if (getElement().getImmagine() != null
				&& getElement().getImmagine().getFilename() != null) {
			Resource r = this.resourceSession.find("img", getElement()
					.getImmagine().getFilename());
			if (r != null)
				resourceSession.delete(r);
		}
		if (this.getImmagine() != null
				&& this.getImmagine().getFilename() != null) {
			Resource r = this.resourceSession.find("img", this.getImmagine()
					.getFilename());
			if (r != null)
				resourceSession.delete(r);
		}
		getElement().setImmagine(new Immagine());
		this.immagine = new Immagine();
		return null;
	}

}
