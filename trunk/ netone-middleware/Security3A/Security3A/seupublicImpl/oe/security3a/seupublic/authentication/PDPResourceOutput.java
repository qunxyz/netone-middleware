package oe.security3a.seupublic.authentication;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
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
import oe.security3a.seupublic.authentication.obj.core.PermissionCtx;
import oe.security3a.seupublic.authentication.obj.core.SubjectCtx;
import oe.security3a.seupublic.authentication.obj.core.TargetCtx;

import org.apache.commons.lang.StringUtils;


public class PDPResourceOutput implements PDP {

	ProtectedObjectService protectedObjectService = (ProtectedObjectService) SeuserEntry.iv("protectedObjectService");

	PermissionManager permissionManager = (PermissionManager) SeumanEntry.iv("permissionManager");

	//这里的code没哟使用
	public ResponseCtx evaluate(String code, RequestCtx request) throws Exception {
		ResponseCtx response = new ResponseCtx();
		TargetCtx target = response.newTarget();
		target.setRequest(request);
		response.newPermission(null);
		SubjectCtx subjectctx = request.getSubject();
		Set<AttributeCtx> attribute = subjectctx.getAttribute();
		String naturalname = "";
		String mode = "";
		for (Iterator iter = attribute.iterator(); iter.hasNext();) {
			AttributeCtx attributectx = (AttributeCtx) iter.next();
			if ("condition".equals(attributectx.getName())) {
				naturalname = attributectx.getValue();
				if (StringUtils.contains(naturalname, ".*")) {
					naturalname = StringUtils.substringBefore(naturalname, ".*");
					mode = ".*";
				} else if (StringUtils.contains(naturalname, ".-")) {
					naturalname = StringUtils.substringBefore(naturalname, ".-");
					mode = ".-";
				}
			}
		}
		String ou = permissionManager.getOuFromNaturalName(naturalname);
		UmsProtectedobject upo = (UmsProtectedobject) protectedObjectService.fetchDao().loadObject(
				UmsProtectedobject.class, StringUtils.substringAfterLast(ou, "."));
		List<UmsProtectedobject> list = new ArrayList<UmsProtectedobject>();
		if (".*".equals(mode)) {
			list.add(upo);
			diGui(upo, list);
		} else if (".-".equals(mode)) {
			diGui(upo, list);
		} else {
			list.add(upo);
		}
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			UmsProtectedobject umspro = (UmsProtectedobject) iter.next();
			PermissionCtx per = new PermissionCtx();
			String parentnaturalname = "0";
			if (!"0".equals(umspro.getParentdir())) {
				parentnaturalname = permissionManager.getNaturalNameFromResourceId(umspro.getParentdir());
			}
			AttributeCtx resource = new AttributeCtx("resource", parentnaturalname);
			Set<AttributeCtx> set = new HashSet<AttributeCtx>();
			AttributeCtx ac = new AttributeCtx("extendinfo", "name=" + umspro.getName() + ",naturalname="
					+ umspro.getNaturalname() + ",actionurl=" + umspro.getActionurl() + ",description="
					+ umspro.getDescription() + ",active=" + umspro.getActive() + ",id=" + umspro.getId() + ",ou="
					+ umspro.getOu() + ",parentdir=" + umspro.getParentdir());
			set.add(ac);
			per.setSet(set);
			per.setResource(resource);
			response.newPermission(per);
		}
		return response;
	}

	private void diGui(UmsProtectedobject upo, List<UmsProtectedobject> list) {
		UmsProtectedobject tmpupo = new UmsProtectedobject();
		tmpupo.setParentdir(upo.getId());
		List reupolist = protectedObjectService.fetchDao().queryObjects(tmpupo, null);
		Iterator rsiter = reupolist.iterator();
		while (rsiter.hasNext()) {
			UmsProtectedobject tmpdirn = (UmsProtectedobject) rsiter.next();
			if (tmpdirn.getParentdir().equals(upo.getId())) {
				diGui(tmpdirn, list);
				list.add(tmpdirn);
			}
		}
	}
}
