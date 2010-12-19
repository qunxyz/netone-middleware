package oe.cav.web.workflow.resource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.frame.bus.workflow.WfEntry;
import oe.midware.workflow.service.WorkflowDesign;
import oe.rmi.client.RmiEntry;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * ������ͼ
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com<br>
 * 
 */
public class FViewAction extends Action {

	// ��Ѷģ���б�
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		String processid = request.getParameter("processid"); // ��־λ

		WorkflowDesign wfdesign = null;
		String tScript = "";
		try {
			wfdesign = (WorkflowDesign) RmiEntry.iv("wfdesign");
			tScript = wfdesign.viewOpe(processid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		request.setAttribute("tScript", tScript);
		return mapping.getInputForward();
	}

}
