package it.flowercms.web.model;

import it.flowercms.par.base.Ricerca;

import java.util.List;

public interface DataProcessor<T> {

	public void process(List<T> list, Ricerca<T> ricerca);

}
