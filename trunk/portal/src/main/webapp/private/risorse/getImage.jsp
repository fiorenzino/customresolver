<%@page import="by.giava.base.common.util.JSFUtils"%>
<%@page import="by.giava.base.controller.ResourceHandler"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	ResourceHandler resourceHandler = JSFUtils
			.getBean(ResourceHandler.class);
	String img = "img";
%>
<jsp:forward page="/img/${resourceHandler.element.id}"></jsp:forward>
