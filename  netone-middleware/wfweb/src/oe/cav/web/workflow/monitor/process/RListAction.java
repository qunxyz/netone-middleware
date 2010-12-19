package oe.cav.web.workflow.monitor.process;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.midware.workflow.runtime.ormobj.TWfRuntime;
import oe.midware.workflow.service.WorkflowConsole;
import oe.midware.workflow.service.WorkflowDesign;
import oe.midware.workflow.service.WorkflowView;
import oe.rmi.client.RmiEntry;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 流程列表
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com<br>
 * 
 */
public class RListAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		String runtimeid = request.getParameter("runtimeid"); // 标志位
		String opetype = request.getParameter("opedo"); // 标志位

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

		TWfRuntime runtime = new TWfRuntime();
		String condition = null;
		String processid = null;
		if ("subflow".equals(opetype)) {
			condition = " and belongruntimeid ='" + runtimeid + "'";
		} else {
			TWfRuntime runtimeTmp=null;
			try {
				runtimeTmp = wfview.loadRuntime(runtimeid);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			processid = runtimeTmp.getProcessid();
			condition = " and runtimeid!='" + runtimeid + "'";
		}

		List list=null;
		try {
			list = wfview.listxinstance(processid, condition);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		request.setAttribute("listinfo", list);

		return mapping.getInputForward();

	}

}
