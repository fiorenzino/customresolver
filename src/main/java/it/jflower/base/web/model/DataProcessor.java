package it.jflower.base.web.model;

import it.jflower.base.par.Ricerca;

import java.util.List;

public interface DataProcessor<T> {

	public void process(List<T> list, Ricerca<T> ricerca);

}
