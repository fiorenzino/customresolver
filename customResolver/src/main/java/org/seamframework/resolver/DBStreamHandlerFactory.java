package org.seamframework.resolver;

import java.net.*;

//TODO Not fully a factory yet, change this 
public class DBStreamHandlerFactory implements URLStreamHandlerFactory {

	public URLStreamHandler createURLStreamHandler(String protocol) {

		if (protocol.equalsIgnoreCase("db")) {
			return new DBProtocolHandler();
		} else {
			return null;
		}
	}
}
