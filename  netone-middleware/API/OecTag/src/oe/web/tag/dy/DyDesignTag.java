package oe.web.tag.dy;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import oe.midware.dyform.service.DyFormService;
import oe.rmi.client.RmiEntry;

public class DyDesignTag extends SimpleTagSupport {
	private String formcode;

	private String dataname;

	public void doTag() throws JspException {
		if (formcode == null) {
			return;
		}

		DyFormService dy = null;
		try {
			dy = (DyFormService) RmiEntry.iv("dyhandle");
			List list = dy.fetchColumnList(formcode);
			getJspContext().setAttribute(dataname, list);

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

	public String getFormcode() {
		return formcode;
	}

	public void setFormcode(String formcode) {
		this.formcode = formcode;
	}

	public String getDataname() {
		return dataname;
	}

	public void setDataname(String dataname) {
		this.dataname = dataname;
	}

}
