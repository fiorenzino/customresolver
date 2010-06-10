package it.jflower.base.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import org.apache.commons.lang.StringEscapeUtils;
import org.w3c.tidy.Tidy;

public class HtmlUtils {
	public static String normalizeHtml(String code) {
		Tidy tidy = new Tidy();
		tidy.setXHTML(true);
		tidy.setTidyMark(false);
		tidy.setDocType("omit");
		tidy.setPrintBodyOnly(true);
		tidy.setInputEncoding("UTF-8");
		InputStream is;
		String content = "";
		OutputStream arg1 = new ByteArrayOutputStream();
		try {
			is = new ByteArrayInputStream(code.getBytes("UTF-8"));

			tidy.parse(is, arg1);
			// System.out.println("*****************PRIMA: ");
			// System.out.println(arg1.toString());
			// System.out.println("*******************DOPO: ");
			StringEscapeUtils escape = new StringEscapeUtils();
			// escape.unescapeHtml(arg0)
			content = escape.unescapeHtml(arg1.toString());
//			System.out.println(content);

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return content;
	}
}
