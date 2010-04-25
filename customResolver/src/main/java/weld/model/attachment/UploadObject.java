package weld.model.attachment;

import java.io.Serializable;

import org.apache.myfaces.custom.fileupload.UploadedFile;

public class UploadObject implements Serializable {

	private String filename;
	private byte[] data;
	private String type;
	private UploadedFile uploadedData;

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public UploadedFile getUploadedData() {
		return uploadedData;
	}

	public void setUploadedData(UploadedFile uploadedData) {
		this.uploadedData = uploadedData;
	}
}
