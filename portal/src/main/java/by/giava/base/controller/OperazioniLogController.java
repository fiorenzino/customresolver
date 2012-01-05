package by.giava.base.controller;

import it.coopservice.commons2.annotations.BackPage;
import it.coopservice.commons2.annotations.ListPage;
import it.coopservice.commons2.annotations.OwnRepository;
import it.coopservice.commons2.annotations.ViewPage;
import it.coopservice.commons2.controllers.AbstractLazyController;

import java.util.Date;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import by.giava.base.model.OperazioniLog;
import by.giava.base.repository.OperazioniLogSession;

@Named
@SessionScoped
public class OperazioniLogController extends
		AbstractLazyController<OperazioniLog> {

	// --------------------------------------------------------
	private static final long serialVersionUID = 1L;

	@BackPage
	public static final String BACK = "/private/amministrazione.xhtml";

	@ViewPage
	public static final String VIEW = "/private/operazioni/scheda-operazione.xhtml";

	@ListPage
	public static final String LIST = "/private/operazioni/lista-operazioni.xhtml";

	// --------------------------------------------------------

	@Inject
	@OwnRepository(OperazioniLogSession.class)
	OperazioniLogSession operazioniLogSession;

	// --------------------------------------------------------

	public OperazioniLogController() {
	}

	// --------------------------------------------------------

	public void save(String tipo, String username, String descrizione) {
		operazioniLogSession.persist(new OperazioniLog(tipo, username,
				descrizione, new Date()));
		reset();
	}

}
