package com.jl.common;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Cookies的读写工具程序
 * 
 * @author chen.jia.xun(robanco)
 * 
 */
public class CookiesOpe {

	public static final int maxage = 8640000;// 日期为12天

	public static final String UserMan_ID_Key = "seq_userman";

	public static final String PW_Enc_Key = "TwoToNone";

	public static final String Cookie_Enc_Key = "QueenHuang";

	public static final String PubInfo_ID_Key = "seq_pubinfo";

	public static final String Userinfo_Cookie_Key = "SM_USERINFO";

	/**
	 * 往客户端写Cookies
	 * 
	 * @param userinfo
	 *            cookies信息
	 * @param rep
	 */
	public static void addCookiesx(String[] userinfo, HttpServletResponse rep) {

		StringBuffer sbcookie = new StringBuffer();
		sbcookie.append(userinfo[0]);// id
		sbcookie.append("#" + userinfo[1]);// name
		sbcookie.append("#" + userinfo[2]);// type
		sbcookie.append("#" + userinfo[3]);// systemid
		sbcookie.append("#");

		String cookiestr = null;
		try {
			cookiestr = java.net.URLEncoder.encode(sbcookie.toString(), "GBK");

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		// 将cookies写入客户端
		Cookie cookie = new Cookie(Userinfo_Cookie_Key, cookiestr);
		cookie.setMaxAge(maxage);
		cookie.setPath("/");
		rep.addCookie(cookie);
	}

	/**
	 * 读取客户端的Cookies
	 * 
	 * @param req
	 * @return
	 */
	public static String readCookiex(HttpServletRequest req) {

		Cookie[] cookie = req.getCookies();
		String styleblue = "ext-all.css|blue|blue";
		String styleblue2 = "xtheme-gray.css|blue|gray";
		String extTheme = null;
		String appTheme = null;
		String mainTheme = null;
		String styleall = null;
		if (cookie != null) {
			for (int i = 0; i < cookie.length; i++) {

				if (cookie[i].getName().equals("extTheme")) {
					extTheme = cookie[i].getValue().trim();
				}
				if (cookie[i].getName().equals("appTheme")) {
					appTheme = cookie[i].getValue().trim();
				}
				if (cookie[i].getName().equals("mainTheme")) {
					mainTheme = cookie[i].getValue().trim();
				}
			}
			styleall = extTheme + "|" + appTheme + "|" + mainTheme;
		}
		if (styleblue.equals(styleall) || styleblue2.equals(styleall)) {
			return "CSSFILE.CSSFILE.DRPBLUE";
		}
		return "CSSFILE.CSSFILE.DRP";

	}

}
