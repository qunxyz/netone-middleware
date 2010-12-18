package oe.security3a.seucore.permission;

import java.util.List;

import oe.frame.orm.Ormer;
import oe.frame.orm.OrmerEntry;
import oe.security3a.seucore.obj.db.UmsProtectedobject;
import oe.security3a.seucore.obj.db.UmsRole;
import oe.security3a.seucore.permission.PermissionManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class PermissionManagerImpl implements PermissionManager {
	private Log log = LogFactory.getLog(PermissionManagerImpl.class);

	private UmsProtectedobject load(String resourceid) {
		Ormer ormer = OrmerEntry.fetchOrmer();
		UmsProtectedobject upp = (UmsProtectedobject) ormer.fetchQuerister().loadObject(UmsProtectedobject.class,
				resourceid);
		return upp;
	}

	public String getOuFromResourceId(String resourceid) throws Exception {
		return load(resourceid).getOu();
	}

	public String getNaturalNameFromResourceId(String dnId) throws Exception {
		return load(dnId).getNaturalname();

	}

	public String getOuFromNaturalName(String dnnatrualname) throws Exception {
		if (dnnatrualname == null || dnnatrualname.length() == 0) {
			throw new RuntimeException("dn描述为空");
		}

		Ormer ormer = OrmerEntry.fetchOrmer();
		UmsProtectedobject upp = new UmsProtectedobject();
		upp.setNaturalname(dnnatrualname.toUpperCase());

		List list = ormer.fetchQuerister().queryObjects(upp, null);
		if (list == null || list.size() == 0) {
			return null;
		}
		if (list != null && list.size() == 1) {
			return ((UmsProtectedobject) list.get(0)).getOu();
		}
		throw new RuntimeException("保护对象结构出现严重异常,出现重复的" + dnnatrualname + " 次数" + list.size());

	}

	public String getRoleIdFromNaturalName(String naturalname) throws Exception {
		Ormer ormer = OrmerEntry.fetchOrmer();
		UmsRole role = new UmsRole();
		role.setNaturalname(naturalname.toUpperCase());
		List list = ormer.fetchQuerister().queryObjects(role, null);
		if (list == null || list.size() == 0) {
			return null;
		}
		if (list.size() > 1) {
			throw new RuntimeException("角色内部错误,出现重复的naturalname");
		}
		return ((UmsRole) list.get(0)).getId().toString();
	}

	public boolean checkCreate(String parentid, String subNatrualname) throws Exception {
		String parentNatrualname = getNaturalNameFromResourceId(parentid);
		if (getOuFromNaturalName(parentNatrualname + "." + subNatrualname) == null) {
			return true;
		}
		return false;
	}
}
