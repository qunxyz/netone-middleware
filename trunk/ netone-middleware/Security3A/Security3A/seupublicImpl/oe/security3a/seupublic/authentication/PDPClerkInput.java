package oe.security3a.seupublic.authentication;

import java.util.Iterator;
import java.util.Set;

import oe.rmi.client.RmiEntry;
import oe.security3a.SeuserEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.accountser.UserService;
import oe.security3a.seucore.obj.Clerk;
import oe.security3a.seupublic.authentication.PDP;
import oe.security3a.seupublic.authentication.obj.RequestCtx;
import oe.security3a.seupublic.authentication.obj.ResponseCtx;
import oe.security3a.seupublic.authentication.obj.core.AttributeCtx;
import oe.security3a.seupublic.authentication.obj.core.ResourceCtx;
import oe.security3a.seupublic.authentication.obj.core.TargetCtx;
import oe.security3a.sso.util.SyncUser;
import oe.security3a.sso.util.SyncUser;
import oe.security3a.sso.util.SyncUserUtil;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

public class PDPClerkInput implements PDP {

	private UserService userservice = (UserService) SeuserEntry
			.iv("userService");

	public ResponseCtx evaluate(String code, RequestCtx request)
			throws Exception {
		ResponseCtx response = new ResponseCtx();
		TargetCtx target = response.newTarget();
		target.setRequest(request);
		ResourceCtx resourcectx = request.getResource();
		Set<AttributeCtx> attribute = resourcectx.getAttribute();
		if (attribute == null) {
			throw new RuntimeException("未发现数据");
		}
		for (Iterator iter = attribute.iterator(); iter.hasNext();) {
			AttributeCtx attributectx = (AttributeCtx) iter.next();
			String parentid = attributectx.getName();
			String[] values = StringUtils.split(attributectx.getValue(), ",");
			Clerk clerk = new Clerk();
			clerk.setOfficeNO(code);
			clerk.setDeptment(parentid);
			String name = "";
			for (int i = 0; i < values.length; i++) {
				String key = StringUtils.substringBefore(values[i], "=");
				String value = StringUtils.substringAfter(values[i], "=");
				if (StringUtils.isEmpty(value) || "null".equals(value)) {
					value = "";
				}
				BeanUtils.setProperty(clerk, key, value);
				if (i == 2) {
					name = value;
				}
			}
			if (!userservice.fetchDao().create(clerk)) {
				return null;
			}
			// 导入用户时,自动同步帐号
			ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");
			rsrmi.SyncUser(SyncUserUtil._PARAM_OPE_ADD, code, name);
		}
		return response;
	}
}
