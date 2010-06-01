package it.jflower.base.web.renderer;

import it.jflower.cc.web.UiRepeatWithParamsHandler;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;
import javax.faces.render.Renderer;

import com.sun.faces.facelets.component.UIRepeat;

@FacesRenderer(componentFamily = "javax.faces.Command", rendererType = "uiRepeatPagerWithParams")
public class UIRepeatPagerWithParams extends Renderer {

	public void encodeBegin(FacesContext context, UIComponent component)
			throws IOException {

		ResponseWriter writer = context.getResponseWriter();

		String styleClass = (String) component.getAttributes()
				.get("styleClass");
		String selectedStyleClass = (String) component.getAttributes().get(
				"selectedStyleClass");
		String uiRepeatId = (String) component.getAttributes()
				.get("uiRepeatId");
		String tipo = (String) component.getAttributes()
				.get("tipo");
		String filtro = (String) component.getAttributes()
				.get("filtro");
		if ( "null".equals(filtro) ) {
			System.out.println("filtro null");
			filtro = null;
		}
		else if ( filtro != null && filtro.endsWith("null") ) {
			System.out.println("filtro ends with null");
			filtro = null;
		}
		System.out.println(" filtro = " + filtro);
		int pagesize = toInt(component.getAttributes().get("pagesize"));
		int showpages = toInt(component.getAttributes().get("showpages"));
		int currentpage = toInt(component.getAttributes().get("currentpage"));
		UiRepeatWithParamsHandler handler = (UiRepeatWithParamsHandler) component.getAttributes()
				.get("handler");

		// find the component with the given ID

		int first = currentpage*pagesize;
		int itemcount = handler.totalSize(tipo,filtro);
		// int pagesize = model.getPageSize();
		if (pagesize <= 0)
			pagesize = itemcount;

		int pages = itemcount / pagesize;
		if (itemcount % pagesize != 0)
			pages++;

		int startPage = 0;
		int endPage = pages;
		if (showpages > 0) {
			startPage = (currentpage / showpages) * showpages;
			endPage = Math.min(startPage + showpages, pages);
		}

		UIRepeat data = (UIRepeat) component.findComponent(uiRepeatId);
		// if (data.getValue() == null) {
		data.setValue(handler.loadPage(tipo, filtro, first, pagesize));
		// }

		writeLinks(writer, component, styleClass,
				selectedStyleClass, currentpage, first, startPage, endPage,
				pages, itemcount, pagesize,tipo, filtro);
	}

	private void writeLinks(ResponseWriter writer, UIComponent component,
			String styleClass,
			String selectedStyleClass, int currentPage, int first,
			int startPage, int endPage, int pages, int itemcount, int pagesize, String tipo, String filtro)
			throws IOException {
		
		String[] paramPageFirst = new String[] { "currentpage", "0" };
		String[] paramPageLast = new String[] { "currentpage", ""+endPage };
		String[] paramPagePrevious = new String[] { "currentpage", ""+(currentPage-1) };
		String[] paramPageNext = new String[] { "currentpage", ""+(currentPage+1) };
		String[] paramTipo = new String[] { "tipo", tipo };
		String[] paramFiltro = new String[] { "filtro", filtro };
		
		if (startPage > 0) {
			writeLink(writer, component, "<<", styleClass, new String[][] { paramPageFirst, paramTipo, paramFiltro } );
		}

		if (currentPage > 0)
			writeLink(writer, component, "<", styleClass, new String[][] { paramPagePrevious, paramTipo, paramFiltro } );

		for (int i = startPage; i < endPage; i++) {
			writeLink(writer, component, "" + (i + 1),
					i == currentPage ? selectedStyleClass : styleClass, new String[][] { new String[] {"currentpage",""+i}, paramTipo, paramFiltro } );
		}

		if (first < itemcount - pagesize)
			writeLink(writer, component, ">", styleClass, new String[][] { paramPageNext, paramTipo, paramFiltro } );

		if (endPage < pages)
			writeLink(writer, component, ">>", styleClass, new String[][] { paramPageLast, paramTipo, paramFiltro } );

	}

	private void writeLink(ResponseWriter writer, UIComponent component,
			String value, String styleClass,
			String[][] params) throws IOException {
		writer.writeText(" ", null);
		writer.startElement("a", component);
		writer.writeAttribute("href", makeHref("", params), null);
		if (styleClass != null)
			writer.writeAttribute("class", styleClass, "styleClass");
		writer.writeText(value, null);
		writer.endElement("a");
	}

	private String makeHref(String base, String[][] params) {
		StringBuffer href = new StringBuffer(base);
		for (int i = 0; i < params.length; i++) {
			href.append(i == 0 ? "?" : "&");
			href.append(params[i][0]).append("=").append(params[i][1]);
		}
		return href.toString();
	}

	private static int toInt(Object value) {
		if (value == null)
			return 0;
		if (value instanceof Number)
			return ((Number) value).intValue();
		if (value instanceof String)
			return Integer.parseInt((String) value);
		throw new IllegalArgumentException("Cannot convert " + value);
	}

//	public void _decode(FacesContext context, UIComponent component) {
//		String id = component.getClientId(context);
//		Map<String, String> parameters = context.getExternalContext()
//				.getRequestParameterMap();
//
//		String response = (String) parameters.get(id);
//		if (response == null || response.equals(""))
//			return;
//
//		String uiRepeatId = (String) component.getAttributes()
//				.get("uiRepeatId");
//		int showpages = toInt(component.getAttributes().get("showpages"));
//		LocalDataModel model = (LocalDataModel) component.getAttributes().get(
//				"model");
//
//		int first = model.getCurrentPage() * model.getPageSize();
//		int itemcount = model.getRowCount();
//		int pagesize = model.getPageSize();
//		if (pagesize <= 0)
//			pagesize = itemcount;
//
//		if (response.equals("<"))
//			first -= pagesize;
//		else if (response.equals(">"))
//			first += pagesize;
//		else if (response.equals("<<"))
//			first -= pagesize * showpages;
//		else if (response.equals(">>"))
//			first += pagesize * showpages;
//		else {
//			int page = Integer.parseInt(response);
//			first = (page - 1) * pagesize;
//		}
//		if (first + pagesize > itemcount)
//			first = itemcount - pagesize;
//		if (first < 0)
//			first = 0;
//
//		UIRepeat data = (UIRepeat) component.findComponent(uiRepeatId);
//		// data.setValue(model.fetchPage(first, pagesize).getData());
//
//		model.setCurrentPage(first / pagesize);
//	}


}