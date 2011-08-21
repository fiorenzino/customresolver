package it.jflower.cc.web;

import it.jflower.base.utils.FileUtils;
import it.jflower.base.utils.ImageUtils;
import it.jflower.cc.par.attachment.UploadObject;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@SessionScoped
public class FileHandler implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<UploadObject> daCaricare;

	private List<String> files;

	private String fileName;

	private String fileContent;

	private Logger logger = LoggerFactory.getLogger(FileHandler.class);

	// 0 css
	// 1 img
	// 2 swf
	// 3 js
	// 4 pdf, p7m, doc, docx, xls, xlsx
	private int fileType;

	public void handleFileUpload(FileUploadEvent event) {
		// InputStream stream =
		// this.getClass().getResourceAsStream("barcalogo.jpg");

		logger.info("Uploaded: {}", event.getFile().getFileName());
		logger.info("Uploaded: {}", event.getFile().getContentType());
		logger.info("Uploaded: {}", event.getFile().getSize());
		UploadObject obj = new UploadObject();
		obj.setFile(event.getFile());
		obj.setData(event.getFile().getContents());
		obj.setFilename(event.getFile().getFileName());
		obj.setType(event.getFile().getContentType());
		getDaCaricare().add(obj);
		switch (fileType) {
		case 0:
			FileUtils.createFile_("css", event.getFile().getFileName(), event
					.getFile().getContents());
		case 1:
			FileUtils.createImage_("img", event.getFile().getFileName(), event
					.getFile().getContents());
		case 2:
			FileUtils.createFile_("swf", event.getFile().getFileName(), event
					.getFile().getContents());
		case 3:
			FileUtils.createFile_("js", event.getFile().getFileName(), event
					.getFile().getContents());
		case 4:
			FileUtils.createFile_("docs", event.getFile().getFileName(), event
					.getFile().getContents());
		}
		FacesMessage msg = new FacesMessage("Succesful", event.getFile()
				.getFileName() + " is uploaded.");
		FacesContext.getCurrentInstance().addMessage(null, msg);
		setFileType(1);
		caricaFiles();
	}

	// 0 css
	// 1 img
	// 2 swf
	// 3 js
	// 3 docs

	public String getTypeString() {
		switch (fileType) {
		case 0:
			return "css";
		case 1:
			return "immagine";
		case 2:
			return "flash";
		case 3:
			return "javascript";
		case 4:
			return "docs";
		default:
			return "cosa??";
		}
	}

	public String caricaFile(int type) {
		setFileType(type);
		this.daCaricare = null;
		return "/private/files/gestione-file.xhtml";
	}

	public int getFileType() {
		return fileType;
	}

	public void setFileType(int fileType) {
		this.fileType = fileType;
	}

	public void deleteSingleFile() {

	}

	public void addSingleFile() {
		getDaCaricare().add(new UploadObject());
	}

	public List<UploadObject> getDaCaricare() {
		if (this.daCaricare == null)
			this.daCaricare = new ArrayList<UploadObject>();
		return daCaricare;
	}

	public void setDaCaricare(List<UploadObject> daCaricare) {
		this.daCaricare = daCaricare;
	}

	public List<String> getFiles() {
		if (this.files == null)
			caricaFiles();
		return files;
	}

	public void setFiles(List<String> files) {
		this.files = files;
	}

	public void caricaFiles() {
		switch (fileType) {
		case 0:
			this.files = FileUtils.getCssFiles();
			break;
		case 1:
			this.files = FileUtils.getImgFiles();
			break;
		case 2:
			this.files = FileUtils.getFlashFiles();
			break;
		case 3:
			this.files = FileUtils.getJsFiles();
			break;
		case 4:
			this.files = FileUtils.getPdfFiles();
			break;
		default:
			this.files = new ArrayList<String>();
			break;
		}
		System.out.println("dim files: " + this.files.size());
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileContent() {
		return fileContent;
	}

	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}

	public String modFile(String fileName) {
		this.fileContent = FileUtils.getFileContent(fileName);
		return "";
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
