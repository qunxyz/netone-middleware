package oe.cms.xhtml;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import oe.cms.CmsEntry;
import oe.cms.xhtml2.ViewInterface;

public class Graph2Tag extends SimpleTagSupport {
	String[] dimvaluelist;

	String dimName;

	String[][] targetvaluelistLeft;

	String[][] targetvaluelistRight;

	String[] targetnameLeft;

	String[] targetnameLRight;

	String title;

	boolean is3D;

	String graphwidth;

	String graphheight;

	public void doTag() throws JspException {
		String is3DStr = is3D ? "true" : "false";
		ViewInterface htmlh = (ViewInterface) CmsEntry.fetchBean("vi");
		String graph = htmlh.fetchGraph2Coordinate(dimvaluelist, dimName,
				targetvaluelistLeft, targetvaluelistRight, targetnameLeft,
				targetnameLRight, title, is3DStr, graphwidth, graphheight);
		try {
			getJspContext().getOut().write(graph);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	public boolean isIs3D() {
		return is3D;
	}

	public void setIs3D(boolean is3D) {
		this.is3D = is3D;
	}

	public String[] getTargetnameLeft() {
		return targetnameLeft;
	}

	public void setTargetnameLeft(String[] targetnameLeft) {
		this.targetnameLeft = targetnameLeft;
	}

	public String[] getTargetnameLRight() {
		return targetnameLRight;
	}

	public void setTargetnameLRight(String[] targetnameLRight) {
		this.targetnameLRight = targetnameLRight;
	}

	public String[][] getTargetvaluelistLeft() {
		return targetvaluelistLeft;
	}

	public void setTargetvaluelistLeft(String[][] targetvaluelistLeft) {
		this.targetvaluelistLeft = targetvaluelistLeft;
	}

	public String[][] getTargetvaluelistRight() {
		return targetvaluelistRight;
	}

	public void setTargetvaluelistRight(String[][] targetvaluelistRight) {
		this.targetvaluelistRight = targetvaluelistRight;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
