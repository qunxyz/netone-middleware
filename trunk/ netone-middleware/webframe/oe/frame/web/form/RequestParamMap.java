package oe.frame.web.form;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * ��װ�����������
 * 
 * @author hls
 * 
 */
public class RequestParamMap extends HashMap {

	private static final long serialVersionUID = 7664860517598307031L;

	public static final String Token_Key = "RequestParamMap_TokenKey_V1";

	static Log log = LogFactory.getLog(RequestParamMap.class);

	private boolean needEncode = true;

	public RequestParamMap() {

	}

	public RequestParamMap(HttpServletRequest req) {
		parse(req);
	}

	/**
	 * ���ݱ����������ƻ�ñ�ֵ��������ֶ��ж��ֵ���򴮳��ԡ�,���ָ����ַ�����
	 * 
	 * @param key
	 * @return �����������ڣ�����null
	 */
	public String getParameter(String key) {
		Object obj = get(key);
		if (obj != null) {
			return obj.toString();
		}
		return null;
	}

	/**
	 * ���ݱ����������ƻ�ñ��Ķ��ֵ
	 * 
	 * @param key
	 * @return �����������ڣ�����null
	 */
	public String[] getParameterValues(String key) {
		Object obj = get(key);
		if (obj != null) {
			String[] values = obj.toString().split(",");
			return values;
		}
		return null;
	}

	/**
	 * ��HttpServletRequest�ж�ȡ������������ŵ�map��
	 * 
	 * @param req
	 */
	public void parse(HttpServletRequest req) {
		setNeedEncode(req);
		Map reqmap = req.getParameterMap();
		Iterator iter = reqmap.keySet().iterator();
		while (iter.hasNext()) {
			String key = iter.next().toString();
			String[] values = req.getParameterValues(key);
			String[] encvs = new String[values.length];
			for (int i = 0; i < encvs.length; i++) {
				encvs[i] = iso8859ToGBK(values[i]);
			}
			put(key, concat(encvs));
		}
	}

	/**
	 * ����key1=value1,key2=value2��ʽ��ֵ
	 * 
	 * @param str
	 */
	public void loadStringValue(String str) {
		if (str != null) {
			String[] items = str.split(",");
			for (int i = 0; i < items.length; i++) {
				int index = items[i].indexOf("=");
				put(items[i].substring(0, index).trim(), items[i].substring(
						index + 1).trim());
			}
		}
	}

	private String iso8859ToGBK(String str) {
		if (!needEncode) {
			return str;
		}
		if (str != null) {
			try {
				return new String(str.getBytes("iso-8859-1"), "GBK");
			} catch (UnsupportedEncodingException e) {
				log.error("", e);
			}
		}
		return null;
	}

	private void setNeedEncode(HttpServletRequest req) {
		if (req.getMethod().equalsIgnoreCase("POST")) {
			String encoding = req.getCharacterEncoding();
			if (encoding != null) {
				if (encoding.equalsIgnoreCase("GB2312")
						|| encoding.equalsIgnoreCase("GBK")) {
					this.needEncode = false;
				}
			}
		}
	}

	private String concat(String[] strs) {
		if (strs == null || strs.length == 0) {
			return "";
		}
		if (strs.length == 1) {
			return strs[0];
		} else {
			String restr = "";
			for (int i = 0; i < strs.length - 1; i++) {
				restr += strs[i] + ",";
			}
			restr += strs[strs.length - 1];
			return restr;
		}
	}

	/**
	 * ������ʾ��Ϣ,ҳ��ʹ��${paramMap.alertMsg}
	 * 
	 * @param msg
	 */
	public void setAlertMsg(String msg) {
		if (msg != null && !"".equals(msg)) {
			StringBuffer sb = new StringBuffer();
			sb.append("<script type='text/javascript'>");
			sb.append("alert('" + msg + "');");
			sb.append("</script>");
			put("alertMsg", sb.toString());
		}
	}

	public boolean isTokenValidate(HttpServletRequest req) {
		String formvalue = getParameter(Token_Key);
		Object sessionvalue = req.getSession().getAttribute(Token_Key);
		boolean b = false;
		if (formvalue == null && sessionvalue == null) {
			b = true;
		} else {
			if (sessionvalue.equals(formvalue)) {
				b = true;
			}
		}
		String newvalue = createToken();
		req.getSession().setAttribute(Token_Key, newvalue);
		String hidden = "<input type='hidden' name='" + Token_Key + "' value='"
				+ newvalue + "' >";
		put("validateToken", hidden);
		return b;
	}

	private String createToken() {
		Random random = new Random();
		double d = random.nextDouble();
		return "Token_Value_" + d;
	}
}
