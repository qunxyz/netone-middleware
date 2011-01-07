package oe.web.tag.resource;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import oe.frame.web.WebCache;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

/**
 * 根据资源的naturalname来加载资源
 * @author wsz
 *
 */
public class LoadResourceByNaturalTag extends TagSupport {

	private String naturalname;// 资源的naturalname

	private String dataname;// 返回信息

	public int doEndTag() throws JspException {

		HttpServletRequest request = (HttpServletRequest) this.pageContext
				.getRequest();
		
		String cache=naturalname+dataname;
		
		if(WebCache.containCache(cache)){
			request.setAttribute(dataname, WebCache.getCache(cache));
			return EVAL_PAGE;
		}
		
		UmsProtectedobject upo = new UmsProtectedobject();

		try {
			ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");
			upo = rsrmi.loadResourceByNatural(naturalname);
			WebCache.setCache(cache, upo, new Date(
						System.currentTimeMillis() + 3600000));
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		request.setAttribute(dataname, upo);
		return EVAL_PAGE;
	}

	public String getNaturalname() {
		return naturalname;
	}

	public void setNaturalname(String naturalname) {
		this.naturalname = naturalname;
	}

	public String getDataname() {
		return dataname;
	}

	public void setDataname(String dataname) {
		this.dataname = dataname;
	}



}
