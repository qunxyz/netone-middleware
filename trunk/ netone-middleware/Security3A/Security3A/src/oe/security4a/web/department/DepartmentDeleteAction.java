package oe.security4a.web.department;

import java.io.File;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.auditingser.OperationLog;
import oe.security3a.seucore.obj.Clerk;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class DepartmentDeleteAction extends Action {

	private static ResourceBundle messages = ResourceBundle.getBundle(
			"resource", Locale.CHINESE);

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		String parentdir = request.getParameter("parentdir");
		String file = "";
		try {
			ResourceRmi rmi = (ResourceRmi) RmiEntry.iv("resource");
			if ("delete".equals(request.getParameter("task"))) {
				if (!"0".equals(id)) {
					UmsProtectedobject upo = rmi.loadResourceById(id);
					Clerk cler = new Clerk();
					cler.setDeptment(id);
					List list = rmi.fetchClerk("0000", cler, null, "");
					if (list != null && list.size() > 0) {
						request.setAttribute("DeleteSuccess", "n");
						OperationLog.info(request, "É¾³ýÄ¿Â¼", upo.getNaturalname()
								+ upo.getName() + "É¾³ýÄ¿Â¼Ê§°Ü,Ä¿Â¼ÏÂÓÐÈËÔ±ÎÞ·¨É¾³ý£¡", false);
						return mapping.findForward("departmentright");
					}
					file = upo.getActionurl();
					if (!rmi.dropResource(id)) {
						request.setAttribute("DeleteSuccess", "n");
						OperationLog.info(request, "É¾³ýÄ¿Â¼", "É¾³ýÄ¿Â¼Ê§°Ü£¡", false);
						return mapping.findForward("departmentright");
					}
				}
				UmsProtectedobject f = rmi.loadResourceById(parentdir);
				request.setAttribute("upo", f);
				if (StringUtils.isNotEmpty(file)
						&& StringUtils.contains(file, messages
								.getString("rsLogicPath"))) {
					String pathRoot = servlet.getServletContext().getRealPath(
							"");
					String dir = pathRoot + messages.getString("rsSaveWebPath");
					file = StringUtils.substringAfter(file, messages
							.getString("rsLogicPath"));
					File delfile = new File(dir + file);
					delfile.delete();
				}
				request.setAttribute("DeleteSuccess", "y");
				OperationLog.info(request, "É¾³ýÄ¿Â¼", f.getNaturalname()
						+ f.getName() + "É¾³ýÄ¿Â¼³É¹¦£¡", true);
			} else if ("del".equals(request.getParameter("task"))) {
				if (request.getParameter("chkid") != null) {
					String str[] = request.getParameterValues("chkid");
					for (int i = 0; i < str.length; i++) {
						if (!"0".equals(str[i])) {
							UmsProtectedobject upo = rmi
									.loadResourceById(str[i]);
							file = upo.getActionurl();
							if (!rmi.dropResource(str[i])) {
								request.setAttribute("DeleteSuccess", "n");
								OperationLog.info(request, "É¾³ýÄ¿Â¼", upo
										.getNaturalname()
										+ upo.getName() + "É¾³ýÄ¿Â¼Ê§°Ü£¡", false);
								break;
							}
						}
						if (StringUtils.isNotEmpty(file)
								&& StringUtils.contains(file, messages
										.getString("rsLogicPath"))) {
							String pathRoot = servlet.getServletContext()
									.getRealPath("");
							String dir = pathRoot
									+ messages.getString("rsSaveWebPath");
							file = StringUtils.substringAfter(file, messages
									.getString("rsLogicPath"));
							File delfile = new File(dir + file);
							delfile.delete();
						}
						request.setAttribute("DelSuccess", "y");
						OperationLog.info(request, "É¾³ýÄ¿Â¼", "É¾³ýÄ¿Â¼³É¹¦£¡", true);
					}
				}
				UmsProtectedobject f = rmi.loadResourceById(id);
				request.setAttribute("upo", f);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("departmentright");
	}
}