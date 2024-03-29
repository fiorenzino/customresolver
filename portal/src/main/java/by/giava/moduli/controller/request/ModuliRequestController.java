package by.giava.moduli.controller.request;

import it.coopservice.commons2.annotations.OwnRepository;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import by.giava.base.common.annotation.HttpParam;
import by.giava.base.common.web.controller.AbstractRequestController;
import by.giava.base.controller.request.ParamsHandler;
import by.giava.moduli.model.Modulo;
import by.giava.moduli.model.type.TipoModulo;
import by.giava.moduli.repository.ModuliRepository;
import by.giava.moduli.repository.TipoModuloRepository;

@Named
@RequestScoped
public class ModuliRequestController extends AbstractRequestController<Modulo> {

	private static final long serialVersionUID = 1L;

	@HttpParam("filtro")
	String filtro;

	@HttpParam("tipo")
	String tipo;

	@HttpParam("currentpage")
	String current;

	@HttpParam("id")
	String idParam;

	@Inject
	@OwnRepository(ModuliRepository.class)
	ModuliRepository moduliRepository;

	@Inject
	ParamsHandler paramsHandler;

	@Inject
	TipoModuloRepository tipoModuloRepository;

	public ModuliRequestController() {
	}

	@Override
	public void defaultCriteria() {
		if (idParam != null && !idParam.isEmpty()) {
			Modulo modulo = moduliRepository.find(idParam);
			setElement(modulo);
		}
		if (tipo != null && tipo.length() > 0) {
			getSearch().getObj().getTipo().setNome(tipo);
		}
		if (filtro != null && filtro.length() > 0) {
			getSearch().getObj().setNome(filtro);
		}
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
		for (TipoModulo tipo : tipoModuloRepository.getAllList()) {
			options.add("<option value=\""
					+ tipo.getNome()
					+ "\" "
					+ (tipo.getNome().equals(this.tipo) ? "selected=\"true\""
							: "") + ">" + tipo.getNome() + "</option>");
		}
		return options;
	}

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getCurrent() {
		return current;
	}

	public void setCurrent(String current) {
		this.current = current;
	}

	public String getIdParam() {
		return idParam;
	}

	public void setIdParam(String idParam) {
		this.idParam = idParam;
	}

}
