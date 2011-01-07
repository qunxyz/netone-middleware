package oe.web.tag.common;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import oe.security3a.sso.SecurityConfig;


public class RoleManageTag extends SimpleTagSupport {

	private boolean tree;

	public void doTag() throws JspException {

		String url = SecurityConfig.getInstance().getSsoServerUrl()
				+ "/rbac/rbacIndex.do";

		try {
			getJspContext().getOut().print(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean isTree() {
		return tree;
	}

	public void setTree(boolean tree) {
		this.tree = tree;
	}
}
