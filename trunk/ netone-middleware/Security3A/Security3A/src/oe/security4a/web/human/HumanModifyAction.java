package oe.security4a.web.human;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.frame.web.WebCache;
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

import oe.security3a.sso.util.SyncUserUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class HumanModifyAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		RequestParamMap reqmap = RequestUtil.setParamMapToRequest(request);
		Clerk clerk = (Clerk) RequestUtil.mappingReqParam(Clerk.class, request);
		OnlineUserMgr olmgr = new DefaultOnlineUserMgr();
		OnlineUser oluser = olmgr.getOnlineUser(request);
		String code = oluser.getBelongto();

		// 进入修改页面前，获得页面数据
		if ("modify".equals(request.getParameter("task"))) {
			try {
				CupmRmi cupmRmi = (CupmRmi) RmiEntry.iv("cupm");
				ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");
				
				request.setAttribute("flag", "modify");
				String loginname = clerk.getDescription();
				clerk = rsrmi.loadClerk(code, loginname);
				request.setAttribute("clerk", clerk);
				List rolelist = rsrmi.getUserRoles(code, loginname);
				request.setAttribute("rolelist", rolelist);
				// 获得群组
				String teams = clerk.getProvince();
				String team[] = null;
				List teamlist = new ArrayList();
				if (teams != null) {
					team = StringUtils.split(teams, "@");

					for (int i = 0; i < team.length; i++) {
						String object = (String) team[i];
						UmsProtectedobject upo = rsrmi
								.loadResourceByNatural(object);
						teamlist.add(upo);
					}
				}

				request.setAttribute("teamlist", teamlist);

				UmsProtectedobject upo = rsrmi.loadResourceByNatural(cupmRmi
						.fetchConfig("ITEMTEAM"));
				List itemList = rsrmi.subResource(upo.getId());
				request.setAttribute("itemList", itemList);
				upo = rsrmi.loadResourceByNatural(cupmRmi.fetchConfig("DEPT"));
				List deptList = rsrmi.subResource(upo.getId());
				request.setAttribute("deptList", deptList);
				if ("y".equals(reqmap.getParameter("state"))) {
					request.setAttribute("state", "y");
				}

				String loginName = oluser.getLoginname();
				request.setAttribute("extendattribute", "DEPT");
				if (!"adminx".equals(loginName)) {
					try {
						Clerk user = rsrmi.loadClerk(code, loginName);
						String extend = user.getExtendattribute();
						request.setAttribute("extendattribute", extend);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				OperationLog.info(request, "修改用户", e.getMessage(),false);
			}
			return mapping.getInputForward();
		}

		// 修改用户
		if ("save".equals(request.getParameter("task"))) {

			
			try {
				CupmRmi cupmRmi = (CupmRmi) RmiEntry.iv("cupm");
				ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");
				String oldou = reqmap.getParameter("hiddenid");
				String newou = reqmap.getParameter("newhiddenid");
				String loginname = reqmap.getParameter("description");
				
				List rolelist = rsrmi.getUserRoles(code, loginname);
				//清空人员角色缓存
				for (Iterator iterator = rolelist.iterator(); iterator.hasNext();) {
					UmsRole umsRole = (UmsRole) iterator.next();
					WebCache.removeCache("ROLER_" + umsRole.getId());
				}
				
				if (StringUtils.isNotEmpty(newou) && !oldou.equals(newou)) {
					if (rsrmi.moveUserDeptOpe(loginname, oldou, newou)) {
						clerk.setDeptment(newou);
						UmsProtectedobject upo = rsrmi.loadResourceById(newou);
						clerk.setExtendattribute(upo.getNaturalname());
					} else {
						clerk.setDeptment(oldou);
					}
				} else {
					clerk.setDeptment(oldou);
				}
				String teams = reqmap.getParameter("teams");
				clerk.setProvince(teams);

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

				if (rsrmi.updateClerk(code, clerk)) {
					cupmRmi.initCacheUser(loginname);
					// 修改用户时,自动同步帐号
					rsrmi
							.SyncUser(SyncUserUtil._PARAM_OPE_MOD, code,
									loginname);

					reqmap.setAlertMsg("用户修改成功！");
					request.setAttribute("result", "y");
					OperationLog.info(request, "修改用户", "用户修改成功！",true);
				}
			} catch (Exception e) {
				e.printStackTrace();
				reqmap.setAlertMsg(e.getMessage());
				request.setAttribute("result", "n");
				OperationLog.info(request, "修改用户", e.getMessage(),false);
			}
		}
		return mapping.getInputForward();
	}
}
