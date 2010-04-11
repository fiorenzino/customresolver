package it.flowercms.web.utils;

import it.flowercms.par.Page;

public class TemplateUtils {

	public static Page generateContent(Page page) {
		StringBuffer contentBuffer = new StringBuffer();
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
}
