package oe.teach.web.chart.pie;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import oe.frame.web.WebCache;
import oe.teach.web.chart.ChartMaker;

public class PieTag extends TagSupport {
	String ds;

	String title;

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

	public int doEndTag() throws JspException {
		HttpServletRequest request = (HttpServletRequest) this.pageContext
		.getRequest();
		if(key!=null){//使用缓冲
			if(WebCache.containCache(key)){
				String url=(String)WebCache.getCache(key) ;
				request.setAttribute(uri, url);
				return EVAL_PAGE;
			}
		}
		
		ChartMaker bar = new PieMakerImpl();
		String url = bar.todo(request, ds, title, "", "", width, height);
		request.setAttribute(uri, url);
		if(key!=null){//加入缓存
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

}
