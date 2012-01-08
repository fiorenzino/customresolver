package by.giava.gallerie.controller;

import it.coopservice.commons2.annotations.BackPage;
import it.coopservice.commons2.annotations.EditPage;
import it.coopservice.commons2.annotations.ListPage;
import it.coopservice.commons2.annotations.OwnRepository;
import it.coopservice.commons2.annotations.ViewPage;
import it.coopservice.commons2.controllers.AbstractLazyController;
import it.coopservice.commons2.utils.JSFUtils;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import by.giava.base.controller.OperazioniLogController;
import by.giava.base.controller.ResourceController;
import by.giava.base.model.OperazioniLog;
import by.giava.base.model.attachment.Immagine;
import by.giava.gallerie.model.Galleria;
import by.giava.gallerie.repository.GallerieRepository;

@Named
@SessionScoped
public class GallerieController extends AbstractLazyController<Galleria> {

	private static final long serialVersionUID = 1L;

	// --------------------------------------------------------
	@BackPage
	public static String BACK = "/private/amministrazione.xhtml";
	@ViewPage
	public static String VIEW = "/private/gallerie/scheda.xhtml";
	@ListPage
	public static String LIST = "/private/gallerie/lista.xhtml";
	@EditPage
	public static String NEW_OR_EDIT = "/private/gallerie/gestione.xhtml";

	// ------------------------------------------------

	@Inject
	@OwnRepository(GallerieRepository.class)
	GallerieRepository gallerieRepository;

	@Inject
	ResourceController resourceController;

	@Inject
	OperazioniLogController operazioniLogController;

	@Override
	public String addElement() {
		resourceController.setImgModel(null);
		return addElement();
	}

	public void addFoto() {
		getElement().addImmagine(new Immagine());
	}

	@Override
	public String save() {
		List<Immagine> immagini = new ArrayList<Immagine>();
		for (Immagine immagine : getElement().getImmagini()) {
			if (immagine.getUploadedData() != null)
				immagini.add(immagine);
		}
		getElement().setImmagini(immagini);

		super.save();
		operazioniLogController.save(OperazioniLog.NEW, JSFUtils.getUserName(),
				"creazione galleria: " + getElement().getTitolo());
		return viewPage();
	}

	@Override
	public String delete() {
		operazioniLogController.save(OperazioniLog.DELETE,
				JSFUtils.getUserName(), "eliminazione galleria: "
						+ getElement().getTitolo());
		return super.delete();
	}

	@Override
	public String update() {
		operazioniLogController.save(OperazioniLog.MODIFY,
				JSFUtils.getUserName(), "eliminazione galleria: "
						+ getElement().getTitolo());
		return super.update();
	}

}
