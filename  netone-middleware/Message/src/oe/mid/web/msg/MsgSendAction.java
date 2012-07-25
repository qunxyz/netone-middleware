package oe.mid.web.msg;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.frame.web.form.RequestParamMap;
import oe.frame.web.form.RequestUtil;
import oe.frame.web.page.PageInfo;
import oe.rmi.client.RmiEntry;
import oe.rmi.message.MessageHandle;
import oe.rmi.message.UmsBussformworklist;
import oe.security3a.sso.onlineuser.DefaultOnlineUserMgr;
import oe.security3a.sso.onlineuser.OnlineUserMgr;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;



/**
 * 消息管理
 * 
 * @author chen.jia.xun(Robanco)<br>
 *         mail:56414429@qq.com, chenjiaxun@oesee.com<br>
 *         tel:13328675083<br>
 */
public class MsgSendAction extends Action {

	private static int _PRE_PAGE = 10;// 默认分页

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		
		
		StringBuffer sb = new StringBuffer();
		RequestParamMap reqmap = RequestUtil.setParamMapToRequest(request);
		try {
			MessageHandle msgHandle = (MessageHandle) RmiEntry.iv("msghandle");
			if ("multidel".equals(reqmap.getParameter("multideltask"))) {
				String[] multiworkid = reqmap.getParameterValues("multiworkid");
				msgHandle.deleteMessage(multiworkid);
			}
			String sender = reqmap.getParameter("sender");
			String participant = reqmap.getParameter("participant");
			String datestart = reqmap.getParameter("created_begin");
			String dateend = reqmap.getParameter("created_end");
			String statusinfo = reqmap.getParameter("statusinfo");
			String extattr2 = reqmap.getParameter("extattr2");
			if (StringUtils.isNotEmpty(sender)) {
				sb.append(" and sender like '%" + sender + "%'");
			}
			if (StringUtils.isNotEmpty(participant)) {
				sb.append(" and participant like '%" + participant + "%'");
			}
			if (StringUtils.isNotEmpty(datestart)) {
				sb.append(" and created >=  '" + datestart + "'");
			}
			if (StringUtils.isNotEmpty(dateend)) {
				sb.append(" and created <=  '" + dateend + "'");
			}
			if (StringUtils.isNotEmpty(statusinfo)) {
				sb.append(" and statusinfo like '%" + statusinfo + "%'");
			}
			if (StringUtils.isNotEmpty(extattr2)) {
				sb.append(" and extattr2 like '%" + extattr2 + "%'");
			}
			UmsBussformworklist wok = new UmsBussformworklist();
			List list = pagination(request, response, sb.toString());
			for (Iterator iter = list.iterator(); iter.hasNext();) {
				UmsBussformworklist bussformworklist = (UmsBussformworklist) iter
						.next();
				bussformworklist.setSendername("");
				bussformworklist.setParticipantname("");
			}
			request.setAttribute("formlist", list);
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

	public List pagination(HttpServletRequest request,
			HttpServletResponse response, String sb) {
		List list = new ArrayList();
		try {

			MessageHandle msgHandle = (MessageHandle) RmiEntry.iv("msghandle");

			OnlineUserMgr oum = new DefaultOnlineUserMgr();
			String loginname = oum.getOnlineUser(request).getLoginname();
			String condition = loginname;

			PageInfo pginfo = null;// 分页信息

			if (PageInfo.isPageEvent(request)
					&& request.getSession().getAttribute("first") != null) {// 每次翻页交互

				pginfo = new PageInfo(request, "flist");

			} else { // 查询或者第一次页面交互

				int dataNumber = msgHandle.totalNum(null, sb);// 总记录数
				pginfo = new PageInfo(dataNumber, _PRE_PAGE, request, "flist");// 分页信息
				pginfo.setNowpage(1);
				request.getSession().setAttribute("first", "yes");
			}
			// 根据分页的参数查询本页的相关数据

			try {
				UmsBussformworklist ums = new UmsBussformworklist();
				list = msgHandle.query(ums, pginfo.getPageStartIndex(), pginfo
						.getPageItemCount() + 0, sb);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
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
