package oe.security3a.seucore.permission.tag;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;

public class TextareaTag extends AbstractUITag {
	
	private String rows ;
	private String cols ;
	
	public String getCols() {
		return cols;
	}


	public void setCols(String cols) {
		this.cols = cols;
	}


	public String getRows() {
		return rows;
	}


	public void setRows(String rows) {
		this.rows = rows;
	}


	public int doStartTag() throws JspTagException {
		if(!checkLogin()){
			return SKIP_PAGE;
		}
		else{
			JspWriter w = pageContext.getOut();
			try {
				StringBuffer sb = new StringBuffer();
				sb.append("<textarea ");
				if(!checkPermission()){
					sb.append(" readonly=\"readonly\" ");
				}
				sb.append(createAttrContent());
				sb.append(">");
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
			sb.append("</textarea>");
			w.print(sb.toString());
		} catch (Exception e) {
			throw new JspTagException(e);
		} 
		return EVAL_PAGE;
	}
}