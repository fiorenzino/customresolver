
<%
	String redirectURL = getServletContext().getContextPath()
			+ "/private/risorse/lista-risorse.jsf";
	response.sendRedirect(redirectURL);
%>