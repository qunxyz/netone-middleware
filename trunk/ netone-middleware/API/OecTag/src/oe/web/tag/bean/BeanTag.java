package oe.web.tag.bean;

import java.io.IOException;
import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.DynamicAttributes;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import oe.mid.soa.bean.BeanService;
import oe.mid.soa.bean.ClassProperty;
import oe.mid.soa.bean.SoaBean;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

/**
 * Bean标签
 * 
 * @author chen.jia.xun(Robanco)<br>
 *         mail:56414429@qq.com, chenjiaxun@oesee.com<br>
 *         tel:13328675083<br>
 */
public class BeanTag extends SimpleTagSupport implements DynamicAttributes {
	private Map vars = new HashMap();

	private String varname;

	private String beanname;

	public String getBeanname() {
		return beanname;
	}

	public void setBeanname(String beanname) {
		this.beanname = beanname;
	}

	public void doTag() throws JspException, IOException {
		String beannameuse = null;
		String beanurl = null;
		try {
			ResourceRmi resourceRmi = (ResourceRmi) RmiEntry.iv("resource");
			UmsProtectedobject pnj = (UmsProtectedobject) resourceRmi
					.loadResourceByNatural(beanname);
			String extet = pnj.getExtendattribute();
			if (extet == null) {
				return;
			}
			String[] extarr = extet.split("#");
			if (extarr.length != 2) {
				return;
			}
			beannameuse = extarr[1];
			beanurl = extarr[0];
		} catch (NotBoundException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}

		String testtip = "";

		BeanService beansv = null;
		try {
			beansv = (BeanService) RmiEntry.ivCore(beanurl);
		} catch (NotBoundException e1) {
			// TODO Auto-generated catch block
			testtip = e1.getMessage() + "未绑定 ";
			throw new RuntimeException(testtip);

		} catch (ConnectException e2) {
			testtip = e2.getMessage() + "无法连接 ";
			throw new RuntimeException(testtip);
		}
		try {
			String beanmonitorurl = beanurl;
			BeanService bean = (BeanService) RmiEntry.ivCore(beanmonitorurl);
			SoaBean soabean = bean.inParamDescription(beannameuse);
			setValuelogic(soabean);
			SoaBean rs = bean.todo(soabean);
			
			this.getJspContext().setAttribute(varname, rs);
		} catch (Exception e) {
			e.printStackTrace();
			testtip = e.getMessage();
		}
	}

	private void setValuelogic(SoaBean soabean) {
		Map mapValuedsc = soabean.getClassproperty();

		for (Iterator iterator = mapValuedsc.keySet().iterator(); iterator
				.hasNext();) {
			String key = (String) iterator.next();
			ClassProperty pro = (ClassProperty) mapValuedsc.get(key);

			String value = (String) vars.get(key);
			if ("java.lang.Integer".equals(pro.getType())) {
				vars.put(key, new Integer(value));
			} else if ("java.lang.Double".equals(pro.getType())) {
				vars.put(key, new Double(value));
			} else if ("java.lang.Float".equals(pro.getType())) {
				vars.put(key, new Float(value));
			} else if ("java.lang.Boolean".equals(pro.getType())) {
				vars.put(key, new Boolean(value));
			}
		}
		soabean.setValues(vars);
	}

	public void setDynamicAttribute(String uri, String localName, Object value)
			throws JspException {
		if (vars.containsKey(localName)) {
			throw new RuntimeException("参数:" + localName + " 已定义过存在重复定义");
		}
		vars.put(localName, value);
	}

	public String getVarname() {
		return varname;
	}

	public void setVarname(String varname) {
		this.varname = varname;
	}

}
