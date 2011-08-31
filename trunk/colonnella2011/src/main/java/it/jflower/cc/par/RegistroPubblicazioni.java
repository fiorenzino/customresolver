package it.jflower.cc.par;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class RegistroPubblicazioni implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String anno;
	private Long progressivo;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAnno() {
		return anno;
	}

	public void setAnno(String anno) {
		this.anno = anno;
	}

	public Long getProgressivo() {
		return progressivo;
	}

	public void setProgressivo(Long progressivo) {
		this.progressivo = progressivo;
	}

	@Transient
	public String getIndice() {
		return anno + "/" + String.format("%05d", progressivo);
	}
}
