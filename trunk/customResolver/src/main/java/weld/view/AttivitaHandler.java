package weld.view;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import weld.model.Attivita;

@Named
@SessionScoped
public class AttivitaHandler implements Serializable {

	private Attivita attivita;

	public String addAttivita() {
		this.attivita = new Attivita();
		return "";
	}

	public String saveAttivita() {

		return "";
	}

	public String modAttivita(Long id) {

		return "";
	}

	public String updateAttivita() {

		return "";
	}

	public String detailAttivita(Long id) {

		return "";
	}

	public Attivita getAttivita() {
		return attivita;
	}

	public void setAttivita(Attivita attivita) {
		this.attivita = attivita;
	}
}
