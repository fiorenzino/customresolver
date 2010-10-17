package it.jflower.cc.par.attachment;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.primefaces.model.UploadedFile;

@Entity
public class Immagine implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String nome;
	private String descrizione;
	private String filename;
	private String filePath;
	private byte[] data;
	private String type;
	private UploadedFile uploadedData;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	@Transient
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

	@Transient
	public UploadedFile getUploadedData() {
		return uploadedData;
	}

	public void setUploadedData(UploadedFile uploadedData) {
		this.uploadedData = uploadedData;
	}

	@Transient
	public String getFilePath() {
		return "img/" + filename;
	}

}
