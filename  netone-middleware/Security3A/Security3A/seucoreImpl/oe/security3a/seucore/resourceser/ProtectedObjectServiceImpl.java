package oe.security3a.seucore.resourceser;

import java.util.ArrayList;
import java.util.List;

import oe.frame.orm.Ormer;
import oe.frame.orm.OrmerEntry;
import oe.security3a.SeudaoEntry;
import oe.security3a.SeumanEntry;
import oe.security3a.seucore.obj.db.UmsProtectedobject;
import oe.security3a.seucore.permission.PermissionManager;
import oe.security3a.seucore.resourceser.ProtectedObjectDao;
import oe.security3a.seucore.resourceser.ProtectedObjectService;

import org.apache.commons.lang.StringUtils;


/**
 * 保护对象服务接口实现
 * 
 * @author ni.he.qing
 * 
 */
public class ProtectedObjectServiceImpl implements ProtectedObjectService {

	/**
	 * 获得保护对象dao
	 */
	public ProtectedObjectDao fetchDao() {
		return (ProtectedObjectDao) SeudaoEntry.iv("protectedObjectDao");
	}

	// public List<UmsProtectedobject> getChildren(String appname, String
	// uponame) {
	// Ormer ormer = OrmerEntry.fetchOrmer();
	// UmsApplication app = new UmsApplication();
	// app.setName(appname);
	// List applist = ormer.fetchQuerister().queryObjects(app, null);
	// if(applist.size() > 0){
	// UmsProtectedobject upo = new UmsProtectedobject();
	// upo.setName(uponame);
	// upo.setAppid(((UmsApplication)applist.get(0)).getId());
	// List upolist = ormer.fetchQuerister().queryObjects(upo,null);
	// if(upolist.size()>0){
	// String id = ((UmsProtectedobject)upolist.get(0)).getId();
	// UmsProtectedobject tmpupo = new UmsProtectedobject();
	// tmpupo.setParentdir(id);
	// List reupolist = ormer.fetchQuerister().queryObjects(tmpupo,null);
	// return reupolist;
	// }
	// }
	// return null;
	// }

	public List<UmsProtectedobject> getChildren(String uponame) {

		Ormer ormer = OrmerEntry.fetchOrmer();
		PermissionManager pm = (PermissionManager) SeumanEntry.iv("permissionManager");

		String ou = null;
		try {
			ou = pm.getOuFromNaturalName(uponame);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (ou != null) {
			UmsProtectedobject upo = new UmsProtectedobject();
			String parentDir = StringUtils.substringAfterLast(ou, ".");
			upo.setParentdir(parentDir);
			return ormer.fetchQuerister().queryObjects(upo, null);
		}
		return new ArrayList<UmsProtectedobject>();

	}

}
