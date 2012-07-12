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
 * ����
 * 
 * @author Don (cai.you.dun)
 * @version 1.0.0 date Mar 26, 2010 create by Don
 * @history
 */
public abstract class BaseLogging {
	/**
	 * ������������װ��String���� �ԡ�&���ָ�
	 * 
	 * @author Don
	 * @param request
	 *            �������
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
	 * ������������װ��String���� �ԡ�&���ָ�
	 * 
	 * @author Don
	 * @param request
	 *            �������
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
	 * ����: ���ı�����ΪHtml����,���ں�������
	 * 
	 * @param s
	 * @return
	 */
	protected static String htmEncode(String s) {
		// System.out.println("���ı�ת��ǰ:" + s);
		s = s.replaceAll("<br>", "\n");
		s = s.replaceAll("<BR>", "\n");
		// System.out.println("���ı�ת����:" + s);
		return s;
	}

	protected static String htmEncode2(String s) {
		// System.out.println("����ĵ�ת��ǰ:" + s);
		s = StringEscapeUtils.unescapeHtml(s);
		// System.out.println("����ĵ�ת����:" + s);
		return s;
	}

	/**
	 * ������������װ��String���� �ԡ�&���ָ�
	 * 
	 * @author Don
	 * @param request
	 *            �������
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
	 * ������������װ��Map����
	 * 
	 * @author Don
	 * @param request
	 *            �������
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
	 * �ж��Ƿ����������
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
	 * ����û���ʵIP��ַ
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
