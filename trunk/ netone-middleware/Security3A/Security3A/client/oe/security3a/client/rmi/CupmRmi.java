package oe.security3a.client.rmi;

import java.rmi.Remote;
import java.util.List;
import java.util.Map;

import oe.security3a.seucore.obj.db.UmsOperationLog;

/**
 * 安全访问控制服务
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail: 56414429@qq.com, chenjiaxun@oesee.com<br>
 *         support by: http://www.oesee.com
 */

public interface CupmRmi extends Remote {
	/**
	 * todo的xml中的主题的4个结构属性
	 */
	String _TODO_SUBJECT_ATTRIBUTE_OPE = "ope";

	String _TODO_SUBJECT_ATTRIBUTE_CONDITION = "condition";

	String _TODO_SUBJECT_ATTRIBUTE_PARTICIPANT = "participant";

	String _TODO_SUBJECT_ATTRIBUTE_PASSWORD = "password";

	/**
	 * 
	 */
	String[] _TODO_SUBJECT_ATTRIBUTE_ALL = { _TODO_SUBJECT_ATTRIBUTE_OPE,
			_TODO_SUBJECT_ATTRIBUTE_CONDITION,
			_TODO_SUBJECT_ATTRIBUTE_PARTICIPANT,
			_TODO_SUBJECT_ATTRIBUTE_PASSWORD };

	/**
	 * todo中ope的7个操作逻辑值
	 */
	String _OPE_GET_RESOURCE = "getResource";

	String _OPE_ADD_RESOURCE = "addResource";

	String _OPE_GET_CLERK = "getClerk";

	String _OPE_ADD_CLERK = "addClerk";

	String _OPE_CHECK_ROLE_PERMISSION = "checkRolePermission";

	String _OPE_CHECK_CLERK_PERMISSION = "checkClerkPermission";

	String _OPE_LOGIN = "login";

	/**
	 * 操作逻辑值汇总
	 */
	String[] _TODO_ALL = { _OPE_LOGIN, _OPE_GET_RESOURCE, _OPE_ADD_RESOURCE,
			_OPE_GET_CLERK, _OPE_ADD_CLERK, _OPE_CHECK_ROLE_PERMISSION,
			_OPE_CHECK_CLERK_PERMISSION };

	/**
	 * 鉴权逻辑,判断角色是否有某个资源的权限。
	 * 
	 * @param roleName
	 *            角色的dnname(带路径的naturalname)
	 * @param dnname
	 *            保护对象的dnname名字(带路径的naturalname)
	 * @param action
	 *            操作(通常从小到大分别为 1,3,7,15,31.....) 2的n次方减1
	 * @return
	 */
	public boolean checkRolePermission(String rolename, String dnname,
			String action) throws Exception;

	/**
	 * 授权逻辑判断角色本身是否有某个资源的权限-核心方法(基于OU)
	 * 
	 * @param roleid
	 *            角色id
	 * @param ou
	 *            保护对象的ou名字(带路径的id串)
	 * @param action
	 *            操作(通常从小到大分别为 1,3,7,15,31.....) 2的n次方减1
	 * @return
	 */
	public boolean checkRolePermissionCore(String roleid, String ou,
			String action) throws Exception;

	/**
	 * 鉴权逻辑,判断角色本身是否有某个资源的权限(仅包含角色本身没有包含继承的角色的权限)
	 * 
	 * @param roleName
	 *            角色的dnname(带路径的naturalname)
	 * @param dnname
	 *            保护对象的dnname名字(带路径的naturalname)
	 * @param action
	 *            操作(操作目前有三种模式,可以继续扩展 1,3,7)
	 * @return
	 */
	public boolean checkRoleSelfPermission(String rolename, String dnname,
			String action) throws Exception;

	/**
	 * 鉴权逻辑,判断角色是否有某个资源的权限(仅包含角色本身没有包含继承的角色的权限)
	 * 
	 * @param roleid
	 *            角色id
	 * @param ou
	 *            保护对象的ou名字(带路径的id串)
	 * @param action
	 *            操作(通常从小到大分别为 1,3,7,15,31.....) 2的n次方减1
	 * @return
	 */
	public boolean checkRoleSelfPermissionCore(String roleid, String ou,
			String action) throws Exception;

	/**
	 * 鉴权逻辑,判断用户是否有某个资源的权限
	 * 
	 * @param code
	 *            用户隶属于的域默认为0000
	 * @param loginname
	 *            用户登陆名
	 * @param dnname
	 *            保护对象的dnname(带路径的naturalname)
	 * @param action
	 *            操作(操作目前有四种模式,可以继续扩展 1,3,7,15)
	 * @return
	 */
	public boolean checkUserPermission(String code, String loginname,
			String dnname, String action) throws Exception;

	/**
	 * 鉴权逻辑,判断用户是否有某个资源的权限-核心方法(基于ID)
	 * 
	 * @param code
	 *            用户隶属于的域默认为0000
	 * @param loginname
	 *            用户登陆名
	 * @param ou
	 *            保护对象的ou名字(带路径的id串)
	 * @param dnname
	 *            保护对象的dnname(带路径的naturalname)
	 * @return
	 * @throws Exception
	 */
	public boolean checkUserPermissionCore(String code, String loginname,
			String ou, String action) throws Exception;

	/**
	 * 审计逻辑,操作日志
	 * 
	 * @param dnname
	 *            被访问资源的naturalname
	 * @param ip
	 *            访问者的来源,比如:IP
	 * @param userid
	 *            访问者的帐户
	 * @param rsinfo
	 *            资源操作结果
	 * @param remark
	 *            资源操作的补充描述
	 * @return
	 * @throws Exception
	 */
	public boolean log(String dnname, String ip, String userid, String rsinfo,
			String remark) throws Exception;

	/**
	 * 审计逻辑,查询日志
	 * 
	 * @param condition
	 *            参考SQL92的where条件,手工编辑字段userid,
	 *            operatetime,operationid,resultinfo,userip,remark 的组合条件
	 * @return
	 */
	public String[] queryLog(String condition) throws Exception;

	/**
	 * 通用逻辑,基于XML模式的访问控制
	 * 
	 * @param code
	 *            用户隶属于
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String todo(String code, String request) throws Exception;

	/**
	 * 安全系统逻辑,初始化全部缓存
	 * 
	 * @throws Exception
	 */
	public void initCacheall() throws Exception;

	/**
	 * 安全系统逻辑,初始化指定用户的缓存
	 * 
	 * @param userid
	 * @throws Exception
	 */
	public void initCacheUser(String userid) throws Exception;

	/**
	 * 安全系统逻辑,初始化指定角色的缓存
	 * 
	 * @param rolename
	 *            角色的dnname(带路径的naturalname)
	 * @throws Exception
	 */
	public void initCacheRole(String rolename) throws Exception;

	/**
	 * 安全系统逻辑,初始化指定角色的缓存
	 * 
	 * @param roleid
	 * @throws Exception
	 */
	public void initCacheRoleCore(String roleid) throws Exception;

	/**
	 * 获得3A安全的配置信息
	 * 
	 * @param name
	 *            相关配置的key，这些配置信息通常在安全的rmi服务的jndi中
	 * @return
	 * @throws Exception
	 */
	public String fetchConfig(String name) throws Exception;

	/**
	 * 查询日志信息
	 * 
	 * @param uolog
	 * @param compation
	 * @param condition
	 * @param form
	 * @param to
	 * @return
	 */
	public List logList(UmsOperationLog uolog, Map compation, String condition,
			int form, int to) throws Exception;

	/**
	 * 查询日志信息
	 * 
	 * @param uolog
	 * @param compation
	 * @param condition
	 * @param form
	 * @param to
	 * @return
	 */
	public long logsNumber(UmsOperationLog uolog, Map compation,
			String condition) throws Exception;
}
