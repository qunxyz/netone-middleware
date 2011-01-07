package oe.web.tag.resource;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsRole;

/**
 * ��ѯ���������Ľ�ɫ
 * 
 * @author wsz
 * 
 */
public class FetctRoleTag extends TagSupport {

	private String condition;//��ѯ����

	private String dataname;// ��ѯ���ؽ��

	public int doEndTag() throws JspException {

		HttpServletRequest request = (HttpServletRequest) this.pageContext
				.getRequest();

		UmsRole umsRole = new UmsRole();
		List<UmsRole> list = new ArrayList<UmsRole>();
		try {
			ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");
			list = rsrmi.fetchRole(umsRole, null, condition);
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute(dataname, list);

		return EVAL_PAGE;
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
