package oe.web.tag.common;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

import oe.mid.soa.bean.BeanService;
import oe.mid.soa.bean.SoaBean;
import oe.rmi.client.RmiEntry;

/**
 * 该标签用户创建 Bean的参数对象SoaBean给前台使用
 * 
 * @author chen.jia.xun
 * 
 */
public class BeanModelDoTag extends BodyTagSupport {
	private String beanname;

	private String usename;

	public String getBeanname() {
		return beanname;
	}

	public void setBeanname(String beanname) {
		this.beanname = beanname;
	}

	public String getUsename() {
		return usename;
	}

	public void setUsename(String usename) {
		this.usename = usename;
	}

	public int doEndTag() throws JspException {
		try {
			BeanService beansv = (BeanService) RmiEntry.iv("beanhandle");
			String body = this.getBodyContent().getString();
			SoaBean be = beansv.inParamDescription(beanname);
			if (body != null && !body.equals("")) {
				String[] bodyInfo = body.split(";");
				for (int i = 0; i < bodyInfo.length; i++) {
					if (bodyInfo[i] != null && bodyInfo[i].length() > 0) {
						String[] keyvaluePre = bodyInfo[i].split(":");
						if (keyvaluePre != null && keyvaluePre.length == 2) {
							System.out.println(keyvaluePre[0].trim() + ":"
									+ keyvaluePre[1].trim());
							be.getValues().put(keyvaluePre[0].trim(),
									keyvaluePre[1].trim());
						}

					}
				}
			}
			beansv.todo(be.getLsh());
			System.out.println(usename);
			SoaBean beRet = beansv.loadOutParam(be.getLsh());
			System.out.println(beRet);
			this.pageContext.setAttribute(usename, beRet);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return EVAL_PAGE;

	}
}
