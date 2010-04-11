package it.flowercms.web.resolver;

import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;

public class DBStreamHandlerFactory implements URLStreamHandlerFactory {

	public URLStreamHandler createURLStreamHandler(String protocol) {

		if (protocol.equalsIgnoreCase("db")) {
			return new DBProtocolHandler();
		} else {
			return null;
		}
	}
}
