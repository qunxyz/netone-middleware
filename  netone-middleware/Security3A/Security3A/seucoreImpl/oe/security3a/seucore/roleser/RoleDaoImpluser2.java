package oe.security3a.seucore.roleser;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import oe.frame.orm.OrmerEntry;
import oe.frame.orm.util.IdServer;

import org.apache.commons.lang.StringUtils;


import oe.security3a.seucore.obj.Clerk;
import oe.security3a.seucore.obj.db.UmsUser2role;

/**
 * ��ɫDaoʵ��(����UmsUser2role)
 * 
 * @author ni.he.qing
 * 
 */
public class RoleDaoImpluser2 implements RoleDao {
	
	
	static ResourceBundle messages = ResourceBundle.getBundle("jndi",
			Locale.CHINESE);
	

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
	 *            UmsUser2role
	 * @return boolean
	 * 
	 */
	public boolean create(Object obj) {
		if (obj == null) {
			return false;
		}
		UmsUser2role uuserrole = (UmsUser2role) obj;
		String userid = uuserrole.getUserid();
		if (StringUtils.isNotEmpty(userid)) {
			Clerk clerk = (Clerk) OrmerEntry.fetchOrmer().fetchQuerister().loadObject(Clerk.class, userid);
			if (clerk != null) {
				uuserrole.setUserid("uid=" + userid + "," + clerk.getDeptment());
			} else {
				return false;
			}
		}
		uuserrole.setId(Long.valueOf(IdServer.xnumID()));
		return OrmerEntry.fetchOrmer().fetchSerializer().create(uuserrole);
	}

	/**
	 * ������������
	 * 
	 * @param objs
	 *            List
	 * @return boolean
	 */
	public boolean creates(List objs) {
		if (objs == null) {
			return false;
		}
		for (Iterator iter = objs.iterator(); iter.hasNext();) {
			UmsUser2role uuserrole = (UmsUser2role) iter.next();
			if (!this.create(uuserrole)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * ɾ������
	 * 
	 * @param obj
	 *            UmsUser2role
	 * @return boolean
	 */
	public boolean drop(Object obj) {
		if (obj == null) {
			return false;
		}
		UmsUser2role uuserrole = (UmsUser2role) obj;
		return OrmerEntry.fetchOrmer().fetchSerializer().drop(uuserrole);
	}

	/**
	 * ����ɾ������
	 * 
	 * @param objs
	 *            List
	 * @return boolean
	 */
	public boolean drops(List objs) {
		if (objs == null) {
			return false;
		}
		for (Iterator iter = objs.iterator(); iter.hasNext();) {
			UmsUser2role uuserrole = (UmsUser2role) iter.next();
			if (!this.drop(uuserrole)) {
				return false;
			}
		}
		return true;
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
	 *            UmsUser2role
	 * @return boolean
	 * 
	 */
	public boolean update(Object obj) {
		if (obj == null) {
			return false;
		}
		UmsUser2role uuserrole = (UmsUser2role) obj;
		String userid = uuserrole.getUserid();
		if (StringUtils.isNotEmpty(userid)) {
			Clerk clerk = (Clerk) OrmerEntry.fetchOrmer().fetchQuerister().loadObject(Clerk.class, userid);
			if (clerk != null) {
				uuserrole.setUserid("uid=" + userid + "," + clerk.getDeptment());
			} else {
				return false;
			}
		}
		return OrmerEntry.fetchOrmer().fetchSerializer().update(uuserrole);
	}

	/**
	 * �������¶���
	 * 
	 * @param objs
	 *            List
	 * @return boolean
	 */
	public boolean updates(List objs) {
		if (objs == null) {
			return false;
		}
		for (Iterator iter = objs.iterator(); iter.hasNext();) {
			UmsUser2role uuserrole = (UmsUser2role) iter.next();
			if (!this.update(uuserrole)) {
				return false;
			}
		}
		return true;
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
	 *            UmsUser2role
	 * 
	 * @param comparisonKey
	 *            ��ѯ�ȽϷ���,����key�� �ֶ�ID,value�ǱȽϷ��ű��� >,<
	 * 
	 * @return List ��������
	 */
	public List queryObjects(Object obj, Map comparisonKey) {
		return queryObjects(obj, comparisonKey, null);
	}

	/**
	 * ���ڶ���ģʽ�Ĳ�ѯ��������������Ķ���֧��SQL����
	 * 
	 * @param Object
	 *            UmsUser2role
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
		if (obj == null) {
			return null;
		}
		Object user2role = RoleDaoImplUserReference.ConverToUmsUser2Role(obj);
		UmsUser2role urole = (UmsUser2role) user2role;
		String userid = urole.getUserid();
		if (StringUtils.isNotEmpty(userid)) {
			Clerk clerk = (Clerk) OrmerEntry.fetchOrmer().fetchQuerister().loadObject(Clerk.class, userid);
			if (clerk != null) {
				urole.setUserid("uid=" + userid + "," + clerk.getDeptment());
			} else {
				return null;
			}
		}
		return OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(urole, comparisonKey, conditionPre);
	}

	/**
	 * ���ڶ���ģʽ�Ĳ�ѯ��������з��������Ķ���֧�ַ�ҳ
	 * 
	 * @param obj
	 *            UmsUser2role
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
		return queryObjects(obj, comparisonKey, from, to, null);
	}

	/**
	 * ���ڶ���ģʽ�Ĳ�ѯ��������з��������Ķ���֧�ַ�ҳ��SQL����
	 * 
	 * @param obj
	 *            UmsUser2role
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
	public List queryObjects(Object obj, Map comparisonKey, int from, int to, String conditionPre) {
		if (obj == null) {
			return null;
		}
		UmsUser2role urole = (UmsUser2role) obj;
		String userid = urole.getUserid();
		if (StringUtils.isNotEmpty(userid)) {
			Clerk clerk = (Clerk) OrmerEntry.fetchOrmer().fetchQuerister().loadObject(Clerk.class, userid);
			if (clerk != null) {
				urole.setUserid("uid=" + userid + "," + clerk.getDeptment());
			} else {
				return null;
			}
		}
		return OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(urole, comparisonKey, from, to, conditionPre);
	}

	/**
	 * ���ڶ���ģʽ�Ĳ�ѯ��������������Ķ�������
	 * 
	 * @param comparisonKey
	 *            ��ѯ�ȽϷ���,����key�� �ֶ�ID,value�ǱȽϷ��ű��� >,<
	 * 
	 * @param Object
	 *            UmsUser2role
	 * 
	 * @return long ��¼����
	 */
	public long queryObjectsNumber(Object obj, Map comparisonKey) {
		List list = queryObjects(obj, comparisonKey);
		if (list != null && list.size() > 0) {
			return list.size();
		}
		return -1;
	}

	/**
	 * ���ڶ���ģʽ�Ĳ�ѯ��������������Ķ���������֧��SQL����
	 * 
	 * @param Object
	 *            UmsUser2role
	 * 
	 * @param comparisonKey
	 *            ��ѯ�ȽϷ���,����key�� �ֶ�ID,value�ǱȽϷ��ű��� >,<
	 * 
	 * @param conditionPre
	 *            String ��������,����SQL92��׼�Ĳ�ѯ����,��ѯ�ֶ����ο���ѯ�����е�get/set�е�����
	 * 
	 * @return long
	 */
	public long queryObjectsNumber(Object obj, Map comparisonKey, String conditionPre) {
		List list = queryObjects(obj, comparisonKey, conditionPre);
		if (list != null && list.size() > 0) {
			return list.size();
		}
		return -1;
	}

	public boolean moveRoleDept(String roleid, String ouOri, String ouAim) {
		return false;
	}

}
