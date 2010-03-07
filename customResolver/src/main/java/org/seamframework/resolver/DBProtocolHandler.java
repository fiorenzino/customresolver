package org.seamframework.resolver;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

public class DBProtocolHandler extends URLStreamHandler {

  @Override
  protected URLConnection openConnection(URL u) throws IOException {
	return new DBURLConnection(u);
  }
}
