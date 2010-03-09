package org.seamframework.resolver;

import java.net.*;

import weld.view.PagesHandler;

//TODO Not fully a factory yet, change this 
public class DBStreamHandlerFactory implements URLStreamHandlerFactory {

	PagesHandler pagesHandler;

	public DBStreamHandlerFactory(PagesHandler pagesHandler) {
		this.pagesHandler = pagesHandler;
	}

	public URLStreamHandler createURLStreamHandler(String protocol) {

		if (protocol.equalsIgnoreCase("db")) {
			return new DBProtocolHandler(pagesHandler);
		} else {
			return null;
		}
	}
}
