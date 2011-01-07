package oe.web.tag.env;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import oe.env.client.EnvService;
import oe.midware.workflow.service.WorkflowConsole;
import oe.midware.workflow.service.WorkflowView;
import oe.rmi.client.RmiEntry;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * 
 * 
 * <p>
 * <li>设置系统环境信息</li>
 * </p>
 * <description></description> × author chen.jia.xun mail: oesee@163.com
 * 
 * 
 * @version 1.0.0 date 2008-11-25 created by chen.jia.xun（Robanco）
 */
public class EnvSetTag extends SimpleTagSupport {

	private String envkey;
	private String value;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.jsp.tagext.SimpleTagSupport#doTag()
	 */
	public String getEnvkey() {
		return envkey;
	}

	public void setEnvkey(String envkey) {
		this.envkey = envkey;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public void doTag() throws JspException, IOException {
		EnvService env = null;

		try {

			env = (EnvService) RmiEntry.iv("envinfo");
			if (envkey != null && !envkey.equals("")) {
				env.addEnv(envkey, value);
			}

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
}
