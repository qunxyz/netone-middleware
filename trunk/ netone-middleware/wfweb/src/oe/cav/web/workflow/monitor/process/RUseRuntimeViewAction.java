package oe.cav.web.workflow.monitor.process;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.midware.workflow.service.WorkflowDesign;
import oe.midware.workflow.service.WorkflowView;
import oe.rmi.client.RmiEntry;

import oe.security3a.sso.onlineuser.DefaultOnlineUserMgr;
import oe.security3a.sso.onlineuser.OnlineUser;
import oe.security3a.sso.onlineuser.OnlineUserMgr;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * ��������Ӧ��
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com<br>
 * 
 */
public class RUseRuntimeViewAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		String runtimeid = request.getParameter("runtimeid");
		String content = null;
		WorkflowDesign design = null;
		try {

			design = (WorkflowDesign) RmiEntry.iv("wfdesign");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			content = "������ִ�н���!";// ���������ʷ����̫������⣬ϵͳ�ᶨ��������������������Щʱ��ִ����ϵ����̻ᱻ�����
		}
		
		try {
			content = design.useFlow(runtimeid);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}// ���̹켣����
		request.setAttribute("rtScript", content);
		request.setAttribute("runtimeid", runtimeid);

		return mapping.findForward("look");

	}

}
