package oe.web.tag.resource;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.Clerk;
import oe.security3a.sso.onlineuser.DefaultOnlineUserMgr;
import oe.security3a.sso.onlineuser.OnlineUser;
import oe.security3a.sso.onlineuser.OnlineUserMgr;

/**
 * 根据角色ID，获得拥有该角色的用户
 * 
 * @author wsz
 * 
 */
public class FetchUserByroleIdTag extends TagSupport {

	private String code;//用户隶属于

	private String roleId;//角色id

	private String dataname;//返回信息

	public int doEndTag() throws JspException {
		HttpServletRequest request = (HttpServletRequest) this.pageContext
				.getRequest();
		List<Clerk> list = new ArrayList<Clerk>();
		if (null == code || "".equals(code.trim())) {
			OnlineUserMgr olmgr = new DefaultOnlineUserMgr();
			OnlineUser oluser = olmgr.getOnlineUser(request);
			if (oluser != null) {
				code = oluser.getBelongto();
			} else {
				return EVAL_PAGE;
			}
		}
		try {
			ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");
			list = rsrmi.fetchUser(code, roleId);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		request.setAttribute(dataname, list);
		return EVAL_PAGE;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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
