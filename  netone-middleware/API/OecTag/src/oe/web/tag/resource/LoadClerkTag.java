package oe.web.tag.resource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.Clerk;
import oe.security3a.sso.onlineuser.DefaultOnlineUserMgr;
import oe.security3a.sso.onlineuser.OnlineUser;
import oe.security3a.sso.onlineuser.OnlineUserMgr;

/**
 * 加载职员
 * 
 * @author wsz
 * 
 */
public class LoadClerkTag extends TagSupport {

	private String code;// 用户隶属于
	private String username;// 用户名
	private String dataname;// 返回加载得到的clerk信息

	public int doEndTag() throws JspException {
		HttpServletRequest request = (HttpServletRequest) this.pageContext
				.getRequest();
		if (null == code || "".equals(code.trim())) {
			OnlineUserMgr olmgr = new DefaultOnlineUserMgr();
			OnlineUser oluser = olmgr.getOnlineUser(request);
			if (oluser != null) {
				code = oluser.getBelongto();
			} else {
				return EVAL_PAGE;
			}
		}
		if ("".equals(username.trim())) {
			return EVAL_PAGE;
		}
		Clerk clerk = new Clerk();
		try {
			ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");
			clerk = rsrmi.loadClerk(code, username);
		} catch (Exception e) {
			e.printStackTrace();
		}

		request.setAttribute(dataname, clerk);
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
