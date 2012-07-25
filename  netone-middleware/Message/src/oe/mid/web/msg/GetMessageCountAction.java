package oe.mid.web.msg;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.rmi.client.RmiEntry;
import oe.rmi.message.MessageHandle;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.Clerk;
import oe.security3a.sso.onlineuser.DefaultOnlineUserMgr;
import oe.security3a.sso.onlineuser.OnlineUserMgr;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 获得消息总数
 */

public class GetMessageCountAction extends Action {

	static ResourceBundle mail = ResourceBundle.getBundle("mail", Locale.CHINESE);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		
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

			int dataNumber = msgHandle.totalNum(loginname, null);// 总记录数
			request.setAttribute("totalcount", dataNumber);
			System.out.println(mail.getString("mail.url"));
			request.setAttribute("mail_url", mail.getString("mail.url"));
			return mapping.findForward("countIndex");
			
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		return mapping.getInputForward();

	}

}
