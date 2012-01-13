package by.giava.catalogo.controller;

import it.coopservice.commons2.annotations.BackPage;
import it.coopservice.commons2.annotations.EditPage;
import it.coopservice.commons2.annotations.ListPage;
import it.coopservice.commons2.annotations.OwnRepository;
import it.coopservice.commons2.annotations.ViewPage;
import it.coopservice.commons2.controllers.AbstractLazyController;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import by.giava.catalogo.model.Realizzazione;
import by.giava.catalogo.repository.RealizzazioniRepository;

@Named
@SessionScoped
public class RealizzazioniController extends
		AbstractLazyController<Realizzazione> {

	private static final long serialVersionUID = 1L;

	// --------------------------------------------------------
	@BackPage
	public static String BACK = "/private/amministrazione.xhtml";
	@ViewPage
	public static String VIEW = "/private/catalogo/realizzazioni/scheda.xhtml";
	@ListPage
	public static String LIST = "/private/catalogo/realizzazioni/lista.xhtml";
	@EditPage
	public static String NEW_OR_EDIT = "/private/catalogo/realizzazioni/gestione.xhtml";

	// ------------------------------------------------

	@Inject
	@OwnRepository(RealizzazioniRepository.class)
	RealizzazioniRepository realizzazioniRepository;

}
