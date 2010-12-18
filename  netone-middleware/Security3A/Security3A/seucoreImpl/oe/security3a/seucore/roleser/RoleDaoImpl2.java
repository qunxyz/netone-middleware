package oe.security3a.seucore.roleser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.naming.directory.SearchControls;

import oe.frame.orm.OrmerEntry;
import oe.frame.orm.util.IdServer;

import org.apache.commons.lang.StringUtils;


import oe.security3a.SeuserEntry;
import oe.security3a.seucore.obj.db.UmsProtectedobject;
import oe.security3a.seucore.obj.db.UmsRole;
import oe.security3a.seucore.obj.db.UmsRole2role2;
import oe.security3a.seucore.obj.db.UmsRolepermission;
import oe.security3a.seucore.obj.db.UmsUser2role;
import oe.security3a.seucore.resourceser.ProtectedObjectService;

/**
 * ��ɫDaoʵ��
 * 
 * @author ni.he.qing
 * 
 */
public class RoleDaoImpl2 implements RoleDao {

	private RoleDao roleDaopermission;

	private RoleDao roleDaouser;

	private RoleDao roleDaorole;

	private ProtectedObjectService pos = (ProtectedObjectService) SeuserEntry.iv("protectedObjectService");

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
	 *         2���������Ϊmap,�������UmsRole2role,UmsUser2Role,UmsRole�����ж��Ƿ�Ϊ��
	 *         3����UmsRole,UmsUser2Role,UmsRole2role,UmsRolepermission��˳��ֱ�ִ�д�������
	 *         4��ע���ڴ���UmsUser2role,UmsUser2Role,UmsRole2role,UmsRolepermission��ʱ����roleid��ֵΪ�մ�����UmsRole��Id
	 */
	public boolean create(Object obj) {
		if (obj == null) {
			return false;
		}
		Map map = (Map) obj;
		UmsRole urole = (UmsRole) map.get(RoleDao._ROLE);
		if (urole == null) {
			return false;
		}
		urole.setNaturalname(urole.getNaturalname().toUpperCase().trim());
		urole.setId(Long.valueOf(IdServer.xnumID()));
		UmsProtectedobject upo = new UmsProtectedobject();
		upo.setNaturalname(urole.getBelongingness());
		List upolist = pos.fetchDao().queryObjects(upo, null);
		if (upolist != null && upolist.size() == 1) {
			upo = (UmsProtectedobject) upolist.get(0);
			urole.setBelongingness("ou=" + upo.getNaturalname() + "," + upo.getParentdir());
		} else {
			return false;
		}
		if (!OrmerEntry.fetchOrmer().fetchSerializer().create(urole)) {
			return false;
		}

		UmsRole2role2 urolerole = (UmsRole2role2) map.get(RoleDao._ROLE2ROLE);
		urolerole.setRelationalrolemainid(urole.getId().toString());
		urolerole.setId(Long.valueOf(IdServer.xnumID()));
		if (StringUtils.isNotEmpty(urolerole.getRelationalrolesubid()) && !roleDaorole.create(urolerole)) {
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
			List<UmsUser2role> roleusers = (List) map.get(RoleDao._ROLE2USER);
			for (UmsUser2role u2r : roleusers) {
				u2r.setRoleid(urole.getId().toString());
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
		UmsUser2role uuserrole = new UmsUser2role();
		uuserrole.setRoleid(roleid);
		List userlist = roleDaouser.queryObjects(uuserrole, null);
		if (!roleDaouser.drops(userlist)) {
			return false;
		}
		UmsRole2role2 urolerole = new UmsRole2role2();
		urolerole.setRelationalrolemainid(roleid);
		List rolelist = roleDaorole.queryObjects(urolerole, null);
		if (!roleDaorole.drops(rolelist)) {
			return false;
		}
		UmsRole urole = (UmsRole) OrmerEntry.fetchOrmer().fetchQuerister().loadObject(UmsRole.class, new Long(roleid));
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
	 *         2���������Ϊmap,�������UmsRole2role,UmsUser2Role,UmsRolepermission,UmsRole�����ж��Ƿ�Ϊ��
	 *         3�����ȵõ�UmsRole��roleid,��ѯUmsUser2role,UmsRole2role,UmsRolepermission,ɾ��֮����������map��Ķ��󴴽�
	 *         4��UmsRoleֱ����map��Ķ�������޸�
	 */
	public boolean update(Object obj) {
		if (obj == null) {
			return false;
		}
		Map map = (Map) obj;
		UmsRole urole = (UmsRole) map.get(RoleDao._ROLE);
		if (urole == null) {
			return false;
		}
		if (StringUtils.isNotEmpty(urole.getBelongingness())) {
			UmsProtectedobject upo = new UmsProtectedobject();
			upo.setNaturalname(urole.getBelongingness());
			List upolist = pos.fetchDao().queryObjects(upo, null);
			if (upolist != null && upolist.size() == 1) {
				upo = (UmsProtectedobject) upolist.get(0);
				urole.setBelongingness("ou=" + upo.getNaturalname() + "," + upo.getParentdir());
			} else {
				return false;
			}
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
			role2.setId(Long.valueOf(IdServer.xnumID()));
			if (StringUtils.isNotEmpty(role2.getRelationalrolesubid()) && !roleDaorole.create(role2)) {
				return false;
			}
		}
		if (map.get(RoleDao._ROLE2USER) != null) {
			UmsUser2role dropu2r = new UmsUser2role();
			dropu2r.setRoleid(roleid);
			List u2rlist = roleDaouser.queryObjects(dropu2r, null);
			roleDaouser.drops(u2rlist);
			List<UmsUser2role> roleusers = (List) map.get(RoleDao._ROLE2USER);
			for (UmsUser2role u2r : roleusers) {
				u2r.setRoleid(urole.getId().toString());
				roleDaouser.create(u2r);
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
		try {
			UmsRole urole = (UmsRole) OrmerEntry.fetchOrmer().fetchQuerister().loadObject(objClass, key);
			UmsProtectedobject upo = (UmsProtectedobject) pos.fetchDao().loadObject(UmsProtectedobject.class,
					urole.getBelongingness());
			urole.setBelongingness(upo.getNaturalname());
			return urole;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

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
		return queryObjects(obj, comparisonKey, null);
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
		if (obj == null) {
			return null;
		}
		UmsRole role = (UmsRole) obj;
		if (StringUtils.isNotEmpty(conditionPre)) {
			String belong = StringUtils.substringBetween(conditionPre, "'", "'");
			if (StringUtils.isNotEmpty(role.getBelongingness())) {
				String belong2 = StringUtils.replace(role.getBelongingness(), "%", "");
				if (StringUtils.contains(belong2, belong)) {
					role.setBelongingness(belong2);
				} else {
					return null;
				}
			} else {
				role.setBelongingness(belong);
			}
			conditionPre = null;
		}
		if (StringUtils.isNotEmpty(role.getBelongingness())) {
			UmsProtectedobject upo = new UmsProtectedobject();
			upo.setNaturalname(StringUtils.replace(role.getBelongingness(), "%", ""));
			List upolist = pos.fetchDao().queryObjects(upo, comparisonKey);
			if (upolist != null && upolist.size() == 1) {
				upo = (UmsProtectedobject) upolist.get(0);
				role.setBelongingness("ou=" + upo.getNaturalname() + "," + upo.getParentdir());
			} else {
				return null;
			}
		}
		if (comparisonKey == null) {
			comparisonKey = new LinkedHashMap();
		}
		comparisonKey.put("searchScope", String.valueOf(SearchControls.ONELEVEL_SCOPE));
		List list = OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(obj, comparisonKey, conditionPre);
		if (list != null && !list.isEmpty()) {
			List tmplist = new ArrayList();
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				UmsRole urole = (UmsRole) iterator.next();
				UmsProtectedobject upo = (UmsProtectedobject) pos.fetchDao().loadObject(UmsProtectedobject.class,
						urole.getBelongingness());
				urole.setBelongingness(upo.getNaturalname());
				tmplist.add(urole);
			}
			list = tmplist;
		}
		return list;
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
		return queryObjects(obj, comparisonKey, from, to, null);
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
	public List queryObjects(Object obj, Map comparisonKey, int from, int to, String conditionPre) {
		if (obj == null) {
			return null;
		}
		UmsRole role = (UmsRole) obj;
		if (StringUtils.isNotEmpty(role.getBelongingness())) {
			UmsProtectedobject upo = (UmsProtectedobject) pos.fetchDao().loadObject(UmsProtectedobject.class,
					role.getBelongingness());
			if (upo != null) {
				role.setBelongingness("ou=" + upo.getNaturalname() + "," + upo.getParentdir());
			}
			if (comparisonKey == null) {
				comparisonKey = new LinkedHashMap();
			}
			comparisonKey.put("searchScope", String.valueOf(SearchControls.ONELEVEL_SCOPE));
		}
		List list = OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(obj, comparisonKey, from, to, conditionPre);
		if (list != null && !list.isEmpty()) {
			List tmplist = new ArrayList();
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				UmsRole urole = (UmsRole) iterator.next();
				UmsProtectedobject upo = (UmsProtectedobject) pos.fetchDao().loadObject(UmsProtectedobject.class,
						urole.getBelongingness());
				urole.setBelongingness(upo.getId());
				tmplist.add(urole);
			}
			list = tmplist;
		}
		return list;
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
	public long queryObjectsNumber(Object obj, Map comparisonKey, String conditionPre) {
		List list = queryObjects(obj, comparisonKey, conditionPre);
		if (list != null && list.size() > 0) {
			return list.size();
		}
		return -1;
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
		try {
			String[] str = new String[2];
			if (StringUtils.isNotEmpty(ouAim) && StringUtils.isNotEmpty(ouOri)) {
				UmsProtectedobject upo = new UmsProtectedobject();
				upo.setNaturalname(ouOri);
				List list = pos.fetchDao().queryObjects(upo, null);
				if (list != null && list.size() == 1) {
					UmsProtectedobject tmpupo = (UmsProtectedobject) list.get(0);
					ouOri = "cn=" + roleid + ",ou=" + tmpupo.getNaturalname() + "," + tmpupo.getParentdir();
				} else {
					return false;
				}
				upo.setNaturalname(ouAim);
				list = pos.fetchDao().queryObjects(upo, null);
				if (list != null && list.size() == 1) {
					UmsProtectedobject tmpupo = (UmsProtectedobject) list.get(0);
					ouAim = "cn=" + roleid + ",ou=" + tmpupo.getNaturalname() + "," + tmpupo.getParentdir();
				} else {
					return false;
				}
			}
			str[0] = ouOri;
			str[1] = ouAim;
			if (!str[0].equals(str[1])) {
				OrmerEntry.fetchOrmer().fetchSerializer().update(str);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

}
