package oe.security4a.web.department;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.auditingser.OperationLog;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import oe.frame.web.form.RequestParamMap;
import oe.frame.web.form.RequestUtil;

public class DepartmentModifyAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		RequestParamMap reqmap = RequestUtil.setParamMapToRequest(request);
		try {
			ResourceRmi rmi = (ResourceRmi) RmiEntry.iv("resource");
			if ("modify".equals(request.getParameter("task"))) {
				UmsProtectedobject f = (UmsProtectedobject) RequestUtil
						.mappingReqParam(UmsProtectedobject.class, reqmap);
				if (rmi.updateResource(f)) {
					request.setAttribute("ModifySuccess", "y");
					OperationLog.info(request, "修改目录", f.getName()+f.getNaturalname()+"修改目录成功！",true);
				} else {
					request.setAttribute("ModifySuccess", "n");
					OperationLog.info(request, "修改目录",  f.getName()+f.getNaturalname()+"修改目录失败！",false);
				}
				return mapping.findForward("departmentright");
			} else if ("edit".equals(request.getParameter("task"))) {
				UmsProtectedobject upo = rmi.loadResourceById(reqmap
						.getParameter("chkid"));
				request.setAttribute("upo", upo);
				return mapping.findForward("editnode");
			} else if ("editn".equals(request.getParameter("task"))) {
				UmsProtectedobject upo = rmi.loadResourceByNatural(reqmap
						.getParameter("naturalname"));
				request.setAttribute("upo", upo);
				return mapping.findForward("editnode");
			}else if ("editsave".equals(request.getParameter("task"))) {
				UmsProtectedobject f = (UmsProtectedobject) RequestUtil
						.mappingReqParam(UmsProtectedobject.class, reqmap);
				request.setAttribute("upo", f);
				if (rmi.updateResource(f)) {
					request.setAttribute("ModifySuccess", "y");
					OperationLog.info(request, "修改目录", f.getName()+f.getNaturalname()+"修改目录成功！",true);
				} else {
					request.setAttribute("ModifySuccess", "n");
					OperationLog.info(request, "修改目录",f.getName()+f.getNaturalname()+"修改目录失败！",false);
				}
				return mapping.findForward("editnode");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}