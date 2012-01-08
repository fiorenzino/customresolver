package by.giava.base.controller.util;

import java.util.Calendar;
import java.util.Date;

public class TimeUtils {
	public static Date correggiOraMinuti(Date n) {
		if (n == null)
			n = new Date();
		Calendar cal = Calendar.getInstance();
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minutes = cal.get(Calendar.MINUTE);
		cal.setTime(n);
		cal.set(Calendar.MINUTE, minutes);
		cal.set(Calendar.HOUR_OF_DAY, hour);
		return cal.getTime();
	}
}
