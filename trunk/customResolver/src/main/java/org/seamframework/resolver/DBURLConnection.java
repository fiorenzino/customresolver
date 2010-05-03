package org.seamframework.resolver;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.io.IOUtils;

import weld.model.Page;
import weld.session.PagesSession;
import weld.view.utils.JSFUtils;

public class DBURLConnection extends URLConnection {

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
		if (this.content != null) {
			return IOUtils.toInputStream(this.content);
		} else {
			return null;
		}

	}

	public String getContentType() {
		return "text/html";
	}

	public synchronized void connect() throws IOException {
		if (!connected) {
			this.form = url.getFile().substring(
					url.getFile().lastIndexOf("/") + 1);
			// System.out.println("form: " + this.form);
			try {
				this.currentPage = JSFUtils.getHandler(new PagesSession())
						.find(this.form);
				if (this.currentPage != null)
					this.content = this.currentPage.getContent();
			} catch (Exception e) {
				e.printStackTrace();
				// System.out.println(" DBUTL EXC 1");
			}
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
