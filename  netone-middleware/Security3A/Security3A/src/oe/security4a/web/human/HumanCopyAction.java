package oe.security4a.web.human;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.frame.web.form.RequestParamMap;
import oe.frame.web.form.RequestUtil;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.CupmRmi;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.auditingser.OperationLog;
import oe.security3a.seucore.obj.Clerk;
import oe.security3a.seucore.obj.db.UmsProtectedobject;
import oe.security3a.seucore.obj.db.UmsRole;
import oe.security3a.sso.onlineuser.DefaultOnlineUserMgr;
import oe.security3a.sso.onlineuser.OnlineUser;
import oe.security3a.sso.onlineuser.OnlineUserMgr;
import oe.security3a.sso.util.SyncUser;
import oe.security3a.sso.util.SyncUser;
import oe.security3a.sso.util.SyncUserUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class HumanCopyAction extends Action {


	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		RequestParamMap reqmap = RequestUtil.setParamMapToRequest(request);
		Clerk clerk = (Clerk) RequestUtil.mappingReqParam(Clerk.class, request);
		
		OnlineUserMgr olmgr = new DefaultOnlineUserMgr();
		OnlineUser oluser = olmgr.getOnlineUser(request);
		String code = oluser.getBelongto();
		
		//进入复制人员
		if ("copy".equals(request.getParameter("task"))) {
			try {
				CupmRmi cupm = (CupmRmi) RmiEntry.iv("cupm");
				ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");
				request.setAttribute("flag", "copy");
				String loginname = clerk.getDescription();
				clerk = rsrmi.loadClerk(code,loginname);
				request.setAttribute("clerk", clerk);
				List rolelist = rsrmi.getUserRoles(code, loginname);
				request.setAttribute("rolelist", rolelist);
				UmsProtectedobject upo = rsrmi.loadResourceByNatural(cupm.fetchConfig("ITEMTEAM"));
				List itemList = rsrmi.subResource(upo.getId());
				request.setAttribute("itemList", itemList);
				upo = rsrmi.loadResourceByNatural(cupm.fetchConfig("DEPT"));
				List deptList = rsrmi.subResource(upo.getId());
				request.setAttribute("deptList", deptList);
				
			
				String loginName = oluser.getLoginname();
			
				request.setAttribute("extendattribute", "DEPT");
				if (!"adminx".equals(loginName)) {
					try {
						Clerk user = rsrmi.loadClerk(code,loginName);
						String extend = user.getExtendattribute();
						request.setAttribute("extendattribute", extend);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				OperationLog.error(request, "复制用户", e.getMessage());
			}
		}
		
		//保存添加的人员
		if ("save".equals(request.getParameter("task"))) {
			try {
				ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");
				if (StringUtils.isNotEmpty(reqmap.getParameter("newhiddenid"))) {
					clerk.setDeptment(reqmap.getParameter("newhiddenid"));
					UmsProtectedobject upo = rsrmi.loadResourceById(reqmap.getParameter("newhiddenid"));
					clerk.setExtendattribute(upo.getNaturalname());
					String loginname = request.getParameter("naturalname").trim();
					clerk.setDescription(loginname);
					if (rsrmi.addClerk(code, clerk)) {
						String roles = reqmap.getParameter("roles");
						List<UmsRole> list = new ArrayList<UmsRole>();
						if (StringUtils.isNotEmpty(roles)) {
							String[] role = roles.split("@");
							if (role != null && role.length > 0) {
								for (int i = 0; i < role.length; i++) {
									UmsRole urole = new UmsRole();
									urole.setId(new Long(role[i]));
									list.add(urole);
								}
							}
						}
						
						rsrmi.roleRelationupdate(code, loginname, list);
						CupmRmi cupmRmi = (CupmRmi) RmiEntry.iv("cupm");
						cupmRmi.initCacheUser(loginname);
						
						//人员同步操作，复制创建
						rsrmi.SyncUser(SyncUserUtil._PARAM_OPE_ADD, code, loginname);
						
						reqmap.setAlertMsg("复制用户成功！");
						request.setAttribute("result", "y");
						OperationLog.info(request, "复制用户", "复制用户" + clerk.getName() + "成功！");
					} else {
						reqmap.setAlertMsg("复制用户失败!请检查用户名是否已存在或帐号是否超过20个字符!");
						request.setAttribute("result", "n");
						OperationLog.error(request, "复制用户", "复制用户失败!请检查用户名是否已存在或帐号是否超过20个字符!");
					}
				} else {
					reqmap.setAlertMsg("复制用户失败！");
					reqmap.setAlertMsg("部门不正确,无法复制用户！");
					request.setAttribute("result", "n");
					OperationLog.error(request, "复制用户", "部门不正确,无法复制用户！");
				}
			} catch (Exception e) {
				e.printStackTrace();
				reqmap.setAlertMsg(e.getMessage());
				request.setAttribute("result", "n");
				OperationLog.error(request, "复制用户", e.getMessage());
			}
		}
		return mapping.getInputForward();
	}
}
