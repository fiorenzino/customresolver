package by.giava.base.common.web.datamodel;


import java.io.Serializable;
import java.util.List;

import org.primefaces.model.LazyDataModel;

import by.giava.base.common.ejb.SuperSession;
import by.giava.base.model.Ricerca;

/**
 * To make this class work, use p:dataTable as follows:
 * 
 * 
 * <p:dataTable rowIndexVar="rowIndex" var="r" value="#{handler.model}"
 * rows="#{handler.pageSize}" paginator="true" dynamic="true" lazy="true">
 * 
 * @author pisi79
 * 
 * @param <T>
 */
public class LocalLazyDataModel<T> extends LazyDataModel<T> implements
		Serializable {

	private static final long serialVersionUID = 1L;

	private Ricerca<T> ricerca;
	private SuperSession<T> session;

	public LocalLazyDataModel(Ricerca<T> r, SuperSession<T> s) {
		super(s.getListSize(r));
		this.ricerca = r;
		this.session = s;
	}

	@Override
	public List<T> fetchLazyData(int first, int pageSize) {
		return session.getList(ricerca, first, pageSize);
	}

}
