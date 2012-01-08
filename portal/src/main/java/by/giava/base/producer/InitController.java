package by.giava.base.producer;

import java.io.Serializable;

import javax.annotation.Resource;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletRequest;
import javax.sql.DataSource;

import by.giava.base.common.annotation.HttpParam;

@Named
public class InitController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Resource(mappedName = "java:jboss/datasources/colonnella")
	DataSource ds;

	@SuppressWarnings("unused")
	@Produces
	@PersistenceContext
	private EntityManager em;

	public DataSource getDs() {
		return ds;
	}

	public void setDs(DataSource ds) {
		this.ds = ds;
	}

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

	@Produces
	@HttpParam("")
	String getParamValue(InjectionPoint ip) {
		ServletRequest request = (ServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		return request.getParameter(ip.getAnnotated()
				.getAnnotation(HttpParam.class).value());

	}

}
