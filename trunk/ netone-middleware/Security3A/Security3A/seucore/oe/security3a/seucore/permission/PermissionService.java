package oe.security3a.seucore.permission;

import java.rmi.Remote;

/**
 * 鉴权接口
 * 
 * @author hls
 */
public interface PermissionService extends Remote {

	/**
	 * 更新角色授权
	 * 
	 * @param roleid
	 */
	public void reflashRolePermission(String roleid);

	/**
	 * 更新用户角色
	 * 
	 * @param userid
	 */
	public void reflashUserRole(String userid);

	/**
	 * 判断角色是否有某个资源的权限。
	 * 
	 * @param roleid
	 * @param podn
	 * @param action
	 * @return
	 */
	public boolean checkRolePermission(String roleid, String podn, String action)
			throws Exception;

	/**
	 * 判断角色的权限，仅包含角色本身没有包含继承的角色
	 * @param roleid
	 * @param podn
	 * @param action
	 * @return
	 * @throws Exception
	 */
	public boolean checkRoleSelfPermission(String roleid, String podn, String action)
		throws Exception ;
	
	/**
	 * 判断用户是否有某个资源的权限
	 * 
	 * @param code	用户的隶属于
	 * @param userid	用户id
	 * @param podn
	 * @param action
	 * @return
	 */
	public boolean checkUserPermission(String code, String userid, String podn, String action)
			throws Exception;

	/**
	 * 更新全部的缓存
	 * 
	 * @throws Exception
	 */
	public void reflashAll() throws Exception;

}
