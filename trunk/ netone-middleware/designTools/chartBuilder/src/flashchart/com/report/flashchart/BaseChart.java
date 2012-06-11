package com.report.flashchart;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONException;

import com.report.chart.entity.BarChartObj;
import com.report.chart.entity.LineChartObj;
import com.report.chart.entity.PieChartObj;

/**
 * 图表基类
 * 
 * @author Don (cai.you.dun)
 * @version 1.0.0 date Mar 8, 2010 create by Don
 * @history
 */
public abstract class BaseChart {
	/** 汇总 */
	public static final String _REPORT_GATHER_COL_GROUPBY = "groupBy";

	/**
	 * 处理函数
	 * 
	 * @param request
	 */
	protected void handle(HttpServletRequest request, String title, String flash) {
		String $flash = request.getParameter("flash");
		String $title = request.getParameter("title");
		if (StringUtils.isEmpty(title)) {
			title = $title;
		}
		if (StringUtils.isEmpty(flash)) {
			flash = $flash;
		}
		int r = (int) (Math.random() * 999999);// 随机数
		String endpoint = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort() + request.getContextPath()
				+ "/";
		if (request.getScheme().equalsIgnoreCase("https")) {// https
			endpoint += "messagebroker/amfsecure";
		} else {// http
			endpoint += "messagebroker/amf";
		}
		JSONObject json = setRequestJSON(request);
		json.put("title", title);
		json.put("flash", flash + "?r=" + r);
		json.put("endpoint", endpoint);// java与flex交互接口
		Object urlReplace = request.getAttribute("reurl");// 地址需要重新跳转
		// 复杂的钻取分析
		if (urlReplace != null) {
			Map urlmap = setRequestMap(request);
			String replaceStatement = "statement2L";
			String replaceflash = "flash2L";
			if (urlmap.containsKey(replaceStatement)) {
				urlmap.put("statement", urlmap.get(replaceStatement));
			}
			if (urlmap.containsKey(replaceflash)) {
				urlmap.put("flash", urlmap.get(replaceflash));
			}
			json.put("url", getUrlParamsByMap(urlmap));
		}
		request.setAttribute("jsonParam", json.toString());
		request.setAttribute("title", title);
		request.setAttribute("flash", flash + "?r=" + r);
	}

	/**
	 * 把col中的对象全部转换成JSON对象，然后用逗号串联起来
	 * 
	 * @param coll
	 * @return
	 */
	protected String toJSONStr(Collection coll) {
		JSONArray jsonArray = new JSONArray();
		for (Iterator iterator = coll.iterator(); iterator.hasNext();) {
			Object _Map = (Object) iterator.next();
			if (_Map instanceof BarChartObj) {
				_Map = (BarChartObj) _Map;
			} else if (_Map instanceof LineChartObj) {
				_Map = (LineChartObj) _Map;
			} else if (_Map instanceof PieChartObj) {
				_Map = (PieChartObj) _Map;
			} else if (_Map instanceof Map) {
				_Map = (Map) _Map;
			}
			String jsonStr = JSONUtil2.fromBean(_Map, "yyyy-MM-dd").toString();
			jsonArray.add(jsonStr);
		}
		return jsonArray.toString();
	}

	/**
	 * String 转成 Map<BR>
	 * String格式如下:<BR>
	 * year:2010,method:2
	 * 
	 * @param param
	 * @return
	 */
	protected Map stringToMap(String param) {
		Map paramMap = new HashMap();
		try {
			if (param != null) {
				String[] paramStrs = param.split("#");
				for (int i = 0; i < paramStrs.length; i++) {
					String[] tmp = paramStrs[i].split(":");
					if (tmp.length == 1) {
						paramMap.put(tmp[0].trim(), null);
					} else if (tmp.length == 2) {
						paramMap.put(tmp[0].trim(), tmp[1].trim());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return paramMap;

	}

	/**
	 * 设置请求对象封装成String对象 以‘&’分隔
	 * 
	 * @author Don
	 * @param request
	 *            请求对象
	 * @param obj
	 */
	protected JSONObject setRequestJSON(HttpServletRequest request) {
		JSONObject json = new JSONObject();
		Enumeration paramNameSet = request.getParameterNames();
		try {
			while (paramNameSet.hasMoreElements()) {
				String paramName = (String) paramNameSet.nextElement();

				String[] values = request.getParameterValues(paramName);
				if (values != null && values.length == 1) {
					json.put(paramName, values[0]);
				} else if (values != null && values.length > 1) {
					json.put(paramName, values);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * 设置请求对象封装成Map对象
	 * 
	 * @author Don
	 * @param request
	 *            请求对象
	 * @param obj
	 */
	protected Map setRequestMap(HttpServletRequest request) {
		Map map = new HashMap();
		Enumeration paramNameSet = request.getParameterNames();
		Enumeration attrNameSet = request.getAttributeNames();
		try {
			while (paramNameSet.hasMoreElements()) {
				String paramName = (String) paramNameSet.nextElement();

				String[] values = request.getParameterValues(paramName);
				if (values != null && values.length == 1) {
					map.put(paramName, values[0]);
				} else if (values != null && values.length > 1) {
					map.put(paramName, values);
				}
			}
			while (attrNameSet.hasMoreElements()) {
				String attrName = (String) attrNameSet.nextElement();
				map.put(attrName, request.getAttribute(attrName));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;

	}

	protected static String htmEncode2(String s) {
		// System.out.println("多彩文档转换前:" + s);
		s = StringEscapeUtils.unescapeHtml(s);
		// System.out.println("多彩文档转换后:" + s);
		return s;
	}

	/**
	 * json转成Map
	 * 
	 * @param json
	 * @return
	 * @throws JSONException
	 */
	protected Map jsonToMap(JSONObject json) throws JSONException {
		Map map = new HashMap();
		String multFiled = "";
		if (json.containsKey("mulFiled")) {
			multFiled = json.getString("mulFiled");// 多选字段
		}
		Map _mulMap = splitStrToMap(multFiled);
		for (Iterator iterator = json.keys(); iterator.hasNext();) {
			String key = (String) iterator.next();
			if (_mulMap.containsKey(key)) {
				String o = (String) json.get(key);
				if (StringUtils.isNotEmpty(o)) {// 多参数处理
					map.put(key, o.split(","));
				}
			} else {
				map.put(key, json.get(key));
			}
		}

		String groupByCol = "";
		if (json.containsKey("groupByCol")) {
			groupByCol = json.getString("groupByCol");

			String[] selectedCol = groupByCol.split(",");
			int len = selectedCol.length;
			for (int i = 0; i < len; i++) {
				String _selectedCol = selectedCol[i].trim();
				map.put(_selectedCol, _REPORT_GATHER_COL_GROUPBY);
			}
		}

		if (map.containsKey("params")) {
			// 特殊处理 JSON
			String params = (String) map.get("params");
			if (StringUtils.isNotEmpty(params)) {
				JSONObject jobj = JSONObject.fromObject(params);
				for (Iterator iterator = jobj.keys(); iterator.hasNext();) {
					String key = (String) iterator.next();
					map.put(key, jobj.get(key));
				}
			}
		}

		return map;
	}

	/**
	 * 分隔字符串转Map
	 * 
	 * @param splitString
	 * @return
	 * @throws JSONException
	 */
	protected Map splitStrToMap(String splitString) throws JSONException {
		Map map = new HashMap();
		if (StringUtils.isNotEmpty(splitString)) {
			String[] s = splitString.split(",");
			for (int i = 0; i < s.length; i++) {
				map.put(s[i], s[i]);
			}
		}
		return map;
	}

	/**
	 * 将url参数转换成map
	 * 
	 * @param param
	 *            aa=11&bb=22&cc=33
	 * @return
	 */
	protected Map<String, Object> getUrlParams(String param) {
		Map<String, Object> map = new HashMap<String, Object>(0);
		if (StringUtils.isBlank(param)) {
			return map;
		}
		String[] params = param.split("&");
		for (int i = 0; i < params.length; i++) {
			String[] p = params[i].split("=");
			if (p.length == 2) {
				map.put(p[0], p[1]);
			}
		}
		return map;
	}

	/**
	 * 将map转换成url
	 * 
	 * @param map
	 * @return
	 */
	protected String getUrlParamsByMap(Map<String, Object> map) {
		if (map == null) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			sb.append(entry.getKey() + "=" + entry.getValue());
			sb.append("&");
		}
		String s = sb.toString();
		if (s.endsWith("&")) {
			s = org.apache.commons.lang.StringUtils.substringBeforeLast(s, "&");
		}
		return s;
	}

	protected List<LineChartObj> transformLineChartData(List<LineChartObj> list)
			throws Exception {
		Map rowMap = new LinkedHashMap();
		Map columnMap = new LinkedHashMap();

		Map raw = new LinkedHashMap();

		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			LineChartObj lineChartObj = (LineChartObj) iterator.next();
			if (!rowMap.containsKey(lineChartObj.getRowKey())) {
				rowMap.put(lineChartObj.getRowKey(), lineChartObj.getRowKey());
			}
			if (!columnMap.containsKey(lineChartObj.getColumnKey())) {
				columnMap.put(lineChartObj.getColumnKey(), lineChartObj);
			}
		}
		raw = columnMap;

		int i = 1;

		for (Iterator iterator2 = rowMap.keySet().iterator(); iterator2
				.hasNext();) {
			String key2 = (String) iterator2.next();
			key2=key2==null?"":key2;
			for (Iterator iterator = columnMap.keySet().iterator(); iterator
					.hasNext();) {
				String key = (String) iterator.next();
				key=key==null?"":key;

				for (Iterator iterator3 = list.iterator(); iterator3.hasNext();) {
					LineChartObj lineChartObj = (LineChartObj) iterator3.next();

					if (key.equalsIgnoreCase(lineChartObj.getColumnKey())
							&& key2.equalsIgnoreCase(lineChartObj.getRowKey())) {

						LineChartObj obj = (LineChartObj) raw.get(key);
						BeanUtils.setProperty(obj, "value" + i, lineChartObj
								.getValue());
						BeanUtils.setProperty(obj, "rowKey" + i, lineChartObj
								.getRowKey());
						raw.put(key, obj);
					}
				}
			}

			i++;
		}

		List<LineChartObj> datalist = new ArrayList();

		for (Iterator iterator = raw.keySet().iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			datalist.add((LineChartObj) raw.get(key));
		}
		return datalist;
	}

	protected List<BarChartObj> transformBarChartData(List<BarChartObj> list)
			throws Exception {
		Map rowMap = new LinkedHashMap();
		Map columnMap = new LinkedHashMap();

		Map raw = new LinkedHashMap();

		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			BarChartObj BarChartObj = (BarChartObj) iterator.next();
			if (!rowMap.containsKey(BarChartObj.getRowKey())) {
				rowMap.put(BarChartObj.getRowKey(), BarChartObj.getRowKey());
			}
			if (!columnMap.containsKey(BarChartObj.getColumnKey())) {
				columnMap.put(BarChartObj.getColumnKey(), BarChartObj);
			}
		}
		raw = columnMap;

		int i = 1;

		for (Iterator iterator2 = rowMap.keySet().iterator(); iterator2
				.hasNext();) {
			String key2 = (String) iterator2.next();

			for (Iterator iterator = columnMap.keySet().iterator(); iterator
					.hasNext();) {
				String key = (String) iterator.next();

				for (Iterator iterator3 = list.iterator(); iterator3.hasNext();) {
					BarChartObj BarChartObj = (BarChartObj) iterator3.next();

					if (key.equals(BarChartObj.getColumnKey())
							&& key2.equals(BarChartObj.getRowKey())) {

						BarChartObj obj = (BarChartObj) raw.get(key);
						BeanUtils.setProperty(obj, "value" + i, BarChartObj
								.getValue());
						BeanUtils.setProperty(obj, "rowKey" + i, BarChartObj
								.getRowKey());
						raw.put(key, obj);
					}
				}
			}

			i++;
		}

		List<BarChartObj> datalist = new ArrayList();

		for (Iterator iterator = raw.keySet().iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			datalist.add((BarChartObj) raw.get(key));
		}
		return datalist;
	}

}
