package by.giava.base.controller.request;

import it.coopservice.commons2.domain.Search;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import by.giava.base.common.web.controller.UiRepeatInterface;
import by.giava.news.repository.NotizieRepository;
import by.giava.notizie.model.Notizia;
import by.giava.notizie.model.type.TipoInformazione;

@Named
@RequestScoped
public class UiRepeatWithParamsHandler implements UiRepeatInterface {

	String filtro;
	int currentpage;

	@Inject
	NotizieRepository notizieRepository;
	@Inject
	ParamsHandler paramsHandler;

	public UiRepeatWithParamsHandler() {
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

	@SuppressWarnings("rawtypes")
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
		Notizia notizia = new Notizia();
		Search<Notizia> ricerca = new Search<Notizia>(notizia);
		if (filtroNomeTipo != null && filtroNomeTipo.length() > 0) {
			ricerca.getObj().setTipo(new TipoInformazione());
			ricerca.getObj().getTipo().setNome(filtroNomeTipo);
		}
		return notizieRepository.getList(ricerca, startRow, pageSize);
	}

	private int totaleNotizie(String filtroNomeTipo) {
		Notizia notizia = new Notizia();
		Search<Notizia> ricerca = new Search<Notizia>(notizia);
		if (filtroNomeTipo != null && filtroNomeTipo.length() > 0) {
			ricerca.getObj().setTipo(new TipoInformazione());
			ricerca.getObj().getTipo().setNome(filtroNomeTipo);
		}
		return notizieRepository.getListSize(ricerca);
	}

}
