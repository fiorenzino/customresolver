package by.giava.base.common.web.controller;

import java.util.List;

public interface UiRepeatInterface<T> {

	public int totalSize(String tipo, String filtro);

	@SuppressWarnings("rawtypes")
	public List<T> loadPage(String tipo, String filtro, int startRow,
			int pageSize);
}
