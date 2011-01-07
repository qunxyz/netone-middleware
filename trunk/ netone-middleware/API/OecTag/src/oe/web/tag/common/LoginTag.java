package oe.web.tag.common;

import javax.servlet.jsp.JspTagException;

public class LoginTag extends AbstractUITag {
	public int doEndTag() throws JspTagException {
		if(!checkLogin()){
			return SKIP_PAGE;
		}
		return EVAL_PAGE;
	}
}
