package oe.cms.jsp;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import oe.cms.CmsEntry;
import oe.cms.jsp.util.GraphSingleOffset;
import oe.cms.xhtml.core.graph.util.ChartCreater;
import oe.cms.xhtml2.ViewInterface;

public class GraphBarLine extends SimpleTagSupport {
	String dimvalue;

	String dimname;

	String tarvalue;

	String tarname;

	String title;

	String width;

	String height;

	public void doTag() throws JspException {
		String info = GraphSingleOffset
				.todo(dimvalue, dimname, tarvalue, tarname, title, width,
						height, ChartCreater.CHART_CombinedBarLin);
		try {
			getJspContext().getOut().write(info);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
