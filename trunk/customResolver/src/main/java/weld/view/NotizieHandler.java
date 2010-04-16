package weld.view;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import weld.model.Notizia;

@Named
@SessionScoped
public class NotizieHandler implements Serializable {

	private Notizia notizia;

	public String addNotizia() {
		this.notizia = new Notizia();
		return "";
	}

	public String saveNotizia() {

		return "";
	}

	public String modNotizia(Long id) {

		return "";
	}

	public String updateNotizia() {

		return "";
	}

	public String detailNotizia(Long id) {

		return "";
	}

	public Notizia getNotizia() {
		return notizia;
	}

	public void setNotizia(Notizia notizia) {
		this.notizia = notizia;
	}
}
