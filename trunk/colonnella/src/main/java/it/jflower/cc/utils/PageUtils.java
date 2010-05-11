package it.jflower.cc.utils;

import it.jflower.cc.par.Page;
import it.jflower.cc.par.Template;
import it.jflower.cc.par.TemplateImpl;

public class PageUtils {

	private static final String prologo = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">";
	
	public static String createPageId(String title) {
		title = title.replaceAll("[^a-zA-Z0-9\\s]", "")
				.replaceAll("[\\s]", "-");
		return title.toLowerCase();
	}
	
	@Deprecated
	public static Page generateContent_old(Page page) {
		StringBuffer contentBuffer = new StringBuffer(prologo);
		if ((page.getTemplate().getHeader() != null)
				&& (!"".equals(page.getTemplate().getHeader()))) {
			contentBuffer.append(page.getTemplate().getTemplate()
					.getHeader_start());
			contentBuffer.append(page.getTemplate().getHeader());
			contentBuffer.append(page.getTemplate().getTemplate()
					.getHeader_stop());

		}
		if ((page.getTemplate().getCol1() != null)
				&& (!"".equals(page.getTemplate().getCol1()))) {
			contentBuffer.append(page.getTemplate().getTemplate()
					.getCol1_start());
			contentBuffer.append(page.getTemplate().getCol1());
			contentBuffer.append(page.getTemplate().getTemplate()
					.getCol1_stop());
		}

		if ((page.getTemplate().getCol2() != null)
				&& (!"".equals(page.getTemplate().getCol2()))) {
			contentBuffer.append(page.getTemplate().getTemplate()
					.getCol2_start());
			contentBuffer.append(page.getTemplate().getCol2());
			contentBuffer.append(page.getTemplate().getTemplate()
					.getCol2_stop());
		}

		if ((page.getTemplate().getCol3() != null)
				&& (!"".equals(page.getTemplate().getCol3()))) {
			contentBuffer.append(page.getTemplate().getTemplate()
					.getCol3_start());
			contentBuffer.append(page.getTemplate().getCol3());
			contentBuffer.append(page.getTemplate().getTemplate()
					.getCol3_stop());
		}
		if ((page.getTemplate().getFooter() != null)
				&& (!"".equals(page.getTemplate().getFooter()))) {
			contentBuffer.append(page.getTemplate().getTemplate()
					.getFooter_start());
			contentBuffer.append(page.getTemplate().getFooter());
			contentBuffer.append(page.getTemplate().getTemplate()
					.getFooter_stop());
		}
		page.setContent(contentBuffer.toString());
		return page;
	}

	/**
	 * La pagina fornisce pezzi solo per i template che prevedono start e stop di una sezione
	 * Le parti del template sono comunque tutte aggiunte
	 * 
	 * @param page
	 * @return
	 */
	public static Page generateContent(Page page) {

		Template t = page.getTemplate().getTemplate();
		TemplateImpl i = page.getTemplate();

		StringBuffer b = new StringBuffer( t.getHeader_start() == null ? prologo : t.getHeader_start() );

		if ( t.getHeader_stop() != null && t.getHeader_stop().length() > 0 ) {
			b.append( i.getHeader() );
			b.append(t.getHeader_stop() );
		}

		b.append( t.getCol1_start() == null ? "" : t.getCol1_start() );
		if ( t.getCol1_stop() != null && t.getCol1_stop().length() > 0 ) {
			b.append( i.getCol1() );
			b.append(t.getCol1_stop() );
		}

		b.append( t.getCol2_start() == null ? "" : t.getCol2_start() );
		if ( t.getCol2_stop() != null && t.getCol2_stop().length() > 0 ) {
			b.append( i.getCol2() );
			b.append(t.getCol2_stop() );
		}

		b.append( t.getCol3_start() == null ? "" : t.getCol3_start() );
		if ( t.getCol3_stop() != null && t.getCol3_stop().length() > 0 ) {
			b.append( i.getCol3() );
			b.append(t.getCol3_stop() );
		}

		b.append( t.getFooter_start() == null ? "" : t.getFooter_start() );
		if ( t.getFooter_stop() != null && t.getFooter_stop().length() > 0 ) {
			b.append( i.getFooter() );
			b.append(t.getFooter_stop() );
		}

		page.setContent(b.toString());
		return page;
	}
	
}
