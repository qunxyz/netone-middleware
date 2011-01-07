package oe.web.tag.resource;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;
import oe.security3a.seucore.obj.db.UmsRole;

/**
 * ������Դ�ĸ��ڵ�ID������е��ӽڵ�
 * 
 * @author wsz
 * 
 */
public class SubResourceTag extends TagSupport {

	private String parentId;//���ڵ�ID

	private String dataname;// ������Ϣ

	public int doEndTag() throws JspException {

		HttpServletRequest request = (HttpServletRequest) this.pageContext
				.getRequest();
		List<UmsProtectedobject> list = new ArrayList<UmsProtectedobject>();

		try {
			ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");
			list = rsrmi.subResource(parentId);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		request.setAttribute(dataname, list);
		return EVAL_PAGE;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getDataname() {
		return dataname;
	}

	public void setDataname(String dataname) {
		this.dataname = dataname;
	}



}
