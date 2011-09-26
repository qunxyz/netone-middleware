package oe.security4a.web.human;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.frame.web.form.RequestParamMap;
import oe.frame.web.form.RequestUtil;
import oe.frame.web.util.WebStr;
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

public class HumanAddAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		RequestParamMap reqmap = RequestUtil.setParamMapToRequest(request);
		Clerk clerk = (Clerk) RequestUtil.mappingReqParam(Clerk.class, reqmap);

		OnlineUserMgr olmgr = new DefaultOnlineUserMgr();
		OnlineUser oluser = olmgr.getOnlineUser(request);
		String loginName = oluser.getLoginname();
		String code = oluser.getBelongto();

		String actionurl = request.getParameter("actionurl");
		actionurl = actionurl == null ? "" : actionurl;

		// 补充的

		// 进入添加页面前，添加页面数据的获得。
		if ("add".equals(reqmap.getParameter("task"))) {
			try {
				ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");
				CupmRmi cupm = (CupmRmi) RmiEntry.iv("cupm");
				request.setAttribute("flag", "add");
				UmsProtectedobject upo = rsrmi.loadResourceByNatural(cupm
						.fetchConfig("ITEMTEAM"));
				List itemList = rsrmi.subResource(upo.getId());
				request.setAttribute("itemList", itemList);
				upo = rsrmi.loadResourceByNatural(cupm.fetchConfig("DEPT"));
				List deptList = rsrmi.subResource(upo.getId());
				request.setAttribute("deptList", deptList);
				saveToken(request);

				// 获得默认的角色，添加到页面中

				if ("dept".equals(request.getParameter("task2"))) {
					Clerk cl = new Clerk();
					String deptid = reqmap.getParameter("id");
					cl.setDeptment(deptid);
					UmsProtectedobject upodept = rsrmi.loadResourceById(deptid);

					cl.setFaxNO(upodept.getName());
					cl.setOfficeNO(code);

					// if ("35".equals(actionurl)) {//省公司
					// String initphone = cupm.fetchConfig("initphone");
					// String initmail = cupm.fetchConfig("initmail");
					//
					// cl.setPhoneNO(initphone);
					// cl.setEmail(initmail);
					//						
					// String defaultRoleID = "63";//固定的省公司角色
					// Long id = Long.valueOf(defaultRoleID);
					// UmsRole role = rsrmi.loadRole(id);
					// List roleList = new ArrayList();
					// roleList.add(role);
					// request.setAttribute("rolelist", roleList);
					//						
					// }else if(actionurl.length()==4){//营销部门
					// String defaultRoleID ="61";
					// Long id = Long.valueOf(defaultRoleID);
					// UmsRole role = rsrmi.loadRole(id);
					// List roleList = new ArrayList();
					// roleList.add(role);
					// request.setAttribute("rolelist", roleList);
					// }else if(actionurl.length()==8){
					// String defaultRoleID ="62";
					// Long id = Long.valueOf(defaultRoleID);
					// UmsRole role = rsrmi.loadRole(id);
					// List roleList = new ArrayList();
					// roleList.add(role);
					// request.setAttribute("rolelist", roleList);
					// }

					List roleList = new ArrayList();
					request.setAttribute("rolelist", roleList);

					request.setAttribute("actionurl", upodept.getActionurl());
					request.setAttribute("clerk", cl);
				}

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
			}
		}

		// 执行添加操作
		if ("save".equals(reqmap.getParameter("task"))) {
			try {
				ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");

				if (StringUtils.isNotEmpty(reqmap.getParameter("newhiddenid"))) {
					clerk.setDeptment(reqmap.getParameter("newhiddenid"));
					UmsProtectedobject upo = rsrmi.loadResourceById(reqmap
							.getParameter("newhiddenid"));
					clerk.setExtendattribute(upo.getNaturalname());
					String loginname = request.getParameter("naturalname")
							.trim();
					clerk.setDescription(loginname);

					// 添加群组
					String teams = reqmap.getParameter("teams");
					clerk.setProvince(teams);
					if (rsrmi.addClerk(code, clerk)) {

						// 添加角色
						String roles = reqmap.getParameter("roles");
						if (roles != null && !roles.equals("")) {
							String[] role = roles.split("@");
							List<UmsRole> list = new ArrayList<UmsRole>();
							if (role != null && role.length > 0) {
								for (int i = 0; i < role.length; i++) {
									UmsRole urole = new UmsRole();
									urole.setId(new Long(role[i]));
									list.add(urole);
								}
							}
							rsrmi.roleRelationupdate(code, loginname, list);
						}
						CupmRmi cupmRmi = (CupmRmi) RmiEntry.iv("cupm");
						cupmRmi.initCacheUser(loginname);

						// 进行创建用户时,自动同步帐号
						rsrmi.SyncUser(SyncUserUtil._PARAM_OPE_ADD, code,
								loginname);

						reqmap.setAlertMsg("新建用户成功！");
						request.setAttribute("result", "y");
						OperationLog.info(request, "新建用户", "新建用户" + loginname
								+ clerk.getName() + "成功！", true);
					} else {
						reqmap.setAlertMsg("新建用户失败!请检查用户名和帐号是否正确或已存在!");
						request.setAttribute("result", "n");
						OperationLog.info(request, "新建用户", "新建用户失败!"
								+ loginname + ",请检查用户名和帐号是否正确或已存在!", false);
					}
				} else {
					reqmap.setAlertMsg("部门不正确,无法新建用户！");
					request.setAttribute("result", "n");
					OperationLog.info(request, "新建用户", "部门不正确,无法新建用户！", false);
				}
				resetToken(request);

			} catch (Exception e) {
				e.printStackTrace();
				reqmap.setAlertMsg(e.getMessage());
				request.setAttribute("result", "n");
				OperationLog.info(request, "新建用户", e.getMessage(),false);
			}
		}

		return mapping.getInputForward();
	}
}
