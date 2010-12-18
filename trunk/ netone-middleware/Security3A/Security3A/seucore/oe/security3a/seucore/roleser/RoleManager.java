package oe.security3a.seucore.roleser;

import java.util.List;

import oe.security3a.seucore.obj.Application;
import oe.security3a.seucore.obj.Clerk;
import oe.security3a.seucore.obj.db.UmsApplication;
import oe.security3a.seucore.obj.db.UmsRole;
import oe.security3a.seucore.obj.db.UmsRolepermission;


/**
 * 操作角色的接口
 * 
 * @author hls
 * 
 */
public interface RoleManager {
	/**
	 * 获得拥有该角色的用户
	 * 
	 * @param code 隶属于
	 * @param roleId
	 * @return
	 */
	public List<Clerk> fetchUser(String code,String roleId);

	/**
	 * 获得角色的授权
	 * 
	 * @param roleId
	 * @return
	 */
	public List<UmsRolepermission> fetchPermission(String roleId);

	/**
	 * 获得角色隶属的系统
	 * 
	 * @param roleId
	 * @return
	 */
	public UmsApplication fetchApplication(String roleId);

	/**
	 * 获得被roleId继承的角色(当前只支持单从继承)
	 * 
	 * @param roleId
	 *            主题角色,找被其继承的角色
	 * @return
	 */
	public UmsRole fetchExtendedRole(String roleId);

	/**
	 * 获得被roleId包含的角色
	 * 
	 * @param roleId
	 *            主题角色,找被其包含的角色
	 * @return
	 */
	public List<UmsRole> fetchIncludibleRole(String roleId);

	/**
	 * 获得被roleId关联的角色
	 * 
	 * @param roleId
	 *            主题角色,找被其关联的角色
	 * @return
	 */
	public List<UmsRole> fetchAssociatedRole(String roleId);

	/**
	 * 获得roleId的聚合角色元素
	 * 
	 * @param roleId
	 * @return
	 */
	public List<UmsRole> fetchPolymericRole(String roleId);
	
	/**
	 * 移动角色
	 * 
	 * @param ouOri
	 *            原始部门的OU
	 * @param ouAim
	 *            目标部门的OU
	 * @return
	 */
	public boolean moveRoleDeptOpe(String roleid, String ouOri, String ouAim);

}
