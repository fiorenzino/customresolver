package it.jflower.par;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Template implements Serializable {

	private Long id;
	private String nome;
	private String header_start;
	private String header_stop;
	private String footer_start;
	private String footer_stop;
	private String col1_start;
	private String col1_stop;
	private String col2_start;
	private String col2_stop;
	private String col3_start;
	private String col3_stop;

	@Id
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

	public String getHeader_start() {
		return header_start;
	}

	public void setHeader_start(String headerStart) {
		header_start = headerStart;
	}

	public String getHeader_stop() {
		return header_stop;
	}

	public void setHeader_stop(String headerStop) {
		header_stop = headerStop;
	}

	public String getFooter_start() {
		return footer_start;
	}

	public void setFooter_start(String footerStart) {
		footer_start = footerStart;
	}

	public String getFooter_stop() {
		return footer_stop;
	}

	public void setFooter_stop(String footerStop) {
		footer_stop = footerStop;
	}

	public String getCol1_start() {
		return col1_start;
	}

	public void setCol1_start(String col1Start) {
		col1_start = col1Start;
	}

	public String getCol1_stop() {
		return col1_stop;
	}

	public void setCol1_stop(String col1Stop) {
		col1_stop = col1Stop;
	}

	public String getCol2_start() {
		return col2_start;
	}

	public void setCol2_start(String col2Start) {
		col2_start = col2Start;
	}

	public String getCol2_stop() {
		return col2_stop;
	}

	public void setCol2_stop(String col2Stop) {
		col2_stop = col2Stop;
	}

	public String getCol3_start() {
		return col3_start;
	}

	public void setCol3_start(String col3Start) {
		col3_start = col3Start;
	}

	public String getCol3_stop() {
		return col3_stop;
	}

	public void setCol3_stop(String col3Stop) {
		col3_stop = col3Stop;
	}
}
