package oe.cav.web.data.dyform.utils;

import java.util.HashMap;
import java.util.Map;

public class DynamicFormTools {
	public static String _LINK_URLS = "link:";

	public static String _SIZE = "size:";

	public static String _OFFSET = "offset:";

	public static String[] KEY_ARR = { _LINK_URLS, _SIZE, _OFFSET };

	/**
	 * ����ͼƬ����չ����
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
	 * ���������չ���Ե�Ԫ������
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
			System.err.println("��Ч��չ����:" + sizeinfo);
			return new String[0];
		}
	}

}
