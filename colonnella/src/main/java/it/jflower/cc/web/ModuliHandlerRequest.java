package it.jflower.cc.web;

import it.jflower.base.par.Ricerca;
import it.jflower.base.web.UiRepeatInterface;
import it.jflower.cc.par.Modulo;
import it.jflower.cc.par.type.TipoModulo;
import it.jflower.cc.session.ModuliSession;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class ModuliHandlerRequest implements UiRepeatInterface {

	String filtro;
	String tipo;
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
		this.tipo = paramsHandler.getParam("tipo");
		this.currentpage = 0;
		try {
			currentpage = Integer.parseInt(paramsHandler
					.getParam("currentpage"));
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@SuppressWarnings("rawtypes")
	public List loadPage(String tipo, String filtro, int startRow, int pageSize) {
		return ultimiModuli(tipo, filtro, startRow, pageSize);
	}

	public int totalSize(String tipo, String filtro) {
		return totaleModuli(tipo, filtro);
	}

	// -----------------------------------------------------------------------------------

	private List<Modulo> ultimiModuli(String tipo, String filtro, int startRow,
			int pageSize) {
		Ricerca<Modulo> ricerca = new Ricerca<Modulo>(Modulo.class);
		if (tipo != null && tipo.length() > 0) {
			ricerca.getOggetto().setTipo(new TipoModulo());
			ricerca.getOggetto().getTipo().setNome(tipo);
		}
		if (filtro != null && filtro.length() > 0) {
			ricerca.getOggetto().setNome(filtro);
		}
		return moduliSession.getList(ricerca, startRow, pageSize);
	}

	private int totaleModuli(String tipo, String filtro) {
		Ricerca<Modulo> ricerca = new Ricerca<Modulo>(Modulo.class);
		if (tipo != null && tipo.length() > 0) {
			ricerca.getOggetto().setTipo(new TipoModulo());
			ricerca.getOggetto().getTipo().setNome(tipo);
		}
		if (filtro != null && filtro.length() > 0) {
			ricerca.getOggetto().setNome(filtro);
		}
		return moduliSession.getListSize(ricerca);
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
