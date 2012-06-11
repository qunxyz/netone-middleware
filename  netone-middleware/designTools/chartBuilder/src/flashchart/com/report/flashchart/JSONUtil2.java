package com.report.flashchart;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import net.sf.json.util.JSONUtils;
import net.sf.json.util.PropertyFilter;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * <code>
 * com.json.test.JSONUtil.java
 * <p><li>JSONUtil����</li></P>
 * <description></description>
 ��  author zhang.chao.yi
 *          mail: eduzcy@126.com
 *</code>
 * 
 * @version 1.0.0 date 2008-12-3 created by zhang.chao.yi��Jim��
 */
public class JSONUtil2 {

	/**
	 * ��col�еĶ���ȫ��ת����JSON����Ȼ���ö��Ŵ�������
	 * 
	 * @param col
	 * @return
	 */
	public static String toJSONStr(Collection col) {
		List result = new ArrayList();

		for (Iterator itr = col.iterator(); itr.hasNext();) {
			result.add(fromBean(itr.hasNext()).toString());
		}

		return StringUtils.join(result.iterator(), ",");
	}

	/**
	 * JavaBean ת����JSON����
	 * 
	 * @param bean
	 * @return
	 */
	public static JSON fromBean(Object bean) {
		return fromBean(bean, null, null, null);
	}

	/**
	 * JavaBean ת����JSON����
	 * 
	 * @param bean
	 * @param datePattern
	 *            ���ڸ�ʽ
	 * @return
	 */
	public static JSON fromBean(Object bean, String datePattern) {
		return fromBean(bean, null, null, datePattern);
	}

	/**
	 * JavaBean ת����JSON����
	 * 
	 * @param bean
	 * 
	 * @param includes
	 *            ����������
	 * @param excludes
	 *            �����������Լ���
	 * @return
	 */
	public static JSON fromBean(Object bean, String[] includes,
			String[] excludes) {
		return fromBean(bean, includes, excludes, null);
	}

	/**
	 * JavaBean ת����JSON����
	 * 
	 * @param bean
	 * @param includes
	 *            ����������
	 * @param excludes
	 *            �����������Լ���
	 * @param datePattern
	 *            ���ڸ�ʽ
	 * @return
	 */

	public static JSON fromBean(Object bean, final String[] includes,
			String[] excludes, String datePattern) {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Integer.class,
				new NullJsonValueProcessor());
		jsonConfig.registerJsonValueProcessor(Double.class,
				new NullJsonValueProcessor());
		jsonConfig.registerJsonValueProcessor(Long.class,
				new NullJsonValueProcessor());
		jsonConfig.registerJsonValueProcessor(Float.class,
				new NullJsonValueProcessor());
		jsonConfig.registerJsonValueProcessor(String.class,
				new NullJsonValueProcessor());

		if (excludes != null) {
			jsonConfig.setExcludes(excludes);
			jsonConfig.setIgnoreDefaultExcludes(false);
		}

		if (datePattern != null) {
			jsonConfig
					.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
			jsonConfig.registerJsonValueProcessor(Date.class,
					new DateJsonValueProcessor(datePattern));
		}

		if (includes != null) {
			Map tempMap = new HashMap();
			for (int i = 0; i < includes.length; i++) {
				String[] tempArray = includes[i].split("\\.");
				for (int j = 0; j < tempArray.length; j++) {
					if (!tempMap.containsKey(tempArray[j])) {
						tempMap.put(tempArray[j], "YES");
					}
				}
			}

			final Map includeMap = tempMap;
			jsonConfig.setJsonPropertyFilter(new PropertyFilter() {
				public boolean apply(Object source, String name, Object value) {
					if (includeMap.containsKey(name)) {
						return false;
					} else {
						return true;
					}
				}
			});
		}

		JSON json = JSONSerializer.toJSON(bean, jsonConfig);

		return json;
	}

	/**
	 * JavaBean ת����JSONArray����
	 * 
	 * @param bean
	 * @param includes
	 *            ����������
	 * @param excludes
	 *            �����������Լ���
	 * @param datePattern
	 *            ���ڸ�ʽ
	 * @return
	 */
	public static JSONArray fromBeanToJsonArray(Object bean,
			final String[] includes, String[] excludes, String datePattern) {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Integer.class,
				new NullJsonValueProcessor());
		jsonConfig.registerJsonValueProcessor(Double.class,
				new NullJsonValueProcessor());
		jsonConfig.registerJsonValueProcessor(Long.class,
				new NullJsonValueProcessor());
		jsonConfig.registerJsonValueProcessor(Float.class,
				new NullJsonValueProcessor());
		jsonConfig.registerJsonValueProcessor(String.class,
				new NullJsonValueProcessor());

		if (excludes != null) {
			jsonConfig.setExcludes(excludes);
			jsonConfig.setIgnoreDefaultExcludes(false);
		}

		if (datePattern != null) {
			jsonConfig
					.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
			jsonConfig.registerJsonValueProcessor(Date.class,
					new DateJsonValueProcessor(datePattern));
		}

		if (includes != null) {
			Map tempMap = new HashMap();
			for (int i = 0; i < includes.length; i++) {
				String[] tempArray = includes[i].split("\\.");
				for (int j = 0; j < tempArray.length; j++) {
					if (!tempMap.containsKey(tempArray[j])) {
						tempMap.put(tempArray[j], "YES");
					}
				}
			}

			final Map includeMap = tempMap;
			jsonConfig.setJsonPropertyFilter(new PropertyFilter() {
				public boolean apply(Object source, String name, Object value) {
					if (includeMap.containsKey(name)) {
						return false;
					} else {
						return true;
					}
				}
			});
		}

		JSONArray jsonArray = JSONArray.fromObject(bean, jsonConfig);

		return jsonArray;
	}

	/**
	 * JSONת����JavaBean <br>
	 * ��ע�� ֧��ʱ���ת��
	 * 
	 * @param json
	 *            json ����
	 * @param clazz
	 *            Ŀ��bean����
	 * @return
	 */
	public static Object toBean(JSONObject json, Class clazz) {
		return toBean(json, clazz, null);
	}

	/**
	 * JSONת����JavaBean <br>
	 * ��ע�� ֧��ʱ���ת��
	 * 
	 * @param json
	 *            json ����
	 * @param clazz
	 *            Ŀ��bean����
	 * @param datePattern
	 *            ���ڸ�ʽ
	 * @return
	 */
	public static Object toBean(JSONObject json, Class clazz, String datePattern) {
		if (StringUtils.isEmpty(datePattern) || datePattern == null) {
			datePattern = "yyyy-MM-dd";
		}

		String[] dateFormats = new String[] { datePattern };
		JSONUtils.getMorpherRegistry().registerMorpher(
				new DateMorpher(dateFormats));
		return JSONObject.toBean(json, clazz);
	}
}
