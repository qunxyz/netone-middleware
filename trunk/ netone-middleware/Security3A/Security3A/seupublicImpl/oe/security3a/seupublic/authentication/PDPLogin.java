package oe.security3a.seupublic.authentication;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import oe.security3a.SeumanEntry;
import oe.security3a.seucore.accountser.UserManager;
import oe.security3a.seucore.obj.Clerk;
import oe.security3a.seupublic.authentication.PDP;
import oe.security3a.seupublic.authentication.obj.RequestCtx;
import oe.security3a.seupublic.authentication.obj.ResponseCtx;
import oe.security3a.seupublic.authentication.obj.core.AttributeCtx;
import oe.security3a.seupublic.authentication.obj.core.PermissionCtx;
import oe.security3a.seupublic.authentication.obj.core.TargetCtx;


public class PDPLogin implements PDP {

	private UserManager userman = (UserManager) SeumanEntry.iv("userManager");

	public ResponseCtx evaluate(String code, RequestCtx request) throws Exception {
		ResponseCtx response = new ResponseCtx();
		TargetCtx target = response.newTarget();
		target.setRequest(request);
		String loginname = "";
		String password = "";
		String ope = "";
		Set<AttributeCtx> attribute = request.getSubject().getAttribute();
		for (Iterator iter = attribute.iterator(); iter.hasNext();) {
			AttributeCtx attr = (AttributeCtx) iter.next();
			if ("ope".equals(attr.getName())) {
				ope = attr.getValue();
			}
			if ("participant".equals(attr.getName())) {
				loginname = attr.getValue();
			}
			if ("password".equals(attr.getName())) {
				password = attr.getValue();
			}
		}
		if(ope.equals("login")){
			response.newPermission(null);
			Clerk clerk = userman.validationUserOpe(code, loginname, password);
			if (clerk.getDescription() == null) {
				return null;
			}
			PermissionCtx per = new PermissionCtx();
			AttributeCtx resource = new AttributeCtx("resource", clerk.getDeptment());
			Set<AttributeCtx> set = new HashSet<AttributeCtx>();
			AttributeCtx ac = new AttributeCtx("extendinfo", "name=" + clerk.getName() + ",naturalname="
					+ clerk.getNaturalname() + ",description=" + clerk.getDescription() + ",company=" + clerk.getCompany()
					+ ",province=" + clerk.getProvince() + ",city=" + clerk.getCity() + ",faxNO=" + clerk.getFaxNO()
					+ ",phoneNO=" + clerk.getPhoneNO() + ",email=" + clerk.getEmail() + ",remark=" + clerk.getRemark());
			set.add(ac);
			per.setSet(set);
			per.setResource(resource);
			response.newPermission(per);
		}
		return response;
	}
}
