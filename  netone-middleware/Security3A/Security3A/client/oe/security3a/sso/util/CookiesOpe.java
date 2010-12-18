package oe.security3a.sso.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Cookies�Ķ�д���߳���
 * 
 * @author chen.jia.xun(robanco)
 * 
 */
public class CookiesOpe {

	public static final int maxage = 8640000;// ����Ϊ12��

	public static final String UserMan_ID_Key = "seq_userman";

	public static final String PW_Enc_Key = "TwoToNone";

	public static final String Cookie_Enc_Key = "QueenHuang";

	public static final String PubInfo_ID_Key = "seq_pubinfo";

	public static final String Userinfo_Cookie_Key = "SM_USERINFO";

	/**
	 * ���ͻ���дCookies
	 * 
	 * @param userinfo
	 *            cookies��Ϣ
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
		// ���ܿͻ��˵�Cookies
		cookiestr = Encryption
				.encry(cookiestr, Cookie_Enc_Key, true)
				+ "@@@";
		// ��cookiesд��ͻ���
		Cookie cookie = new Cookie(Userinfo_Cookie_Key, cookiestr);
		cookie.setMaxAge(maxage);
		cookie.setPath("/");
		rep.addCookie(cookie);
	}

	/**
	 * ��ȡ�ͻ��˵�Cookies
	 * 
	 * @param req
	 * @return
	 */
	public static String[] readCookiex(HttpServletRequest req) {
		Cookie usercookie = null;
		Cookie[] cookie = req.getCookies();
		if (cookie != null) {
			for (int i = 0; i < cookie.length; i++) {
				if (cookie[i].getName().equals(Userinfo_Cookie_Key)) {
					usercookie = cookie[i];
					break;
				}
			}
		}
		if (usercookie != null) {
			String userinfo = usercookie.getValue();
			if (userinfo == null) {
				return null;
			}
			// ����Cookie
			userinfo = Encryption.encry(userinfo, "QueenHuang",
					false);
			try {
				userinfo = new String(userinfo.getBytes("ISO8859_1"), "GBK");

			} catch (Exception ex) {
				ex.printStackTrace();
				return null;
			}
			if (userinfo == null) {
				return null;
			}
			String[] p = userinfo.split("%23");// %23Ϊ#����
			return p;
		} else {
			return null;
		}
	}

}
