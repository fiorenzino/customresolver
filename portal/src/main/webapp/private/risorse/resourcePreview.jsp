<%@page import="by.giava.base.model.Resource"%>
<%@page import="by.giava.base.controller.ResourceHandler"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	int row = Integer.parseInt(request.getParameter("row"));
	Resource resource = ((ResourceHandler) session
			.getAttribute("resourceHandler")).getResource(row);
	byte[] bytes = resource.getBytes();
	response.getOutputStream().write(bytes);
%>