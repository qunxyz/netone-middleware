package oe.cav.web.workflow.monitor.process;

import java.rmi.RemoteException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.frame.bus.workflow.WfEntry;
import oe.midware.workflow.service.WorkflowDesign;
import oe.midware.workflow.track.WorkflowRuntime;
import oe.rmi.client.RmiEntry;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * ����չ��
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com<br>
 * 
 */

public class RViewAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		WorkflowDesign design = null;
		try {

			design = (WorkflowDesign) RmiEntry.iv("wfdesign");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String runtimeid = request.getParameter("runtimeid"); // ��־λ

		String content = null;
		try {
			content = design.controlFlow(runtimeid);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// ���̹켣����
		request.setAttribute("rtScript", content);
		request.setAttribute("runtimeid", runtimeid);
		return mapping.getInputForward();
	}

}
