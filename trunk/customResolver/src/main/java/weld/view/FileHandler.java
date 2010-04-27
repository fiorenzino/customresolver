package weld.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import weld.model.attachment.UploadObject;
import weld.view.utils.FileUtils;

@Named
@SessionScoped
public class FileHandler implements Serializable {

	private List<UploadObject> daCaricare;

	private List<String> files;

	private String fileName;

	private String fileContent;

	private Logger logger = LoggerFactory.getLogger(FileHandler.class);

	// 0 css
	// 1 img
	// 2 swf
	// 3 js
	private int fileType;

	private SelectItem[] fileTypeItems = new SelectItem[] {};

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
		FacesMessage msg = new FacesMessage("Succesful", event.getFile()
				.getFileName()
				+ " is uploaded.");
		FacesContext.getCurrentInstance().addMessage(null, msg);
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

	public String addNewFiles() {
		switch (fileType) {
		case 0:

			break;
		case 1:

			break;
		case 2:

			break;
		case 3:

			break;

		default:
			break;
		}
		return "";
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
			this.files = new ArrayList<String>();
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
		default:
			this.files = new ArrayList<String>();
			break;
		}
	}

	public SelectItem[] getFileTypeItems() {
		if (fileTypeItems == null || fileTypeItems.length == 0) {
			fileTypeItems = new SelectItem[4];
			fileTypeItems[0] = new SelectItem(0, "css");
			fileTypeItems[1] = new SelectItem(1, "img");
			fileTypeItems[2] = new SelectItem(2, "flash");
			fileTypeItems[3] = new SelectItem(3, "javascript");
		}
		return fileTypeItems;
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

}
