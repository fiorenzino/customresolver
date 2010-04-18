package it.flowercms.par;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class MenuItem implements Serializable {

	private static final long serialVersionUID = 1L;

	// ------------------------------------------------------------------------

	private Long id;
	boolean attivo = true;

	private String nome;

	private Page pagina;
	private MenuGroup gruppo;
	
	// ------------------------------------------------------------------------

	public MenuItem(){
	}
	public MenuItem(Page pagina, MenuGroup gruppo){
		this.pagina = pagina;
		this.nome = pagina.getId();
		this.gruppo = gruppo;
	}

	// ------------------------------------------------------------------------

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

	@ManyToOne
	public Page getPagina() {
		return pagina;
	}

	public void setPagina(Page pagina) {
		this.pagina = pagina;
	}

	@ManyToOne
	public MenuGroup getGruppo() {
		return gruppo;
	}

	public void setGruppo(MenuGroup gruppo) {
		this.gruppo = gruppo;
	}

	public boolean isAttivo() {
		return attivo;
	}

	public void setAttivo(boolean attivo) {
		this.attivo = attivo;
	}

}
