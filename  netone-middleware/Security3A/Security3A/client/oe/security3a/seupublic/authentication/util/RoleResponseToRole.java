package oe.security3a.seupublic.authentication.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import oe.security3a.seucore.obj.Role;
import oe.security3a.seupublic.authentication.obj.RequestCtx;
import oe.security3a.seupublic.authentication.obj.ResponseCtx;
import oe.security3a.seupublic.authentication.obj.core.AttributeCtx;
import oe.security3a.seupublic.authentication.obj.core.PermissionCtx;
import oe.security3a.seupublic.authentication.obj.core.SubjectCtx;
import oe.security3a.seupublic.authentication.obj.core.TargetCtx;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;


public class RoleResponseToRole {

	public static List evaluate(ResponseCtx response) throws Exception {
		List<Role> rolelist = new ArrayList<Role>();
		TargetCtx targetctx = response.getTarget();
		Set permissionsetctx = response.getPermissions();
		RequestCtx requestctx = targetctx.getRequest();
		SubjectCtx subjectctx = requestctx.getSubject();
		Set<AttributeCtx> subjects = subjectctx.getAttribute();
		String operation = "";
		for (Iterator iter = subjects.iterator(); iter.hasNext();) {
			AttributeCtx attributectx = (AttributeCtx) iter.next();
			if ("ope".equals(attributectx.getName())) {
				operation = attributectx.getValue();
			}
		}
		if ("getRole".equals(operation)) {
			if (permissionsetctx != null) {
				for (Iterator iter = permissionsetctx.iterator(); iter.hasNext();) {
					PermissionCtx pctx = (PermissionCtx) iter.next();
					AttributeCtx resource = pctx.getResource();
					Set<AttributeCtx> set = pctx.getSet();
					if ("resource".equals(resource.getName())) {
						Role role = new Role();
						for (Iterator iterator = set.iterator(); iterator.hasNext();) {
							AttributeCtx attributectx = (AttributeCtx) iterator.next();
							if ("extendinfo".equals(attributectx.getName())
									&& StringUtils.isNotEmpty(attributectx.getValue())) {
								String[] values = StringUtils.split(attributectx.getValue(), ",");
								for (int i = 0; i < values.length; i++) {
									String key = StringUtils.substringBefore(values[i], "=");
									String value = StringUtils.substringAfter(values[i], "=");
									if (StringUtils.isEmpty(value) || "null".equals(value)) {
										value = "";
									}
									BeanUtils.setProperty(role, key, value);
								}
							}
						}
						rolelist.add(role);
					}
				}
			}
		}
		return rolelist;
	}

}