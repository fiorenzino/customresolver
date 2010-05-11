package it.jflower.base.web.model;

import it.jflower.base.par.Ricerca;
import it.jflower.base.session.SuperSession;

import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

public class LocalDataModel<T> extends PagedListDataModel<T> {

	private Ricerca<T> ricerca;
	private SuperSession<T> ejb;
	private List<DataProcessor<T>> processors = new ArrayList<DataProcessor<T>>();

	public LocalDataModel(int pageSize, Ricerca<T> ricerca, SuperSession<T> ejb) {
		super(pageSize);
		this.ricerca = ricerca;
		this.ejb = ejb;
	}

	public DataPage<T> fetchPage(int startRow, int pageSize) {
		try {
			return getDataPage(startRow, pageSize);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return null;
	}

	private DataPage<T> getDataPage(int startRow, int pageSize)
			throws NamingException {
		List<T> data = ejb.getList(ricerca, startRow, pageSize);
		// --- aggiunta per permettere elaborazioni personalizzate sul dm prima di mostrarlo nella view ----
		for( DataProcessor<T> processor : processors ) {
			processor.process(data, ricerca);
		}
		// --- fine ---------------------------------------------------------
		DataPage<T> dataPage = new DataPage<T>(ejb.getListSize(ricerca),
				startRow, data);
		return dataPage;
	}
	
	public void addProcessor(DataProcessor<T> processor) {
		if ( processor != null )
			this.processors.add(processor);
	}
}
