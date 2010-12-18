package oe.security3a.seupublic.authentication;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import oe.security3a.SeuserEntry;
import oe.security3a.seucore.accountser.UserService;
import oe.security3a.seucore.obj.Clerk;
import oe.security3a.seupublic.authentication.PDP;
import oe.security3a.seupublic.authentication.obj.RequestCtx;
import oe.security3a.seupublic.authentication.obj.ResponseCtx;
import oe.security3a.seupublic.authentication.obj.core.AttributeCtx;
import oe.security3a.seupublic.authentication.obj.core.PermissionCtx;
import oe.security3a.seupublic.authentication.obj.core.SubjectCtx;
import oe.security3a.seupublic.authentication.obj.core.TargetCtx;

import org.apache.commons.lang.StringUtils;


public class PDPClerkOutput implements PDP {

	private UserService userservice = (UserService) SeuserEntry.iv("userService");

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
				String[] cns = StringUtils.split(attributectx.getValue(), ",");
				for (int i = 0; i < cns.length; i++) {
					Clerk clerk = (Clerk) userservice.fetchDao().loadObject(code, cns[i]);
					PermissionCtx per = new PermissionCtx();
					AttributeCtx resource = new AttributeCtx("resource", clerk.getDeptment());
					Set<AttributeCtx> set = new HashSet<AttributeCtx>();
					AttributeCtx ac = new AttributeCtx("extendinfo", "name=" + clerk.getName() + ",naturalname="
							+ clerk.getNaturalname() + ",description=" + clerk.getDescription() + ",company="
							+ clerk.getCompany() + ",province=" + clerk.getProvince() + ",faxNO=" + clerk.getFaxNO()
							+ ",phoneNO=" + clerk.getPhoneNO() + ",email=" + clerk.getEmail() + ",remark="
							+ clerk.getRemark());
					set.add(ac);
					per.setSet(set);
					per.setResource(resource);
					response.newPermission(per);
				}
			}
		}
		return response;
	}
}
