package oe.web.tag.resource;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;


/**
 * 根据资源的ID来装载资源
 * @author wsz
 *
 */
public class LoadResourceByIdTag extends TagSupport {
	
	private String id;//资源ID
	private String dataname;// 返回信息
	public int doEndTag() throws JspException {
		HttpServletRequest request = (HttpServletRequest) this.pageContext
				.getRequest();
		UmsProtectedobject upo = new UmsProtectedobject();
			
		try {
			ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");
			upo =rsrmi.loadResourceById(id);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		request.setAttribute(dataname, upo);
		return EVAL_PAGE;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDataname() {
		return dataname;
	}
	public void setDataname(String dataname) {
		this.dataname = dataname;
	}


}
