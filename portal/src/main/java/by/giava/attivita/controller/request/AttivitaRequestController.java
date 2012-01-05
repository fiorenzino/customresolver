package by.giava.attivita.controller.request;

import it.coopservice.commons2.annotations.OwnRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import by.giava.attivita.model.Attivita;
import by.giava.attivita.repository.AttivitaRepository;
import by.giava.base.common.annotation.HttpParam;
import by.giava.base.common.web.controller.AbstractRequestController;
import by.giava.base.controller.request.ParamsHandler;

@Named
@RequestScoped
public class AttivitaRequestController extends
		AbstractRequestController<Attivita> {

	private static final long serialVersionUID = 1L;

	@HttpParam("filtro")
	String filtro;

	@HttpParam("tipo")
	String tipo;

	@HttpParam("currentpage")
	String currentpage;

	@HttpParam("id")
	String idParam;

	@Inject
	@OwnRepository(AttivitaRepository.class)
	AttivitaRepository attivitaRepository;

	@Inject
	ParamsHandler paramsHandler;

	public AttivitaRequestController() {
	}

	@Override
	public void defaultCriteria() {
		if (idParam != null && !idParam.isEmpty()) {
			Attivita attivita = attivitaRepository.find(idParam);
			setElement(attivita);
		}
		if (tipo != null && tipo.length() > 0) {
			getSearch().getObj().getCategoria().setCategoria(tipo);
		}
		if (filtro != null && filtro.length() > 0) {
			getSearch().getObj().setNome(filtro);
		}
	}

}
