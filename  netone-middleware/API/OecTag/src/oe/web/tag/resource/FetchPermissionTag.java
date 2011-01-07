package oe.web.tag.resource;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsRolepermission;

/**
 * 
 * 获得一个角色的所有授权
 * 
 * @author wsz
 * 
 */
public class FetchPermissionTag extends TagSupport {

	private String roleId;// 角色ID

	private String dataname;// 返回结果

	public int doEndTag() throws JspException {
		HttpServletRequest request = (HttpServletRequest) this.pageContext
				.getRequest();

		List<UmsRolepermission> list = new ArrayList<UmsRolepermission>();
		try {
			ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");
			list = rsrmi.fetchPermission(roleId);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		request.setAttribute(dataname, list);
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
