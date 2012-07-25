package oe.mid.web.msg;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.frame.web.form.RequestParamMap;
import oe.frame.web.form.RequestUtil;
import oe.rmi.client.RmiEntry;
import oe.rmi.message.MessageHandle;
import oe.rmi.message.UmsBussformworklist;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.Clerk;
import oe.security3a.sso.onlineuser.DefaultOnlineUserMgr;
import oe.security3a.sso.onlineuser.OnlineUser;
import oe.security3a.sso.onlineuser.OnlineUserMgr;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 发送消息
 * 
 * @author chen.jia.xun(Robanco)<br>
 *         mail:56414429@qq.com, chenjiaxun@oesee.com<br>
 *         tel:13328675083<br>
 */
public class SendMessageAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		RequestParamMap reqmap = RequestUtil.setParamMapToRequest(request);

		try {
			MessageHandle msgHandle = (MessageHandle) RmiEntry.iv("msghandle");

			OnlineUserMgr oum = new DefaultOnlineUserMgr();
			OnlineUser useronline = oum.getOnlineUser(request);
			String loginname = useronline.getLoginname();
			String code = useronline.getBelongto();

			ResourceRmi rmi = (ResourceRmi) RmiEntry.iv("resource");
			Clerk clerk = rmi.loadClerk(code, loginname);
			msgHandle.setEmail(loginname);
			msgHandle.setPassword(clerk.getPassword());
			// 发送消息
			if ("sendmessage".equals(reqmap.getParameter("task"))) {
				String title = reqmap.getParameter("title");
				String msg = reqmap.getParameter("content");
				String users = reqmap.getParameter("recevier");
				String extattr3 = reqmap.getParameter("extattr3");
				String extattr4 = reqmap.getParameter("extattr4");

				// 将服务器基地址存在extattr4中
				String path = request.getContextPath();
				String basePath = request.getScheme() + "://"
						+ request.getServerName() + ":"
						+ request.getServerPort() + path + "/";
				extattr4 = basePath;

				String[] user = users.split(",");
				for (String u : user) {
					UmsBussformworklist ubf = new UmsBussformworklist();
					ubf.setParticipant(StringUtils.trim(StringUtils
							.substringBefore(u, "[")));
					ubf.setExtattr1(title);
					ubf.setExtattr2(msg);
					ubf.setExtattr3(extattr3);
					ubf.setExtattr4(extattr4);
					ubf.setSender(loginname);
					msgHandle.send(ubf);
				}
				reqmap.setAlertMsg("发送消息成功！");
			} else if ("seturl".equals(reqmap.getParameter("task"))) {// 来自nlnmcpms外部"启动"设值
				String title = reqmap.getParameter("title");
				String content = reqmap.getParameter("content");
				String recevier = reqmap.getParameter("recevier");
				String extattr3 = reqmap.getParameter("extattr3");
				String extattr4 = reqmap.getParameter("extattr4");
				request.setAttribute("title", title);
				request.setAttribute("content", content);
				request.setAttribute("recevier", recevier);
				request.setAttribute("extattr3", extattr3);
				request.setAttribute("extattr4", extattr4);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
			reqmap.setAlertMsg("发送消息失败！");
		} catch (RemoteException e) {
			e.printStackTrace();
			reqmap.setAlertMsg("发送消息失败！");
		} catch (NotBoundException e) {
			e.printStackTrace();
			reqmap.setAlertMsg("发送消息失败！");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("sendmessage");
	}
}
