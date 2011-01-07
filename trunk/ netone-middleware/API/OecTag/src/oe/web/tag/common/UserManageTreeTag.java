package oe.web.tag.common;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import oe.security3a.sso.SecurityConfig;


public class UserManageTreeTag extends SimpleTagSupport {

	public void doTag() throws JspException {

		String url = SecurityConfig.getInstance().getSsoServerUrl()
				+ "/rsinfo/dept/frameIndex.jsp";

		try {
			getJspContext().getOut().print(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
