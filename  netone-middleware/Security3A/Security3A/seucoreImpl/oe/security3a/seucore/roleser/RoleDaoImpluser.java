package oe.security3a.seucore.roleser;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.beanutils.BeanUtils;


import oe.frame.orm.OrmerEntry;
import oe.security3a.seucore.obj.db.UmsUser2role;
import oe.security3a.seucore.roleser.RoleDao;


/**
 * ��ɫDaoʵ��(����UmsUser2role)
 * 
 * @author ni.he.qing
 * 
 */
public class RoleDaoImpluser implements RoleDao {
	
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
		if(obj==null){
			return false;
		}
		String code="";
		try {
			code = BeanUtils.getProperty(obj, "code");
		} catch (Exception e) {
			e.printStackTrace();
		} 
		String jndix = "_ACCOUNT_" + code;
		String ds = messages.getString(jndix);
		Object user2role = RoleDaoImplUserReference.ConverToUmsUser2Role(obj);
		return OrmerEntry.fetchOrmer(ds).fetchSerializer().create(user2role);
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
		boolean result = false;
		List list = new ArrayList();
		for (Iterator iter = objs.iterator(); iter.hasNext();) {
			Object obj =  iter.next();
			result = this.create(obj);
		}
		return result;
	}

	/**
	 * ɾ������
	 * 
	 * @param obj
	 *            UmsUser2role
	 * @return boolean
	 */
	public boolean drop(Object obj) {
		if(obj==null){
			return false;
		}
		String code="";
		try {
			code = BeanUtils.getProperty(obj, "code");
		} catch (Exception e) {
			e.printStackTrace();
		} 
		String jndix = "_ACCOUNT_" + code;
		String ds = messages.getString(jndix);
		Object user2role = RoleDaoImplUserReference.ConverToUmsUser2Role(obj);
		return OrmerEntry.fetchOrmer(ds).fetchSerializer().drop(user2role);
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
		boolean relult = false;
		List list = new ArrayList();
		for (Iterator iter = objs.iterator(); iter.hasNext();) {
			Object obj =  iter.next();
			relult = this.drop(obj);
		}
		return relult;
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
		if(obj==null){
			return false;
		}
		String code="";
		try {
			code = BeanUtils.getProperty(obj, "code");
		} catch (Exception e) {
			e.printStackTrace();
		} 
		String jndix = "_ACCOUNT_" + code;
		String ds = messages.getString(jndix);
		Object user2role = RoleDaoImplUserReference.ConverToUmsUser2Role(obj);
		return OrmerEntry.fetchOrmer(ds).fetchSerializer().update(user2role);
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
		boolean result = false;
		List list = new ArrayList();
		for (Iterator iter = objs.iterator(); iter.hasNext();) {
			Object obj =  iter.next();
			result = this.update(obj);
		}
		return result;

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
		if(obj==null){
			return null;
		}
		String code = "";
		try {
			code = BeanUtils.getProperty(obj, "code");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String jndix = "_ACCOUNT_" + code;
		String ds = messages.getString(jndix);
		Object user2role = RoleDaoImplUserReference.ConverToUmsUser2Role(obj);
		return OrmerEntry.fetchOrmer(ds).fetchQuerister().queryObjects(user2role, comparisonKey);
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
		if(obj==null){
			return null;
		}
		String code = "";
		try {
			code = BeanUtils.getProperty(obj, "code");
		} catch (Exception e) {
			e.printStackTrace();
		}
		String jndix = "_ACCOUNT_" + code;
		String ds = messages.getString(jndix);
		Object user2role = RoleDaoImplUserReference.ConverToUmsUser2Role(obj);
		return OrmerEntry.fetchOrmer(ds).fetchQuerister().queryObjects(user2role, comparisonKey, conditionPre);
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
		if(obj==null){
			return null;
		}
		String code = "";
		try {
			code = BeanUtils.getProperty(obj, "code");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String jndix = "_ACCOUNT_" + code;
		String ds = messages.getString(jndix);
		Object user2role = RoleDaoImplUserReference.ConverToUmsUser2Role(obj);
		return OrmerEntry.fetchOrmer(ds).fetchQuerister().queryObjects(user2role, comparisonKey, from, to);
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
	public List queryObjects(Object obj, Map comparisonKey, int from, int to,
			String conditionPre) {
		if(obj==null){
			return null;
		}
		String code="";
		try {
			code = BeanUtils.getProperty(obj, "code");
		} catch (Exception e) {
			e.printStackTrace();
		} 
		String jndix = "_ACCOUNT_" + code;
		String ds = messages.getString(jndix);
		Object user2role = RoleDaoImplUserReference.ConverToUmsUser2Role(obj);
		return OrmerEntry.fetchOrmer(ds).fetchQuerister().queryObjects(user2role, comparisonKey, from, to, conditionPre);
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
		if(obj==null){
			return -1;
		}
		String code = "";
		try {
			code = BeanUtils.getProperty(obj, "code");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String jndix = "_ACCOUNT_" + code;
		String ds = messages.getString(jndix);
		Object user2role = RoleDaoImplUserReference.ConverToUmsUser2Role(obj);
		return OrmerEntry.fetchOrmer(ds).fetchQuerister().queryObjectsNumber(user2role, comparisonKey);
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
	public long queryObjectsNumber(Object obj, Map comparisonKey,
			String conditionPre) {
		if(obj==null){
			return -1;
		}
		String code = "";
		try {
			code = BeanUtils.getProperty(obj, "code");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String jndix = "_ACCOUNT_" + code;
		String ds = messages.getString(jndix);
		Object user2role = RoleDaoImplUserReference.ConverToUmsUser2Role(obj);
		return OrmerEntry.fetchOrmer(ds).fetchQuerister().queryObjectsNumber(user2role, comparisonKey, conditionPre);
	}

	public boolean moveRoleDept(String roleid, String ouOri, String ouAim) {
		return false;
	}

}
