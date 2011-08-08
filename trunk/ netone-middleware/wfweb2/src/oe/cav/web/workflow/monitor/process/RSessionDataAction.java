package oe.cav.web.workflow.monitor.process;

import java.rmi.RemoteException;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.midware.workflow.client.Session;
import oe.midware.workflow.runtime.ormobj.TWfRelevantvar;
import oe.midware.workflow.service.WorkflowConsole;
import oe.rmi.client.RmiEntry;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 相关数据处理
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com<br>
 * 
 */
public class RSessionDataAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String runtimeid = request.getParameter("runtimeid"); // 标志位

		WorkflowConsole console = null;

		try {
			console = (WorkflowConsole) RmiEntry.iv("wfhandle");
			Session map = console.descriptSession(runtimeid);

			request.setAttribute("description", map);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return mapping.getInputForward();
	}

}
