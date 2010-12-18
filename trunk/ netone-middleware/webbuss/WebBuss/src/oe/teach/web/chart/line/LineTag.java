package oe.teach.web.chart.line;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import oe.frame.web.WebCache;
import oe.teach.web.chart.ChartMaker;

public class LineTag extends TagSupport {
	String ds;

	String zcolumn;

	String title;

	String ytitle;

	String xtitle;

	String width;

	String height;
	
	String uri;
	
	String key;
	
	int limit=10;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public int doEndTag() throws JspException {
		HttpServletRequest request = (HttpServletRequest) this.pageContext
				.getRequest();
		if(key!=null){
			if(WebCache.containCache(key)){
				String url=(String)WebCache.getCache(key) ;
				request.setAttribute(uri, url);
				return EVAL_PAGE;
			}
		}
		
		ChartMaker line = null;
		String url = null;
		if (zcolumn == null) {
			line = new LineMakerImpl2();
			url = line.todo(request, ds, title, ytitle, xtitle, width, height);
		} else {

			line = new LineMakerImpl3();
			url = line.todo(request, ds + "#" + zcolumn, title, ytitle, xtitle,
					width, height);
		}

		request.setAttribute(uri, url);
		if(key!=null){//º”»Îª∫¥Ê
			WebCache.setCache(key, url, new Date(
					(System.currentTimeMillis() + limit * 60 * 1000)));
		}
		return EVAL_PAGE;

	}

	public String getDs() {
		return ds;
	}

	public void setDs(String ds) {
		this.ds = ds;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getYtitle() {
		return ytitle;
	}

	public void setYtitle(String ytitle) {
		this.ytitle = ytitle;
	}

	public String getXtitle() {
		return xtitle;
	}

	public void setXtitle(String xtitle) {
		this.xtitle = xtitle;
	}

	public String getZcolumn() {
		return zcolumn;
	}

	public void setZcolumn(String zcolumn) {
		this.zcolumn = zcolumn;
	}

}
