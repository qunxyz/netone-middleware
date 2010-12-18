package oe.security3a.seucore.permission.tag;

import javax.servlet.jsp.JspTagException;

public class PermissionTag extends AbstractUITag {
	public int doStartTag() throws JspTagException {
		if(!checkLogin()){
			return SKIP_PAGE;
		}
		if (checkPermission()){
			return (EVAL_BODY_INCLUDE);
		}
		else{
			return (SKIP_BODY);
		}
	}

	public int doEndTag() throws JspTagException {
		return (EVAL_PAGE);
	}
}
