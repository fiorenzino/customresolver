package by.giava.pubblicazioni.controller;

import it.coopservice.commons2.annotations.BackPage;
import it.coopservice.commons2.annotations.EditPage;
import it.coopservice.commons2.annotations.ListPage;
import it.coopservice.commons2.annotations.OwnRepository;
import it.coopservice.commons2.annotations.ViewPage;
import it.coopservice.commons2.controllers.AbstractLazyController;
import it.coopservice.commons2.domain.Search;
import it.coopservice.commons2.model.LocalDataModel;
import it.coopservice.commons2.utils.JSFUtils;

import java.util.Calendar;
import java.util.Date;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;

import by.giava.base.common.util.FileUtils;
import by.giava.base.controller.OperazioniLogController;
import by.giava.base.controller.PropertiesHandler;
import by.giava.base.controller.request.ParamsHandler;
import by.giava.base.model.OperazioniLog;
import by.giava.base.model.attachment.Documento;
import by.giava.pubblicazioni.model.Pubblicazione;
import by.giava.pubblicazioni.repository.PubblicazioniRepository;
import by.giava.pubblicazioni.repository.RegistroPubblicazioniRepository;

@Named
@SessionScoped
public class PubblicazioniController extends
		AbstractLazyController<Pubblicazione> {

	private static final long serialVersionUID = 1L;

	// --------------------------------------------------------
	@BackPage
	public static String BACK = "/private/amministrazione.xhtml";
	@ViewPage
	public static String VIEW = "/private/pubblicazioni/scheda.xhtml";
	@ListPage
	public static String LIST = "/private/pubblicazioni/lista.xhtml";
	@EditPage
	public static String NEW_OR_EDIT = "/private/pubblicazioni/gestione.xhtml";
	public static String PRINT_LIST = "/private/pubblicazioni/stampa.xhtml";

	// --------------------------------------------------------

	@Inject
	@OwnRepository(PubblicazioniRepository.class)
	PubblicazioniRepository pubblicazioniRepository;

	@Inject
	PropertiesHandler propertiesHandler;

	@Inject
	ParamsHandler paramsHandler;

	@Inject
	OperazioniLogController operazioniLogController;

	@Inject
	RegistroPubblicazioniRepository registroPubblicazioniRepository;

	// -----------------------------------------------
	private String anno;

	// ------------------------------------------------

	/**
	 * Obbligatoria l'invocazione 'appropriata' di questo super construttore
	 * protetto da parte delle sottoclassi
	 */
	public PubblicazioniController() {
	}

	// -----------------------------------------------------
	@Override
	public String addElement() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		getElement().setData(cal.getTime());
		getElement().setDal(cal.getTime());
		cal.add(Calendar.DAY_OF_YEAR, 15);
		getElement().setAl(cal.getTime());
		return editPage();
	}

	// -----------------------------------------------------

	public String allineaRegistro() {
		boolean result = registroPubblicazioniRepository
				.allineaRegistro(this.anno);
		if (result) {
			super.addFacesMessage("anno", "Anno resettato con successo.");
			return null;
		} else {
			super.addFacesMessage("anno", "qualche problemino!");
			return null;
		}

	}

	@Override
	public String save() {
		// recupero e preelaborazioni dati in input
		// nelle sottoclassi!! ovverride!
		// salvataggio
		if (getElement().getTipo().getId() == null) {
			super.addFacesMessage("", "Selezionare il tipo.");
			return null;
		}

		super.save();

		String indiceReg = registroPubblicazioniRepository.getNext();
		logger.info("nuovo indice registro: " + indiceReg);
		getElement().setProgressivoRegistro(indiceReg);
		pubblicazioniRepository.update(getElement());
		operazioniLogController.save(OperazioniLog.NEW, JSFUtils.getUserName(),
				"nuova publicazione: " + getElement().getTitolo());
		return viewPage();
	}

	@Override
	public String update() {
		if (getElement().getTipo().getId() == null) {
			super.addFacesMessage("Tipo non valido", "Selezionare il tipo.");
			return null;
		}
		operazioniLogController.save(OperazioniLog.MODIFY,
				JSFUtils.getUserName(), "modifica publicazione: "
						+ getElement().getTitolo());
		return super.update();
	}

	@Override
	public String delete() {
		operazioniLogController.save(OperazioniLog.DELETE,
				JSFUtils.getUserName(), "eliminazione publicazione: "
						+ getElement().getTitolo());
		return super.delete();
	}

	public void handleFileUpload(FileUploadEvent event) {
		logger.info("Uploaded: {}" + event.getFile().getFileName());
		logger.info("Uploaded: {}" + event.getFile().getContentType());
		logger.info("Uploaded: {}" + event.getFile().getSize());
		Documento doc = new Documento();
		doc.setUploadedData(event.getFile());
		doc.setData(event.getFile().getContents());
		doc.setType(event.getFile().getContentType());
		String filename = FileUtils.createFile_("docs", event.getFile()
				.getFileName(), event.getFile().getContents());
		doc.setFilename(filename);
		getElement().addDocumento(doc);
	}

	public void removeDocument(int index) {
		getElement().getDocumenti().remove(index);
	}

	public String cerca() {
		refreshModel();
		return LIST;
	}

	// -----------------------------------------------------------------

	private LocalDataModel<Pubblicazione> attiModel;
	private String attiTipo;
	private Integer attiPageSize;

	public LocalDataModel<Pubblicazione> atti(int pageSize) {
		return atti(null, pageSize);
	}

	private void leggiParams() {
		String id = paramsHandler.getParam("id");
		logger.info("TROVATO ID: " + id);
	}

	public LocalDataModel<Pubblicazione> atti(String tipo, int pageSize) {
		leggiParams();
		if (attiModel == null || this.attiTipo == null
				|| this.attiPageSize == null || !this.attiTipo.equals(tipo)
				|| this.attiPageSize != pageSize) {
			Pubblicazione pubblicazione = new Pubblicazione();
			pubblicazione.getTipo().setNome(tipo);
			Search<Pubblicazione> ricerca = new Search<Pubblicazione>(
					pubblicazione);
			attiModel = new LocalDataModel<Pubblicazione>(pageSize, ricerca,
					pubblicazioniRepository);
		}
		return attiModel;
	}

	public String print() {

		return PRINT_LIST;
	}

	public String getAnno() {
		return anno;
	}

	public void setAnno(String anno) {
		this.anno = anno;
	}
}
