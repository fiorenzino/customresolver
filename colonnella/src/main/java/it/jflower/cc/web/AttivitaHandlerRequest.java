package it.jflower.cc.web;

import it.jflower.base.par.Ricerca;
import it.jflower.base.web.UiRepeatInterface;
import it.jflower.cc.par.Attivita;
import it.jflower.cc.par.type.CategoriaAttivita;
import it.jflower.cc.session.AttivitaSession;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class AttivitaHandlerRequest implements UiRepeatInterface {

	String filtro;
	int currentpage;
	String id;
	private Attivita attivita;

	@Inject
	AttivitaSession attivitaSession;

	@Inject
	ParamsHandler paramsHandler;

	public AttivitaHandlerRequest() {
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
		this.id = paramsHandler.getParam("id");
		if (this.id != null)
			this.attivita = attivitaSession.find(this.id);
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

	private List<Attivita> ultimeNotizie(String filtroNomeCategoria,
			int startRow, int pageSize) {
		Ricerca<Attivita> ricerca = new Ricerca<Attivita>(Attivita.class);
		if (filtroNomeCategoria != null && filtroNomeCategoria.length() > 0) {
			ricerca.getOggetto().setCategoria(new CategoriaAttivita());
			ricerca.getOggetto().getCategoria().setCategoria(
					filtroNomeCategoria);
		}
		return attivitaSession.getList(ricerca, startRow, pageSize);
	}

	private int totaleNotizie(String filtroNomeCategoria) {
		Ricerca<Attivita> ricerca = new Ricerca<Attivita>(Attivita.class);
		if (filtroNomeCategoria != null && filtroNomeCategoria.length() > 0) {
			ricerca.getOggetto().setCategoria(new CategoriaAttivita());
			ricerca.getOggetto().getCategoria().setCategoria(
					filtroNomeCategoria);
		}
		return attivitaSession.getListSize(ricerca);
	}

	public Attivita getAttivita() {
		if (this.attivita == null)
			this.attivita = attivitaSession.findLast();
		return attivita;
	}

	public void setAttivita(Attivita attivita) {
		this.attivita = attivita;
	}

}
