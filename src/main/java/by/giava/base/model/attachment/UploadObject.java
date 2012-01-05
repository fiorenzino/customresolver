package by.giava.base.model.attachment;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.UploadedFile;

public class UploadObject implements Serializable {

	private static final long serialVersionUID = 1L;

	private String filename;
	private byte[] data;
	private String type;
	private UploadedFile file;
//	private DefaultStreamedContent stream;

	public DefaultStreamedContent getStream() {
		InputStream stream;
		try {
			stream = getFile().getInputstream();
			return new DefaultStreamedContent(stream, "image/jpeg");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

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

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

}
