package oe.cav.web.workflow.monitor.process;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import oe.midware.workflow.service.WorkflowDesign;
import oe.rmi.client.RmiEntry;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class RBiProcessRuntimeAction extends Action
{
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
  {
    String runtimeid = request.getParameter("runtimeid");

    WorkflowDesign design = null;

    String content = null;
    try {
      design = (WorkflowDesign)RmiEntry.iv("wfdesign");
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