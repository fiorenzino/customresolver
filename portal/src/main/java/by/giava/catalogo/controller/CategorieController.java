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

import by.giava.catalogo.model.Categoria;
import by.giava.catalogo.repository.CategorieRepository;

@Named
@SessionScoped
public class CategorieController extends AbstractLazyController<Categoria> {

	private static final long serialVersionUID = 1L;

	// --------------------------------------------------------
	@BackPage
	public static String BACK = "/private/amministrazione.xhtml";
	@ViewPage
	public static String VIEW = "/private/catalogo/categorie/scheda.xhtml";
	@ListPage
	public static String LIST = "/private/catalogo/categorie/lista.xhtml";
	@EditPage
	public static String NEW_OR_EDIT = "/private/catalogo/categorie/gestione.xhtml";

	// ------------------------------------------------

	@Inject
	@OwnRepository(CategorieRepository.class)
	CategorieRepository categoriaRepository;

}
