package it.jflower.cc.web;

import it.jflower.base.par.Ricerca;
import it.jflower.base.web.UiRepeatInterface;
import it.jflower.cc.par.Modulo;
import it.jflower.cc.par.type.TipoModulo;
import it.jflower.cc.session.ModuliSession;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class ModuliHandlerRequest implements UiRepeatInterface {

	String filtro;
	int currentpage;

	@Inject
	ModuliSession moduliSession;
	@Inject
	ParamsHandler paramsHandler;

	public ModuliHandlerRequest() {
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
		this.currentpage = 0;
		try {
			currentpage = Integer.parseInt(paramsHandler
					.getParam("currentpage"));
		} catch (Exception e) {
			// TODO: handle exception
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

	private List<Modulo> ultimeNotizie(String filtroNomeTipo, int startRow,
			int pageSize) {
		Ricerca<Modulo> ricerca = new Ricerca<Modulo>(Modulo.class);
		if (filtroNomeTipo != null && filtroNomeTipo.length() > 0) {
			ricerca.getOggetto().setTipo(new TipoModulo());
			ricerca.getOggetto().getTipo().setNome(filtroNomeTipo);
		}
		return moduliSession.getList(ricerca, startRow, pageSize);
	}

	private int totaleNotizie(String filtroNomeTipo) {
		Ricerca<Modulo> ricerca = new Ricerca<Modulo>(Modulo.class);
		if (filtroNomeTipo != null && filtroNomeTipo.length() > 0) {
			ricerca.getOggetto().setTipo(new TipoModulo());
			ricerca.getOggetto().getTipo().setNome(filtroNomeTipo);
		}
		return moduliSession.getListSize(ricerca);
	}

}
