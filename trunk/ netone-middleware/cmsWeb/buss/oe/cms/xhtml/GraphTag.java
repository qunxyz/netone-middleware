package oe.cms.xhtml;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import oe.cms.CmsEntry;
import oe.cms.xhtml2.ViewInterface;

public class GraphTag extends SimpleTagSupport {
	String[] dimvaluelist;

	String dimName;

	String[][] targetvaluelist;

	String[] targetname;

	String charttype;

	String title;

	String graphwidth;

	String graphheight;

	public void doTag() throws JspException {

		ViewInterface htmlh = (ViewInterface) CmsEntry.fetchBean("vi");
		String graph = htmlh.fetchGraph(dimvaluelist, dimName, targetvaluelist,
				targetname, charttype, title, graphwidth, graphheight);
		try {
			getJspContext().getOut().write(graph);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getCharttype() {
		return charttype;
	}

	public void setCharttype(String charttype) {
		this.charttype = charttype;
	}

	public String getDimName() {
		return dimName;
	}

	public void setDimName(String dimName) {
		this.dimName = dimName;
	}

	public String[] getDimvaluelist() {
		return dimvaluelist;
	}

	public void setDimvaluelist(String[] dimvaluelist) {
		this.dimvaluelist = dimvaluelist;
	}

	public String getGraphheight() {
		return graphheight;
	}

	public void setGraphheight(String graphheight) {
		this.graphheight = graphheight;
	}

	public String getGraphwidth() {
		return graphwidth;
	}

	public void setGraphwidth(String graphwidth) {
		this.graphwidth = graphwidth;
	}

	public String[] getTargetname() {
		return targetname;
	}

	public void setTargetname(String[] targetname) {
		this.targetname = targetname;
	}

	public String[][] getTargetvaluelist() {
		return targetvaluelist;
	}

	public void setTargetvaluelist(String[][] targetvaluelist) {
		this.targetvaluelist = targetvaluelist;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
