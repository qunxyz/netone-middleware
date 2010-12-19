package oe.cav.web.workflow.monitor.process;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.frame.bus.workflow.ProcessEngine;
import oe.frame.bus.workflow.WfEntry;
import oe.midware.workflow.runtime.ormobj.TWfRuntime;
import oe.midware.workflow.service.WorkflowConsole;
import oe.midware.workflow.service.WorkflowDesign;
import oe.midware.workflow.service.WorkflowView;
import oe.midware.workflow.track.WorkflowRuntime;
import oe.rmi.client.RmiEntry;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 流程初始化
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com<br>
 * 
 */
public class RInitAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		String runtimeid = request.getParameter("runtimeid"); // 标志位

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

		TWfRuntime twfruntime = null;
		String content = null;
		try {
			twfruntime = wfview.loadRuntime(runtimeid);
			console.initProcess(twfruntime);
			// ae.getMessageDialog().info(Message._M_INIT_SUC);
			content = design.controlFlow(runtimeid);// 流程轨迹内容
		} catch (Exception e) {
			e.printStackTrace();
		}

		request.setAttribute("rtScript", content);
		request.setAttribute("runtimeid", runtimeid);
		return mapping.getInputForward();
	}

}
