package it.flowercms.web.resolver;

import it.flowercms.par.Page;
import it.flowercms.session.PageSession;
import it.flowercms.web.utils.JSFUtils;
import it.flowercms.web.utils.PageUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.io.IOUtils;

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

			this.form = url.getFile().substring(url.getFile().indexOf("/") + 1);
			System.out.println("form: " + this.form);
			try {
				this.currentPage = JSFUtils.getBean(PageSession.class).find(this.form);
				PageUtils.generateContent(this.currentPage);
				this.content = this.currentPage.getContent();
				System.out.println(this.content);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(" DBUTL EXC 1");
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
