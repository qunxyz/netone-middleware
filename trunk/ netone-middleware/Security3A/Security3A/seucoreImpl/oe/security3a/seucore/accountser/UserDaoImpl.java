package oe.security3a.seucore.accountser;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import oe.frame.orm.OrmerEntry;

import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.CupmRmi;
import oe.security3a.seucore.obj.Clerk;
import oe.security3a.seucore.obj.db.TCsUser;
import oe.security3a.sso.LoginInfo;
import oe.security3a.sso.util.Encryption;
import oe.security3a.sso.util.Md5;
import oe.security4a.severlet.MD5Util;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

/**
 * �û�Daoʵ��(����UmsUser2dept)
 * 
 * @author ni.he.qing
 * 
 */
public class UserDaoImpl implements UserDao, LoginInfo {

	static ResourceBundle messages = ResourceBundle.getBundle("jndi",
			Locale.CHINESE);

	/**
	 * �жϺϳɵ�key�Ƿ���jndi�ļ��д���
	 * 
	 * @param key
	 * @return
	 */
	private boolean existKey(String key) {

		Enumeration<String> enumer = messages.getKeys();
		Boolean result = false;
		while (enumer.hasMoreElements() && !result) {
			if (key.equals(enumer.nextElement())) {
				result = true;
			}
		}
		return result;
	}

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
	 *            UmsApplication
	 * @return boolean
	 */
	public boolean create(Object obj) {
		if (obj == null) {
			return false;
		}
		Clerk clerk = (Clerk) obj;
		String code = clerk.getOfficeNO();

		String jndix = "_ACCOUNT_" + code;

		if (existKey(jndix)) {
			String ds = messages.getString(jndix);
			try {
				Object quser = new TCsUser();
				String condition = " and usercode='" + clerk.getDescription()
						+ "'";
				long exist = OrmerEntry.fetchOrmer(ds).fetchQuerister()
						.queryObjectsNumber(quser, null, condition);
				if (exist == 1) {
					// throw new RuntimeException("�û���:" +
					// clerk.getDescription()
					// + " �Ѵ��� !");
					return false;
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			String key = messages.getString("EncrypKey");
			String inispassword = messages.getString("initpassword");

			String encryptionMode = "default";
			try {
				CupmRmi cupm = (CupmRmi) RmiEntry.iv("cupm");
				encryptionMode=cupm.fetchConfig("EncryptionMode");
			} catch (Exception e) {
				e.printStackTrace();
			}
			if ("md5".equals(encryptionMode)) {
				try {
					clerk.setPassword(MD5Util.MD5_UTF16LE(inispassword));
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else {
				clerk.setPassword(Encryption.encry(inispassword, key, true));
			}

			Object user = null;
			try {
				user = UserDaoImplReference.buildTCsUser(clerk, null);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return OrmerEntry.fetchOrmer(ds).fetchSerializer().create(user);
		} else {
			throw new RuntimeException("����:" + clerk.getDescription() + "����");
		}
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
			Clerk clerk = (Clerk) iter.next();
			if (!this.create(clerk)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * ɾ������
	 * 
	 * @param obj
	 *            UmsApplication
	 * @return boolean
	 */
	public boolean drop(Object obj) {
		if (obj == null) {
			return false;
		}
		Clerk clerk = (Clerk) obj;
		String code = clerk.getOfficeNO();
		String jndix = "_ACCOUNT_" + code;
		String ds = messages.getString(jndix);

		String delmode = messages.getString("delmode");
		Object user = null;
		try {
			user = UserDaoImplReference.buildTCsUser(clerk, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if ("real".equals(delmode)) { // ����ɾ��
			return OrmerEntry.fetchOrmer(ds).fetchSerializer().drop(user);
		} else { // �߼�ɾ��
			try {
				BeanUtils.setProperty(user, "statusinfo",
						UserDao._USER_STATUS_DEL);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return OrmerEntry.fetchOrmer(ds).fetchSerializer().update(user);
		}

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
			Clerk clerk = (Clerk) iter.next();
			if (!this.drop(clerk)) {
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
	 *            UmsApplication
	 * @return boolean
	 */
	public boolean update(Object obj) {
		if (obj == null) {
			return false;
		}
		Clerk clerk = (Clerk) obj;
		String code = clerk.getOfficeNO();

		String jndix = "_ACCOUNT_" + code;
		String ds = messages.getString(jndix);

		Object user = null;
		try {
			user = UserDaoImplReference.buildTCsUser(clerk, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return OrmerEntry.fetchOrmer(ds).fetchSerializer().update(user);
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
			Clerk clerk = (Clerk) iter.next();
			if (!this.update(clerk)) {
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
	public Object loadObject(String code, Serializable key) {

		try {
			String jndix = "_ACCOUNT_" + code;
			String ds = messages.getString(jndix);
			TCsUser tcsuser = new TCsUser();
			String userid = key.toString();
			String conditionPre = "and usercode = '" + userid + "'";
			Object user = null;
			List list = OrmerEntry.fetchOrmer(ds).fetchQuerister()
					.queryObjects(tcsuser, null, conditionPre);
			if (!list.isEmpty()) {
				user = list.get(0);
			}
			List<Object> buildlist = new ArrayList<Object>();
			if (user != null) {
				buildlist.add(user);
				return UserDaoImplReference.buildClerkList(buildlist).get(0);
			}
			return null;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * ���ڶ���ģʽ�Ĳ�ѯ��������������Ķ���
	 * 
	 * @param Object
	 *            UmsApplication
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
	 *            UmsApplication
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
		return this.queryObjects(obj, comparisonKey, -1, -1, conditionPre);
	}

	/**
	 * ���ڶ���ģʽ�Ĳ�ѯ��������з��������Ķ���֧�ַ�ҳ
	 * 
	 * @param obj
	 *            UmsApplication
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
	 *            UmsApplication
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
		if (obj == null) {
			return null;
		}
		Clerk clerk = (Clerk) obj;
		String code = clerk.getOfficeNO();
		String jndix = "_ACCOUNT_" + code;
		String ds = messages.getString(jndix);
		Object user = null;
		try {
			user = UserDaoImplReference.buildTCsUser(clerk, comparisonKey);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (conditionPre == null) {
			conditionPre = "";
		}
		conditionPre += " and statusinfo!='" + this._USER_STATUS_DEL + "'";
		if (from == -1 || to == -1) {
			return UserDaoImplReference.buildClerkList(OrmerEntry
					.fetchOrmer(ds).fetchQuerister().queryObjects(user,
							comparisonKey, conditionPre));
		} else {
			return UserDaoImplReference.buildClerkList(OrmerEntry
					.fetchOrmer(ds).fetchQuerister().queryObjects(user,
							comparisonKey, from, to, conditionPre));
		}

	}

	/**
	 * ���ڶ���ģʽ�Ĳ�ѯ��������������Ķ�������
	 * 
	 * @param comparisonKey
	 *            ��ѯ�ȽϷ���,����key�� �ֶ�ID,value�ǱȽϷ��ű��� >,<
	 * 
	 * @param Object
	 *            UmsApplication
	 * 
	 * @return long ��¼����
	 */
	public long queryObjectsNumber(Object obj, Map comparisonKey,
			String conditionPre) {
		if (obj == null) {
			return -1;
		}
		Clerk clerk = (Clerk) obj;
		String code = clerk.getOfficeNO();
		String jndix = "_ACCOUNT_" + code;
		String ds = messages.getString(jndix);
		Object user = null;
		try {
			user = UserDaoImplReference.buildTCsUser(clerk, comparisonKey);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (conditionPre == null) {
			conditionPre = "";
		}
		conditionPre += " and statusinfo!='" + this._USER_STATUS_DEL + "'";
		return OrmerEntry.fetchOrmer(ds).fetchQuerister().queryObjectsNumber(
				user, comparisonKey, conditionPre);
	}

	/**
	 * ���ڶ���ģʽ�Ĳ�ѯ��������������Ķ���������֧��SQL����
	 * 
	 * @param Object
	 *            UmsApplication
	 * 
	 * @param comparisonKey
	 *            ��ѯ�ȽϷ���,����key�� �ֶ�ID,value�ǱȽϷ��ű��� >,<
	 * 
	 * @param conditionPre
	 *            String ��������,����SQL92��׼�Ĳ�ѯ����,��ѯ�ֶ����ο���ѯ�����е�get/set�е�����
	 * 
	 * @return long
	 */
	public long queryObjectsNumber(Object obj, Map comparisonKey) {
		return this.queryObjectsNumber(obj, comparisonKey, "");
	}

	/**
	 * ע��
	 * 
	 * @param name
	 * @return
	 */
	public boolean logout(String name) {
		return false;
	}

	/**
	 * ��½��֤
	 * 
	 * @param code
	 *            �û�������
	 * @param name
	 *            �û���
	 * @param pass
	 *            ����
	 * @return clerk ����operationinfo�ֶ��Ƿ�����֤�Ĵ�����Ϣ �㷨��<br>
	 *         1�����ݴ����name����cnֵ����Ĭ���û���½��ѯ�����SearchResult���� 2����SearchResult�л��dn��
	 *         3���û�õ�dn���ʹ��������pass��������ldap��������������֤�ɹ�����֮ʧ��
	 */
	public Clerk validationUser(String code, String name, String pass) {
		Clerk clerk = new Clerk();
		if (StringUtils.isEmpty(pass)) {
			clerk.setOperationinfo(_ERROR_3[0]);
			return clerk;
		}

		String jndix = "_ACCOUNT_" + code;

		if (existKey(jndix)) {
			try {
				String ds = messages.getString(jndix);
				Object quser = new TCsUser();
				String condition = " and usercode='" + name + "'";
				long exist = OrmerEntry.fetchOrmer(ds).fetchQuerister()
						.queryObjectsNumber(quser, null, condition);

				if (exist == 0) {
					clerk.setOperationinfo(_ERROR_5[0]);
					return clerk;

				} else {
					Object user = OrmerEntry.fetchOrmer(ds).fetchQuerister()
							.loadObject(TCsUser.class, name);
					String key = messages.getString("EncrypKey");

					String oripassword = Encryption.encry(BeanUtils
							.getProperty(user, "passwordx"), key, false);
					if (pass.equals(oripassword)) {
						List<Object> list = new ArrayList<Object>();
						list.add(user);
						clerk = UserDaoImplReference.buildClerkList(list)
								.get(0);
						return clerk;
					} else {
						clerk.setOperationinfo(_ERROR_6[0]);
						return clerk;
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
				clerk.setOperationinfo(_ERROR_6[0]);
				return clerk;
			}
		} else {
			clerk.setOperationinfo(_ERROR_7[0]);
			return clerk;
		}

	}

	public boolean moveUserDept(String loginname, String ouOri, String ouAim) {
		return true;
	}
}
