package it.test;

import it.jflower.cc.utils.PageUtils;

public class StringTest {
	public static void main(String[] args) {
		String pageName = "che dio salvi la regina!";

		pageName = pageName.replaceAll("[^a-zA-Z0-9\\s]", "");
		pageName = pageName.replaceAll("[\\s]", "-");
		System.out.println(pageName);

		String pageName2 = "Wow! che Culo";

		// pageName2 = pageName2.replaceAll("[^a-zA-Z0-9\\s]", "");
		// pageName2 = pageName2.replaceAll("[\\s]", "-");
		// System.out.println(pageName2);
		// String result = PageUtils.createPageId(pageName2);
		System.out.println(result);
	}
}
