package oe.security3a.seucore.roleser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.beanutils.BeanUtils;

import oe.frame.orm.OrmerEntry;
import oe.security3a.seucore.obj.User2Role;
import oe.security3a.seucore.obj.db.UmsRole;
import oe.security3a.seucore.obj.db.UmsRole2role2;
import oe.security3a.seucore.obj.db.UmsRolepermission;
import oe.security3a.seucore.roleser.RoleDao;

/**
 * ��ɫDaoʵ��
 * 
 * @author ni.he.qing
 * 
 */
public class RoleDaoImpl implements RoleDao {

	private RoleDao roleDaopermission;

	private RoleDao roleDaouser;

	private RoleDao roleDaorole;

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
	 *            Map
	 * @return boolean �㷨��<br>
	 *         1���ж���������Ƿ�Ϊ��
	 *         2���������Ϊmap,�������UmsRole2role,UmsUser2Role,UmsRole,code�����ж��Ƿ�Ϊ��
	 *         3����UmsRole,UmsUser2Role,UmsRole2role,UmsRolepermission��˳��ֱ�ִ�д�������
	 *         4��ע���ڴ���UmsUser2role,UmsUser2Role,UmsRole2role,UmsRolepermission��ʱ����roleid��ֵΪ�մ�����UmsRole��Id
	 *         5��code���û��������ڣ�����ڴ�����ɫʱ������û�������Ҫָ���û���������
	 */
	public boolean create(Object obj) {
		if (obj == null) {
			return false;
		}
		Map map = (Map) obj;
		String code = map.get(RoleDao._CODE).toString();
		UmsRole urole = (UmsRole) map.get(RoleDao._ROLE);
		if (urole == null) {
			return false;
		}
		urole.setNaturalname(urole.getNaturalname().toUpperCase().trim());
		if (!OrmerEntry.fetchOrmer().fetchSerializer().create(urole)) {
			return false;
		}

		UmsRole2role2 urolerole = (UmsRole2role2) map.get(RoleDao._ROLE2ROLE);
		urolerole.setRelationalrolemainid(urole.getId().toString());
		if (!roleDaorole.create(urolerole)) {
			return false;
		}
		List list = (List) map.get(RoleDao._ROLE2PERMISSION);
		List<UmsRolepermission> newlist = new ArrayList<UmsRolepermission>();
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			UmsRolepermission uroleper = (UmsRolepermission) iter.next();
			uroleper.setRoleid(urole.getId().toString());
			newlist.add(uroleper);
		}
		if (!roleDaopermission.creates(newlist)) {
			return false;
		}
		if (map.get(RoleDao._ROLE2USER) != null) {
			List<User2Role> roleusers = (List) map.get(RoleDao._ROLE2USER);
			for (User2Role u2r : roleusers) {
				u2r.setRoleid(urole.getId().toString());
				u2r.setCode(code);
				roleDaouser.create(u2r);
			}
		}

		return true;
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
			Map map = (Map) iter.next();
			if (!create(map)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * ɾ������
	 * 
	 * @param obj
	 *            String
	 * @return boolean �㷨��<br>
	 *         1���ж���������Ƿ�Ϊ�� 2���������Ϊ��ɫid
	 *         3����UmsRolepermission,UmsUser2Role,UmsRole2role,UmsRole��˳��ֱ���Ҷ�Ӧ�ļ��ϣ�ɾ��֮
	 */
	public boolean drop(Object obj) {
		if (obj == null) {
			return false;
		}
		String roleid = (String) obj;
		UmsRolepermission uroleper = new UmsRolepermission();
		uroleper.setRoleid(roleid);
		List roleperlist = roleDaopermission.queryObjects(uroleper, null);
		if (!roleDaopermission.drops(roleperlist)) {
			return false;
		}

		// ɾ���û��ͽ�ɫ֮��Ĺ�ϵ�����ﲻɾ����ֻ�ѽ�ɫɾ�����û��Ҳ�����Ӧ�Ľ�ɫ��Ҳ��û��Ȩ��
		//����ɾ����������Ϊ����ɾ����ɫǰ���Ѿ��жϸý�ɫû�й������û���
		// User2Role userrole = new User2Role();
		// userrole.setRoleid(roleid);
		// userrole.setCode("0000");
		// List userlist = roleDaouser.queryObjects(userrole, null);
		// if (!roleDaouser.drops(userlist)) {
		// return false;
		// }
		UmsRole2role2 urolerole = new UmsRole2role2();
		urolerole.setRelationalrolemainid(roleid);
		List rolelist = roleDaorole.queryObjects(urolerole, null);
		if (!roleDaorole.drops(rolelist)) {
			return false;
		}
		UmsRole urole = (UmsRole) OrmerEntry.fetchOrmer().fetchQuerister()
				.loadObject(UmsRole.class, new Long(roleid));
		if (!OrmerEntry.fetchOrmer().fetchSerializer().drop(urole)) {
			return false;
		}
		return true;
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
			String roleid = (String) iter.next();
			if (!drop(roleid)) {
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
	 *            Map
	 * @return boolean �㷨��<br>
	 *         1���ж���������Ƿ�Ϊ��
	 *         2���������Ϊmap,�������UmsRole2role,UmsUser2Role,UmsRolepermission,UmsRole��code���ж��Ƿ�Ϊ��
	 *         3�����ȵõ�UmsRole��roleid,��ѯUmsUser2role,UmsRole2role,UmsRolepermission,ɾ��֮����������map��Ķ��󴴽�
	 *         4��UmsRoleֱ����map��Ķ�������޸�
	 *         5)code���û��������ڣ����޸Ľ�ɫʱ���漰����Ȩ�����û�ʱ����Ҫָ���û���������
	 */
	public boolean update(Object obj) {
		if (obj == null) {
			return false;
		}
		Map map = (Map) obj;
		String code = map.get(RoleDao._CODE).toString();
		UmsRole urole = (UmsRole) map.get(RoleDao._ROLE);
		if (urole == null) {
			return false;
		}
		if (!OrmerEntry.fetchOrmer().fetchSerializer().update(urole)) {
			return false;
		}

		String roleid = urole.getId().toString();

		UmsRolepermission uroleper = new UmsRolepermission();
		uroleper.setRoleid(roleid);
		List roleperlist = roleDaopermission.queryObjects(uroleper, null);
		if (!roleDaopermission.drops(roleperlist)) {
			return false;
		}
		List list = (List) map.get(RoleDao._ROLE2PERMISSION);
		if (list != null) {
			if (!roleDaopermission.creates(list)) {
				return false;
			}
		}
		UmsRole2role2 urolerole = new UmsRole2role2();
		urolerole.setRelationalrolemainid(roleid);
		List rolelist = roleDaorole.queryObjects(urolerole, null);
		if (!roleDaorole.drops(rolelist)) {
			return false;
		}
		UmsRole2role2 role2 = (UmsRole2role2) map.get(RoleDao._ROLE2ROLE);
		if (role2 != null) {
			if (!roleDaorole.create(role2)) {
				return false;
			}
		}

		if (map.get(RoleDao._ROLE2USER) != null) {
			List<User2Role> roleusers = (List) map.get(RoleDao._ROLE2USER);
			User2Role u2r = new User2Role();
			u2r.setRoleid(roleid);
			u2r.setCode(code);
			List list1 = roleDaouser.queryObjects(u2r, null);
			List<User2Role> list2 = RoleDaoImplUserReference
					.ConverToUser2RoleList(code, list1);
			for (User2Role u2r2 : list2) {
				u2r2.setCode(code);
				roleDaouser.drop(u2r2);
			}

			for (User2Role newu2r : roleusers) {
				newu2r.setRoleid(urole.getId().toString());
				newu2r.setCode(code);
				roleDaouser.create(newu2r);
			}
		}
		return true;
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
			Map map = (Map) iter.next();
			if (!update(map)) {
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
		return OrmerEntry.fetchOrmer().fetchQuerister().loadObject(objClass,
				key);
	}

	/**
	 * ���ڶ���ģʽ�Ĳ�ѯ��������������Ķ���
	 * 
	 * @param Object
	 *            UmsRole
	 * 
	 * @param comparisonKey
	 *            ��ѯ�ȽϷ���,����key�� �ֶ�ID,value�ǱȽϷ��ű��� >,<
	 * 
	 * @return List ��������
	 */
	public List queryObjects(Object obj, Map comparisonKey) {
		return OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(obj,
				comparisonKey);
	}

	/**
	 * ���ڶ���ģʽ�Ĳ�ѯ��������������Ķ���֧��SQL����
	 * 
	 * @param Object
	 *            UmsRole
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

		return OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(obj,
				comparisonKey, conditionPre);
	}

	/**
	 * ���ڶ���ģʽ�Ĳ�ѯ��������з��������Ķ���֧�ַ�ҳ
	 * 
	 * @param obj
	 *            UmsRole
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
		return OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(obj,
				comparisonKey, from, to);
	}

	/**
	 * ���ڶ���ģʽ�Ĳ�ѯ��������з��������Ķ���֧�ַ�ҳ��SQL����
	 * 
	 * @param obj
	 *            UmsRole
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
		return OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(obj,
				comparisonKey, from, to, conditionPre);
	}

	/**
	 * ���ڶ���ģʽ�Ĳ�ѯ��������������Ķ�������
	 * 
	 * @param comparisonKey
	 *            ��ѯ�ȽϷ���,����key�� �ֶ�ID,value�ǱȽϷ��ű��� >,<
	 * 
	 * @param Object
	 *            UmsRole
	 * 
	 * @return long ��¼����
	 */
	public long queryObjectsNumber(Object obj, Map comparisonKey) {
		return OrmerEntry.fetchOrmer().fetchQuerister().queryObjectsNumber(obj,
				comparisonKey);
	}

	/**
	 * ���ڶ���ģʽ�Ĳ�ѯ��������������Ķ���������֧��SQL����
	 * 
	 * @param Object
	 *            UmsRole
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
		return OrmerEntry.fetchOrmer().fetchQuerister().queryObjectsNumber(obj,
				comparisonKey, conditionPre);
	}

	public RoleDao getRoleDaopermission() {
		return roleDaopermission;
	}

	public void setRoleDaopermission(RoleDao roleDaopermission) {
		this.roleDaopermission = roleDaopermission;
	}

	public RoleDao getRoleDaouser() {
		return roleDaouser;
	}

	public void setRoleDaouser(RoleDao roleDaouser) {
		this.roleDaouser = roleDaouser;
	}

	public RoleDao getRoleDaorole() {
		return roleDaorole;
	}

	public void setRoleDaorole(RoleDao roleDaorole) {
		this.roleDaorole = roleDaorole;
	}

	public boolean moveRoleDept(String roleid, String ouOri, String ouAim) {
		return true;
	}

}
