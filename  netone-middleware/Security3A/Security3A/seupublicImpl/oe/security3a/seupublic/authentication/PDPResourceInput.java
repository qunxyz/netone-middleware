package oe.security3a.seupublic.authentication;

import java.util.Iterator;
import java.util.Set;

import oe.security3a.SeumanEntry;
import oe.security3a.SeuserEntry;
import oe.security3a.seucore.obj.db.UmsProtectedobject;
import oe.security3a.seucore.permission.PermissionManager;
import oe.security3a.seucore.resourceser.ProtectedObjectService;
import oe.security3a.seupublic.authentication.PDP;
import oe.security3a.seupublic.authentication.obj.RequestCtx;
import oe.security3a.seupublic.authentication.obj.ResponseCtx;
import oe.security3a.seupublic.authentication.obj.core.AttributeCtx;
import oe.security3a.seupublic.authentication.obj.core.ResourceCtx;
import oe.security3a.seupublic.authentication.obj.core.TargetCtx;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;


public class PDPResourceInput implements PDP {

	ProtectedObjectService protectedObjectService = (ProtectedObjectService) SeuserEntry.iv("protectedObjectService");

	PermissionManager permissionManager = (PermissionManager) SeumanEntry.iv("permissionManager");

	//这里的code没有使用
	public ResponseCtx evaluate(String code, RequestCtx request) throws Exception {
		ResponseCtx response = new ResponseCtx();
		TargetCtx target = response.newTarget();
		target.setRequest(request);
		ResourceCtx resourcectx = request.getResource();
		Set<AttributeCtx> attribute = resourcectx.getAttribute();
		if(attribute==null){
			throw new RuntimeException("未发现数据");
		}
		for (Iterator iter = attribute.iterator(); iter.hasNext();) {
			AttributeCtx attributectx = (AttributeCtx) iter.next();
			String[] ous = StringUtils.split(permissionManager.getOuFromNaturalName(attributectx.getName()), ".");
			String appid = ous[0];
			String parentdir = ous[ous.length - 1];
			String[] value = StringUtils.split(attributectx.getValue(), ",");

			UmsProtectedobject upo = new UmsProtectedobject();
			upo.setAppid(new Long(appid));
			upo.setParentdir(parentdir);
			for (int i = 0; i < value.length; i++) {
				String[] keyValue = value[i].split("=");
				if (keyValue == null || keyValue.length != 2) {
					if (keyValue.length == 1 || keyValue[1].equals("null")) {
						continue;
					} else {
						throw new RuntimeException("无效属性:" + attributectx.getValue());
					}
				}
				BeanUtils.setProperty(upo, keyValue[0], keyValue[1]);
			}
			if (!protectedObjectService.fetchDao().create(upo)) {
				return null;
			}
		}
		return response;
	}

}
