package by.giava.pubblicazioni.controller.request;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;

import by.giava.attivita.repository.CategorieSession;
import by.giava.base.common.web.controller.UiRepeatInterface;
import by.giava.base.controller.request.ParamsHandler;
import by.giava.base.model.Ricerca;
import by.giava.pubblicazioni.model.Pubblicazione;
import by.giava.pubblicazioni.model.type.TipoPubblicazione;
import by.giava.pubblicazioni.repository.PubblicazioniSession;


@Named
@RequestScoped
public class PubblicazioniHandlerRequest implements UiRepeatInterface {

	String filtro;
	String tipo;
	int currentpage;
	String id;
	private Pubblicazione pubblicazione;

	Logger logger = Logger.getLogger(getClass());

	@Inject
	PubblicazioniSession pubblicazioniSession;

	@Inject
	ParamsHandler paramsHandler;

	@Inject
	CategorieSession categorieSession;

	public PubblicazioniHandlerRequest() {
	}

	@PostConstruct
	public void init() {
		this.filtro = paramsHandler.getParam("filtro");
		this.tipo = paramsHandler.getParam("tipo");
		this.currentpage = 0;
		this.id = paramsHandler.getParam("id");
		if (this.id != null && !"".equals(this.id)) {
			this.pubblicazione = pubblicazioniSession.find(this.id);
			if ( this.pubblicazione.getTipo().getNome() != null && this.pubblicazione.getTipo().getNome().toUpperCase().contains("MATRIMONIO") && !this.pubblicazione.isValido()) {
				Pubblicazione scaduta = new Pubblicazione();
				scaduta.setTipo(this.pubblicazione.getTipo());
				scaduta.setTitolo("Pubblicazione non consultabile pubblicamente");
				scaduta.setDescrizione("La presente pubblicazioine non e' consultabile pubblicamente in quanto scaduti i termini di visualizzazione. Si consiglia di rivolgersi presso gli uffici comunali per poterla visionare.");
				scaduta.setAutore("Comune di Colonnella");
				scaduta.setProgressivoRegistro(this.pubblicazione.getProgressivoRegistro());
				scaduta.setDal(this.pubblicazione.getDal());
				scaduta.setAl(this.pubblicazione.getAl());
				scaduta.setData(this.pubblicazione.getData());
				this.pubblicazione = scaduta;
			}
		}
		try {
			currentpage = Integer.parseInt(paramsHandler
					.getParam("currentpage"));
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@SuppressWarnings("rawtypes")
	public List loadPage(String tipo, String filtro, int startRow, int pageSize) {
		Ricerca<Pubblicazione> ricerca = new Ricerca<Pubblicazione>(
				Pubblicazione.class);
		ricerca.getOggetto().setValidoIl(new Date());
		if (tipo != null && tipo.length() > 0) {
			ricerca.getOggetto().setTipo(new TipoPubblicazione());
			ricerca.getOggetto().getTipo().setNome(tipo);
		}
		if (filtro != null && filtro.length() > 0) {
			ricerca.getOggetto().setNome(filtro);
		}
		return pubblicazioniSession.getList(ricerca, startRow, pageSize);
	}

	public int totalSize(String tipo, String filtro) {
		return totalePubblicazioni(tipo, filtro);

	}

	// -----------------------------------------------------------------------------------

	public List<Pubblicazione> ultimePubblicazioni(String tipo, String filtro,
			int startRow, int pageSize) {
		Ricerca<Pubblicazione> ricerca = new Ricerca<Pubblicazione>(
				Pubblicazione.class);
		ricerca.getOggetto().setValidoIl(new Date());
		ricerca.getOggetto().setAllegati(false);
		if (tipo != null && tipo.length() > 0) {
			ricerca.getOggetto().setTipo(new TipoPubblicazione());
			ricerca.getOggetto().getTipo().setNome(tipo);
		}
		if (filtro != null && filtro.length() > 0) {
			ricerca.getOggetto().setNome(filtro);
		}
		return pubblicazioniSession.getList(ricerca, startRow, pageSize);
	}

	private int totalePubblicazioni(String tipo, String filtro) {
		Ricerca<Pubblicazione> ricerca = new Ricerca<Pubblicazione>(
				Pubblicazione.class);
		ricerca.getOggetto().setValidoIl(new Date());
		if (tipo != null && tipo.length() > 0) {
			ricerca.getOggetto().setTipo(new TipoPubblicazione());
			ricerca.getOggetto().getTipo().setNome(tipo);
		}
		if (filtro != null && filtro.length() > 0) {
			ricerca.getOggetto().setNome(filtro);
		}
		return pubblicazioniSession.getListSize(ricerca);
	}

	public Pubblicazione getPubblicazione() {
		if (this.pubblicazione == null)
			this.pubblicazione = pubblicazioniSession.findLast();
		return pubblicazione;
	}

	public void setPubblicazione(Pubblicazione pubblicazione) {
		this.pubblicazione = pubblicazione;
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

	/**
	 * faccio questo perché per qualche strano motivo dentro lo ui:repeat questo
	 * codice si perde il valore di tipo dopo il primo accesso...
	 * 
	 * <ui:repeat value="#{categorieSession.allTipoPubblicazione}" var="tipo">
	 * <f:verbatim rendered="#{tipo.nome == pubblicazioniHandlerRequest.tipo}">
	 * <option value="#{tipo.nome}" selected="true">#{tipo.nome}</option>
	 * </f:verbatim> <f:verbatim
	 * rendered="#{not (tipo.nome == pubblicazioniHandlerRequest.tipo)}">
	 * <option value="#{tipo.nome}">#{tipo.nome}</option> </f:verbatim>
	 * </ui:repeat>
	 * 
	 * @return
	 */
	public List<String> getTipoOptions() {
		List<String> options = new ArrayList<String>();
		for (TipoPubblicazione tipo : categorieSession
				.getAllTipoPubblicazione()) {
			options.add("<option value=\""
					+ tipo.getNome()
					+ "\" "
					+ (tipo.getNome().equals(this.tipo) ? "selected=\"true\""
							: "") + ">" + tipo.getNome() + "</option>");
		}
		return options;
	}

}
