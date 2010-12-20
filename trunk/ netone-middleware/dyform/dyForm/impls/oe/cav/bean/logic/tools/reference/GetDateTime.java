package oe.cav.bean.logic.tools.reference;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Locale;

public class GetDateTime {
	public static String getDateTime(){
		GregorianCalendar nowgc = new GregorianCalendar();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
		return sdf.format(nowgc.getTime());
	}
}
