package oe.security3a.sso.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Cookie;
import java.io.*;

import oe.frame.web.WebCache;
import oe.security3a.sso.SecurityConfig;
import oe.security3a.sso.SsoToken;
import oe.security3a.sso.onlineuser.DefaultOnlineUserMgr;
import oe.security3a.sso.onlineuser.OnlineUser;
import oe.security3a.sso.onlineuser.OnlineUserMgr;
import oe.security4a.severlet.SecondCheckSvl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletResponse;
import javax.swing.JApplet;

/**
 * 提供web应用与sso server交互的方法。
 * 
 * @author hls
 * @version 1.0
 */
public class SsoUtil {

	static Log log = LogFactory.getLog(SsoUtil.class);

	public static final String SsoToken_key = "SsoToken";

	public static final String ContextPath_Applet = "/applet";

	public static final String ContextPath_WebStart = "/jws";

	public SsoUtil() {
	}

	public void doLogout(HttpServletRequest request, HttpServletResponse resp,
			String gotourl) throws IOException {
		// 删除ssoserver的session
		String ssourl = SecurityConfig.getInstance().getSsoServerUrl();
		String logouturl = ssourl + "/logoutsvl?gotourl=" + gotourl;

		Cookie SsoCookie = null;
		Cookie[] cookie = request.getCookies();
		if (cookie != null) {
			for (int i = 0; i < cookie.length; i++) {
				if (cookie[i].getName()
						.equals(SsoUtil.getSsoToken_key(request))) {
					SsoCookie = cookie[i];
					break;
				}
			}
		}

		if (SsoCookie != null) {
			log.debug("找到注销的cookie：" + SsoCookie);

			// 清空二次认证中的内容
			OnlineUserMgr olmgr = new DefaultOnlineUserMgr();
			OnlineUser oluser = olmgr.getOnlineUser(request);
			String username = oluser.getLoginname();
			if (WebCache.containCache(SecondCheckSvl._SECOND_CHECK_HEAD
					+ username)) {
				WebCache.removeCache(SecondCheckSvl._SECOND_CHECK_HEAD
						+ username);
			}

			// 删除本身的cookie
			SsoCookie.setMaxAge(0);
			SsoCookie.setPath(getSsoCookiePath(request));
			SsoCookie.setValue(null);
			resp.addCookie(SsoCookie);

		} else {
			log.debug("未找到注销的cookie！");
		}

		// resp.sendRedirect(logouturl);
		postRedirect(logouturl, request, resp);

	}

	// post方式提交重定向。
	private void postRedirect(String requri, HttpServletRequest req,
			HttpServletResponse resp) throws IOException {
		PrintWriter out = resp.getWriter();
		out.println("<html>");
		out.println("<head><title>redirecting...</title></head>");
		out.println("<body onload=\"javascript:document.forms[0].submit();\">");
		out.println("<form action='" + requri + "' method='post'>");

		out.println("</form>");
		out.println("</body>");
		out.println("</html>");
		out.flush();
		out.close();
	}

	public void saveSsoToken(HttpServletRequest req, HttpServletResponse resp,
			String token) {
		String tokenTimeOut = SecurityConfig.getInstance()
				.getSSOServerProperty("tokenTimeout");
		int limit = Integer.parseInt(tokenTimeOut);
		int limittime = limit * 60*60;
		String cookieName = SsoUtil.getSsoToken_key(req);
		Cookie cookie = new Cookie(cookieName, token);
		if (limittime != 0) {
			cookie.setMaxAge(limittime);// 单位为 秒
		}

		cookie.setPath(getSsoCookiePath(req));
		resp.addCookie(cookie);
	}

	private String getSsoCookiePath(HttpServletRequest req) {
		return "/";
		// return req.getContextPath();
	}

	public SsoToken getSsoToken(HttpServletRequest request) {
		SsoToken st = null;
		Cookie[] cookie = request.getCookies();
		if (cookie != null) {
			for (int i = 0; i < cookie.length; i++) {
				if (getSsoToken_key(request).equals(cookie[i].getName())) {
					st = new SsoToken();
					st.parse(cookie[i].getValue());
					break;
				}
			}
		}
		if (st == null) {
			// 看看request的参数中是否存在token
			String addTokenStr = request.getParameter("addtoken");
			if (addTokenStr != null && !addTokenStr.equals("")) {
				try {
					st = new SsoToken();
					st.parse(addTokenStr);
				} catch (Exception ex) {
					st = null;
				}
			}
		}
		return st;
	}

	public SsoToken getSsoToken(JApplet applet) {
		SsoToken st = null;
		try {
			netscape.javascript.JSObject win = netscape.javascript.JSObject
					.getWindow(applet);
			netscape.javascript.JSObject doc = (netscape.javascript.JSObject) win
					.getMember("document");
			String cookies = doc.getMember("cookie").toString();
			log.debug("从applet中获取的cookie：" + cookies);
			String ssocookie = null;
			String cookiename = getSsoToken_key(ContextPath_Applet);
			int i = cookies.indexOf(cookiename);
			int startindex;
			int endindex;
			if (i != -1) {
				startindex = i + cookiename.length() + 1; // 加个"="
				cookies = cookies.substring(startindex);
				endindex = cookies.indexOf(";");
				if (endindex != -1) {
					ssocookie = cookies.substring(0, endindex);
				} else {
					ssocookie = cookies;
				}
			} else {
				log.error("在applet中没有找到sso_cookie！");
			}
			if (ssocookie != null) {
				st = new SsoToken();
				st.parse(ssocookie);
			}
		} catch (netscape.javascript.JSException ex) {
			log.error("未取得applet的window对象！", ex);
		} catch (Exception ex) {
			log.error("从applet中获取sso_cookie出错！", ex);
		}

		return st;
	}

	public SsoToken getSsoToken(String ssosessionid) {
		SsoToken st = null;
		String url = SecurityConfig.getInstance().getSsoServerUrl()
				+ "/validatesvl?todo=regettoken";
		try {
			String restr = HttpCommunicate.httpRequest(url, ssosessionid);
			if (restr.startsWith("regettoken:")) {
				restr = restr.substring(11, restr.length());
				if (!restr.equals("false")) {
					st = new SsoToken();
					st.parse(restr);
				} else {
					log.error("根据ssosessionid获取SsoToken出错！");
				}
			}
		} catch (Exception ex) {
			log.error("根据ssosessionid获取SsoToken出错！", ex);
		}
		return st;
	}

	public String getSsoSessionId(HttpServletRequest request) {
		SsoToken st = getSsoToken(request);
		return st.getSessionId();
	}

	public String getSsoSessionId(JApplet applet) {
		SsoToken st = getSsoToken(applet);
		return st.getSessionId();
	}

	public static String getSsoToken_key(HttpServletRequest request) {
		return SsoToken_key;
	}

	public static String getSsoToken_key(String contextpath) {
		return SsoToken_key;
	}

}
