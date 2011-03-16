package it.jflower.cc.web;

import it.jflower.base.par.Ricerca;
import it.jflower.base.web.UiRepeatInterface;
import it.jflower.cc.par.Notizia;
import it.jflower.cc.par.type.TipoInformazione;
import it.jflower.cc.session.NotizieSession;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class NewsHandlerRequest implements UiRepeatInterface {

	String filtro;
	String tipo;
	int currentpage;
	String id;
	private Notizia notizia;

	@Inject
	NotizieSession notizieSession;
	@Inject
	ParamsHandler paramsHandler;

	public NewsHandlerRequest() {
	}

	public String getFiltro() {
		return filtro;
	}

	public int getCurrentpage() {
		return currentpage;
	}

	@PostConstruct
	public void init() {
		this.filtro = paramsHandler.getParam("filtro");
		this.tipo = paramsHandler.getParam("tipo");
		this.currentpage = 0;
		this.id = paramsHandler.getParam("id");
		if (this.id != null)
			this.notizia = notizieSession.find(this.id);
		try {
			currentpage = Integer.parseInt(paramsHandler
					.getParam("currentpage"));
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@SuppressWarnings("rawtypes")
	public List loadPage(String tipo, String filtro, int startRow, int pageSize) {
		return ultimeNotizie(tipo, filtro, startRow, pageSize);
	}

	public int totalSize(String tipo, String filtro) {
		return totaleNotizie(tipo, filtro);
	}

	// -----------------------------------------------------------------------------------

	private List<Notizia> ultimeNotizie(String tipo, String filtro,
			int startRow, int pageSize) {
		Ricerca<Notizia> ricerca = new Ricerca<Notizia>(Notizia.class);
		if (tipo != null && tipo.length() > 0) {
			ricerca.getOggetto().setTipo(new TipoInformazione());
			ricerca.getOggetto().getTipo().setNome(tipo);
		}
		if (filtro != null && filtro.length() > 0) {
			ricerca.getOggetto().setTitolo(filtro);
		}
		return notizieSession.getList(ricerca, startRow, pageSize);
	}

	private int totaleNotizie(String tipo, String filtro) {
		Ricerca<Notizia> ricerca = new Ricerca<Notizia>(Notizia.class);
		if (tipo != null && tipo.length() > 0) {
			ricerca.getOggetto().setTipo(new TipoInformazione());
			ricerca.getOggetto().getTipo().setNome(tipo);
		}
		if (filtro != null && filtro.length() > 0) {
			ricerca.getOggetto().setTitolo(filtro);
			ricerca.getOggetto().setContenuto(filtro);
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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
