package it.flowercms.par;

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

	public void setTesto(String testo) {
		this.bytes = testo.getBytes();
	}

	// ------------------------------------------------------------------------

	@Override
	public String toString() {
		return (this.id != null) ? this.id.toString() : super.toString();
	}

}
