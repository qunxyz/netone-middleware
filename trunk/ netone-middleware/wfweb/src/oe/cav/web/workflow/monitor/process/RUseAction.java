package oe.cav.web.workflow.monitor.process;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.frame.bus.workflow.ProcessEngine;
import oe.frame.bus.workflow.WfEntry;
import oe.frame.web.util.WebTip;
import oe.midware.workflow.runtime.XpdlModelHandler;
import oe.midware.workflow.runtime.ormobj.TWfRuntime;
import oe.midware.workflow.service.WorkflowConsole;
import oe.midware.workflow.service.WorkflowDesign;
import oe.midware.workflow.service.WorkflowView;
import oe.midware.workflow.track.WorkflowRuntime;
import oe.midware.workflow.xpdl.model.workflow.WorkflowProcess;
import oe.rmi.client.RmiEntry;

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
public class RUseAction extends Action {

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

		String mode = request.getParameter("mode");
		String opetip = "";

		if ("new".equals(mode)) {
			String processid = request.getParameter("processid");
			opetip = "����װ�سɹ�";
			try {
				console.newProcess(processid);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				opetip=e.getMessage();
			}
			
		} else {
			String runtimeid = request.getParameter("runtimeid");
			if (runtimeid == null || runtimeid.equals("")) {
				opetip = "ȱ������ Runtimeid";
			} else {
				if ("run".equals(mode)) {
					opetip = "��������";
					try {
						console.runProcess(runtimeid);
					} catch (Exception e) {
						opetip=e.getMessage();
					}
				
				} else if ("init".equals(mode)) {
					opetip = "��ʼ������";
					try {
						console.initProcess(runtimeid);
					} catch (RemoteException e) {
						e.printStackTrace();
						opetip=e.getMessage();
					}
					
				} else if ("drop".equals(mode)) {
					opetip = "ɾ������";
					try {
						console.dropProcess(runtimeid);
					} catch (RemoteException e) {
						e.printStackTrace();
						opetip=e.getMessage();
					}
					
				}
			}
		}

		WebTip.htmlInfo(opetip, true, true, response);
		return null;
		// WorkflowRuntime trackImp = (WorkflowRuntime) WfEntry
		// .fetchBean("workflowTrackRuntime");
		//
		// String content = trackImp.useFlow(rutime.getRuntimeid());// ���̹켣����
		// request.setAttribute("rtScript", content);
		// request.setAttribute("runtimeid", rutime.getRuntimeid());
		// return mapping.getInputForward();

	}
}
