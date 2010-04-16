package weld.view;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import weld.model.Notizia;

@Named
@SessionScoped
public class NotizieHandler implements Serializable {

	private Notizia notizia;

	public Notizia getNotizia() {
		return notizia;
	}

	public void setNotizia(Notizia notizia) {
		this.notizia = notizia;
	}
}
