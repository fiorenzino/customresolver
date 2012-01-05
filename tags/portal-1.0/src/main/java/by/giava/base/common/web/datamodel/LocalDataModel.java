package by.giava.base.common.web.datamodel;


import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import by.giava.base.common.ejb.SuperSession;
import by.giava.base.model.Ricerca;

public class LocalDataModel<T> extends PagedListDataModel<T> {

	protected Ricerca<T> ricerca;
	protected SuperSession<T> ejb;
	protected List<DataProcessor<T>> processors = new ArrayList<DataProcessor<T>>();
	
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

	protected DataPage<T> getDataPage(int startRow, int pageSize)
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
