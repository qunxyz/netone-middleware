package oe.cav.web.workflow.monitor.process;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import oe.midware.workflow.service.WorkflowConsole;
import oe.midware.workflow.service.WorkflowDesign;
import oe.rmi.client.RmiEntry;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class RBiNewRuntimeAction extends Action
{
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
  {
    String processsid = request.getParameter("processid");
    WorkflowConsole console = null;
    WorkflowDesign design = null;
    String runtimeid = null;
    try {
      console = (WorkflowConsole)RmiEntry.iv("wfhandle");

      design = (WorkflowDesign)RmiEntry.iv("wfdesign");
      runtimeid = console.newProcess(processsid);
      console.runProcess(runtimeid);
    } catch (Exception e) {
      e.printStackTrace();
    }

    String content = null;
    try {
      content = design.useFlow(runtimeid);
    } catch (Exception e1) {
      e1.printStackTrace();
      content = "流程已执行结束!";
    }

    request.setAttribute("rtScript", content);
    request.setAttribute("runtimeid", runtimeid);

    return mapping.findForward("console");
  }
}