package by.giava.attivita.controller.request;


import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import by.giava.attivita.model.Attivita;
import by.giava.attivita.model.type.CategoriaAttivita;
import by.giava.attivita.repository.AttivitaSession;
import by.giava.base.common.web.UiRepeatInterface;
import by.giava.base.controller.request.ParamsHandler;
import by.giava.base.model.Ricerca;

@Named
@RequestScoped
public class AttivitaHandlerRequest implements UiRepeatInterface {

	String filtro;
	String tipo;
	int currentpage;
	String id;
	private Attivita attivita;

	@Inject
	AttivitaSession attivitaSession;

	@Inject
	ParamsHandler paramsHandler;

	public AttivitaHandlerRequest() {
	}

	@PostConstruct
	public void init() {
		this.filtro = paramsHandler.getParam("filtro");
		this.tipo = paramsHandler.getParam("tipo");
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

	@SuppressWarnings("rawtypes")
	public List loadPage(String tipo, String filtro, int startRow, int pageSize) {
		return ultimeAttivita(tipo, filtro, startRow, pageSize);
	}

	public int totalSize(String tipo, String filtro) {
		return totaleAttivita(tipo, filtro);
	}

	// -----------------------------------------------------------------------------------

	private List<Attivita> ultimeAttivita(String tipo, String filtro,
			int startRow, int pageSize) {
		Ricerca<Attivita> ricerca = new Ricerca<Attivita>(Attivita.class);
		if (tipo != null && tipo.length() > 0) {
			ricerca.getOggetto().setCategoria(new CategoriaAttivita());
			ricerca.getOggetto().getCategoria().setCategoria(tipo);
		}
		if (filtro != null && filtro.length() > 0) {
			ricerca.getOggetto().setNome(filtro);
		}
		return attivitaSession.getList(ricerca, startRow, pageSize);
	}

	private int totaleAttivita(String tipo, String filtro) {
		Ricerca<Attivita> ricerca = new Ricerca<Attivita>(Attivita.class);
		if (tipo != null && tipo.length() > 0) {
			ricerca.getOggetto().setCategoria(new CategoriaAttivita());
			ricerca.getOggetto().getCategoria().setCategoria(tipo);
		}
		if (filtro != null && filtro.length() > 0) {
			ricerca.getOggetto().setNome(filtro);
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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getFiltro() {
		return filtro;
	}

	public int getCurrentpage() {
		return currentpage;
	}

}
