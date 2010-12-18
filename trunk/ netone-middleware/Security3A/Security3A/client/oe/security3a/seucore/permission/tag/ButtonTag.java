package oe.security3a.seucore.permission.tag;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;

public class ButtonTag extends AbstractUITag {
	
	public int doEndTag() throws JspTagException {
		if(!checkLogin()){
			return SKIP_PAGE;
		}
		else{
			JspWriter w = pageContext.getOut();
			try {
				StringBuffer sb = new StringBuffer();
				sb.append("<input type=\"button\" ");
				if(!checkPermission()){
					sb.append(" disabled=\"disabled\" ");
				}
				sb.append(createAttrContent());
				sb.append(" />");
				w.print(sb.toString());
			} catch (Exception e) {
				throw new JspTagException(e);
			} 
			return EVAL_PAGE;
		}
	}
}
