package by.giava.moduli.controller;

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

import by.giava.base.common.util.FileUtils;
import by.giava.base.controller.OperazioniLogController;
import by.giava.base.controller.request.ParamsHandler;
import by.giava.base.model.OperazioniLog;
import by.giava.base.model.attachment.Documento;
import by.giava.base.producer.PropertiesHandler;
import by.giava.moduli.model.Modulo;
import by.giava.moduli.repository.ModuliRepository;

@Named
@SessionScoped
public class ModuliController extends AbstractLazyController<Modulo> {

	private static final long serialVersionUID = 1L;

	private static final String DOCUMENT_FOLDER = "docs";

	// --------------------------------------------------------
	@BackPage
	public static String BACK = "/private/amministrazione.xhtml";
	@ViewPage
	public static String VIEW = "/private/moduli/scheda.xhtml";
	@ListPage
	public static String LIST = "/private/moduli/lista.xhtml";
	@EditPage
	public static String NEW_OR_EDIT = "/private/moduli/gestione.xhtml";

	// --------------------------------------------------------

	@Inject
	@OwnRepository(ModuliRepository.class)
	ModuliRepository moduliRepository;

	@Inject
	PropertiesHandler propertiesHandler;

	@Inject
	ParamsHandler paramsHandler;

	@Inject
	OperazioniLogController operazioniLogController;

	// ------------------------------------------------

	/**
	 * Obbligatoria l'invocazione 'appropriata' di questo super construttore
	 * protetto da parte delle sottoclassi
	 */
	public ModuliController() {
	}

	// ------------------------------------------------

	// -----------------------------------------------------

	@Override
	public String save() {

		if (getElement().getIdTipo() == null) {
			super.addFacesMessage("Tipo non valido", "Selezionare il tipo.");
			return null;
		}
		// vista di destinazione
		operazioniLogController.save(OperazioniLog.NEW, JSFUtils.getUserName(),
				"eliminazione moduli: " + getElement().getNome());
		return super.save();
	}

	@Override
	public String update() {
		// recupero dati in input
		// nelle sottoclassi!! ovverride!
		// salvataggio
		if (getElement().getIdTipo() == null) {
			super.addFacesMessage("Tipo non valido", "Selezionare il tipo.");
			return null;
		}
		operazioniLogController.save(OperazioniLog.MODIFY,
				JSFUtils.getUserName(), "modifica moduli: "
						+ getElement().getNome());
		return super.update();
	}

	@Override
	public String delete() {
		// operazione su db
		operazioniLogController.save(OperazioniLog.DELETE,
				JSFUtils.getUserName(), "eliminazione moduli: "
						+ getElement().getNome());
		return super.delete();
	}

	public void handleFileUpload(FileUploadEvent event) {
		logger.info("Uploaded: {} " + event.getFile().getFileName());
		logger.info("Uploaded: {}" + event.getFile().getContentType());
		logger.info("Uploaded: {}" + event.getFile().getSize());

		getElement().getDocumento().setUploadedData(event.getFile());
		getElement().getDocumento().setData(event.getFile().getContents());
		getElement().getDocumento().setType(event.getFile().getContentType());
		String filename = FileUtils.createFile_(DOCUMENT_FOLDER, event
				.getFile().getFileName(), event.getFile().getContents());
		getElement().getDocumento().setFilename(filename);
	}

	public String removeDocument() {
		getElement().setDocumento(new Documento());
		return null;
	}

}