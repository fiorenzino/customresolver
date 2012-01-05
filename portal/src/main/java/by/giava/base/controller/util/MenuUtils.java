package by.giava.base.controller.util;

import by.giava.base.common.util.JSFUtils;



public class MenuUtils {

	public static java.lang.Boolean showMenu(String open, String nomeMenu) {
		if (open != null && open.length() > 0)
			return Boolean.parseBoolean(open);
		else
			return JSFUtils.urlContains(nomeMenu);
	}

}
