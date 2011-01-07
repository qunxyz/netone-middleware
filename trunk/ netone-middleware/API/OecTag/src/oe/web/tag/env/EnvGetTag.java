package oe.web.tag.env;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import oe.env.client.EnvService;
import oe.rmi.client.RmiEntry;


/**
 * 
 * 
 * 
 * <p>
 * <li>读取系统环境信息</li>
 * </p>
 * <description></description> × author chen.jia.xun mail: oesee@163.com
 * 
 * 
 * @version 1.0.0 date 2008-11-25 created by chen.jia.xun（Robanco）
 */
public class EnvGetTag extends SimpleTagSupport {

	private String envkey;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.jsp.tagext.SimpleTagSupport#doTag()
	 */
	@Override
	public void doTag() throws JspException, IOException {
		EnvService env = null;

		try {

			env = (EnvService) RmiEntry.iv("envinfo");
			String value = env.fetchEnvValue(envkey);
			this.getJspContext().getOut().print(value);

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

	}

	public String getEnvkey() {
		return envkey;
	}

	public void setEnvkey(String envkey) {
		this.envkey = envkey;
	}

}
