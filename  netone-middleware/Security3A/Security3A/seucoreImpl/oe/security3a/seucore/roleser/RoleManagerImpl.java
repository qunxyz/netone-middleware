package oe.security3a.seucore.roleser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import oe.security3a.SeuserEntry;
import oe.security3a.seucore.accountser.UserService;
import oe.security3a.seucore.obj.Clerk;
import oe.security3a.seucore.obj.User2Role;
import oe.security3a.seucore.obj.db.UmsApplication;
import oe.security3a.seucore.obj.db.UmsRole;
import oe.security3a.seucore.obj.db.UmsRole2role2;
import oe.security3a.seucore.obj.db.UmsRolepermission;
import oe.security3a.seucore.resourceser.ApplicationService;
import oe.security3a.seucore.roleser.RoleDao;
import oe.security3a.seucore.roleser.RoleManager;
import oe.security3a.seucore.roleser.RoleService;

/**
 * ��ɫ����ӿ�ʵ��
 * 
 * @author ni.he.qing
 * 
 */
public class RoleManagerImpl implements RoleManager {

	private RoleService roleservice = (RoleService) SeuserEntry
			.iv("roleService");

	private UserService userservice = (UserService) SeuserEntry
			.iv("userService");

	private ApplicationService applicationservice = (ApplicationService) SeuserEntry
			.iv("applicationService");

	/**
	 * ��ý�ɫ������ϵͳ
	 * 
	 * @param roleId
	 * @return UmsApplication �㷨��<br>
	 *         1������roleIdװ�س�UmsRole���� 2���ж��Ƿ�װ�سɹ��������������appid��װ��UmsApplication����
	 *         3���ж��Ƿ���سɹ�����
	 */
	public UmsApplication fetchApplication(String roleId) {
		UmsRole urole = new UmsRole();
		try {
			urole = (UmsRole) roleservice.fetchDao().loadObject(
					urole.getClass(), roleId);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		UmsApplication uapp = new UmsApplication();
		try {
			uapp = (UmsApplication) applicationservice.fetchDao().loadObject(
					uapp.getClass(), urole.getAppid());
			return uapp;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * ���ӵ�иý�ɫ���û�
	 * 
	 * @param roleId
	 * @return List<Clerk> �㷨��<br>
	 *         1������roleId��ѯ�����ڵ�UmsUser2role����
	 *         2������UmsRole2role2�����е�userid��װ�س�SearchResult
	 *         3���ж��Ƿ���سɹ�,������ʼ��SearchResult�е����Թ���Clerk
	 */
	public List<Clerk> fetchUser(String code, String roleId) {
		List<Clerk> clerklist = new ArrayList<Clerk>();
		User2Role user2role = new User2Role();
		user2role.setCode(code);
		user2role.setRoleid(roleId);
		List list = roleservice.fetchDao("roleDaouser").queryObjects(user2role,
				null);
		List user2roles = null;
		if (list != null && list.size() > 0) {
			user2roles = RoleDaoImplUserReference.ConverToUser2RoleList(code,
					list);
		}
		if (user2roles != null && user2roles.size() > 0) {
			for (Iterator iter = user2roles.iterator(); iter.hasNext();) {
				User2Role user2role2 = (User2Role) iter.next();
				try {
					Clerk clerk = (Clerk) userservice.fetchDao().loadObject(
							code, user2role2.getUserid());
					if (clerk != null) {
						clerklist.add(clerk);
					} else {
						continue;
					}
				} catch (Exception e) {
					continue;
				}
			}
		}
		return clerklist;
	}

	/**
	 * ��ý�ɫ����Ȩ
	 * 
	 * @param roleId
	 * @return List<UmsRolepermission>
	 */
	public List<UmsRolepermission> fetchPermission(String roleId) {
		UmsRolepermission uroleper = new UmsRolepermission();
		uroleper.setRoleid(roleId);
		return roleservice.fetchDao("roleDaopermission").queryObjects(uroleper,
				null);
	}

	/**
	 * ��ñ�roleId�����Ľ�ɫ
	 * 
	 * @param roleId
	 *            �����ɫ,�ұ�������Ľ�ɫ
	 * @return List<UmsRole> �㷨��<br>
	 *         1������roleId�͹�����ϵ��ѯ�����ڵ�UmsRole2role2����
	 *         2������UmsRole2role2�����е�relationalrolemainid���õ�relationalrolesubid
	 *         3����relationalrolesubidװ��UmsRole,�ж��Ƿ���سɹ�,����list����
	 */
	public List<UmsRole> fetchAssociatedRole(String roleId) {
		List<UmsRole> rolelist = new ArrayList<UmsRole>();
		UmsRole2role2 urolerole = new UmsRole2role2();
		urolerole.setRelationalrolemainid(roleId);
		urolerole.setRelationtype(RoleDao._ROLE_RELATION_TYPE[1][0]);
		List list = roleservice.fetchDao("roleDaorole").queryObjects(urolerole,
				null);
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			UmsRole2role2 urolerole2 = (UmsRole2role2) iter.next();
			UmsRole urole = new UmsRole();
			if (urolerole2.getRelationalrolesubid() == null
					|| urolerole2.getRelationalrolesubid().equals("")) {
				return null;
			}
			try {
				urole = (UmsRole) roleservice.fetchDao().loadObject(
						urole.getClass(),
						new Long(urolerole2.getRelationalrolesubid()));
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			rolelist.add(urole);
		}
		return rolelist;
	}

	/**
	 * ��ñ�roleId�̳еĽ�ɫ(��ǰֻ֧�ֵ��Ӽ̳�)
	 * 
	 * @param roleId
	 *            �����ɫ,�ұ���̳еĽ�ɫ
	 * @return UmsRole �㷨��<br>
	 *         1������roleId�ͼ̳й�ϵ��ѯ�����ڵ�UmsRole2role2����
	 *         2������UmsRole2role2�����е�relationalrolemainid���õ�relationalrolesubid
	 *         3����relationalrolesubidװ��UmsRole,�ж��Ƿ���سɹ�����
	 */
	public UmsRole fetchExtendedRole(String roleId) {
		UmsRole2role2 urolerole = new UmsRole2role2();
		urolerole.setRelationalrolemainid(roleId);
		urolerole.setRelationtype(RoleDao._ROLE_RELATION_TYPE[2][0]);
		List list = roleservice.fetchDao("roleDaorole").queryObjects(urolerole,
				null);
		if (list != null && list.size() > 0) {
			UmsRole2role2 urolerole2 = (UmsRole2role2) list.get(0);
			if (urolerole2.getRelationalrolesubid() == null
					|| "".equals(urolerole2.getRelationalrolesubid().trim())) {
				return null;
			}
			UmsRole urole = new UmsRole();
			try {
				urole = (UmsRole) roleservice.fetchDao().loadObject(
						urole.getClass(),
						new Long(urolerole2.getRelationalrolesubid()));
				return urole;
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return null;
	}

	/**
	 * ��ñ�roleId�����Ľ�ɫ
	 * 
	 * @param roleId
	 *            �����ɫ,�ұ�������Ľ�ɫ
	 * @return List<UmsRole> �㷨��<br>
	 *         1������roleId�Ͱ�����ϵ��ѯ�����ڵ�UmsRole2role2����
	 *         2������UmsRole2role2�����е�relationalrolemainid���õ�relationalrolesubid
	 *         3����relationalrolesubidװ��UmsRole,�ж��Ƿ���سɹ�,����list����
	 */
	public List<UmsRole> fetchIncludibleRole(String roleId) {
		List<UmsRole> rolelist = new ArrayList<UmsRole>();
		UmsRole2role2 urolerole = new UmsRole2role2();
		urolerole.setRelationalrolemainid(roleId);
		urolerole.setRelationtype(RoleDao._ROLE_RELATION_TYPE[0][0]);
		List list = roleservice.fetchDao("roleDaorole").queryObjects(urolerole,
				null);
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			UmsRole2role2 urolerole2 = (UmsRole2role2) iter.next();
			UmsRole urole = new UmsRole();
			if (urolerole2.getRelationalrolesubid() == null
					|| urolerole2.getRelationalrolesubid().equals("")) {
				return null;
			}
			try {
				urole = (UmsRole) roleservice.fetchDao().loadObject(
						urole.getClass(),
						new Long(urolerole2.getRelationalrolesubid()));
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			rolelist.add(urole);
		}
		return rolelist;
	}

	/**
	 * ���roleId�ľۺϽ�ɫԪ��
	 * 
	 * @param roleId
	 * @return List<UmsRole> �㷨��<br>
	 *         1������roleId�;ۺϹ�ϵ��ѯ�����ڵ�UmsRole2role2����
	 *         2������UmsRole2role2�����е�relationalrolemainid���õ�relationalrolesubid
	 *         3����relationalrolesubidװ��UmsRole,�ж��Ƿ���سɹ�,����list����
	 */
	public List<UmsRole> fetchPolymericRole(String roleId) {
		List<UmsRole> rolelist = new ArrayList<UmsRole>();
		UmsRole2role2 urolerole = new UmsRole2role2();
		urolerole.setRelationalrolemainid(roleId);
		urolerole.setRelationtype(RoleDao._ROLE_RELATION_TYPE[3][0]);
		List list = roleservice.fetchDao("roleDaorole").queryObjects(urolerole,
				null);
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			UmsRole2role2 urolerole2 = (UmsRole2role2) iter.next();
			UmsRole urole = new UmsRole();
			if (urolerole2.getRelationalrolesubid() == null
					|| urolerole2.getRelationalrolesubid().equals("")) {
				return null;
			}
			try {
				urole = (UmsRole) roleservice.fetchDao().loadObject(
						urole.getClass(),
						new Long(urolerole2.getRelationalrolesubid()));
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			rolelist.add(urole);
		}
		return rolelist;
	}

	public boolean moveRoleDeptOpe(String roleid, String ouOri, String ouAim) {
		return roleservice.fetchDao().moveRoleDept(roleid, ouOri, ouAim);
	}

}
