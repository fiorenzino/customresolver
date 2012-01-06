package by.giava.base.controller;

import it.coopservice.commons2.annotations.BackPage;
import it.coopservice.commons2.annotations.EditPage;
import it.coopservice.commons2.annotations.ListPage;
import it.coopservice.commons2.annotations.OwnRepository;
import it.coopservice.commons2.annotations.ViewPage;
import it.coopservice.commons2.controllers.AbstractLazyController;
import it.coopservice.commons2.domain.Search;
import it.coopservice.commons2.model.LocalDataModel;
import it.coopservice.commons2.utils.JSFUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import by.giava.base.common.util.ImageUtils;
import by.giava.base.model.OperazioniLog;
import by.giava.base.model.Resource;
import by.giava.base.repository.ResourceRepository;

@Named
@SessionScoped
public class ResourceController extends AbstractLazyController<Resource> {

	private static final long serialVersionUID = 1L;

	// --------------------------------------------------------

	@BackPage
	public static String BACK = "/private/amministrazione.xhtml";
	@ListPage
	@ViewPage
	public static String LIST = "/private/risorse/lista-risorse.xhtml";
	@EditPage
	public static String EDIT = "/private/risorse/gestione-risorsa.xhtml";

	public static String UPLOAD = "/private/risorse/carica-risorse.xhtml";

	// --------------------------------------------------------

	@Inject
	@OwnRepository(ResourceRepository.class)
	ResourceRepository resourceRepository;

	@Inject
	PropertiesHandler propertiesHandler;

	@Inject
	OperazioniLogController operazioniLogController;
	// --------------------------------------------------------

	private List<Resource> uploadedResources = null;
	private LocalDataModel<Resource> imgModel;
	private Search<Resource> imgRicerca;

	// ------------------------------------------------

	/**
	 * Obbligatoria l'invocazione 'appropriata' di questo super construttore
	 * protetto da parte delle sottoclassi
	 */
	public ResourceController() {
	}

	// ------------------------------------------------

	/**
	 * Metodo da implementare per assicurare che i criteri di ricerca contengano
	 * sempre tutti i vincoli desiderati (es: identità utente, selezioni
	 * esterne, ecc...)
	 */
	@Override
	public void defaultCriteria() {
		getSearch().getObj().setTipo(
				propertiesHandler.getRisorseItems()[0].getValue().toString());
	}

	/**
	 * Metodo di navigazione per resettare lo stato interno e tornare alla
	 * pagina dell'elenco generale
	 * 
	 * @return
	 */
	@Override
	public String reset() {
		this.uploadedResources = null;
		return reset();
	}

	// -----------------------------------------------------

	public List<Resource> getUploadedResources() {
		return uploadedResources;
	}

	public Resource getResource(int index) {
		return uploadedResources.get(index);
	}

	public StreamedContent getResourceStream(int index) {
		Resource rs = uploadedResources.get(index);
		if (!"img".equals(rs.getTipo()))
			return null;
		StreamedContent image = new DefaultStreamedContent(rs.getInputStream(),
				rs.getTipo());
		return image;
	}

	// -----------------------------------------------------
	@Override
	public String addElement() {
		// impostazioni locali
		this.uploadedResources = new ArrayList<Resource>();
		super.addElement();
		return UPLOAD;
	}

	// -----------------------------------------------------
	@Override
	public String save() {
		for (Resource resource : uploadedResources) {
			if (resource.getTipo() == null)
				resource.setTipo(getElement().getTipo());
			resourceRepository.persist(resource);
		}
		// refresh locale
		refreshModel();
		// altre dipendenze
		//
		// vista di destinazione
		operazioniLogController.save(OperazioniLog.NEW, JSFUtils.getUserName(),
				"creazione risorsa: " + getElement().getNome());
		return listPage();
	}

	@Override
	public String update() {
		// recupero dati in input
		// TODO
		// salvataggio
		resourceRepository.update(getElement());
		// refresh locale
		refreshModel();
		// altre dipendenze
		//
		// vista di destinzione
		operazioniLogController.save(OperazioniLog.MODIFY,
				JSFUtils.getUserName(), "modifica risorsa: "
						+ getElement().getNome());
		return listPage();
	}

	@Override
	public String delete() {
		// operazione su db
		operazioniLogController.save(OperazioniLog.DELETE,
				JSFUtils.getUserName(), "eliminazione risorsa: "
						+ getElement().getNome());
		return super.delete();
	}

	// -----------------------------------------------------

	public String delElement(String tipo, String id) {
		Resource resource = new Resource();
		resource.setId(id);
		resource.setNome(id);
		resource.setTipo(tipo);
		resourceRepository.delete(resource);
		return this.reset();
	}

	public void handleFileUpload(FileUploadEvent event) {
		try {
			UploadedFile file = event.getFile();
			InputStream is = event.getFile().getInputstream();
			String filename = file.getFileName();
			if (filename.contains("\\"))
				filename = filename.substring(filename.lastIndexOf("\\") + 1);
			Resource resource = new Resource();
			resource.setInputStream(is);
			resource.setNome(filename);
			resource.setTipo(filename.endsWith("css") ? "css" : filename
					.endsWith("js") ? "js" : filename.endsWith("swf") ? "swf"
					: filename.endsWith("pdf") ? "docs" : "img");
			resource.setBytes(file.getContents());
			uploadedResources.add(resource);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Resource getSingleResource(int row) {
		try {
			Resource resource = uploadedResources.get(row);
			return resource;
		} catch (Exception e) {
			return null;
		}
	}

	public Search<Resource> getImgRicerca() {
		if (imgRicerca == null) {
			Resource resource = new Resource();
			resource.setTipo("img");
			imgRicerca = new Search<Resource>(resource);
		}
		return imgRicerca;
	}

	public String reloadImgModel() {
		Resource resource = new Resource();
		resource.setTipo("img");
		imgRicerca = new Search<Resource>(resource);
		return null;
	}

	public LocalDataModel<Resource> getImgModel() {
		if (imgModel == null) {
			imgRicerca.getObj().setTipo("img");
			imgModel = new LocalDataModel<Resource>(9, imgRicerca,
					resourceRepository);
		}
		return imgModel;
	}

	public void setImgModel(LocalDataModel<Resource> object) {
		this.imgModel = object;
	}

	public Integer proportionalHeight(String url, int maxWidth, int maxHeight) {
		return ImageUtils.getImageHeightProportional("img" + File.separator
				+ url, maxWidth, maxHeight);
	}

	public Integer proportionalWidth(String url, int maxWidth, int maxHeight) {
		return ImageUtils.getImageWidthProportional("img" + File.separator
				+ url, maxWidth, maxHeight);
	}

}