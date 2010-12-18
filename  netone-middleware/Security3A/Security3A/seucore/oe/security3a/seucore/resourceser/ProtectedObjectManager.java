package oe.security3a.seucore.resourceser;

import java.util.List;

import oe.security3a.seucore.obj.ProtectedObject;
import oe.security3a.seucore.obj.db.UmsApplication;
import oe.security3a.seucore.obj.db.UmsProtectedobject;


/**
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public interface ProtectedObjectManager {

	UmsApplication fetchApplication(UmsProtectedobject ope);

	/**
	 * ����ӱ�������
	 * 
	 * @param group
	 * @return
	 */
	public List subProtectedObject(UmsProtectedobject protectdObject);

	/**
	 * ����������������
	 * 
	 * @param list
	 *            ������������
	 * @return 2ά����String[n][0] ��������ID,String[n][1]�������[�ɹ�,ʧ��,����]
	 */
	public String[][] createProtectedObjects(List list);

	/**
	 * ����ɾ������
	 * 
	 * @param list
	 *            ������������
	 * @return 2ά����String[n][0] ��������ID,String[n][1]ɾ�����[�ɹ�,ʧ��]
	 */
	public String[][] delProtectedObjects(List list);

}
