<%@page import="it.jflower.cc.web.ResourceHandler"%><%@ page
	language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String img = "img";
%>
<jsp:forward page="/risorse/img/${resourceHandler.element.id}"></jsp:forward>
