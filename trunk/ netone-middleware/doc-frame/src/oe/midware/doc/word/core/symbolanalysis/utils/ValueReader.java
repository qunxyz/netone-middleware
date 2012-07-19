package oe.midware.doc.word.core.symbolanalysis.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import oe.frame.bus.res.doc.WordHandler;
import oe.frame.bus.res.doc.common.XmlObj;

import org.apache.commons.beanutils.BeanUtils;

/**
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public class ValueReader {

	/**
	 * 抽离出对象元素的值
	 * 
	 * @param valueList
	 *            对象元素列表
	 * @param element
	 *            模板对象
	 * @return String[] 值数组
	 */
	public static String[] fetchValueByMulti(List valueList, XmlObj element) {
		List list = new ArrayList();
		String key = element.getId();
		for (Iterator iterator = valueList.iterator(); iterator.hasNext();) {
			Object dataObj = (Object) iterator.next();
			String value = null;
			try {
				value = BeanUtils.getProperty(dataObj, key);
				value = value == null ? "" : value;
			} catch (Exception e) {
				e.printStackTrace();
				return new String[] { WordHandler.LOSE_VALUE };
			}
			list.add(value);
		}
		return (String[]) list.toArray(new String[0]);
	}

	/**
	 * 抽离出对象元素的值
	 * 
	 * @param valueList
	 *            对象元素列表
	 * @param element
	 *            模板对象
	 * @return String[] 值数组
	 */
	public static String fetchValueBySingle(Map valueSingle, XmlObj element) {
		String key = element.getId();
		String value = null;
		try {
			value = (String) valueSingle.get(key);
			return value == null ? "" : value;
		} catch (Exception e) {
			e.printStackTrace();
			return WordHandler.LOSE_VALUE;
		}

	}
}
