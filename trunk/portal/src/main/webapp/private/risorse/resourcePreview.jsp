<%@page import="by.giava.base.model.Resource"%>
<%@page import="by.giava.base.controller.ResourceController"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	int row = Integer.parseInt(request.getParameter("row"));
	Resource resource = ((ResourceController) session
			.getAttribute("ResourceController")).getResource(row);
	byte[] bytes = resource.getBytes();
	response.getOutputStream().write(bytes);
%>