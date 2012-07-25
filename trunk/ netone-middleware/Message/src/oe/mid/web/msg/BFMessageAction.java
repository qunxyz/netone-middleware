package oe.mid.web.msg;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.frame.web.form.RequestParamMap;
import oe.frame.web.form.RequestUtil;
import oe.frame.web.page.PageInfo;
import oe.rmi.client.RmiEntry;
import oe.rmi.message.MessageHandle;
import oe.rmi.message.UmsBussformworklist;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.Clerk;
import oe.security3a.sso.onlineuser.DefaultOnlineUserMgr;
import oe.security3a.sso.onlineuser.OnlineUser;
import oe.security3a.sso.onlineuser.OnlineUserMgr;
import oe.security3a.sso.util.Encryption;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;



/**
 * 个人消息(查看,回复,转发,删除)
 * 
 * @author chen.jia.xun(Robanco)<br>
 *         mail:56414429@qq.com, chenjiaxun@oesee.com<br>
 *         tel:13328675083<br>
 */
public class BFMessageAction extends Action {
	
	private static int _PRE_PAGE = 10;// 默认分页

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		RequestParamMap reqmap = RequestUtil.setParamMapToRequest(request);
		
		String usemail=request.getParameter("mail");
		request.setAttribute("mail",usemail);

		try {
			MessageHandle msgHandle = (MessageHandle) RmiEntry.iv("msghandle");
			// ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");
			List list = pagination(request, response);
			request.setAttribute("formlist", list);
			String workid = request.getParameter("workid");
			// 查看消息
			if ("look".equals(reqmap.getParameter("task"))) {
				toset(workid, request, msgHandle, "look");
				return mapping.findForward("lookmessage");
				// 回复消息
			} else if ("reply".equals(reqmap.getParameter("task"))) {
				toset(workid, request, msgHandle, "reply");
				return mapping.findForward("replymessage");
				// 转发消息
			} else if ("returnsend".equals(reqmap.getParameter("task"))) {
				toset(workid, request, msgHandle, "returnsend");
				return mapping.findForward("returnsendmessage");
			} else {
				if ("del".equals(reqmap.getParameter("task"))) {
					msgHandle.deleteMessage(workid);
					list = pagination(request, response);
					request.setAttribute("formlist", list);
				} else if ("multidel".equals(reqmap
						.getParameter("multideltask"))) {
					String[] multiworkid = reqmap
							.getParameterValues("multiworkid");
					msgHandle.deleteMessage(multiworkid);
					list = pagination(request, response);
					request.setAttribute("formlist", list);
				}
				if ("yes".equals(request.getParameter("short"))) {
					return mapping.findForward("shortmeg");
				}

			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.getInputForward();

	}

	private void toset(String workid, HttpServletRequest request,
			MessageHandle msgHandle, String mode) {
		UmsBussformworklist bussformworklist = null;
		try {
			bussformworklist = msgHandle.load(workid);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (workid.equals(bussformworklist.getWorkid())) {
			request.setAttribute("workid", workid);
			if ("reply".equals(mode)) {
				request.setAttribute("title", "Re: "
						+ bussformworklist.getExtattr1());
			} else {
				request.setAttribute("title", bussformworklist.getExtattr1());
			}

			request.setAttribute("content", bussformworklist.getExtattr2());
			request.setAttribute("recevier", bussformworklist.getSender());
			request.setAttribute("extattr3", bussformworklist.getExtattr3());
			request.setAttribute("extattr4", bussformworklist.getExtattr4());
		}
	}

	public List pagination(HttpServletRequest request,
			HttpServletResponse response) {
		List list = new ArrayList();
		try {

			MessageHandle msgHandle = (MessageHandle) RmiEntry.iv("msghandle");
			
			OnlineUserMgr oum = new DefaultOnlineUserMgr();
			OnlineUser user = oum.getOnlineUser(request);
			String username=user.getLoginname();
			String code=user.getBelongto();
	
			ResourceRmi rmi=(ResourceRmi)RmiEntry.iv("resource");
			Clerk clerk=rmi.loadClerk(code,username);
			msgHandle.setEmail(username);

			msgHandle.setPassword(clerk.getPassword());
			
			PageInfo pginfo = null;// 分页信息

			if (PageInfo.isPageEvent(request)
					&& request.getSession().getAttribute("first") != null) {// 每次翻页交互

				pginfo = new PageInfo(request, "flist");

			} else { // 查询或者第一次页面交互

				int dataNumber = msgHandle.totalNum(username, null);// 总记录数

				pginfo = new PageInfo(dataNumber, _PRE_PAGE, request, "flist");// 分页信息
				pginfo.setNowpage(1);
				request.getSession().setAttribute("first", "yes");
			}
			// 根据分页的参数查询本页的相关数据

			UmsBussformworklist ums = new UmsBussformworklist();
			ums.setParticipant(username);
			list = msgHandle.query(ums, pginfo.getPageStartIndex(), pginfo
					.getPageItemCount(), "");

		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		return list;
	}

}
