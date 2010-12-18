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
 * 角色管理接口实现
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
	 * 获得角色隶属的系统
	 * 
	 * @param roleId
	 * @return UmsApplication 算法：<br>
	 *         1）根据roleId装载成UmsRole对象 2）判断是否装载成功，若是则根据其appid来装载UmsApplication对象
	 *         3）判断是否加载成功返回
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
	 * 获得拥有该角色的用户
	 * 
	 * @param roleId
	 * @return List<Clerk> 算法：<br>
	 *         1）根据roleId查询其所在的UmsUser2role对象
	 *         2）根据UmsRole2role2对象中的userid来装载成SearchResult
	 *         3）判断是否加载成功,若是则开始用SearchResult中的属性构造Clerk
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
	 * 获得角色的授权
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
	 * 获得被roleId关联的角色
	 * 
	 * @param roleId
	 *            主题角色,找被其关联的角色
	 * @return List<UmsRole> 算法：<br>
	 *         1）根据roleId和关联关系查询其所在的UmsRole2role2对象
	 *         2）根据UmsRole2role2对象中的relationalrolemainid来得到relationalrolesubid
	 *         3）用relationalrolesubid装载UmsRole,判断是否加载成功,加入list返回
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
	 * 获得被roleId继承的角色(当前只支持单从继承)
	 * 
	 * @param roleId
	 *            主题角色,找被其继承的角色
	 * @return UmsRole 算法：<br>
	 *         1）根据roleId和继承关系查询其所在的UmsRole2role2对象
	 *         2）根据UmsRole2role2对象中的relationalrolemainid来得到relationalrolesubid
	 *         3）用relationalrolesubid装载UmsRole,判断是否加载成功返回
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
	 * 获得被roleId包含的角色
	 * 
	 * @param roleId
	 *            主题角色,找被其包含的角色
	 * @return List<UmsRole> 算法：<br>
	 *         1）根据roleId和包含关系查询其所在的UmsRole2role2对象
	 *         2）根据UmsRole2role2对象中的relationalrolemainid来得到relationalrolesubid
	 *         3）用relationalrolesubid装载UmsRole,判断是否加载成功,加入list返回
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
	 * 获得roleId的聚合角色元素
	 * 
	 * @param roleId
	 * @return List<UmsRole> 算法：<br>
	 *         1）根据roleId和聚合关系查询其所在的UmsRole2role2对象
	 *         2）根据UmsRole2role2对象中的relationalrolemainid来得到relationalrolesubid
	 *         3）用relationalrolesubid装载UmsRole,判断是否加载成功,加入list返回
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
