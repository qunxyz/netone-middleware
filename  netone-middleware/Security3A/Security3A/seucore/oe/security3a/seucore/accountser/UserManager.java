package oe.security3a.seucore.accountser;

import java.util.List;

import oe.security3a.seucore.obj.Clerk;
import oe.security3a.seucore.obj.User;
import oe.security3a.seucore.obj.db.UmsProtectedobject;
import oe.security3a.seucore.obj.db.UmsRole;


/**
 * 用户业务应用
 * 
 * @author chen.jia.xun
 * 
 */
public interface UserManager {

	/**
	 * 用户查询接口，
	 * 
	 * @param condi
	 *            这里的条件可以为null,表示查询所有的用户,
	 * @param from
	 *            可以为null
	 * @param to
	 *            可以为null
	 * @return List<UserInfo>
	 * @throws Exception
	 */
	public List<Clerk> searchUser(String condi, Integer from, Integer to) throws Exception;

	/**
	 * 查询用户结果的数量
	 * 
	 * @param condi
	 * @return
	 * @throws Exception
	 */
	public int searchUserCount(String condi) throws Exception;

	/**
	 * 判断用户是否已经存在
	 * 
	 * @param loginname
	 * @param code 隶属于
	 * @return
	 * @throws Exception
	 */
	public boolean isUserExist(String code, String loginname) throws Exception;

	/**
	 * 新建用户
	 * 
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public boolean createUser(User user) throws Exception;

	/**
	 * 删除用户
	 * 
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public boolean delUser(User user) throws Exception;

	/**
	 * 修改用户
	 * 
	 * @param userinfo
	 * @return
	 * @throws Exception
	 */
	public boolean modifyUser(User user) throws Exception;

	/**
	 * 获取用户具有的角色
	 * 
	 * @param code	用户的隶属于
	 * @param userid 用户的ID
	 * @return List<Role>
	 * @throws Exception
	 */
	public List<UmsRole> getUserRoles(String code, String userid) throws Exception;

	/**
	 * 获取用户所属的用户组
	 * 
	 * @param userid
	 * @return List<Group>
	 * @throws Exception
	 */
	public List<UmsProtectedobject> getUserGroups(String userid) throws Exception;

	/**
	 * 重置密码<br>
	 * 注: 该方法中需要调度短信接口发消息给用户
	 * 
	 * @param code 用户隶属于
	 * @param clerk Clerk
	 * @return
	 */
	public boolean resetPassword(String code, Clerk clerk);

	
	/**
	 * 
	 * 更新用户角色的关系
	 * @param code 用户隶属于
	 * @param userid 用户id
	 * @param role 角色列表
	 */
	public void roleRelationupdate(String code, String userid, List<UmsRole> role);

	/**
	 * 更新用户部门关系
	 * 
	 * @param dept
	 */
	public void groupRelationupdate(String userid, List<UmsProtectedobject> dept);

	/**
	 * 登陆验证
	 * 
	 * @param code 用户隶属于
	 * @param name 用户名
	 * @param pass 密码
	 * @return clerk 其中operationinfo字段是返回验证的错误信息
	 */
	public Clerk validationUserOpe(String code, String name, String pass);

	/**
	 * 移动人员
	 * 
	 * @param ouOri
	 *            原始部门的OU
	 * @param ouAim
	 *            目标部门的OU
	 * @return
	 */
	public boolean moveUserDeptOpe(String loginname, String ouOri, String ouAim);

	/**
	 * 注销
	 * 
	 * @param name
	 * @return
	 */
	public boolean logoutOpe(String name);

}
