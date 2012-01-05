<%@page import="it.jflower.base.utils.JSFUtils"%><%@page import="it.jflower.cc.par.Modulo"%><%@page import="java.io.DataOutputStream"%><%@page import="java.io.DataOutput"%><%@page import="it.jflower.cc.web.ModuliHandler"%><%@page import="it.jflower.cc.web.ResourceHandler"%><%@ page
	language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%
try {
	ServletOutputStream outputStream = response.getOutputStream();
	DataOutput dataOutput = new DataOutputStream(outputStream);

	ModuliHandler mh = (ModuliHandler) JSFUtils.getBean(ModuliHandler.class);
	Modulo m = null;
	String id = request.getParameter("id");
	if ( id != null && id.length() > 0 ) {
		m = mh.getSession().fetch(id);
		request.getRequestDispatcher("/docs/"+m.getDocumento().getFilename()).forward(request,response);
	}
	else {
		m = mh.getElement();
		request.getRequestDispatcher("/docs/"+m.getDocumento().getFilename()).forward(request,response);
	}
} catch (Exception e) {
	e.printStackTrace();
}
%>