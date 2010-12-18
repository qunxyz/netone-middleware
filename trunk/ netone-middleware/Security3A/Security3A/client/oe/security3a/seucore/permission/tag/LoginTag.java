package oe.security3a.seucore.permission.tag;

import javax.servlet.jsp.JspTagException;

public class LoginTag extends AbstractUITag {
	public int doEndTag() throws JspTagException {
		if(!checkLogin()){
			return SKIP_PAGE;
		}
		return EVAL_PAGE;
	}
}
