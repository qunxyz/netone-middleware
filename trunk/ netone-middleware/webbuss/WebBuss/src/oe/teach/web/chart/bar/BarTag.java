package oe.teach.web.chart.bar;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import oe.frame.web.WebCache;
import oe.teach.web.chart.ChartMaker;

public class BarTag extends TagSupport {
	String ds;

	String title;

	String ytitle;

	String xtitle;

	String height;

	String width;

	String uri;

	String key;

	int limit = 10;

	public int doEndTag() throws JspException {
		HttpServletRequest request = (HttpServletRequest) this.pageContext
				.getRequest();
		if (key != null) {
			if (WebCache.containCache(key)) {
				String url = (String) WebCache.getCache(key);
				request.setAttribute(uri, url);
				return EVAL_PAGE;
			}
		}

		ChartMaker bar = new BarMakerImpl();

		String url = bar
				.todo(request, ds, title, xtitle, ytitle, width, height);

		request.setAttribute(uri, url);
		if (key != null) {// º”»Îª∫¥Ê
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

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

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


}
