package oe.security3a.seucore.roleser;

import java.util.List;

import oe.security3a.SeudaoEntry;
import oe.security3a.seucore.obj.Permission;
import oe.security3a.seucore.obj.Role;
import oe.security3a.seucore.obj.db.UmsRolepermission;
import oe.security3a.seucore.roleser.RoleDao;
import oe.security3a.seucore.roleser.RoleService;


/**
 * 角色服务接口实现
 * 
 * @author ni.he.qing
 * 
 */
public class RoleServiceImpl implements RoleService {

	/**
	 * 获得资源对象的dao操作
	 * 
	 * @return RoleDao
	 */
	public RoleDao fetchDao() {
		return (RoleDao) SeudaoEntry.iv("roleDao");
	}
	
	/**
	 * 获得资源对象的dao操作
	 * 
	 * @return RoleDao
	 * 
	 */
	public RoleDao fetchDao(String pointto){
		if(pointto!=null && !pointto.equals("")){
			if(pointto.equals("roleDaopermission")){
				return (RoleDao) SeudaoEntry.iv("roleDaopermission");
			}
			if(pointto.equals("roleDaouser")){
				return (RoleDao) SeudaoEntry.iv("roleDaouser");
			}
			if(pointto.equals("roleDaorole")){
				return (RoleDao) SeudaoEntry.iv("roleDaorole");
			}
		}
		return null;
	}
	
	/**
	 * 检查角色中是否包含某权限
	 * 
	 * @param role
	 * @param permission
	 * @return
	 * @throws Exception
	 */
	public boolean checkContainPermission(Role role, Permission permission)
			throws Exception {
		return false;
	}

	/**
	 * 获得角色权限
	 * 
	 * @param role
	 * @return
	 * @throws Exception
	 */
	public List fetchAvaliablePermission(Role role) throws Exception {
		return null;
	}

	/**
	 * 获取角色的权限信息
	 * 
	 * @param Roleid
	 * @return
	 * @throws Exception
	 */
	public List<UmsRolepermission> fetchRoleUmsRolepermission(String Roleid) throws Exception {
		return null;
	}

}
