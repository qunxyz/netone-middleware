package oe.security3a.seucore.permission.tag;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;


import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

public class FlowTag extends AbstractUITag {
	private String pagename;

	public int doEndTag() throws JspTagException {
		String pageid = null;
		ResourceRmi rmi;
		try {
			if (pagename != null && !pagename.equals("")) {
				rmi = (ResourceRmi) RmiEntry.iv("resource");
				UmsProtectedobject upo = rmi.loadResourceByNatural(pagename);
				pageid = upo.getId();
			}

		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NotBoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		JspWriter w = pageContext.getOut();
		try {

			if (pageid != null) {
				w.print(pageid + ".jsp");
			}

		} catch (Exception e) {
			throw new JspTagException(e);
		}
		return EVAL_PAGE;

	}

	public String getPagename() {
		return pagename;
	}

	public void setPagename(String pagename) {
		this.pagename = pagename;
	}

}
