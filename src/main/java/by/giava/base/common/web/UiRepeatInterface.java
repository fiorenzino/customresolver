package by.giava.base.common.web;

import java.util.List;

public interface UiRepeatInterface {

	public int totalSize(String tipo, String filtro);

	@SuppressWarnings("rawtypes")
	public List loadPage(String tipo, String filtro, int startRow, int pageSize);
}
