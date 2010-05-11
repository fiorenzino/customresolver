
<%
	String redirectURL = getServletContext().getContextPath()
			+ "/private/pagine/lista-pagine.jsf";
	response.sendRedirect(redirectURL);
%>