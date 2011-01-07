package oe.web.tag.resource;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsRole;
import oe.security3a.sso.onlineuser.DefaultOnlineUserMgr;
import oe.security3a.sso.onlineuser.OnlineUser;
import oe.security3a.sso.onlineuser.OnlineUserMgr;

/**
 * 获得用户具有的角色
 * 
 * @author wsz
 * 
 */
public class GetUserRolesTag extends TagSupport {

	private String code;//隶属于
	
	private String username;//用户名

	private String dataname;// 返回信息

	public int doEndTag() throws JspException {
		HttpServletRequest request = (HttpServletRequest) this.pageContext
				.getRequest();
		List<UmsRole> list = new ArrayList<UmsRole>();
		
		// 如果不设置隶属于，则取当前登陆用户的隶属于
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
			list = rsrmi.getUserRoles(code, username);
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDataname() {
		return dataname;
	}

	public void setDataname(String dataname) {
		this.dataname = dataname;
	}


}
