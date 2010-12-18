package oe.security3a.seucore.resourceser;

import java.util.Iterator;
import java.util.List;

import oe.security3a.SeuserEntry;
import oe.security3a.seucore.obj.db.UmsApplication;
import oe.security3a.seucore.obj.db.UmsProtectedobject;
import oe.security3a.seucore.resourceser.ApplicationService;
import oe.security3a.seucore.resourceser.ProtectedObjectManager;
import oe.security3a.seucore.resourceser.ProtectedObjectService;


public class ProtectedObjectManagerImpl implements ProtectedObjectManager {

	private ProtectedObjectService protectedobjectservice = (ProtectedObjectService) SeuserEntry
			.iv("protectedObjectService");

	private ApplicationService applicationservice = (ApplicationService) SeuserEntry
			.iv("applicationService");

	public UmsApplication fetchApplication(UmsProtectedobject ope) {
		return (UmsApplication) applicationservice.fetchDao().loadObject(
				new UmsApplication().getClass(), ope.getAppid());
	}

	/**
	 * 批量创建保护对象
	 * 
	 * @param list
	 *            保护对象数组
	 * @return 2维数组String[n][0] 保护对象ID,String[n][1]创建结果[成功,失败,覆盖]
	 */
	public String[][] createProtectedObjects(List list) {
		String[][] str = new String[list.size()][2];
		int i = 0;
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			UmsProtectedobject proobj = (UmsProtectedobject) iter.next();
			try {
				protectedobjectservice.fetchDao().loadObject(proobj.getClass(),
						proobj.getId());
				protectedobjectservice.fetchDao().update(proobj);
				str[i][0] = new Long(proobj.getId()).toString();
				str[i][1] = "覆盖";
			} catch (Exception e) {
				if (protectedobjectservice.fetchDao().create(proobj)) {
					str[i][0] = new Long(proobj.getId()).toString();
					str[i][1] = "成功";
				} else {
					str[i][0] = new Long(proobj.getId()).toString();
					str[i][1] = "失败";
				}
			}
			i++;
		}
		return str;
	}

	/**
	 * 批量删除对象
	 * 
	 * @param list
	 *            保护对象数组
	 * @return 2维数组String[n][0] 保护对象ID,String[n][1]删除结果[成功,失败]
	 */
	public String[][] delProtectedObjects(List list) {
		String[][] str = new String[list.size()][2];
		int i = 0;
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			UmsProtectedobject proobj = (UmsProtectedobject) iter.next();
			if (protectedobjectservice.fetchDao().drop(proobj)) {
				str[i][0] = new Long(proobj.getId()).toString();
				str[i][1] = "成功";
			} else {
				str[i][0] = new Long(proobj.getId()).toString();
				str[i][1] = "失败";
			}
			i++;
		}
		return str;
	}

	/**
	 * 获得子保护对象
	 * 
	 * @param group
	 * @return
	 */
	public List subProtectedObject(UmsProtectedobject protectdObject) {
		 UmsProtectedobject proobj = new UmsProtectedobject();
		 proobj.setParentdir(protectdObject.getId().toString());
		 return protectedobjectservice.fetchDao().queryObjects(proobj, null);
	}

}
