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



public class RbacModifyAction extends Action {


	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		RequestParamMap reqmap = RequestUtil.setParamMapToRequest(request);
		request.setAttribute("flag", "modify");
		String source = request.getParameter("source");
		request.setAttribute("source", source);
		
		OnlineUserMgr olmgr = new DefaultOnlineUserMgr();
		OnlineUser oluser = olmgr.getOnlineUser(request);
		String loginName = oluser.getLoginname();
		String code = oluser.getBelongto();
		
		if ("modify".equals(request.getParameter("task"))) {
			try {
				CupmRmi cupmRmi = (CupmRmi) RmiEntry.iv("cupm");
				ApplicationRmi rmi = (ApplicationRmi) RmiEntry.iv("application");
				ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");
				UmsRole umsRole = rsrmi.loadRole(new Long(request.getParameter("chkid")));

				UmsRole parent = rsrmi.fetchExtendedRole(request.getParameter("chkid"));
				List<UmsRolepermission> permisssions = rsrmi.fetchPermission(request.getParameter("chkid"));
				UmsProtectedobject up = new UmsProtectedobject();
				if (StringUtils.isNotEmpty(source)) {
					up.setParentdir("0");
				}
				
				
				
				List list = rsrmi.fetchResource(up, null);
				request.setAttribute("list", list);
				request.setAttribute("umsRole", umsRole);
				request.setAttribute("parent", parent);
				request.setAttribute("permissions", permisssions);
				List<Clerk> roleuserlist = rsrmi.fetchUser(code,request.getParameter("chkid"));
				request.setAttribute("roleuserlist", roleuserlist);
				UmsApplication ua = new UmsApplication();
				List applist = rmi.queryObjects(ua, null);
				request.setAttribute("applist", applist);
				UmsProtectedobject ups = rsrmi.loadResourceByNatural(cupmRmi.fetchConfig("OPE"));
				List levellist = rsrmi.subResource(ups.getId());
				request.setAttribute("levellist", levellist);
				
				
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
				UmsRole urole = (UmsRole) RequestUtil.mappingReqParam(UmsRole.class, reqmap);
				String roleid = request.getParameter("chkid");
				String oldou = request.getParameter("oldbelonging");
				String newou = request.getParameter("belonging");
				urole.setId(new Long(roleid));
				if (StringUtils.isNotEmpty(newou) && !oldou.equals(newou)) {
					if (rsrmi.moveRoleDeptOpe(roleid, oldou, newou)) {
						urole.setBelongingness(newou);
					} else {
						urole.setBelongingness(oldou);
					}
				} else {
					urole.setBelongingness(oldou);
				}
				

				UmsRole2role2 urolerole = new UmsRole2role2();
				if(!"".endsWith(request.getParameter("parentId"))||request.getParameter("parentId")!=null){
					urolerole.setRelationalrolemainid(roleid);
					urolerole.setRelationalrolesubid(request.getParameter("parentId"));
					urolerole.setRelationtype(RoleDao._ROLE_RELATION_TYPE[2][0]);
				}
				
				List<UmsRolepermission> urplist = new ArrayList<UmsRolepermission>();
				List<User2Role> userroles = new ArrayList<User2Role>();
				String roleusersstr = request.getParameter("roleusers");
				String[] roleusers = null;
				if(roleusersstr.length()>0){
					 roleusers = roleusersstr.split(",");
					if (roleusers != null) {
						for (String userid : roleusers) {
							User2Role u2r = new User2Role();
							u2r.setUserid(userid);
							userroles.add(u2r);
						}
					}
				}
				

				if (RbacIndexAction.PurviewType.equals("1")) {
					// 使用jssq。jsp
					String urpsstr = request.getParameter("allPermission");
					String[] urps = urpsstr.split("@");
					if (urps != null) {
						for (String urpstr : urps) {
							if (!urpstr.equals("")) {
								String[] value = urpstr.split("#");
								UmsRolepermission urp = new UmsRolepermission();
								urp.setRoleid(roleid);
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
					String urpsstr = request.getParameter("permissions");
					String[] urps = urpsstr.split("@");
					for (int i = 0; i < urps.length; i++) {
						RequestParamMap map = new RequestParamMap();
						map.loadStringValue(urps[i]);
						for (Iterator iter = map.keySet().iterator(); iter.hasNext();) {
							String dn = (String) iter.next();
							String action = map.get(dn).toString();
							if (action.equals("7") || action.equals("3") || action.equals("1")) {
								UmsRolepermission urp = new UmsRolepermission();
								urp.setRoleid(roleid);
								urp.setDninfo(dn);
								urp.setOperationid(action);
								urp.setComments(dn);
								urplist.add(urp);
							}
						}
					}
				}

				Map<String, Object> map = new HashMap<String, Object>();
				map.put(RoleDao._ROLE2PERMISSION, urplist);
				map.put(RoleDao._ROLE, urole);
				map.put(RoleDao._ROLE2ROLE, urolerole);
				map.put(RoleDao._ROLE2USER, userroles);
				CupmRmi cupmRmi = (CupmRmi) RmiEntry.iv("cupm");
				List<Clerk> roleuserlist = rsrmi.fetchUser(code,roleid);
				for (Clerk clerk : roleuserlist) {
					cupmRmi.initCacheUser(clerk.getName());
				}
				rsrmi.updateRole(code, map);
				if(roleusers!=null){
					for (String userid : roleusers) {
						cupmRmi.initCacheUser(userid);
						//角色修改，如果有涉及人员变动，进行人员同步操作
						rsrmi.SyncUser(SyncUserUtil._PARAM_OPE_ROLE, code, userid);
					}
				}
				String rolename = rsrmi.loadRole(Long.valueOf(roleid)).getNaturalname();
				cupmRmi.initCacheRoleCore(roleid);
				cupmRmi.initCacheRole(rolename);

				reqmap.setAlertMsg("角色修改成功！");
				request.setAttribute("result", "y");
				OperationLog.info(request, "修改角色", "角色修改成功！");
			} catch (Exception e) {
				e.printStackTrace();
				reqmap.setAlertMsg(e.getMessage());
				request.setAttribute("result", "n");
				OperationLog.error(request, "修改角色", e.getMessage());
			}

			return mapping.findForward("addindex");
		}
		return null;
	}
}
