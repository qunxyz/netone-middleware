package oe.web.tag.resource;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.CupmRmi;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.Clerk;
import oe.security3a.sso.onlineuser.DefaultOnlineUserMgr;
import oe.security3a.sso.onlineuser.OnlineUser;
import oe.security3a.sso.onlineuser.OnlineUserMgr;

/**
 * 根据查询条件，查询职员
 * 
 * @author wsz
 * 
 */
public class FetchClerkTag extends TagSupport {

	private String code;// 用户隶属于

	private String condition;// 查询条件

	private String dataname;// 查询返回结果

	public int doEndTag() throws JspException {
		HttpServletRequest request = (HttpServletRequest) this.pageContext
				.getRequest();
		Clerk clerk = new Clerk();
		List<Clerk> list = new ArrayList<Clerk>();

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
			condition += " and statusinfo!=0";
			ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");
			list = rsrmi.fetchClerk(code, clerk, null, condition);
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute(dataname, list);

		return EVAL_PAGE;
	}



	public String getDataname() {
		return dataname;
	}



	public void setDataname(String dataname) {
		this.dataname = dataname;
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

}
