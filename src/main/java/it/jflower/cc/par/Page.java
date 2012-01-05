package it.jflower.cc.par;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

@Entity
public class Page implements Serializable {

	private static final long serialVersionUID = 1L;

	// ------------------------------------------------------------------------

	private String id;
	boolean attivo = true;
	private String title;
	private String description;
	private TemplateImpl template;

	// ------------------------------------------------------------------------

	// transiente, per accumulare il risultato finale
	private String content;

	// ------------------------------------------------------------------------

	public Page() {
	}

	// ------------------------------------------------------------------------

	@Id
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@OneToOne( fetch = FetchType.EAGER, cascade = CascadeType.ALL )
	public TemplateImpl getTemplate() {
		return template;
	}

	public void setTemplate(TemplateImpl template) {
		this.template = template;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Lob
	@Column(length = 1024)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isAttivo() {
		return attivo;
	}

	public void setAttivo(boolean attivo) {
		this.attivo = attivo;
	}

	// ------------------------------------------------------------------------
	
	@Transient
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	// ------------------------------------------------------------------------
	
	@Override
	public String toString() {
		return ( this.id != null ) ? this.id : super.toString();
	}
	
	@Override
	public boolean equals(Object o) {
		if ( ! ( o instanceof Page ) )
			return false;
		Page p = (Page)o;
		return p.getId() == null ? false : p.getId().equals(this.id);
	}

	@Override
	public int hashCode() {
		return ( this.id != null ) ? this.id.hashCode() : super.hashCode();
	}

}
