package it.jflower.base.web;

import java.util.List;

public interface UiRepeatInterface {

	public int totalSize(String tipo, String filtro);

	public List loadPage(String tipo, String filtro, int startRow, int pageSize);
}
