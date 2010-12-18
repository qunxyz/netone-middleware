package oe.security3a.seupublic.authentication;

import java.util.Iterator;
import java.util.List;

import oe.security3a.SeumanEntry;
import oe.security3a.SeuserEntry;
import oe.security3a.seucore.obj.db.UmsProtectedobject;
import oe.security3a.seucore.permission.PermissionManager;
import oe.security3a.seucore.permission.PermissionService;
import oe.security3a.seucore.resourceser.ProtectedObjectService;
import oe.security3a.seupublic.authentication.PDP;
import oe.security3a.seupublic.authentication.obj.RequestCtx;
import oe.security3a.seupublic.authentication.obj.ResponseCtx;
import oe.security3a.seupublic.authentication.obj.core.ActionCtx;
import oe.security3a.seupublic.authentication.obj.core.AttributeCtx;
import oe.security3a.seupublic.authentication.obj.core.PermissionCtx;
import oe.security3a.seupublic.authentication.obj.core.ResourceCtx;
import oe.security3a.seupublic.authentication.obj.core.SubjectCtx;
import oe.security3a.seupublic.authentication.obj.core.TargetCtx;

import org.apache.commons.lang.StringUtils;


public class PDPClerkPermission implements PDP {

	PermissionService permissionService = (PermissionService) SeuserEntry.iv("permissionService");
	
	PermissionManager permissionManager = (PermissionManager) SeumanEntry.iv("permissionManager");

	ProtectedObjectService protectedobjectservice = (ProtectedObjectService) SeuserEntry.iv("protectedObjectService");

	public ResponseCtx evaluate(String code, RequestCtx request) throws Exception {
		ResponseCtx response = new ResponseCtx();
		TargetCtx target = response.newTarget();
		target.setRequest(request);
		response.newPermission(null);
		SubjectCtx subjectctx = request.getSubject();
		ResourceCtx resourcectx = request.getResource();
		ActionCtx actionctx = request.getAction();
		String participant = "";
		String actionvalue = "";
		for (Iterator iter = subjectctx.getAttribute().iterator(); iter.hasNext();) {
			AttributeCtx attributectx = (AttributeCtx) iter.next();
			if ("participant".equals(attributectx.getName())) {
				participant = attributectx.getValue();
			}
		}
		for (Iterator iter = actionctx.getAttribute().iterator(); iter.hasNext();) {
			AttributeCtx attributectx = (AttributeCtx) iter.next();
			actionvalue = attributectx.getValue();
		}
		for (Iterator iter = resourcectx.getAttribute().iterator(); iter.hasNext();) {
			AttributeCtx attributectx = (AttributeCtx) iter.next();
			String statusvalue = "";
			String dnname = permissionManager.getOuFromNaturalName(attributectx.getName());
			String lastSymbol = StringUtils.substringAfterLast(dnname, ".");
			if ("*".equals(lastSymbol)) {
				statusvalue = "1";
				String[] str = StringUtils.split(StringUtils.substringBeforeLast(dnname, ".*"), ".");
				UmsProtectedobject upt = new UmsProtectedobject();
				upt.setParentdir(str[str.length-1]);
				List list = protectedobjectservice.fetchDao().queryObjects(upt, null);
				for (Iterator iteror = list.iterator(); iteror.hasNext();) {
					UmsProtectedobject uproobj = (UmsProtectedobject) iteror.next();
					if (!permissionService.checkUserPermission(code, participant, uproobj.getOu(), actionvalue)) {
						statusvalue = "0";
						break;
					}
				}
			} else {
				if (permissionService.checkUserPermission(code, participant, dnname, actionvalue)) {
					statusvalue = "1";
				} else {
					statusvalue = "0";
				}
			}
			PermissionCtx per = new PermissionCtx();
			AttributeCtx resource = new AttributeCtx("resource", attributectx.getName());
			AttributeCtx action = new AttributeCtx("action", actionvalue);
			AttributeCtx status = new AttributeCtx("status", statusvalue);
			per.setResource(resource);
			per.setAction(action);
			per.setStatus(status);
			response.newPermission(per);
		}
		return response;
	}
}
