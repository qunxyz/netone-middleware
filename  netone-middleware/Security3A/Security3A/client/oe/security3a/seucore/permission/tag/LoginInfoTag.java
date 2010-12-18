package oe.security3a.seucore.permission.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;

import oe.security3a.sso.onlineuser.DefaultOnlineUserMgr;
import oe.security3a.sso.onlineuser.OnlineUser;
import oe.security3a.sso.onlineuser.OnlineUserMgr;


public class LoginInfoTag extends AbstractUITag {
	public int doEndTag() throws JspTagException {
		OnlineUserMgr olmgr = new DefaultOnlineUserMgr();
		OnlineUser oluser = olmgr
				.getOnlineUser((HttpServletRequest) pageContext.getRequest());
		if (oluser != null) {
			String loginName = oluser.getLoginname();
			JspWriter w = pageContext.getOut();
			try {
				w.print(loginName);
			} catch (Exception e) {
				throw new JspTagException(e);
			}
		}

		return EVAL_PAGE;
	}
}
