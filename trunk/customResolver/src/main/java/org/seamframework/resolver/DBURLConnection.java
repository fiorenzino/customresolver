package org.seamframework.resolver;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.io.IOUtils;

import weld.model.Page;
import weld.view.PagesHandler;
import weld.view.utils.DbUtils;
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

			this.form = url.getFile().substring(url.getFile().indexOf("/") + 1);
			// System.out.println("pd: " + pd);
			System.out.println("form: " + this.form);
			try {
				this.currentPage = JSFUtils.getPageHandler()
						.findPage(this.form);
				// this.currentPage = DbUtils.getPage(this.form);
				// System.out.println("PH1: " + this.currentPage.getTitle());
				this.content = this.currentPage.getContent();
				System.out.println(this.content);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(" DBUTL EXC 1");
			}
			// try {
			// PagesHandler ph = (PagesHandler) JSFUtils
			// .getManagedBean("pageHandler");
			// this.currentPage = ph.findPage(this.form);
			// System.out.println("PH2: " + this.currentPage.getTitle());
			// } catch (Exception e) {
			// // e.printStackTrace();
			// System.out.println(" DBUTL EXC 2");
			// this.content = "<ui:composition "
			// + " xmlns=\"http://www.w3.org/1999/xhtml\" "
			// + " xmlns:ui=\"http://java.sun.com/jsf/facelets\" "
			// + " xmlns:c=\"http://java.sun.com/jstl/core\" "
			// + " xmlns:f=\"http://java.sun.com/jsf/core\" "
			// + " xmlns:h=\"http://java.sun.com/jsf/html\"> "
			// + " <h:outputText value=\"#{bookFactory.text2}\" />"
			// + " </ui:composition>";
			// }

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
