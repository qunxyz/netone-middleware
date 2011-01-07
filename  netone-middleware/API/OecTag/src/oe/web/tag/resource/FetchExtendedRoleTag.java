package oe.web.tag.resource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsRole;

/**
 * 获得被roleid继承的角色
 * 
 * @author wsz
 * 
 */
public class FetchExtendedRoleTag extends TagSupport {

	private String roleId;// 角色id

	private String dataname;// 返回结果

	public int doEndTag() throws JspException {

		HttpServletRequest request = (HttpServletRequest) this.pageContext
				.getRequest();

		UmsRole umsRole = new UmsRole();
		try {
			ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");
			umsRole = rsrmi.fetchExtendedRole(roleId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute(dataname, umsRole);

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
