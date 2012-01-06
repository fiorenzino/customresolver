package by.giava.news.controller.request;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import by.giava.base.common.annotation.HttpParam;
import by.giava.base.common.web.controller.AbstractRequestController;
import by.giava.base.controller.request.ParamsHandler;
import by.giava.base.model.attachment.Immagine;
import by.giava.news.repository.NewNotizieRepository;
import by.giava.notizie.model.Notizia;

@Named
@RequestScoped
public class NewsRequestController extends AbstractRequestController<Notizia> {

	private static final long serialVersionUID = 1L;

	@HttpParam("filtro")
	String filtro;

	@HttpParam("tipo")
	String tipo;

	@HttpParam("currentpage")
	String currentpage;

	@HttpParam("id")
	String idParam;

	private Notizia evidenzaNotizia;
	private Immagine evidenzaImmagine;

	@Inject
	NewNotizieRepository newNotizieRepository;

	@Inject
	ParamsHandler paramsHandler;

	public NewsRequestController() {
	}

	@Override
	public void defaultCriteria() {
		if (idParam != null && !idParam.isEmpty()) {
			Notizia notiziaF = newNotizieRepository.find(idParam);
			setElement(notiziaF);
		} else {

			Notizia notiziaF = newNotizieRepository.findLast();
			setElement(notiziaF);
		}

		if (tipo != null && tipo.length() > 0) {
			getSearch().getObj().getTipo().setNome(tipo);
		}
		if (filtro != null && filtro.length() > 0) {
			getSearch().getObj().setTitolo(filtro);
		}
	}

	// -----------------------------------------------------------------------------------

	public Notizia getInEvidenza() {
		if (evidenzaNotizia == null) {
			evidenzaNotizia = newNotizieRepository.findInEvidenza();
		}
		return evidenzaNotizia;
	}

	public Immagine getInEvidenzaImmagine() {
		if (evidenzaImmagine == null) {
			evidenzaImmagine = newNotizieRepository.findInEvidenzaImmagine();
		}
		return evidenzaImmagine;
	}

}
