package org.seamframework.resolver;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

import weld.view.PagesHandler;

public class DBProtocolHandler extends URLStreamHandler {

	PagesHandler pagesHandler;

	public DBProtocolHandler(PagesHandler pagesHandler) {
		this.pagesHandler = pagesHandler;
	}

	@Override
	protected URLConnection openConnection(URL u) throws IOException {
		return new DBURLConnection(u, pagesHandler);
	}
}
