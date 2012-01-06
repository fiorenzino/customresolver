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
	public static String VIEW = "/private/gallerie/scheda-galleria.xhtml";
	@ListPage
	public static String LIST = "/private/gallerie/lista-gallerie.xhtml";
	@EditPage
	public static String NEW_OR_EDIT = "/private/gallerie/gestione-galleria.xhtml";

	// ------------------------------------------------

	@Inject
	@OwnRepository(GallerieRepository.class)
	GallerieRepository gallerieRepository;

	@Inject
	ResourceController resourceController;

	@Inject
	OperazioniLogController operazioniLogController;
	private List<Galleria> all;

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
		this.all = null;
		operazioniLogController.save(OperazioniLog.NEW, JSFUtils.getUserName(),
				"creazione galleria: " + getElement().getTitolo());
		return VIEW;
	}

	@Override
	public String delete() {
		this.all = null;
		operazioniLogController.save(OperazioniLog.DELETE,
				JSFUtils.getUserName(), "eliminazione galleria: "
						+ getElement().getTitolo());
		return super.delete();
	}

	public String update() {
		this.all = null;
		operazioniLogController.save(OperazioniLog.MODIFY,
				JSFUtils.getUserName(), "eliminazione galleria: "
						+ getElement().getTitolo());
		return super.update();
	}

	public List<Galleria> getAll() {
		if (this.all == null)
			this.all = gallerieRepository.getAllList();
		return all;
	}

	public void setAll(List<Galleria> all) {
		this.all = all;
	}

}
