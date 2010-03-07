package org.seamframework.resolver;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.io.IOUtils;

import weld.model.Page;
import weld.view.PagesHandler;

@Named
@RequestScoped
public class DBURLConnection extends URLConnection {

	@Inject
	PagesHandler pagesHandler;

	private String form;
	private String content = null;
	private Page currentPage;

	public DBURLConnection(URL u) {
		super(u);
	}

	@Override
	public synchronized InputStream getInputStream() throws IOException {

		if (!connected)
			connect();
		if (content != null) {
			return IOUtils.toInputStream(content);
		} else {
			return null;
		}

	}

	public String getContentType() {
		return "text/html";
	}

	public synchronized void connect() throws IOException {
		if (!connected) {

			this.form = url.getFile().substring(url.getFile().indexOf("/") + 1);
			// System.out.println("pd: " + pd);
			System.out.println("form: " + this.form);
			// this.currentPage = pagesHandler.findPage(this.form);
			// this.content = this.currentPage.getContent();
			content = "<html><body>ciao</body></html>";

			this.connected = true;
		}
	}

	@Override
	public long getExpiration() {
		return -1l;
	}

	@Override
	public long getLastModified() {
		return -1l;
	}
}
