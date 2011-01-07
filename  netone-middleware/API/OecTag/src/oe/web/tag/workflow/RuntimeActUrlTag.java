package oe.web.tag.workflow;

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
 * <li>返回指定流程的交互地址</li>
 * </p>
 * <description></description> × author chen.jia.xun mail: oesee@163.com
 * 
 * 
 * @version 1.0.0 date 2008-11-25 created by chen.jia.xun（Robanco）
 */
public class RuntimeActUrlTag extends SimpleTagSupport {

	private String runtimeid;

	public String getRuntimeid() {
		return runtimeid;
	}

	public void setRuntimeid(String runtimeid) {
		this.runtimeid = runtimeid;
	}

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
			String urlHead = env.fetchEnvValue("WEBSER_WFWEB")
					+ "/listRuntimeprocess.do?runtimeid=" + runtimeid;
			this.getJspContext().getOut().print(urlHead);

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
