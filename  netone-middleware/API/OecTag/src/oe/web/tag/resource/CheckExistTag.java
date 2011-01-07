package oe.web.tag.resource;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;

/**
 * 根据资源的naturalname来判断该资源是否存在
 * 
 * @author wsz
 * 
 */
public class CheckExistTag extends TagSupport {

	private String naturalname;// 资源的naturalname

	private String dataname;// 查询返回结果

	public int doEndTag() throws JspException {
		HttpServletRequest request = (HttpServletRequest) this.pageContext
				.getRequest();
		boolean exist = false;
		try {
			ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");
			exist = rsrmi.checkExist(naturalname);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		try {
			if (exist == true) {
				request.setAttribute(dataname, "y");
			} else {
				request.setAttribute(dataname, "n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}

	public String getNaturalname() {
		return naturalname;
	}

	public void setNaturalname(String naturalname) {
		this.naturalname = naturalname;
	}

	public String getDataname() {
		return dataname;
	}

	public void setDataname(String dataname) {
		this.dataname = dataname;
	}



}
