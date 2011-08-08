package oe.cav.web.workflow.monitor.process;

import java.rmi.RemoteException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import oe.midware.workflow.runtime.ormobj.TWfRuntime;
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

public class RUseRuntimeAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String runtimeid = request.getParameter("runtimeid");

		WorkflowDesign design = null;

		try {
			design = (WorkflowDesign) RmiEntry.iv("wfdesign");

		} catch (Exception e) {
			e.printStackTrace();
		}

		String content = null;
		try {
			content = design.useFlow(runtimeid);
		} catch (Exception e1) {
			e1.printStackTrace();
			content = "������ִ�н���!";// ���������ʷ����̫������⣬ϵͳ�ᶨ��������������������Щʱ��ִ����ϵ����̻ᱻ�����

		}
		request.setAttribute("rtScript", content);
		request.setAttribute("runtimeid", runtimeid);

		return mapping.findForward("console");
		//return null; // ��ʳ����Ϊ����ԣϵͳ�İ�ȫ������
	}
}