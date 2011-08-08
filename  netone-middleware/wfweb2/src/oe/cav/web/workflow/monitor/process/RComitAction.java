package oe.cav.web.workflow.monitor.process;

import java.rmi.RemoteException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.midware.workflow.runtime.ormobj.TWfWorklist;
import oe.midware.workflow.service.WorkflowConsole;
import oe.midware.workflow.service.WorkflowDesign;
import oe.midware.workflow.service.WorkflowView;
import oe.rmi.client.RmiEntry;
import oe.security3a.sso.Security;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
/**
 * 流程提交
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com<br>
 * 
 */
public class RComitAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		String runtimeid = request.getParameter("runtimeid"); // 标志位
		String activity = request.getParameter("activityid"); // 标志位
		Security ser = new Security(request);

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

		if (runtimeid != null) {
			runtimeid = runtimeid.split(",")[0];

			List wList = null;
			try {
				wList = wfview.fetchRunningWorklist(runtimeid, activity);
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (wList != null && wList.size() > 0) {
				TWfWorklist worklist = (TWfWorklist) wList
						.get(wList.size() - 1);
				if (worklist.isRunning()) {
					worklist.setParticipant(ser.getUserLoginName());
					try {
						console.commitActivity(worklist);
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}

		String content = null;
		try {
			content = design.controlFlow(runtimeid);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// 流程轨迹内容
		request.setAttribute("rtScript", content);
		request.setAttribute("runtimeid", runtimeid);

		return mapping.getInputForward();
	}
}