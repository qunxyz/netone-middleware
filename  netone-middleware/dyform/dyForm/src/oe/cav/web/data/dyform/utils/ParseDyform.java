package oe.cav.web.data.dyform.utils;

import java.util.Iterator;

import com.rongji.webframework.struts.DynaUIForm;

public class ParseDyform {
	/**
	 * 将动态表单的内容解析为html信息
	 * 
	 * @param dyform
	 * @return
	 */
	public static String parseDyform(DynaUIForm dyform) {
		StringBuffer but = new StringBuffer();
		for (Iterator itr = dyform.iterator(); itr.hasNext();) {
			but.append(itr.next().toString());
		}
		return but.toString();
	}
}
