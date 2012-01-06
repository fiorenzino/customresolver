package by.giava.news.controller;

import it.coopservice.commons2.annotations.BackPage;
import it.coopservice.commons2.annotations.EditPage;
import it.coopservice.commons2.annotations.ListPage;
import it.coopservice.commons2.annotations.OwnRepository;
import it.coopservice.commons2.annotations.ViewPage;
import it.coopservice.commons2.controllers.AbstractLazyController;
import it.coopservice.commons2.utils.JSFUtils;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import by.giava.base.controller.OperazioniLogController;
import by.giava.base.model.OperazioniLog;
import by.giava.news.repository.NotizieRepository;
import by.giava.notizie.model.Notizia;

@Named
@SessionScoped
public class NotizieController extends AbstractLazyController<Notizia> {

	private static final long serialVersionUID = 1L;

	// --------------------------------------------------------
	@BackPage
	public static String BACK = "/private/amministrazione.xhtml";
	@ViewPage
	public static String VIEW = "/private/notizie/scheda-notizia.xhtml";
	@ListPage
	public static String LIST = "/private/notizie/lista-notizie.xhtml";
	@EditPage
	public static String NEW_OR_EDIT = "/private/notizie/gestione-notizia.xhtml";

	// --------------------------------------------------------

	@Inject
	@OwnRepository(NotizieRepository.class)
	NotizieRepository notizieRepository;

	@Inject
	OperazioniLogController operazioniLogController;

	// --------------------------------------------------------

	public NotizieController() {
	}

	// --------------------------------------------------------
	@Override
	public String save() {

		super.save();
		operazioniLogController.save(OperazioniLog.NEW, JSFUtils.getUserName(),
				"creazione notizia: " + getElement().getTitolo());
		return VIEW;
	}

	@Override
	public String delete() {
		super.delete();
		operazioniLogController.save(OperazioniLog.DELETE,
				JSFUtils.getUserName(), "eliminazione menu: "
						+ getElement().getTitolo());
		return LIST;
	}

	@Override
	public String update() {
		super.update();
		operazioniLogController.save(OperazioniLog.MODIFY,
				JSFUtils.getUserName(), "modifica notizia: "
						+ getElement().getTitolo());
		return VIEW;
	}

	// // -----------------------------------------------------------
	//
	// private LocalDataModel<Notizia> latestNewsModel;
	// private String latestTipo;
	// private Integer latestPageSize;
	//
	// public LocalDataModel<Notizia> ultimeNotizie(String tipo, int pageSize) {
	// if (latestNewsModel == null || this.latestTipo == null
	// || this.latestPageSize == null || !this.latestTipo.equals(tipo)
	// || this.latestPageSize != pageSize) {
	// Ricerca<Notizia> ricerca = new Ricerca<Notizia>(Notizia.class);
	// ricerca.getOggetto().setTipo(new TipoInformazione());
	// ricerca.getOggetto().getTipo().setNome(tipo);
	// latestNewsModel = new LocalDataModel<Notizia>(pageSize, ricerca,
	// notizieSession);
	// }
	// return latestNewsModel;
	// }
	//
	// // -----------------------------------------------------------

}
