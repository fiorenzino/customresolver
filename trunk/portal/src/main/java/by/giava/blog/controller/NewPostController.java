package by.giava.blog.controller;

import it.coopservice.commons2.annotations.BackPage;
import it.coopservice.commons2.annotations.EditPage;
import it.coopservice.commons2.annotations.ListPage;
import it.coopservice.commons2.annotations.OwnRepository;
import it.coopservice.commons2.annotations.ViewPage;
import it.coopservice.commons2.controllers.AbstractLazyController;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import by.giava.blog.model.NewPost;
import by.giava.blog.repository.NewPostRepository;

@Named
@SessionScoped
public class NewPostController extends AbstractLazyController<NewPost> {

	private static final long serialVersionUID = 1L;

	// --------------------------------------------------------
	@BackPage
	public static String BACK = "/private/amministrazione.xhtml";
	@ViewPage
	public static String VIEW = "/private/blog/commenti/scheda.xhtml";
	@ListPage
	public static String LIST = "/private/blog/commenti/lista.xhtml";
	@EditPage
	public static String NEW_OR_EDIT = "/private/blog/commenti/gestione.xhtml";

	// ------------------------------------------------

	@Inject
	@OwnRepository(NewPostRepository.class)
	NewPostRepository newPostRepository;

}
