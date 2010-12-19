package oe.cav.web.workflow.monitor.process;

import java.rmi.RemoteException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.frame.bus.workflow.WfEntry;
import oe.midware.workflow.runtime.ormobj.TWfWorklist;
import oe.midware.workflow.service.WorkflowConsole;
import oe.midware.workflow.service.WorkflowDesign;
import oe.midware.workflow.service.WorkflowView;
import oe.midware.workflow.track.WorkflowRuntime;
import oe.midware.workflow.xpdl.model.activity.Activity;
import oe.rmi.client.RmiEntry;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * �����ύ
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com<br>
 * 
 */
public class RUseCommitAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		WorkflowView wfview = null;
		WorkflowConsole console = null;
		WorkflowDesign design = null;
		try {
			wfview = (WorkflowView) RmiEntry.iv("wfview");
			console = (WorkflowConsole) RmiEntry.iv("wfhandle");
			design = (WorkflowDesign) RmiEntry.iv("wfdesign");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String runtimeid = request.getParameter("runtimeid"); // ��־λ
		String activity = request.getParameter("activityid"); // ��־λ
		String activityto = request.getParameter("activityto"); // ��־λ
		if (runtimeid != null) {
			runtimeid = runtimeid.split(",")[0];
			try {
				List wList = wfview.fetchRunningWorklist(runtimeid, activity);
				if (wList != null && wList.size() > 0) {
					TWfWorklist worklist = (TWfWorklist) wList
							.get(wList.size() - 1);
					if (worklist.isRunning()) {
						if (activityto == null) {// �ύ
							console.commitActivity(worklist);
						} else {// ��ת
							String processid=wfview.loadRuntime(runtimeid).getProcessid();
							Activity act = wfview.fetchWorkflowProcess(
									processid).getActivity(activityto);
							console.commitActivityByManual(worklist, act);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		WorkflowRuntime trackImp = (WorkflowRuntime) WfEntry
				.fetchBean("workflowTrackRuntime");
		String content = null;
		try {
			content = design.useFlow(runtimeid);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// ���̹켣����
		request.setAttribute("rtScript", content);
		request.setAttribute("runtimeid", runtimeid);

		return mapping.getInputForward();
	}

}
