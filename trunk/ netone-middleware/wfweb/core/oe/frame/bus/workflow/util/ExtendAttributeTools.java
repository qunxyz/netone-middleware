package oe.frame.bus.workflow.util;

/**
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com<br>
 *         support by http://www.oesee.com
 * 
 */
public class ExtendAttributeTools {

	public static String getExtendedAttributeValueSet(
			String extendedAttributes, String keyWord) {

		// 判断传入的字符串是否是null或者空串
		if (extendedAttributes == null || extendedAttributes.equals("")) {
			return null;
		}

		// 判断传入的关键字是否是null或者空串
		if (keyWord == null || keyWord.equals("")) {
			return null;
		}

		String[] allData = extendedAttributes.split(",");
		if (allData == null || allData.length == 0) {
			return null;
		}

		for (int i = 0; i < allData.length; i++) {
			String tmpData = allData[i] == null ? "" : allData[i];
			int indexOfFirstSplit = tmpData.indexOf(":");
			if (indexOfFirstSplit > -1) {
				String nameTmp = tmpData.substring(0, indexOfFirstSplit);
				if (keyWord.equalsIgnoreCase(nameTmp)) {
					return tmpData.substring(indexOfFirstSplit + 1);
				}
			}
		}
		return null;
	}

}
