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
	 * ����������������
	 * 
	 * @param list
	 *            ������������
	 * @return 2ά����String[n][0] ��������ID,String[n][1]�������[�ɹ�,ʧ��,����]
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
				str[i][1] = "����";
			} catch (Exception e) {
				if (protectedobjectservice.fetchDao().create(proobj)) {
					str[i][0] = new Long(proobj.getId()).toString();
					str[i][1] = "�ɹ�";
				} else {
					str[i][0] = new Long(proobj.getId()).toString();
					str[i][1] = "ʧ��";
				}
			}
			i++;
		}
		return str;
	}

	/**
	 * ����ɾ������
	 * 
	 * @param list
	 *            ������������
	 * @return 2ά����String[n][0] ��������ID,String[n][1]ɾ�����[�ɹ�,ʧ��]
	 */
	public String[][] delProtectedObjects(List list) {
		String[][] str = new String[list.size()][2];
		int i = 0;
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			UmsProtectedobject proobj = (UmsProtectedobject) iter.next();
			if (protectedobjectservice.fetchDao().drop(proobj)) {
				str[i][0] = new Long(proobj.getId()).toString();
				str[i][1] = "�ɹ�";
			} else {
				str[i][0] = new Long(proobj.getId()).toString();
				str[i][1] = "ʧ��";
			}
			i++;
		}
		return str;
	}

	/**
	 * ����ӱ�������
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
