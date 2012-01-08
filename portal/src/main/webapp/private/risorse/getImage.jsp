<%@page import="it.coopservice.commons2.utils.JSFUtils"%>
<%@page import="by.giava.base.controller.ResourceController"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	ResourceController resourceHandler = JSFUtils
			.getBean(ResourceController.class);
	String img = "img";
%>
<jsp:forward page="/img/${resourceHandler.element.id}"></jsp:forward>
