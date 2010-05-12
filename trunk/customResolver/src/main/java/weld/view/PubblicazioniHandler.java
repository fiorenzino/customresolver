package weld.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import weld.model.Pubblicazione;
import weld.model.attachment.Documento;
import weld.session.PubblicazioniSession;
import weld.view.utils.FileUtils;

@Named
@SessionScoped
public class PubblicazioniHandler implements Serializable {

	@Inject
	PubblicazioniSession pubblicazioniSession;

	private Logger logger = LoggerFactory.getLogger(AttivitaHandler.class);
	private boolean editMode;
	private List<Pubblicazione> all;
	private List<Documento> documenti;
	private Pubblicazione pubblicazione;

	public String addPubblicazione() {
		this.pubblicazione = new Pubblicazione();
		this.documenti = null;
		setEditMode(false);
		return "/private/pubblicazioni/gestione-pubblicazione.xhtml";
	}

	public String savePubblicazione() {
		this.pubblicazione.setDataPubblicazione(new Date());
		this.pubblicazione.setDocumenti(getDocumenti());
		pubblicazioniSession.persist(this.pubblicazione);
		return "/private/pubblicazioni/scheda-pubblicazione.xhtml";
	}

	public String deletePubblicazione() {
		pubblicazioniSession.delete(this.pubblicazione.getId());
		this.all = null;
		return "/private/gallerie/lista-pubblicazioni?redirect=true";
	}

	public String modPubblicazione(Long id) {
		setEditMode(true);
		this.pubblicazione = pubblicazioniSession.find(id);
		this.documenti = this.pubblicazione.getDocumenti();
		return "/private/pubblicazioni/gestione-pubblicazione.xhtml";
	}

	public String updatePubblicazione() {
		this.pubblicazione.setDocumenti(getDocumenti());
		pubblicazioniSession.update(this.pubblicazione);
		return "/private/pubblicazioni/scheda-pubblicazione.xhtml";
	}

	public String detailPubblicazione(Long id) {
		this.pubblicazione = pubblicazioniSession.find(id);
		this.documenti = this.pubblicazione.getDocumenti();
		return "/private/pubblicazioni/scheda-pubblicazione.xhtml";
	}

	public boolean isEditMode() {
		return editMode;
	}

	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}

	public List<Pubblicazione> getAll() {
		if (all == null)
			this.all = pubblicazioniSession.getAll();
		return all;
	}

	public void setAll(List<Pubblicazione> all) {
		this.all = all;
	}

	public List<Documento> getDocumenti() {
		if (this.documenti == null)
			this.documenti = new ArrayList<Documento>();
		return documenti;
	}

	public void setDocumenti(List<Documento> documenti) {
		this.documenti = documenti;
	}

	public Pubblicazione getPubblicazione() {
		return pubblicazione;
	}

	public void setPubblicazione(Pubblicazione pubblicazione) {
		this.pubblicazione = pubblicazione;
	}

	public void handleFileUpload(FileUploadEvent event) {
		logger.info("Uploaded: {}", event.getFile().getFileName());
		logger.info("Uploaded: {}", event.getFile().getContentType());
		logger.info("Uploaded: {}", event.getFile().getSize());
		Documento doc = new Documento();
		doc.setUploadedData(event.getFile());
		doc.setData(event.getFile().getContents());
		doc.setFilename(event.getFile().getFileName());
		doc.setType(event.getFile().getContentType());
		FileUtils.createFile("docs", event.getFile().getFileName(), event
				.getFile().getContents());
		getDocumenti().add(doc);
	}

	public void remDocumento(int index) {
		getDocumenti().remove(index);
	}
}