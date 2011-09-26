package oe.security4a.web.department;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.frame.web.form.RequestParamMap;
import oe.frame.web.form.RequestUtil;
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
import oe.frame.web.util.WebTip;

public class DepartmentAddAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		RequestParamMap reqmap = RequestUtil.setParamMapToRequest(request);
		String task = reqmap.getParameter("task");
		if ("addsave".equals(task)) {
			
			
			UmsProtectedobject upo = new UmsProtectedobject();
			upo
					.setNaturalname(reqmap.getParameter("naturalname")
							.toUpperCase());
			upo.setName(reqmap.getParameter("name"));
			String actionurl = reqmap.getParameter("actionurl");
			upo.setActionurl(actionurl);
			String objecttype = reqmap.getParameter("objecttype");
			upo.setObjecttype(objecttype);
			upo.setExtendattribute(reqmap.getParameter("extendattribute"));
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
				// 检查编码是否存在
			
				UmsProtectedobject upoq = new UmsProtectedobject();
				upoq.setActionurl(upo.getActionurl());
				List list = new ArrayList();
				if (upo.getActionurl() != null
						&& !"".equals(upo.getActionurl())) {
					list = rmi.fetchResource(upoq, null, null);
				}
			
				// 检查目录是否合法
				// 寻找营销部门
				UmsProtectedobject f = rmi.loadResourceById(upo.getParentdir());
// if ("jxs".equals(objecttype)) {
// boolean pass = findHeadCom(f.getNaturalname(), rmi,
// response);
// if(!pass) return null;
// }
// if("fxs".equals(objecttype)){
// boolean pass = findHeadSell(f.getNaturalname(), rmi, response);
// if(!pass) return null;
// }
				

				// if (list.size() <= 0) {

					Serializable ouid = rmi.addResource(upo);
					if (ouid != null) {
						RequestUtil.loadObjectParam(reqmap, f);
						request.setAttribute("CreateSuccess", "y");
						OperationLog.info(request, "新建目录", upo.getNaturalname()+upo.getName()+"新建目录成功！",true);
						UmsProtectedobject upox = rmi.loadResourceById(ouid
								.toString());
						request.setAttribute("upo", upox);
						return mapping.findForward("editnode");
					} else {
						reqmap.setAlertMsg("该名称已存在！");
						request.setAttribute("CreateSuccess", "n");
						OperationLog.info(request, "新建目录", upo.getNaturalname()+upo.getName()+"该名称已存在！新建目录失败！",false);
					}

				// } else {
				// reqmap.setAlertMsg("该编码已存在！");
				// request.setAttribute("CreateSuccess", "n");
				// OperationLog.error(request, "新建目录", "该名称已存在！新建目录失败！");
				// }
			} catch (Exception e) {
				e.printStackTrace();
				reqmap.setAlertMsg(e.getMessage());
				request.setAttribute("CreateSuccess", "n");
				OperationLog.info(request, "新建目录", e.getMessage(),false);
			}
		}
		return mapping.findForward("newnode");
	}

	// 找上级营销部门
	private boolean findHeadCom(String name, ResourceRmi rmi,
			HttpServletResponse response) throws RemoteException {

		UmsProtectedobject headComObj = null;
		String headComName = null;
		String headCom = name;
		String names[] = headCom.split(".");
		for (int i = 0; i < names.length - 3; i++) { // 循环次数
			headComObj = rmi.loadResourceByNatural(headCom);
			// 找到营销部门类型的部门
			if ("yxbm".equals(headComObj.getObjecttype())) {
				headComName = headComObj.getName();
				break; // 从后往前找到最近的一个即可完成
			}
			// 继续从后往前找可能营销部门
			headCom = StringUtils.substringBeforeLast(headCom, ".");
		}
		if (headComName == null) {
			WebTip.htmlInfo("无法创建该经销商公司,缺少上级营销部门", true, response);
			return false;
		}
		return true;

	}

	// 分销商找上级经销商
	private boolean findHeadSell(String name, ResourceRmi rmi,
			HttpServletResponse response) throws RemoteException {

		UmsProtectedobject headComObj = null;
		String headComName = null;
		String headCom = name;
		String names[] = headCom.split(".");
		for (int i = 0; i < names.length - 3; i++) { // 循环次数
			headComObj = rmi.loadResourceByNatural(headCom);
			// 找到营销部门类型的部门
			if ("jxs".equals(headComObj.getObjecttype())) {
				headComName = headComObj.getName();
				break; // 从后往前找到最近的一个即可完成
			}
			// 继续从后往前找可能营销部门
			headCom = StringUtils.substringBeforeLast(headCom, ".");
		}
		if (headComName == null) {
			WebTip.htmlInfo("无法创建该分销商公司,缺少上级经销商", true, response);
			return false;
		}
		return true;

	}
}