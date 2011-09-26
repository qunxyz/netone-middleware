package oe.security4a.web.rbac;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.frame.web.form.RequestParamMap;
import oe.frame.web.form.RequestUtil;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ApplicationRmi;
import oe.security3a.client.rmi.CupmRmi;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.auditingser.OperationLog;
import oe.security3a.seucore.obj.Clerk;
import oe.security3a.seucore.obj.User2Role;
import oe.security3a.seucore.obj.db.UmsApplication;
import oe.security3a.seucore.obj.db.UmsProtectedobject;
import oe.security3a.seucore.obj.db.UmsRole;
import oe.security3a.seucore.obj.db.UmsRole2role2;
import oe.security3a.seucore.obj.db.UmsRolepermission;
import oe.security3a.seucore.roleser.RoleDao;
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



public class RbacAddAction extends Action {


	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		RequestParamMap reqmap = RequestUtil.setParamMapToRequest(request);
		request.setAttribute("flag", "add");
		String source = reqmap.getParameter("source");
		request.setAttribute("source", source);
		
		OnlineUserMgr olmgr = new DefaultOnlineUserMgr();
		OnlineUser oluser = olmgr.getOnlineUser(request);
		String loginName = oluser.getLoginname();
		String code =oluser.getBelongto();
		
		
		//进入角色创建页面
		if ("add".equals(request.getParameter("task"))) {
			try {
				ApplicationRmi rmi = (ApplicationRmi) RmiEntry.iv("application");
				CupmRmi cupmRmi = (CupmRmi) RmiEntry.iv("cupm");
				ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");
				saveToken(request);
				UmsProtectedobject up = new UmsProtectedobject();
				List list = rsrmi.fetchResource(up, null);
				request.setAttribute("list", list);
				UmsApplication ua = new UmsApplication();
				List applist = rmi.queryObjects(ua, null);
				request.setAttribute("applist", applist);
				UmsProtectedobject ups = rsrmi.loadResourceByNatural(cupmRmi.fetchConfig("OPE"));
				List levellist = rsrmi.subResource(ups.getId());
				request.setAttribute("levellist", levellist);
				if (StringUtils.isNotEmpty(request.getParameter("belongto"))) {
					UmsProtectedobject upo = rsrmi.loadResourceByNatural(request.getParameter("belongto"));
					UmsRole umsRole = new UmsRole();
					umsRole.setBelongingness(upo.getId());
					request.setAttribute("umsRole", umsRole);
					request.setAttribute("name", request.getAttribute("name"));
					request.setAttribute("style", "display:none");
				}
				
				
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
			}
			return mapping.findForward("addindex");
		}
		if ("save".equals(request.getParameter("task"))) {
			try {
				ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");
				CupmRmi cupmRmi = (CupmRmi) RmiEntry.iv("cupm");
				UmsRole urole = (UmsRole) RequestUtil.mappingReqParam(UmsRole.class, reqmap);
				if (StringUtils.isNotEmpty(reqmap.getParameter("naturalname"))) {
					UmsRole role = new UmsRole();
					List list1 = rsrmi.fetchRole(urole, null, null);
					if (list1 != null && list1.size() > 0) {
						role.setNaturalname(reqmap.getParameter("naturalname").toUpperCase());
						List list2 = rsrmi.fetchRole(role, null, null);
						if (list2 != null && list2.size() > 0) {
							reqmap.setAlertMsg("角色名称已存在,新建角色失败！");
							request.setAttribute("result", "n");
							OperationLog.info(request, "新建角色", role.getNaturalname()+"角色名称已存在,新建角色失败！",false);
							return mapping.findForward("addindex");
						}
					}
				}
				if (isTokenValid(request, true)) {
					if (urole.getName() == null || urole.getName().equals("")) {
						return null;
					}
					urole.setBelongingness(reqmap.getParameter("belonging"));
					List<User2Role> userrole = new ArrayList<User2Role>();
					String roleusersstr = reqmap.getParameter("roleusers");
					if(roleusersstr.length()>0)
					{
						String[] roleusers = roleusersstr.split(",");
						if (roleusers.length>0) {
							for (String userid : roleusers) {
								User2Role u2r = new User2Role();
								u2r.setUserid(userid);
								userrole.add(u2r);
							}
						}
					}

					UmsRole2role2 urolerole = new UmsRole2role2();
					urolerole.setRelationalrolesubid(reqmap.getParameter("parentId"));
					urolerole.setRelationtype(RoleDao._ROLE_RELATION_TYPE[2][0]);

					List<UmsRolepermission> urplist = new ArrayList<UmsRolepermission>();

					if (RbacIndexAction.PurviewType.equals("1")) {
						// 使用jssq。jsp
						String urpsstr = reqmap.getParameter("allPermission");
						String[] urps = urpsstr.split("@");
						if (urps != null) {
							for (String urpstr : urps) {
								if (!urpstr.equals("")) {
									String[] value = urpstr.split("#");
									UmsRolepermission urp = new UmsRolepermission();
									urp.setDninfo(value[0]);
									urp.setOperationid(value[1]);
									urp.setComments(value[2]);
									urplist.add(urp);
								}
							}
						}
					}

					if (RbacIndexAction.PurviewType.equals("2")) {
						// 使用jssq2。jsp
						String urpsstr = reqmap.getParameter("permissions");
						String[] urps = urpsstr.split("@");
						for (int i = 0; i < urps.length; i++) {
							RequestParamMap map = new RequestParamMap();
							map.loadStringValue(urps[i]);
							for (Iterator iter = map.keySet().iterator(); iter.hasNext();) {
								String dn = (String) iter.next();
								String action = map.get(dn).toString();
								if (action.equals("7") || action.equals("3") || action.equals("1")) {
									UmsRolepermission urp = new UmsRolepermission();
									urp.setDninfo(dn);
									urp.setOperationid(action);
									urp.setComments(dn);
									urplist.add(urp);
								}
							}
						}
					}

					Map<String, Object> map = new HashMap<String, Object>();
					map.put(RoleDao._ROLE, urole);
					map.put(RoleDao._ROLE2USER, userrole);
					map.put(RoleDao._ROLE2ROLE, urolerole);
					map.put(RoleDao._ROLE2PERMISSION, urplist);

					String roleid = rsrmi.addRole(code, map).toString();
					

					List<Clerk> roleuserlist = rsrmi.fetchUser(code,roleid);
					for (Clerk clerk : roleuserlist) {
						cupmRmi.initCacheUser(clerk.getName());
						//角色修改，如果有涉及人员变动，进行人员同步操作
						rsrmi.SyncUser(SyncUserUtil._PARAM_OPE_ROLE, code, clerk.getDescription());
					}
					cupmRmi.initCacheRoleCore(roleid);
					resetToken(request);
				} else {
					saveToken(request);
				}
				reqmap.setAlertMsg("新建角色成功！");
				request.setAttribute("result", "y");
				OperationLog.info(request, "新建角色", "新建角色成功",true);
			} catch (Exception e) {
				e.printStackTrace();
				reqmap.setAlertMsg(e.getMessage());
				request.setAttribute("result", "n");
				OperationLog.info(request, "新建角色", e.getMessage(),false);
			}

			return mapping.findForward("addindex");
		}

		return null;
	}
}
