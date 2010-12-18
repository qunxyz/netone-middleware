package oe.security3a.seucore.permission.tag;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspTagException;

import oe.frame.web.WebCache;
import oe.security3a.sso.SecurityConfig;
import oe.security3a.sso.onlineuser.DefaultOnlineUserMgr;
import oe.security3a.sso.onlineuser.OnlineUser;
import oe.security3a.sso.onlineuser.OnlineUserMgr;

import oe.security4a.severlet.SecondCheckSvl;

public class SecondAuthTag extends AbstractUITag {

	public int doEndTag() throws JspTagException {
		if (!checkLogin()) {
			return SKIP_PAGE;
		}

		HttpServletRequest request = ((HttpServletRequest) pageContext
				.getRequest());
		HttpServletResponse response = ((HttpServletResponse) pageContext
				.getResponse());
		response.setContentType("text/html; charset=GBK");
		String gotourl = request.getRequestURL().toString();

		OnlineUserMgr olmgr = new DefaultOnlineUserMgr();
		OnlineUser oluser = olmgr.getOnlineUser(request);
		String username = oluser.getLoginname();

		String keyvalue = (String) WebCache
				.getCache(SecondCheckSvl._SECOND_CHECK_HEAD + username);
		if (!SecondCheckSvl._SECOND_CHECK_PASS.equals(keyvalue)) {
			try {
				String ssourl = SecurityConfig.getInstance().getSsoServerUrl()
						+ "/sso/secondAuth.jsp?gotourl2="
						+ URLEncoder.encode(gotourl, "GBK");
				response.sendRedirect(ssourl);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return EVAL_PAGE;
	}
}
