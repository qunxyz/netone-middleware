package oe.security3a.seucore.roleser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import oe.frame.orm.OrmerEntry;
import oe.security3a.seucore.obj.db.UmsRolepermission;
import oe.security3a.seucore.roleser.RoleDao;


/**
 * ��ɫDaoʵ��(����UmsRolepermission)
 * 
 * @author ni.he.qing
 * 
 */
public class RoleDaoImplpermission implements RoleDao {
	
	/**
	 * ��ȫ��죬�ڶ����м����û���Ϣ
	 * 
	 * @param obj
	 * @return
	 */
	public boolean checkSecrity(Object obj) {
		throw new RuntimeException("�÷���û��ʵ��");
	}

	/**
	 * �������
	 * 
	 * @param obj
	 *            UmsRolepermission
	 * @return boolean
	 * 
	 */
	public boolean create(Object obj) {
		if(obj==null){
			return false;
		}
		UmsRolepermission urper = (UmsRolepermission) obj;
		return OrmerEntry.fetchOrmer().fetchSerializer().create(urper);
	}

	/**
	 * ������������
	 * 
	 * @param objs
	 *            List
	 * @return boolean
	 */
	public boolean creates(List objs) {
		if(objs==null){
			return false;
		}
		List<UmsRolepermission> list = new ArrayList<UmsRolepermission>();
		for (Iterator iter = objs.iterator(); iter.hasNext();) {
			UmsRolepermission urper = (UmsRolepermission) iter.next();
			list.add(urper);
		}
		return OrmerEntry.fetchOrmer().fetchSerializer().creates(list);
	}

	/**
	 * ɾ������
	 * 
	 * @param obj
	 *            UmsRolepermission
	 * @return boolean
	 * 
	 */
	public boolean drop(Object obj) {
		if(obj==null){
			return false;
		}
		UmsRolepermission urper = (UmsRolepermission) obj;
		return OrmerEntry.fetchOrmer().fetchSerializer().drop(urper);
	}

	/**
	 * ����ɾ������
	 * 
	 * @param objs
	 *            List
	 * @return boolean
	 */
	public boolean drops(List objs) {
		if(objs==null){
			return false;
		}
		List<UmsRolepermission> list = new ArrayList<UmsRolepermission>();
		for (Iterator iter = objs.iterator(); iter.hasNext();) {
			UmsRolepermission urper = (UmsRolepermission) iter.next();
			list.add(urper);
		}
		return OrmerEntry.fetchOrmer().fetchSerializer().drops(list);
	}

	/**
	 * ˢ�¶���
	 * 
	 * @param objClass
	 *            Class ������
	 * @param key
	 *            Serializable ����ID
	 */
	public void refreshObject(Object objClass, Serializable key) {
		throw new RuntimeException("�÷���û��ʵ��");
	}

	public boolean serial(Object obj) {
		throw new RuntimeException("�÷���û��ʵ��");
	}

	/**
	 * ���¶���
	 * 
	 * @param obj
	 *            UmsRolepermission
	 * @return boolean
	 * 
	 */
	public boolean update(Object obj) {
		if(obj==null){
			return false;
		}
		UmsRolepermission urper = (UmsRolepermission) obj;
		return OrmerEntry.fetchOrmer().fetchSerializer().update(urper);
	}

	/**
	 * �������¶���
	 * 
	 * @param objs
	 *            List
	 * @return boolean
	 */
	public boolean updates(List objs) {
		if(objs==null){
			return false;
		}
		List<UmsRolepermission> list = new ArrayList<UmsRolepermission>();
		for (Iterator iter = objs.iterator(); iter.hasNext();) {
			UmsRolepermission urper = (UmsRolepermission) iter.next();
			list.add(urper);
		}
		return OrmerEntry.fetchOrmer().fetchSerializer().updates(list);
	}
	
	/**
	 * װ�ض���
	 * 
	 * @param objClass
	 *            Class ������
	 *            
	 *            
	 * @param key
	 *            Serializable ����ID
	 * @return Object ����ʵ��
	 */
	public Object loadObject(Class objClass, Serializable key) {
		return OrmerEntry.fetchOrmer().fetchQuerister().loadObject(objClass, key);
	}

	/**
	 * ���ڶ���ģʽ�Ĳ�ѯ��������������Ķ���
	 * 
	 * @param Object
	 *           UmsRolepermission
	 *            
	 * @param comparisonKey
	 *            ��ѯ�ȽϷ���,����key�� �ֶ�ID,value�ǱȽϷ��ű��� >,<
	 * 
	 * @return List ��������
	 */
	public List queryObjects(Object obj, Map comparisonKey) {
		if(obj==null){
			return null;
		}
		UmsRolepermission urole = (UmsRolepermission)obj;
		return OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(urole, comparisonKey);
	}

	/**
	 * ���ڶ���ģʽ�Ĳ�ѯ��������������Ķ���֧��SQL����
	 * 
	 * @param Object
	 *            UmsRolepermission
	 *            
	 * @param comparisonKey
	 *            ��ѯ�ȽϷ���,����key�� �ֶ�ID,value�ǱȽϷ��ű��� >,<
	 * 
	 * @param conditionPre
	 *            String ��������,����SQL92��׼�Ĳ�ѯ����,��ѯ�ֶ����ο���ѯ�����е�get/set�е�����
	 * 
	 * @return List ��������
	 */
	public List queryObjects(Object obj, Map comparisonKey, String conditionPre) {
		if(obj==null){
			return null;
		}
		UmsRolepermission urole = (UmsRolepermission)obj;
		return OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(urole, comparisonKey, conditionPre);
	}

	/**
	 * ���ڶ���ģʽ�Ĳ�ѯ��������з��������Ķ���֧�ַ�ҳ
	 * 
	 * @param obj
	 *            UmsRolepermission
	 * 
	 * @param comparisonKey
	 *            ��ѯ�ȽϷ���,����key�� �ֶ�ID,value�ǱȽϷ��ű��� >,<
	 * @param from
	 *            int ��Ҫ���صĶ����������ʼλ��
	 * @param to
	 *            int ��Ҫ���صĶ������������λ��
	 * 
	 * @return List ���������Ķ�������
	 */
	public List queryObjects(Object obj, Map comparisonKey, int from, int to) {
		if(obj==null){
			return null;
		}
		UmsRolepermission urole = (UmsRolepermission)obj;
		return OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(urole, comparisonKey, from, to);
	}

	/**
	 * ���ڶ���ģʽ�Ĳ�ѯ��������з��������Ķ���֧�ַ�ҳ��SQL����
	 * 
	 * @param obj
	 *            UmsRolepermission
	 * @param comparisonKey
	 *            ��ѯ�ȽϷ���,����key�� �ֶ�ID,value�ǱȽϷ��ű��� >,<
	 * @param from
	 *            int ��Ҫ���صĶ����������ʼλ��
	 * @param to
	 *            int ��Ҫ���صĶ������������λ��
	 * @param conditionPre
	 *            String ��������,����SQL92��׼����Where�Ĳ�ѯ����,��ѯ�ֶ����ɲο���ѯ�����е�get/set�е�����
	 * 
	 * @return List ���������Ķ�������
	 */
	public List queryObjects(Object obj, Map comparisonKey, int from, int to,
			String conditionPre) {
		if(obj==null){
			return null;
		}
		UmsRolepermission urole = (UmsRolepermission)obj;
		return OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(urole, comparisonKey, from, to, conditionPre);
	}

	/**
	 * ���ڶ���ģʽ�Ĳ�ѯ��������������Ķ�������
	 * 
	 * @param comparisonKey
	 *            ��ѯ�ȽϷ���,����key�� �ֶ�ID,value�ǱȽϷ��ű��� >,<
	 *            
	 * @param Object
	 *            UmsRolepermission
	 * 
	 * @return long ��¼����
	 */
	public long queryObjectsNumber(Object obj, Map comparisonKey) {
		if(obj==null){
			return -1;
		}
		UmsRolepermission urole = (UmsRolepermission)obj;
		return OrmerEntry.fetchOrmer().fetchQuerister().queryObjectsNumber(urole, comparisonKey);
	}

	/**
	 * ���ڶ���ģʽ�Ĳ�ѯ��������������Ķ���������֧��SQL����
	 * 
	 * @param Object
	 *            UmsRolepermission
	 *            
	 * @param comparisonKey
	 *            ��ѯ�ȽϷ���,����key�� �ֶ�ID,value�ǱȽϷ��ű��� >,<
	 * 
	 * @param conditionPre
	 *            String ��������,����SQL92��׼�Ĳ�ѯ����,��ѯ�ֶ����ο���ѯ�����е�get/set�е�����
	 * 
	 * @return long
	 */
	public long queryObjectsNumber(Object obj, Map comparisonKey,
			String conditionPre) {
		if(obj==null){
			return -1;
		}
		UmsRolepermission urole = (UmsRolepermission)obj;
		return OrmerEntry.fetchOrmer().fetchQuerister().queryObjectsNumber(urole, comparisonKey, conditionPre);
	}

	public boolean moveRoleDept(String roleid, String ouOri, String ouAim) {
		return false;
	}

}
