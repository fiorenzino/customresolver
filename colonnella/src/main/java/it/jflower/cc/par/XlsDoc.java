package it.jflower.cc.par;

import java.io.Serializable;

public class XlsDoc implements Serializable {

	private static final long serialVersionUID = 1L;
	private byte[] data;
	private String nome;

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
