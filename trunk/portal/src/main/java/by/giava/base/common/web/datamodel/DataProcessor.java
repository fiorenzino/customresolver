package by.giava.base.common.web.datamodel;


import java.util.List;

import by.giava.base.model.Ricerca;

public interface DataProcessor<T> {

	public void process(List<T> list, Ricerca<T> ricerca);

}
