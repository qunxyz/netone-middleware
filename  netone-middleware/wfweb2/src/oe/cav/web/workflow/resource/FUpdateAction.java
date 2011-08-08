package oe.cav.web.workflow.resource;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.frame.bus.workflow.WfEntry;
import oe.frame.web.util.WebStr;
import oe.frame.web.util.WebTip;
import oe.midware.workflow.service.WorkflowDesign;
import oe.rmi.client.RmiEntry;
import oe.security3a.sso.Security;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 流程更新
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com<br>
 * 
 */
public class FUpdateAction extends Action {

	// 资讯模型列表
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		String xpdlContent = request.getParameter("xpdlContent");// 文件内容
		String processid = request.getParameter("processid");
		String processname = request.getParameter("description");

		// 存在中文信息需要转编码
		xpdlContent = WebStr.encode(request, xpdlContent);
		processname = WebStr.encode(request, processname);
		boolean todo = false;
		WorkflowDesign wfdesign = null;
		try {
			wfdesign = (WorkflowDesign) RmiEntry.iv("wfdesign");
			todo = wfdesign.updateOpe(xpdlContent, processid, processname);

			Security ser = new Security(request);
			ser.log(processid, "edit", "update flow info");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		request.setAttribute("tipinfo", WebTip.tip(todo));

		String tScript = "";
		try {
			tScript = wfdesign.viewOpe(processid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("tScript", tScript);
		return mapping.getInputForward();

	}

}
