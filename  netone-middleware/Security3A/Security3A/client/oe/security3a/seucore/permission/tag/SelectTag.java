package oe.security3a.seucore.permission.tag;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;

public class SelectTag extends AbstractUITag {
	
	private String multiple ;
	
	private String size ;
	
	public String getMultiple() {
		return multiple;
	}


	public void setMultiple(String multiple) {
		this.multiple = multiple;
	}


	public String getSize() {
		return size;
	}


	public void setSize(String size) {
		this.size = size;
	}


	public int doStartTag() throws JspTagException {
		if(!checkLogin()){
			return SKIP_PAGE;
		}
		else{
			JspWriter w = pageContext.getOut();
			try {
				StringBuffer sb = new StringBuffer();
				sb.append("<select ");
				if(!checkPermission()){
					sb.append(" disabled=\"disabled\" ");
				}
				sb.append(createAttrContent());
				sb.append(" >");
				w.print(sb.toString());
			} catch (Exception e) {
				throw new JspTagException(e);
			} 
		}
		return (EVAL_BODY_INCLUDE);
	}
	
	
	public int doEndTag() throws JspTagException {
		JspWriter w = pageContext.getOut();
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("</select>");
			w.print(sb.toString());
		} catch (Exception e) {
			throw new JspTagException(e);
		} 
		return EVAL_PAGE;
	}
}
