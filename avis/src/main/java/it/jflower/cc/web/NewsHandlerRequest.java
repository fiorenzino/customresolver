package it.jflower.cc.web;

import it.jflower.base.par.Ricerca;
import it.jflower.base.web.UiRepeatInterface;
import it.jflower.cc.par.Notizia;
import it.jflower.cc.par.type.TipoInformazione;
import it.jflower.cc.session.NotizieSession;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class NewsHandlerRequest implements UiRepeatInterface {

	String filtro;
	int currentpage;
	String id;
	private Notizia notizia;

	@Inject
	NotizieSession notizieSession;
	@Inject
	ParamsHandler paramsHandler;

	public NewsHandlerRequest() {
		System.out.println("NewsHandlerRequest: " + new Date());
	}

	public String getFiltro() {
		return filtro;
	}

	public int getCurrentpage() {
		return currentpage;
	}

	@PostConstruct
	public void init() {
		System.out.println("init: " + new Date());
		this.filtro = paramsHandler.getParam("filtro");
		this.currentpage = 0;
		this.id = paramsHandler.getParam("id");

		if (this.id != null)
			setNotizia(notizieSession.fetch(this.id));
		try {
			currentpage = Integer.parseInt(paramsHandler
					.getParam("currentpage"));
		} catch (Exception e) {

		}
	}

	@SuppressWarnings("unchecked")
	public List loadPage(String tipo, String filtro, int startRow, int pageSize) {
		if ("notizie".equals(tipo)) {
			return ultimeNotizie(filtro, startRow, pageSize);
		}
		return new ArrayList();
	}

	public int totalSize(String tipo, String filtro) {
		if ("notizie".equals(tipo)) {
			return totaleNotizie(filtro);
		}
		return 0;
	}

	// -----------------------------------------------------------------------------------

	private List<Notizia> ultimeNotizie(String filtroNomeTipo, int startRow,
			int pageSize) {
		Ricerca<Notizia> ricerca = new Ricerca<Notizia>(Notizia.class);
		if (filtroNomeTipo != null && filtroNomeTipo.length() > 0) {
			ricerca.getOggetto().setTipo(new TipoInformazione());
			ricerca.getOggetto().getTipo().setNome(filtroNomeTipo);
		}
		return notizieSession.getList(ricerca, startRow, pageSize);
	}

	private int totaleNotizie(String filtroNomeTipo) {
		Ricerca<Notizia> ricerca = new Ricerca<Notizia>(Notizia.class);
		if (filtroNomeTipo != null && filtroNomeTipo.length() > 0) {
			ricerca.getOggetto().setTipo(new TipoInformazione());
			ricerca.getOggetto().getTipo().setNome(filtroNomeTipo);
		}
		return notizieSession.getListSize(ricerca);
	}

	public Notizia getNotizia() {
		if (this.notizia == null)
			this.notizia = notizieSession.findLast();
		return notizia;
	}

	public void setNotizia(Notizia notizia) {
		this.notizia = notizia;
	}

}
