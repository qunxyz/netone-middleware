package oe.cav.web.data.dyform.utils;

import java.util.HashMap;
import java.util.Map;

public class DynamicFormTools {
	public static String _LINK_URLS = "link:";

	public static String _SIZE = "size:";

	public static String _OFFSET = "offset:";

	public static String[] KEY_ARR = { _LINK_URLS, _SIZE, _OFFSET };

	/**
	 * 解析图片的扩展属性
	 * 
	 * @param extendattribute
	 * @return
	 */
	public static Map parseImageExtendattribute(String extendattribute) {
		Map map = new HashMap();
		if (extendattribute == null || extendattribute.equals("")) {
			return map;
		}
		extendattribute = extendattribute.replaceAll(";\r\n", ";");
		String[] attributes = extendattribute.split(";");
		if (attributes == null) {
			return map;
		}

		for (int i = 0; i < attributes.length; i++) {
			for (int j = 0; j < KEY_ARR.length; j++) {
				if (attributes[i].indexOf(KEY_ARR[j]) >= 0) {
					String[] tmp = attributes[i].split(KEY_ARR[j]);
					if (tmp == null || tmp.length != 2) {
						continue;
					}
					map.put(KEY_ARR[j], tmp[1]);
				}
			}
		}
		return map;
	}

	/**
	 * 解析获得扩展属性的元素数组
	 * 
	 * @param sizeinfo
	 * @return
	 */
	public static String[] dealEach(String sizeinfo) {
		if (sizeinfo == null || sizeinfo.equals("")) {
			return new String[0];
		}
		String[] infoArr;
		try {
			infoArr = sizeinfo.split(",");
			return infoArr;
		} catch (Exception e) {
			System.err.println("无效扩展属性:" + sizeinfo);
			return new String[0];
		}
	}

}
