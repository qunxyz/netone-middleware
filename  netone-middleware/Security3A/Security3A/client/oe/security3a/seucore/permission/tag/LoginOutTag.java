package oe.security3a.seucore.permission.tag;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

import oe.security3a.sso.SecurityConfig;
import oe.security3a.sso.util.SsoUtil;


public class LoginOutTag extends AbstractUITag {

	public int doEndTag() throws JspTagException {
		if (!checkLogin()) {
			return SKIP_PAGE;
		}

		HttpServletRequest request = ((HttpServletRequest) pageContext
				.getRequest());

		String gotourl = request.getRequestURL().toString();

		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort() + path + "/logoutsvl?gotourl="
				+ gotourl;

		try {
			this.pageContext.getOut().print(basePath);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return EVAL_PAGE;
	}
}
