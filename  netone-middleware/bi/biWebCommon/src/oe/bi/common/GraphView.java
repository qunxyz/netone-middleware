package oe.bi.common;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import oe.env.client.EnvService;
import oe.frame.orm.OrmerEntry;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

public class GraphView extends SimpleTagSupport {
	private String charname;

	private String style;

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getCharname() {
		return charname;
	}

	public void setCharname(String charname) {
		this.charname = charname;
	}

	public void doTag() throws JspException {
		String url = null;
		EnvService env;
		ResourceRmi rmi;
		try {
			rmi = (ResourceRmi) RmiEntry.iv("resource");
			String id = rmi.loadResourceByNatural(charname).getId();
			env = (EnvService) RmiEntry.iv("envinfo");
			String biinfo = env.fetchEnvValue("WEBSER_BIWEB");

			url = biinfo + "/MakeChartSvl?chkid=" + id;

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

		try {
			getJspContext().getOut().print(
					"<img src=" + url + " " + style + "</>");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
