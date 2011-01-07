package oe.web.tag.common;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspTagException;

import oe.security3a.sso.SecurityConfig;


public class PagePermissionTag extends AbstractUITag {
	public int doEndTag() throws JspTagException {
		if(!checkLogin()){
			return SKIP_PAGE;
		}
		if(!checkPermission()){
			String url = SecurityConfig.getInstance().getSsoServerUrl()+"/sso/impl/nopermission.jsp";
			try {
				((HttpServletResponse)pageContext.getResponse()).sendRedirect(url);
			} catch (IOException e) {
				log.error("",e);
			}
			return SKIP_PAGE;
		}
		return EVAL_PAGE;
	}

}
