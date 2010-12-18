package oe.security3a.seucore.accountser;

import java.io.Serializable;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.naming.directory.SearchControls;

import oe.frame.orm.OrmerEntry;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

import oe.security3a.SeuserEntry;
import oe.security3a.seucore.obj.Clerk;
import oe.security3a.seucore.obj.db.TCsUser;
import oe.security3a.seucore.obj.db.UmsProtectedobject;
import oe.security3a.seucore.resourceser.ProtectedObjectService;
import oe.security3a.sso.util.Encryption;
import oe.security3a.sso.util.Md5;

/**
 * �û�Daoʵ��(LDAP)
 * 
 * @author ni.he.qing
 * 
 */
public class UserDaoImpl2 implements UserDao {

	static Md5 md5 = new Md5();

	static ResourceBundle messages = ResourceBundle.getBundle("jndi",
			Locale.CHINESE);

	private ProtectedObjectService pos = (ProtectedObjectService) SeuserEntry
			.iv("protectedObjectService");

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
//		if (obj == null) {
//			return false;
//		}
//		Clerk clerk = (Clerk) obj;
//		Clerk user = new Clerk();
//		user.setDescription(clerk.getDescription());
//		user.setOfficeNO(clerk.getOfficeNO());
//
//		String code = clerk.getOfficeNO();
//		String jndix = "_ACCOUNT_" + code;
//
//		if (existKey(jndix)) {
//			String ds = messages.getString(jndix);
//			try {
//				long exist = queryObjectsNumber(user, null, null);
//				if (exist == 1) {
//					throw new RuntimeException("�û���:" + clerk.getDescription()
//							+ " �Ѵ��� !");
//				}
//				if (StringUtils.isNotEmpty(clerk.getDeptment())) {
//					UmsProtectedobject upo = (UmsProtectedobject) pos
//							.fetchDao().loadObject(UmsProtectedobject.class,
//									clerk.getDeptment());
//					if (upo != null) {
//						clerk.setDeptment("ou=" + upo.getNaturalname() + ","
//								+ upo.getParentdir());
//					}
//				}
//			} catch (Exception e1) {
//				e1.printStackTrace();
//			}
//			clerk.setPassword(md5.calcMD5(messages.getString("initpassword")));
//			return OrmerEntry.fetchOrmer(ds).fetchSerializer().create(clerk);
//		} else {
//			throw new RuntimeException("����:" + clerk.getDescription() + "����");
//		}
		
		if (obj == null) {
			return false;
		}
		Clerk clerk = (Clerk) obj;
		Clerk user = new Clerk();
		user.setDescription(clerk.getDescription());
		long exist = queryObjectsNumber(user, null, null);
		if (exist == 1) {
			throw new RuntimeException("�û���:" + clerk.getDescription()
					+ " �Ѵ��� !");
		}
		if (StringUtils.isNotEmpty(clerk.getDeptment())) {
			UmsProtectedobject upo = (UmsProtectedobject) pos.fetchDao()
					.loadObject(UmsProtectedobject.class, clerk.getDeptment());
			if (upo != null) {
				clerk.setDeptment("ou=" + upo.getNaturalname() + ","
						+ upo.getParentdir());
			}
		}
		clerk.setPassword(md5.calcMD5(messages.getString("initpassword")));
		return OrmerEntry.fetchOrmer().fetchSerializer().create(clerk);
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
		/**
		 * if (obj == null) { return false; } Clerk clerk = (Clerk) obj; String
		 * code = clerk.getOfficeNO(); String jndix = "_ACCOUNT_" + code; String
		 * ds = messages.getString(jndix);
		 * 
		 * Object user = null; try { user =
		 * UserDaoImplReference.buildTCsUser(clerk, null);
		 * BeanUtils.setProperty(user, "statusinfo", UserDao._USER_STATUS_DEL); }
		 * catch (Exception e) { e.printStackTrace(); }
		 * 
		 * return OrmerEntry.fetchOrmer(ds).fetchSerializer().update(user);
		 */
		if (obj == null) {
			return false;
		}
		Clerk clerk = (Clerk) obj;
		//����ļ���ldapʹ�ã�����loadObject�ĵ�һ������û��ʹ�ã��ھ����ѯʱĬ����Clerk.class
		clerk = (Clerk) loadObject("", clerk.getDescription());
		return OrmerEntry.fetchOrmer().fetchSerializer().drop(clerk);
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
		if (StringUtils.isNotEmpty(clerk.getDeptment())) {
			UmsProtectedobject upo = (UmsProtectedobject) pos.fetchDao()
					.loadObject(UmsProtectedobject.class, clerk.getDeptment());
			if (upo != null) {
				clerk.setDeptment("ou=" + upo.getNaturalname() + ","
						+ upo.getParentdir());
			}
		}
		return OrmerEntry.fetchOrmer().fetchSerializer().update(clerk);
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
			Clerk clerk = (Clerk) OrmerEntry.fetchOrmer().fetchQuerister()
					.loadObject(Clerk.class, key);
			UmsProtectedobject up = (UmsProtectedobject) OrmerEntry
					.fetchOrmer().fetchQuerister().loadObject(
							UmsProtectedobject.class, clerk.getDeptment());
			clerk.setFaxNO(up.getName());
			return clerk;
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
		if (obj == null) {
			return null;
		}
		Clerk clerk = (Clerk) obj;
		if (StringUtils.isNotEmpty(clerk.getDeptment())) {
			UmsProtectedobject upo = (UmsProtectedobject) pos.fetchDao()
					.loadObject(UmsProtectedobject.class, clerk.getDeptment());
			if (upo != null) {
				clerk.setDeptment("ou=" + upo.getNaturalname() + ","
						+ upo.getParentdir());
			}
			if (comparisonKey == null) {
				comparisonKey = new LinkedHashMap();
			}
			comparisonKey.put("searchScope", String
					.valueOf(SearchControls.ONELEVEL_SCOPE));
		}
		return UserDaoImplReference2.buildClerkList(OrmerEntry.fetchOrmer()
				.fetchQuerister().queryObjects(clerk, comparisonKey,
						conditionPre));
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
		if (StringUtils.isNotEmpty(clerk.getDeptment())) {
			UmsProtectedobject upo = (UmsProtectedobject) pos.fetchDao()
					.loadObject(UmsProtectedobject.class, clerk.getDeptment());
			if (upo != null) {
				clerk.setDeptment("ou=" + upo.getNaturalname() + ","
						+ upo.getParentdir());
			}
			if (comparisonKey == null) {
				comparisonKey = new LinkedHashMap();
			}
			comparisonKey.put("searchScope", String
					.valueOf(SearchControls.ONELEVEL_SCOPE));
		}
		return UserDaoImplReference2.buildClerkList(OrmerEntry.fetchOrmer()
				.fetchQuerister().queryObjects(clerk, comparisonKey, from, to,
						conditionPre));
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
	public long queryObjectsNumber(Object obj, Map comparisonKey,
			String conditionPre) {
		List list = queryObjects(obj, comparisonKey, conditionPre);
		if (list != null && list.size() > 0) {
			return list.size();
		}
		return -1;
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
	 * @param code �û���λ���� ��LDAP��û��ʹ��
	 * @param name �û���
	 * @param pass ����
	 * @return clerk ����operationinfo�ֶ��Ƿ�����֤�Ĵ�����Ϣ �㷨��<br>
	 *         1�����ݴ����name����cnֵ����Ĭ���û���½��ѯ�����SearchResult���� 2����SearchResult�л��dn��
	 *         3���û�õ�dn���ʹ��������pass��������ldap��������������֤�ɹ�����֮ʧ��
	 */
	public Clerk validationUser(String code, String name, String pass) {

		Clerk clerk = new Clerk();
		if (StringUtils.isEmpty(pass)) {
			clerk.setOperationinfo("���벻��Ϊ��!");
			return clerk;
		}
		Clerk user = (Clerk) OrmerEntry.fetchOrmer().fetchQuerister()
				.loadObject(Clerk.class, name);
		Md5 md5 = new Md5();
		if (user == null) {
			clerk.setOperationinfo("�û������ڣ�");
			return clerk;
		} else if (md5.calcMD5(pass).equals(user.getPassword())) {
			return user;
		} else {
			clerk.setOperationinfo("�û��������");
			return clerk;
		}
	}

	public boolean moveUserDept(String loginname, String ouOri, String ouAim) {
		// try {
		// String[] str = new String[2];
		// str[0] = "uid=" + loginname + "," + ouOri;
		// if (StringUtils.isNotEmpty(ouAim)) {
		// UmsProtectedobject upo = (UmsProtectedobject) pos.fetchDao()
		// .loadObject(UmsProtectedobject.class, ouAim);
		// if (upo != null) {
		// ouAim = "uid=" + loginname + ",ou=" + upo.getNaturalname()
		// + "," + upo.getParentdir();
		// }
		// }
		// str[1] = ouAim;
		// if (!str[0].equals(str[1])) {
		// UmsUser2role uuserrole = new UmsUser2role();
		// uuserrole.setUserid(str[0]);
		// List list = OrmerEntry.fetchOrmer().fetchQuerister()
		// .queryObjects(uuserrole, null);
		// OrmerEntry.fetchOrmer().fetchSerializer().update(str);
		// if (list != null && !list.isEmpty()) {
		// List<UmsUser2role> tmplist = new ArrayList<UmsUser2role>();
		// for (Iterator iterator = list.iterator(); iterator
		// .hasNext();) {
		// UmsUser2role ur = (UmsUser2role) iterator.next();
		// ur.setUserid(str[1]);
		// tmplist.add(ur);
		// }
		// OrmerEntry.fetchOrmer().fetchSerializer().updates(tmplist);
		// }
		// }
		// return true;
		// } catch (Exception e) {
		// e.printStackTrace();
		// throw new RuntimeException(e.getMessage());
		// }
		return true;
	}
}
