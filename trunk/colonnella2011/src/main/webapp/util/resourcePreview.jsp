<%@ page language="java"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page
	import="it.jflower.base.utils.JSFUtils,it.jflower.cc.web.ResourceHandler,org.apache.log4j.Logger,java.io.DataOutputStream,java.io.DataOutput,it.jflower.cc.par.Resource"%>


<%
	Logger log = Logger.getLogger(getClass().getName());
	ResourceHandler resourceHandler = JSFUtils
			.getBean(ResourceHandler.class);
	if (request.getParameter("row") != null) {
		String row = request.getParameter("row");
		try {
			Resource image = resourceHandler.getSingleResource(Integer
					.parseInt(row));

			ServletOutputStream outputStream = response
					.getOutputStream();
			DataOutput dataOutput = new DataOutputStream(outputStream);

			byte[] imageData = image.getBytes();
			String fileName = image.getNome();

			String imageType = fileName.substring(
					fileName.lastIndexOf(".")).toLowerCase();

			if (imageType.equals("png"))
				response.setContentType("image/png");
			if (imageType.equals("gif"))
				response.setContentType("image/gif");
			if (imageType.equals("jpg"))
				response.setContentType("image/jpg");

			response.setContentLength(imageData.length);

			for (int i = 0; i < imageData.length; i++) {
				dataOutput.writeByte(imageData[i]);
			}

			out.clear();
			out = pageContext.pushBody();

		} catch (Exception e) {
			log.info(e.getMessage());
			e.printStackTrace();
		}
	}
%>