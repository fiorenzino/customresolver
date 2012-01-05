
<%
	String redirectURL = getServletContext().getContextPath()
			+ "/private/modelli/lista-modelli.jsf";
	response.sendRedirect(redirectURL);
%>