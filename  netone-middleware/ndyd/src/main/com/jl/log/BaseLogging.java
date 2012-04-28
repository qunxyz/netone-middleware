package com.jl.log;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.oro.text.regex.Perl5Compiler;

/**
 * 基类
 * 
 * @author Don (cai.you.dun)
 * @version 1.0.0 date Mar 26, 2010 create by Don
 * @history
 */
public abstract class BaseLogging {
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
	 * 设置请求对象封装成String对象 以‘&’分隔
	 * 
	 * @author Don
	 * @param request
	 *            请求对象
	 * @param obj
	 */
	protected JSONObject setRequestEncodeJSON(HttpServletRequest request) {
		JSONObject json = new JSONObject();
		Enumeration paramNameSet = request.getParameterNames();
		try {
			while (paramNameSet.hasMoreElements()) {
				String paramName = (String) paramNameSet.nextElement();

				String[] values = request.getParameterValues(paramName);
				if (values != null && values.length == 1) {
					json.put(paramName, htmEncode2(values[0]));
				} else if (values != null && values.length > 1) {
					json.put(paramName, htmEncode2(values.toString()));
				}
			}
			json.put("$lsh", request.getAttribute("$lsh"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * 功能: 把文本编码为Html代码,由于函数返回
	 * 
	 * @param s
	 * @return
	 */
	protected static String htmEncode(String s) {
		// System.out.println("大文本转换前:" + s);
		s = s.replaceAll("<br>", "\n");
		s = s.replaceAll("<BR>", "\n");
		// System.out.println("大文本转换后:" + s);
		return s;
	}

	protected static String htmEncode2(String s) {
		// System.out.println("多彩文档转换前:" + s);
		s = StringEscapeUtils.unescapeHtml(s);
		// System.out.println("多彩文档转换后:" + s);
		return s;
	}

	/**
	 * 设置请求对象封装成String对象 以‘&’分隔
	 * 
	 * @author Don
	 * @param request
	 *            请求对象
	 * @param obj
	 */
	public static JSONObject setRequestJSON2(HttpServletRequest request) {
		JSONObject json = new JSONObject();
		Enumeration paramNameSet = request.getParameterNames();
		Enumeration attrNameSet = request.getAttributeNames();
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
			while (attrNameSet.hasMoreElements()) {
				String attrName = (String) attrNameSet.nextElement();
				json.put(attrName, request.getAttribute(attrName));
			}
			json.remove("org.apache.struts.action.MODULE");
			json.remove("org.apache.struts.action.mapping.instance");
			json.remove("ErrorJson");
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;

	}

	/**
	 * 判断是否是审核流程
	 * 
	 * @param str
	 * @return
	 */
	protected boolean isSubmit(String str) {
		String regStr = "update\\w*status";
		Pattern pattern = Pattern.compile(regStr,
				Perl5Compiler.CASE_INSENSITIVE_MASK);
		Matcher matcher = pattern.matcher(str.toLowerCase());
		if (matcher.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获得用户真实IP地址
	 * 
	 * @param request
	 * @return
	 */
	protected String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}
