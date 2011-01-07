package oe.web.tag.resource;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import oe.frame.web.WebCache;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;

import oe.security3a.seucore.obj.db.UmsProtectedobject;

/**
 * 查询符合条件的资源
 * 
 * @author wsz
 * 
 */
public class FetchResourceTag extends TagSupport {

	private String condition;// 查询条件

	private String dataname;// 查询返回结果

	private int prepage = 5;

	public int getPrepage() {
		return prepage;
	}

	public void setPrepage(int prepage) {
		this.prepage = prepage;
	}

	public int doEndTag() throws JspException {
		HttpServletRequest request = (HttpServletRequest) this.pageContext
				.getRequest();

		String cache = dataname + condition;

		if (WebCache.containCache(cache)) {
			request.setAttribute(dataname, WebCache.getCache(cache));

			return EVAL_PAGE;
		}

		UmsProtectedobject upo = new UmsProtectedobject();

		List<UmsProtectedobject> list = new ArrayList<UmsProtectedobject>();

		try {
			ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");
			list = rsrmi.fetchResource(upo, null, condition);
			WebCache.setCache(cache, list, new Date(
					System.currentTimeMillis() + 3600000));
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute(dataname, list.subList(0, prepage));

		return EVAL_PAGE;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getDataname() {
		return dataname;
	}

	public void setDataname(String dataname) {
		this.dataname = dataname;
	}

}
