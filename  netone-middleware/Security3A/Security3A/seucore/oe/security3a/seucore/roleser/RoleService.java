package oe.security3a.seucore.roleser;

import java.util.List;

import oe.security3a.seucore.obj.Permission;
import oe.security3a.seucore.obj.Role;
import oe.security3a.seucore.obj.db.UmsRolepermission;


public interface RoleService {

	/**
	 * 获得角色权限
	 * 
	 * @param role
	 * @return
	 * @throws Exception
	 */
	public List fetchAvaliablePermission(Role role) throws Exception;

	/**
	 * 检查角色中是否包含某权限
	 * 
	 * @param role
	 * @param permission
	 * @return
	 * @throws Exception
	 */
	public boolean checkContainPermission(Role role, Permission permission)
			throws Exception;

	/**
	 * 获取角色的权限信息
	 * 
	 * @param Roleid
	 * @return
	 * @throws Exception
	 */
	public List<UmsRolepermission> fetchRoleUmsRolepermission(String Roleid)
			throws Exception;

	/**
	 * 获得资源对象的dao操作
	 * 
	 * @return
	 */
	public RoleDao fetchDao();

	/**
	 * 获得资源对象的dao操作
	 * 
	 * @return
	 */
	public RoleDao fetchDao(String pointto);

}
