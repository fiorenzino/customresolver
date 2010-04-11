package it.flowercms.web.utils;

public class PagesUtils {

	public static String createPageId(String title) {
		title = title.replaceAll("[^a-zA-Z0-9\\s]", "")
				.replaceAll("[\\s]", "-");
		return title.toLowerCase();
	}
}
