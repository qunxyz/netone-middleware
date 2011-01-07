package oe.web.tag.resource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;
import oe.security3a.seucore.obj.db.UmsRole;

/**
 * 根据角色的ID来装载角色
 * 
 * @author wsz
 * 
 */
public class LoadRoleTag extends TagSupport {

	private String roleId;//角色ID

	private String dataname;// 返回信息

	public int doEndTag() throws JspException {

		HttpServletRequest request = (HttpServletRequest) this.pageContext
				.getRequest();
		UmsRole role = new UmsRole();

		try {
			ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");
			Long roleId_l = Long.valueOf(roleId);
			role = rsrmi.loadRole(roleId_l);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		request.setAttribute(dataname, role);
		return EVAL_PAGE;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getDataname() {
		return dataname;
	}

	public void setDataname(String dataname) {
		this.dataname = dataname;
	}



}
