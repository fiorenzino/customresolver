package by.giava.base.pojo;

import java.io.InputStream;
import java.io.Serializable;

import javax.persistence.Transient;

//@Entity
public class Resource implements Serializable {

	private static final long serialVersionUID = 1L;

	// ------------------------------------------------------------------------

	private String id;
	private String tipo;
	private byte[] bytes;

	private String nome;
	private InputStream inputStream;

	// ------------------------------------------------------------------------

	public Resource() {
	}

	// ------------------------------------------------------------------------

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Transient
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@Transient
	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	@Transient
	public String getTesto() {
		return new String(bytes);
	}

	@Transient
	public int getSize() {
		return bytes == null ? 0 : bytes.length;
	}

	public void setTesto(String testo) {
		this.bytes = testo.getBytes();
	}

	// ------------------------------------------------------------------------

	@Override
	public String toString() {
		return (this.id != null) ? this.id.toString() : super.toString();
	}

	@Transient
	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

}