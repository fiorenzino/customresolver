<%@page import="by.giava.base.common.util.JSFUtils"%>
<%@page import="by.giava.pubblicazioni.model.XlsDoc"%>
<%@page import="by.giava.pubblicazioni.controller.StampaPubblicazioniHandler"%>
<%@page import="java.io.DataOutputStream"%>
<%@page import="java.io.DataOutput"%>
<%@page import="java.io.OutputStream"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%
	try {
		StampaPubblicazioniHandler pubblicazioniHandler = JSFUtils
				.getBean(StampaPubblicazioniHandler.class);

		ServletOutputStream outputStream = response.getOutputStream();
		DataOutput dataOutput = new DataOutputStream(outputStream);
		XlsDoc documento = pubblicazioniHandler.export();
		byte[] data = documento.getData();
		//("byte[]: " + data.length);
		String fileName = documento.getNome();

		response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
		response.setHeader("Pragma", "no-cache"); //HTTP 1.0
		response.setDateHeader("Expires", 0); //prevents caching at the proxy server

		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition",
				"attachment; filename=\"" + documento.getNome() + "\"");

		response.setContentLength(data.length);

		for (int i = 0; i < data.length; i++) {
			dataOutput.writeByte(data[i]);
		}

		out.clear();
		out = pageContext.pushBody();

	} catch (Exception e) {
		e.printStackTrace();
	}
%>