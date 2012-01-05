
<%
	String redirectURL = getServletContext().getContextPath()
			+ "/private/tipi-informazione/lista-tipi-informazione.jsf";
	response.sendRedirect(redirectURL);
%>