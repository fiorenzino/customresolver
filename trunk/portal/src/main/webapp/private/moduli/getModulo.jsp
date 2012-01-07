<%@page import="it.coopservice.commons2.utils.JSFUtils"%>

<%@page import="by.giava.moduli.model.Modulo"%>
<%@page import="by.giava.moduli.controller.ModuliController"%>
<%@page import="java.io.DataOutputStream"%>
<%@page import="java.io.DataOutput"%>


<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	try {
		ServletOutputStream outputStream = response.getOutputStream();
		DataOutput dataOutput = new DataOutputStream(outputStream);

		ModuliController mh = (ModuliController) JSFUtils
				.getBean(ModuliController.class);
		Modulo m = null;
		String id = request.getParameter("id");
		if (id != null && id.length() > 0) {
			m = mh.getSession().fetch(id);
			request.getRequestDispatcher(
					"/docs/" + m.getDocumento().getFilename()).forward(
					request, response);
		} else {
			m = mh.getElement();
			request.getRequestDispatcher(
					"/docs/" + m.getDocumento().getFilename()).forward(
					request, response);
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
%>