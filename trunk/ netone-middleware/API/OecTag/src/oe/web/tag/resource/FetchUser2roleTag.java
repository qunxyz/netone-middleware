package oe.web.tag.resource;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.User2Role;
import oe.security3a.seucore.obj.db.UmsRole;
import oe.security3a.sso.onlineuser.DefaultOnlineUserMgr;
import oe.security3a.sso.onlineuser.OnlineUser;
import oe.security3a.sso.onlineuser.OnlineUserMgr;

/**
 * 获得所有符合条件的用户角色关系
 * @author wsz
 *
 */
public class FetchUser2roleTag extends TagSupport {

	private String code;//隶属于
	private String condition;//查询条件
	private String dataname;//输出信息

	public int doEndTag() throws JspException {

		HttpServletRequest request = (HttpServletRequest) this.pageContext
				.getRequest();

		List<UmsRole> list = new ArrayList<UmsRole>();
		User2Role user2role = new User2Role();

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
			list = rsrmi.fetchUser2role(code, user2role, null, condition);
		} catch (Exception e) {
			e.printStackTrace();
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
