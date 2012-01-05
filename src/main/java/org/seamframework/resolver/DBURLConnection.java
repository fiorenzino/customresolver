package org.seamframework.resolver;

import it.jflower.base.utils.JSFUtils;
import it.jflower.cc.par.Page;
import it.jflower.cc.session.PageSession;
import it.jflower.cc.utils.PageUtils;
import it.jflower.cc.web.DbPageHandler;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.io.IOUtils;
import org.jboss.logging.Logger;

public class DBURLConnection extends URLConnection {

	private String form;
	Logger logger = Logger.getLogger(getClass());
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

			this.form = url.getFile().substring(url.getFile().indexOf("/") + 1,
					url.getFile().lastIndexOf("?"));
			logger.debug("form: " + this.form);
			try {
				this.currentPage = JSFUtils.getBean(PageSession.class).fetch(
						this.form);
				JSFUtils.getBean(DbPageHandler.class).setPage(currentPage);
				PageUtils.generateContent(this.currentPage);
				this.content = this.currentPage.getContent();

			} catch (Exception e) {
//				e.printStackTrace();
				logger.error(e.getMessage());
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
