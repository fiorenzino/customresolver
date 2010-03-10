package org.seamframework.resolver;

import java.net.MalformedURLException;
import java.net.URL;

import javax.inject.Inject;

import org.seamframework.tx.PageManager;

import weld.view.PagesHandler;

import com.sun.faces.facelets.impl.DefaultResourceResolver;

public class CustomResourceResolver extends DefaultResourceResolver {

	@Inject
	@PageManager
	PagesHandler pagesHandler;

	URL resourceUrl = null;

	public CustomResourceResolver() {
		System.out.println("creo custom resolver");
	}

	@Override
	public URL resolveUrl(String resource) {
		try {
			System.out.println("TEST PAGEHANDLER: " + pagesHandler.getPage());
		} catch (Exception e) {
			System.out.println("TEST PAGEHANDLER NULLO");
		}

		resourceUrl = super.resolveUrl(resource);
		System.out.println("url: " + resourceUrl);
		if (resourceUrl == null) {
			if (resource.startsWith("/")) {
				resource = resource.substring(1);
				System.out.println("res: " + resource);
				try {
					// Use real factory... hmm...should I? Don't think I do.
					resourceUrl = new URL(null, resource,
							new DBStreamHandlerFactory(pagesHandler)
									.createURLStreamHandler("db"));
				} catch (MalformedURLException e) {
					// TODO Fix exceptionhandling
					e.printStackTrace();
				}
			}
		}
		return resourceUrl;
	}
}
