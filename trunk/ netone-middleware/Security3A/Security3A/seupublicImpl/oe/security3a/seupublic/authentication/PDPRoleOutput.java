package oe.security3a.seupublic.authentication;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import oe.security3a.SeumanEntry;
import oe.security3a.SeuserEntry;
import oe.security3a.seucore.accountser.UserManager;
import oe.security3a.seucore.accountser.UserService;
import oe.security3a.seucore.obj.Clerk;
import oe.security3a.seucore.obj.db.UmsRole;
import oe.security3a.seucore.roleser.RoleService;
import oe.security3a.seupublic.authentication.PDP;
import oe.security3a.seupublic.authentication.obj.RequestCtx;
import oe.security3a.seupublic.authentication.obj.ResponseCtx;
import oe.security3a.seupublic.authentication.obj.core.AttributeCtx;
import oe.security3a.seupublic.authentication.obj.core.PermissionCtx;
import oe.security3a.seupublic.authentication.obj.core.SubjectCtx;
import oe.security3a.seupublic.authentication.obj.core.TargetCtx;

import org.apache.commons.lang.StringUtils;


public class PDPRoleOutput implements PDP {

	private RoleService roleService = (RoleService) SeuserEntry.iv("roleService");

	private UserService userService = (UserService) SeuserEntry.iv("userService");

	private UserManager usermanager = (UserManager) SeumanEntry.iv("userManager");

	public ResponseCtx evaluate(String code, RequestCtx request) throws Exception {
		ResponseCtx response = new ResponseCtx();
		TargetCtx target = response.newTarget();
		target.setRequest(request);
		response.newPermission(null);
		SubjectCtx subjectctx = request.getSubject();
		Set<AttributeCtx> attribute = subjectctx.getAttribute();
		for (Iterator iter = attribute.iterator(); iter.hasNext();) {
			AttributeCtx attributectx = (AttributeCtx) iter.next();
			if ("condition".equals(attributectx.getName())) {
				List list = null;
				if (StringUtils.isEmpty(attributectx.getValue())) {
					list = roleService.fetchDao().queryObjects(new UmsRole(), null);
				} else {
					Clerk clerk = (Clerk) userService.fetchDao().loadObject("", attributectx.getValue());
					if (clerk != null) {
						list = usermanager.getUserRoles(code, clerk.getDescription());
					}
				}
				if (list != null && list.size() > 0) {
					for (Iterator iterator = list.iterator(); iterator.hasNext();) {
						UmsRole role = (UmsRole) iterator.next();
						PermissionCtx per = new PermissionCtx();
						Set<AttributeCtx> set = new HashSet<AttributeCtx>();
						AttributeCtx resource = new AttributeCtx("resource", attributectx.getValue());
						AttributeCtx ac = new AttributeCtx("extendinfo", "name=" + role.getName() + ",naturalname="
								+ role.getNaturalname() + ",description=" + role.getDescription() + ",id="
								+ role.getId().toString() + ",appid=" + role.getAppid().toString() + ",active="
								+ role.getActive() + ",belongingness=" + role.getBelongingness());
						set.add(ac);
						per.setSet(set);
						per.setResource(resource);
						response.newPermission(per);
					}
				}
			}
		}
		return response;
	}
}
