package oe.security4a.web.department2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.auditingser.OperationLog;
import oe.security3a.seucore.obj.ProtectedObjectReference;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import oe.frame.web.form.RequestParamMap;
import oe.frame.web.form.RequestUtil;

public class DepartmentAddAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		RequestParamMap reqmap = RequestUtil.setParamMapToRequest(request);
		String task = reqmap.getParameter("task");
		if ("addsave".equals(task)) {
			UmsProtectedobject upo = new UmsProtectedobject();
			upo.setNaturalname(reqmap.getParameter("naturalname"));
			upo.setName(reqmap.getParameter("name"));
			upo.setActionurl(reqmap.getParameter("actionurl"));
			if (StringUtils.isEmpty(reqmap.getParameter("id"))) {
				upo.setParentdir("0");
			} else {
				upo.setParentdir(reqmap.getParameter("id"));
			}
			upo.setInclusion(ProtectedObjectReference._OBJ_INCLUSTION_YES);
			if (StringUtils.isNotEmpty(reqmap.getParameter("appid"))) {
				upo.setAppid(new Long(reqmap.getParameter("appid")));
			}
			try {
				ResourceRmi rmi = (ResourceRmi) RmiEntry.iv("resource");
				if (rmi.addResource(upo) != null) {
					UmsProtectedobject f = rmi.loadResourceById(upo.getParentdir());
					RequestUtil.loadObjectParam(reqmap, f);
					request.setAttribute("CreateSuccess", "y");
					OperationLog.info(request, "新建目录", "新建目录成功！");
				} else {
					reqmap.setAlertMsg("该名称已存在！");
					request.setAttribute("CreateSuccess", "n");
					OperationLog.error(request, "新建目录", "该名称已存在！新建目录失败！");
				}
			} catch (Exception e) {
				e.printStackTrace();
				reqmap.setAlertMsg(e.getMessage());
				request.setAttribute("CreateSuccess", "n");
				OperationLog.error(request, "新建目录", e.getMessage());
			}
		}
		return mapping.findForward("newnode");
	}
}