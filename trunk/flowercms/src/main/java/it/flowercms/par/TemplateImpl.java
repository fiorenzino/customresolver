package it.flowercms.par;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class TemplateImpl implements Serializable {

	private static final long serialVersionUID = 1L;

	// ------------------------------------------------------------------------

	private Long id;
	boolean attivo = true;

	private String header;
	private String footer;
	private String col1;
	private String col2;
	private String col3;
	private Page page;
	private Template template;

	// ------------------------------------------------------------------------

	public TemplateImpl() {
	}

	// ------------------------------------------------------------------------

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	public Template getTemplate() {
		return template;
	}

	public void setTemplate(Template template) {
		this.template = template;
	}

	@Lob
	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	@Lob
	public String getFooter() {
		return footer;
	}

	public void setFooter(String footer) {
		this.footer = footer;
	}

	@Lob
	public String getCol1() {
		return col1;
	}

	public void setCol1(String col1) {
		this.col1 = col1;
	}

	@Lob
	public String getCol2() {
		return col2;
	}

	public void setCol2(String col2) {
		this.col2 = col2;
	}

	@Lob
	public String getCol3() {
		return col3;
	}

	public void setCol3(String col3) {
		this.col3 = col3;
	}

	@OneToOne
	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public boolean isAttivo() {
		return attivo;
	}

	public void setAttivo(boolean attivo) {
		this.attivo = attivo;
	}

	// ------------------------------------------------------------------------

	@Override
	public String toString() {
		return (this.id != null) ? this.id.toString() : super.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof TemplateImpl))
			return false;
		TemplateImpl t = (TemplateImpl) o;
		return t.getId() == null ? false : t.getId().equals(this.id);
	}

	@Override
	public int hashCode() {
		return (this.id != null) ? this.id.hashCode() : super.hashCode();
	}

}
