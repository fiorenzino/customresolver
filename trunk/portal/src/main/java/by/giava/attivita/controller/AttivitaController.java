package by.giava.attivita.controller;

import it.coopservice.commons2.annotations.BackPage;
import it.coopservice.commons2.annotations.EditPage;
import it.coopservice.commons2.annotations.ListPage;
import it.coopservice.commons2.annotations.OwnRepository;
import it.coopservice.commons2.annotations.ViewPage;
import it.coopservice.commons2.controllers.AbstractLazyController;
import it.coopservice.commons2.utils.JSFUtils;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;

import by.giava.attivita.model.Attivita;
import by.giava.attivita.repository.AttivitaRepository;
import by.giava.attivita.repository.CategorieAttivitaRepository;
import by.giava.base.common.util.FileUtils;
import by.giava.base.controller.OperazioniLogController;
import by.giava.base.model.OperazioniLog;
import by.giava.base.model.attachment.Immagine;
import by.giava.base.pojo.Resource;
import by.giava.base.producer.PropertiesHandler;
import by.giava.base.repository.ResourceRepository;

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
	@OwnRepository(AttivitaRepository.class)
	AttivitaRepository attivitaRepository;

	@Inject
	PropertiesHandler propertiesHandler;

	@Inject
	CategorieAttivitaRepository categorieAttivitaRepository;

	@Inject
	ResourceRepository resourceRepository;

	@Inject
	OperazioniLogController operazioniLogController;

	// private int tipoId;
	// private int catId;

	// private Immagine immagine;

	public AttivitaController() {
	}

	// public String addAttivita() {
	// this.tipoId = 0;
	// this.catId = 0;
	// this.immagine = null;
	// this.element = new Attivita();
	// this.element.setCategoria(new CategoriaAttivita());
	// this.element.getCategoria().setTipoAttivita(new TipoAttivita());
	// this.editMode = false;
	// return NEW_OR_EDIT;
	// }

	@Override
	public String save() {
		if (getElement().getCategoria().getId() == null) {
			super.addFacesMessage("Categoria non valida");
			return null;
		}
		operazioniLogController.save(OperazioniLog.NEW, JSFUtils.getUserName(),
				"creazione attivita': " + getElement().getNome());
		super.save();
		return viewPage();
	}

	// public String modAttivita(String id) {
	// this.element = attivitaSession.find(id);
	// this.immagine = null;
	// this.catId = this.element.getCategoria().getId().intValue();
	// this.tipoId = this.element.getCategoria().getTipoAttivita().getId()
	// .intValue();
	// propertiesHandler.cambioTipoDirect(this.tipoId);
	// this.immagine = new Immagine();
	// if (this.element.getImmagine() != null) {
	// this.immagine.setFilename(this.element.getImmagine().getFilename());
	// }
	// this.editMode = true;
	// return NEW_OR_EDIT;
	// }

	@Override
	public String delete() {
		// TODO Auto-generated method stub
		operazioniLogController.save(OperazioniLog.DELETE,
				JSFUtils.getUserName(), "emilinazione attivita': "
						+ getElement().getNome());
		return super.delete();
	}

	// public String deleteAttivita(String id) {
	//
	// attivitaSession.delete(id);
	// this.model = null;
	// return LIST;
	// }

	@Override
	public String update() {
		operazioniLogController.save(OperazioniLog.MODIFY,
				JSFUtils.getUserName(), "modifica attivita': "
						+ getElement().getNome());
		return super.update();
	}

	// public String updateAttivita() {
	// CategoriaAttivita cat = categorieSession
	// .findCategoriaAttivita(new Long(getCatId()));
	// if (cat == null)
	// return NEW_OR_EDIT;
	// this.element.setCategoria(cat);
	// if (getImmagine().getData() != null)
	// this.element.setImmagine(getImmagine());
	// attivitaSession.update(this.element);
	//
	// this.model = null;
	// return VIEW;
	// }

	// // -----------------------------------------------------
	//
	// public int getTipoId() {
	//
	// return tipoId;
	// }
	//
	// public void setTipoId(int tipoId) {
	// this.tipoId = tipoId;
	// }
	//
	// public int getCatId() {
	// return catId;
	// }
	//
	// public void setCatId(int catId) {
	// this.catId = catId;
	// }

	public void handleFileUpload(FileUploadEvent event) {
		logger.info("Uploaded: {}" + event.getFile().getFileName());
		logger.info("Uploaded: {}" + event.getFile().getContentType());
		logger.info("Uploaded: {}" + event.getFile().getSize());

		getElement().getImmagine().setUploadedData(event.getFile());
		getElement().getImmagine().setData(event.getFile().getContents());
		getElement().getImmagine().setType(event.getFile().getContentType());
		String filename = FileUtils.createImage_("img", event.getFile()
				.getFileName(), event.getFile().getContents());
		getElement().getImmagine().setFilename(filename);
	}

	public String discardImage() {
		if (getElement().getImmagine() != null
				&& getElement().getImmagine().getFilename() != null) {
			Resource r = resourceRepository.find("img", getElement()
					.getImmagine().getFilename());
			if (r != null)
				resourceRepository.delete(r);
		}
		if (getElement().getImmagine() != null
				&& getElement().getImmagine().getFilename() != null) {
			Resource r = resourceRepository.find("img", getElement()
					.getImmagine().getFilename());
			if (r != null)
				resourceRepository.delete(r);
		}
		getElement().setImmagine(new Immagine());
		return null;
	}

}
