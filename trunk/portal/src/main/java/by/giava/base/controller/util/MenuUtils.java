package by.giava.base.controller.util;

import by.giava.base.common.util.JSFLocalUtils;



public class MenuUtils {

	public static java.lang.Boolean showMenu(String open, String nomeMenu) {
		if (open != null && open.length() > 0)
			return Boolean.parseBoolean(open);
		else
			return JSFLocalUtils.urlContains(nomeMenu);
	}

}
