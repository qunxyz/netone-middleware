package oe.cav.web.workflow.monitor.process;

import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.frame.web.util.WebTip;
import oe.midware.workflow.runtime.ormobj.TWfRuntime;
import oe.midware.workflow.service.WorkflowConsole;
import oe.midware.workflow.service.WorkflowDesign;
import oe.midware.workflow.service.WorkflowView;
import oe.midware.workflow.xpdl.model.workflow.WorkflowProcess;
import oe.rmi.client.RmiEntry;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 流程列表-子流程应用
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com<br>
 * 
 */
public class RListSubUseAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		String runtimeid = request.getParameter("runtimeid"); // 标志位

		WorkflowView wfview = null;

		try {
			wfview = (WorkflowView) RmiEntry.iv("wfview");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String condition = " and belongruntimeid ='" + runtimeid + "'";

		condition += " order by starttime desc";
		List list = null;
		try {
			list = wfview.listxinstance(null, condition);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (list.size() == 0) {
			String info = WebTip.tipSpi("没有子流程");
			request.setAttribute("subflow", info);
		}
		String processname = null;
		try {

			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				TWfRuntime object = (TWfRuntime) iterator.next();
				String processid = object.getProcessid();
				WorkflowProcess proc = wfview.fetchWorkflowProcess(wfview
						.loadRuntime(runtimeid).getProcessid());
				object.setNameExt(proc.getName());
			}

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		request.setAttribute("processname", processname);
		request.setAttribute("listinfo", list);

		return mapping.getInputForward();

	}

}
