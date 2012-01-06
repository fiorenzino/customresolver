package by.giava.news.controller;

import it.coopservice.commons2.annotations.BackPage;
import it.coopservice.commons2.annotations.EditPage;
import it.coopservice.commons2.annotations.ListPage;
import it.coopservice.commons2.annotations.OwnRepository;
import it.coopservice.commons2.annotations.ViewPage;
import it.coopservice.commons2.controllers.AbstractLazyController;
import it.coopservice.commons2.utils.JSFUtils;

import java.io.IOException;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;

import by.giava.base.common.util.FileUtils;
import by.giava.base.common.util.ImageUtils;
import by.giava.base.controller.OperazioniLogController;
import by.giava.base.model.OperazioniLog;
import by.giava.base.model.attachment.Documento;
import by.giava.base.model.attachment.Immagine;
import by.giava.news.repository.NewNotizieRepository;
import by.giava.news.repository.TipoInformazioniRepository;
import by.giava.notizie.model.Notizia;

@Named
@SessionScoped
public class NewNotizieController extends AbstractLazyController<Notizia> {

	private static final long serialVersionUID = 1L;

	// --------------------------------------------------------

	@BackPage
	public static String BACK = "/private/amministrazione.xhtml";
	@ViewPage
	public static String VIEW = "/private/notizieconfoto/scheda-notizia.xhtml";
	@ListPage
	public static String LIST = "/private/notizieconfoto/lista-notizie.xhtml";
	@EditPage
	public static String NEW_OR_EDIT = "/private/notizieconfoto/gestione-notizia.xhtml";

	// --------------------------------------------------------

	@Inject
	@OwnRepository(NewNotizieRepository.class)
	NewNotizieRepository newNotizieRepository;

	@Inject
	OperazioniLogController operazioniLogController;

	// --------------------------------------------------------

	public NewNotizieController() {
	}

	// --------------------------------------------------------

	// --------------------------------------------------------

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
		getElement().getDocumenti().add(doc);
	}

	public void handleImgUpload(FileUploadEvent event) {
		logger.info("Uploaded: {}" + event.getFile().getFileName());
		logger.info("Uploaded: {}" + event.getFile().getContentType());
		logger.info("Uploaded: {}" + event.getFile().getSize());
		try {
			String type = event
					.getFile()
					.getFileName()
					.substring(
							event.getFile().getFileName().lastIndexOf(".") + 1);
			byte[] imgRes = ImageUtils.resizeImage(event.getFile()
					.getContents(), 500, type);
			Immagine img = new Immagine();
			img.setUploadedData(event.getFile());
			img.setData(imgRes);
			img.setType(event.getFile().getContentType());
			String filename = FileUtils.createImage_("img", event.getFile()
					.getFileName(), imgRes);
			img.setFilename(filename);
			getElement().getImmagini().add(img);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void removeDocument(int index) {
		getElement().getDocumenti().remove(index);
	}

	public void removeImage(int index) {
		getElement().getImmagini().remove(index);
	}

	@Override
	public String save() {

		super.save();
		if (getElement().isEvidenza()) {
			newNotizieRepository.refreshEvidenza(getElement().getId());
		}
		operazioniLogController.save(OperazioniLog.NEW, JSFUtils.getUserName(),
				"creazione notizia: " + getElement().getTitolo());
		return VIEW;
	}

	@Override
	public String delete() {
		operazioniLogController.save(OperazioniLog.DELETE,
				JSFUtils.getUserName(), "eliminazione notizia: "
						+ getElement().getTitolo());
		return super.delete();
	}

	@Override
	public String update() {
		super.update();
		if (getElement().isEvidenza()) {
			newNotizieRepository.refreshEvidenza(getElement().getId());
		}
		operazioniLogController.save(OperazioniLog.MODIFY,
				JSFUtils.getUserName(), "modifica notizia: "
						+ getElement().getTitolo());
		return VIEW;
	}

	// // -----------------------------------------------------------
	//
	// private LocalDataModel<Notizia> latestNewsModel;
	// private String latestTipo;
	// private Integer latestPageSize;
	//
	// public LocalDataModel<Notizia> ultimeNotizie(String tipo, int pageSize) {
	// if (latestNewsModel == null || this.latestTipo == null
	// || this.latestPageSize == null || !this.latestTipo.equals(tipo)
	// || this.latestPageSize != pageSize) {
	// Ricerca<Notizia> ricerca = new Ricerca<Notizia>(Notizia.class);
	// ricerca.getOggetto().setTipo(new TipoInformazione());
	// ricerca.getOggetto().getTipo().setNome(tipo);
	// latestNewsModel = new LocalDataModel<Notizia>(pageSize, ricerca,
	// notizieSession);
	// }
	// return latestNewsModel;
	// }
	//
	// // -----------------------------------------------------------
	//
	//

}
