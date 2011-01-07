package oe.web.tag.mail;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import oe.rmi.client.RmiEntry;
import oe.rmi.message.MessageHandle;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.Clerk;
import oe.security3a.sso.onlineuser.DefaultOnlineUserMgr;
import oe.security3a.sso.onlineuser.OnlineUserMgr;

public class GetMailCountTag extends TagSupport {

	public int doEndTag() throws JspTagException {

		HttpServletRequest request = (HttpServletRequest) this.pageContext
				.getRequest();
		try {
			MessageHandle msgHandle = (MessageHandle) RmiEntry.iv("msghandle");
			OnlineUserMgr oum = new DefaultOnlineUserMgr();
			String loginname = oum.getOnlineUser(request).getLoginname();
			String code = oum.getOnlineUser(request).getBelongto();
			ResourceRmi rmi = (ResourceRmi) RmiEntry.iv("resource");
			Clerk clerk;

			clerk = rmi.loadClerk(code, loginname);

			msgHandle.setEmail(loginname);
			msgHandle.setPassword(clerk.getPassword());

			int dataNumber = 0;
			dataNumber = msgHandle.totalNum(loginname, null);// ×Ü¼ÇÂ¼Êý

			JspWriter w = this.pageContext.getOut();
			w.print(dataNumber);

		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return EVAL_PAGE;
	}

}
