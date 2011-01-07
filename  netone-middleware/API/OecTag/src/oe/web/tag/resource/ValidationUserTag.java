package oe.web.tag.resource;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.Clerk;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

/**
 * 登陆验证
 * 
 * @author wsz
 * 
 */
public class ValidationUserTag extends TagSupport {

	private String code;//隶属于
	private String name;//用户名
	private String pass;//密码
	private String dataname;// 返回信息

	public int doEndTag() throws JspException {
		HttpServletRequest request = (HttpServletRequest) this.pageContext
				.getRequest();
		Clerk clerk = new Clerk();
		try {
			ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");
			clerk = rsrmi.validationUserOpe(code, name, pass);
		} catch (Exception e1) {
			e1.printStackTrace();
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getDataname() {
		return dataname;
	}

	public void setDataname(String dataname) {
		this.dataname = dataname;
	}



}
