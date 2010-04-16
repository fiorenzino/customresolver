package weld.view;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import weld.model.Pubblicazione;

@Named
@SessionScoped
public class PubblicazioniHandler implements Serializable {

	private Pubblicazione pubblicazione;

	public String addPubblicazione() {
		this.pubblicazione = new Pubblicazione();
		return "";
	}

	public String savePubblicazione() {

		return "";
	}

	public String modPubblicazione(Long id) {

		return "";
	}

	public String updatePubblicazione() {

		return "";
	}

	public String detailPubblicazione(Long id) {

		return "";
	}
}
