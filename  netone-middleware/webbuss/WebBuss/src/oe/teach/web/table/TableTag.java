package oe.teach.web.table;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import oe.frame.web.WebCache;
import oe.teach.mid.buss.MyAccount;
import oe.teach.mid.bussmap.CommSelecTools;

public class TableTag extends TagSupport {
	String ds;

	String data;

	String key;

	int limit = 10;

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

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public int doEndTag() throws JspException {
		HttpServletRequest request = (HttpServletRequest) this.pageContext
				.getRequest();
		if (key != null) {
			if (WebCache.containCache(key)) {
				List obj = (List) WebCache.getCache(key);
				request.setAttribute(data, obj);
				return EVAL_PAGE;
			}
		}

		List list = (new CommSelecTools()).fetchdata(ds);
		request.setAttribute(data, list);
		if (key != null) {
			WebCache.setCache(key, list, new Date(
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

}
