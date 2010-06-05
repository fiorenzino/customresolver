package it.jflower.cc.web;

import it.jflower.base.par.Ricerca;
import it.jflower.base.web.UiRepeatInterface;
import it.jflower.cc.par.Pubblicazione;
import it.jflower.cc.par.type.TipoPubblicazione;
import it.jflower.cc.session.PubblicazioniSession;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class PubblicazioniHandlerRequest implements UiRepeatInterface {

	String filtro;
	int currentpage;
	String id;

	@Inject
	PubblicazioniSession pubblicazioniSession;

	@Inject
	ParamsHandler paramsHandler;

	private Pubblicazione pubblicazione;

	public PubblicazioniHandlerRequest() {
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
			this.pubblicazione = pubblicazioniSession.find(this.id);
		try {
			currentpage = Integer.parseInt(paramsHandler
					.getParam("currentpage"));
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@SuppressWarnings("unchecked")
	public List loadPage(String tipo, String filtro, int startRow, int pageSize) {
		// if ("notizie".equals(tipo)) {
		return ultimePubblicazioni(filtro, startRow, pageSize);
		// }
		// return new ArrayList();
	}

	public int totalSize(String tipo, String filtro) {
		if ("notizie".equals(tipo)) {
			return totalePubblicazioni(filtro);
		}
		return 0;
	}

	// -----------------------------------------------------------------------------------

	private List<Pubblicazione> ultimePubblicazioni(String filtroNomeTipo,
			int startRow, int pageSize) {
		Ricerca<Pubblicazione> ricerca = new Ricerca<Pubblicazione>(
				Pubblicazione.class);
		if (filtroNomeTipo != null && filtroNomeTipo.length() > 0) {
			ricerca.getOggetto().setTipo(new TipoPubblicazione());
			ricerca.getOggetto().getTipo().setNome(filtroNomeTipo);
		}
		return pubblicazioniSession.getList(ricerca, startRow, pageSize);
	}

	private int totalePubblicazioni(String filtroNomeTipo) {
		Ricerca<Pubblicazione> ricerca = new Ricerca<Pubblicazione>(
				Pubblicazione.class);
		if (filtroNomeTipo != null && filtroNomeTipo.length() > 0) {
			ricerca.getOggetto().setTipo(new TipoPubblicazione());
			ricerca.getOggetto().getTipo().setNome(filtroNomeTipo);
		}
		return pubblicazioniSession.getListSize(ricerca);
	}

	public Pubblicazione getPubblicazione() {
		return pubblicazione;
	}

	public void setPubblicazione(Pubblicazione pubblicazione) {
		this.pubblicazione = pubblicazione;
	}

}
