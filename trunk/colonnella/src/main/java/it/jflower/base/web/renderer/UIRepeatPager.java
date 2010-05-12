package it.jflower.base.web.renderer;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.component.UIForm;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.model.ListDataModel;
import javax.faces.render.FacesRenderer;
import javax.faces.render.Renderer;

import com.sun.faces.facelets.component.UIRepeat;

@FacesRenderer(componentFamily = "javax.faces.Command", rendererType = "com.corejsf.Pager")
public class UIRepeatPager extends Renderer {
	@SuppressWarnings("unchecked")
	public void encodeBegin(FacesContext context, UIComponent component)
			throws IOException {
		String id = component.getClientId(context);
		UIComponent parent = component;
		while (!(parent instanceof UIForm))
			parent = parent.getParent();
		String formId = parent.getClientId(context);

		ResponseWriter writer = context.getResponseWriter();

		String styleClass = (String) component.getAttributes()
				.get("styleClass");
		String selectedStyleClass = (String) component.getAttributes().get(
				"selectedStyleClass");
		String per = (String) component.getAttributes().get("for");
		int showpages = toInt(component.getAttributes().get("showpages"));

		// find the component with the given ID

		com.sun.faces.facelets.component.UIRepeat data = (com.sun.faces.facelets.component.UIRepeat) component
				.findComponent(per);

		int first = data.getBegin();
		int itemcount = data.getSize();
		int pagesize = data.getStep();
		if (pagesize <= 0)
			pagesize = itemcount;

		if (pagesize <= 0)
			pagesize = itemcount;

		int pages = itemcount / pagesize;
		if (itemcount % pagesize != 0)
			pages++;

		int currentPage = first / pagesize;
		if (first >= itemcount - pagesize)
			currentPage = pages - 1;
		int startPage = 0;
		int endPage = pages;
		if (showpages > 0) {
			startPage = (currentPage / showpages) * showpages;
			endPage = Math.min(startPage + showpages, pages);
		}
		if (currentPage > 0)
			writeLink(writer, component, formId, id, "<", styleClass);

		if (startPage > 0)
			writeLink(writer, component, formId, id, "<<", styleClass);

		for (int i = startPage; i < endPage; i++) {
			writeLink(writer, component, formId, id, "" + (i + 1),
					i == currentPage ? selectedStyleClass : styleClass);
		}

		if (endPage < pages)
			writeLink(writer, component, formId, id, ">>", styleClass);

		if (first < itemcount - pagesize)
			writeLink(writer, component, formId, id, ">", styleClass);

		// hidden field to hold result
		writeHiddenField(writer, component, id);
	}

	private void writeLink(ResponseWriter writer, UIComponent component,
			String formId, String id, String value, String styleClass)
			throws IOException {
		writer.writeText(" ", null);
		writer.startElement("a", component);
		writer.writeAttribute("href", "#", null);
		writer.writeAttribute("onclick", onclickCode(formId, id, value), null);
		if (styleClass != null)
			writer.writeAttribute("class", styleClass, "styleClass");
		writer.writeText(value, null);
		writer.endElement("a");
	}

	private String onclickCode(String formId, String id, String value) {
		return new StringBuilder().append("document.forms['").append(formId)
				.append("']['").append(id).append("'].value='").append(value)
				.append("'; document.forms['").append(formId).append(
						"'].submit(); return false;").toString();
	}

	private void writeHiddenField(ResponseWriter writer, UIComponent component,
			String id) throws IOException {
		writer.startElement("input", component);
		writer.writeAttribute("type", "hidden", null);
		writer.writeAttribute("name", id, null);
		writer.endElement("input");
	}

	@SuppressWarnings("unchecked")
	public void decode(FacesContext context, UIComponent component) {
		String id = component.getClientId(context);
		Map<String, String> parameters = context.getExternalContext()
				.getRequestParameterMap();

		String response = (String) parameters.get(id);
		if (response == null || response.equals(""))
			return;

		String per = (String) component.getAttributes().get(
			"for");
		int showpages = toInt(component.getAttributes().get("showpages"));

		 UIRepeat data = (UIRepeat) component.findComponent(per);

		  int first = data.getBegin();
		 int itemcount = data.getSize();
		 int pagesize = data.getStep();
		
		if (pagesize <= 0)
			pagesize = itemcount;

		if (response.equals("<"))
			first -= pagesize;
		else if (response.equals(">"))
			first += pagesize;
		else if (response.equals("<<"))
			first -= pagesize * showpages;
		else if (response.equals(">>"))
			first += pagesize * showpages;
		else {
			int page = Integer.parseInt(response);
			first = (page - 1) * pagesize;
		}
		if (first + pagesize > itemcount)
			first = itemcount - pagesize;
		if (first < 0)
			first = 0;
		
		data.setBegin(first);
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
}