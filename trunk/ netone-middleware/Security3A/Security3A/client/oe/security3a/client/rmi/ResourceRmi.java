package oe.security3a.client.rmi;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;


import oe.security3a.seucore.obj.Clerk;
import oe.security3a.seucore.obj.User2Role;
import oe.security3a.seucore.obj.db.UmsProtectedobject;
import oe.security3a.seucore.obj.db.UmsRole;
import oe.security3a.seucore.obj.db.UmsRolepermission;
import oe.security3a.seucore.roleser.RoleDao;


/**
 * 资源服务,负责域内的资源管理,其中由于人员这个资源比较特殊所以专门针对人员有相关的方法 这些方法以Clerk为后缀
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail: 56414429@qq.com, chenjiaxun@oesee.com<br>
 *         support by: http://www.oesee.com
 */
public interface ResourceRmi extends Remote {
	/**
	 * 角色移动(重命名)
	 * 
	 * @param ouOri
	 * @param ouAim
	 * @return
	 * @throws RemoteException
	 */
	public boolean moveRoleDeptOpe(String roleid, String ouOri, String ouAim)
			throws RemoteException;

	/**
	 * 人员移动(重命名)
	 * 
	 * @param ouOri
	 * @param ouAim
	 * @return
	 * @throws RemoteException
	 */
	boolean moveUserDeptOpe(String loginname, String ouOri, String ouAim)
			throws RemoteException;

	/**
	 * 查询符合条件的职员
	 * 
	 * @param code 用户隶属于
	 * @param user	Clerk
	 * @return
	 * @throws RemoteException
	 */
	List fetchClerk(String code, Clerk user, Map comparekey, String condition)
			throws RemoteException;

	/**
	 * 检查对象是否已经存在(该方法需要被addResource 相关的方法调用)
	 * 
	 * @param naturalname
	 * @return
	 */
	boolean checkExist(String naturalname) throws RemoteException;

	/**
	 * 增加职员
	 * 
	 * @param code 用户隶属于
	 * @param user Clerk
	 * @throws RemoteException
	 */
	boolean addClerk(String code, Clerk user) throws RemoteException;

	/**
	 * 删除职员
	 * 
	 * @param code 隶属于 ，即Clerk的officeNO
	 * @param id	Clerk 的 description字段
	 * @return
	 * @throws RemoteException
	 */
	boolean dropClerk(String code, String id) throws RemoteException;

	/**
	 * 更新职员
	 * 
	 * @param code 用户隶属于
	 * @param user Clerk
	 * @return
	 * @throws RemoteException
	 */
	boolean updateClerk(String code, Clerk user) throws RemoteException;

	/**
	 * 装载职员
	 * 使用db时，指定到那个表查询
	 * @param code 隶属于
	 * @param id id
	 * @return
	 * @throws RemoteException
	 */
	Clerk loadClerk(String code,String id) throws RemoteException;

	/**
	 * 查询所有的资源
	 * 
	 * @param pro
	 * @return
	 * @throws RemoteException
	 */
	List fetchResource(UmsProtectedobject pro, Map comparekey, String condition)
			throws RemoteException;

	/**
	 * 查询所有的资源
	 * 
	 * @param pro
	 * @param comparekey
	 * @return
	 * @throws RemoteException
	 */
	List fetchResource(UmsProtectedobject pro, Map comparekey)
			throws RemoteException;

	/**
	 * 添加资源<br>
	 * 注意添加的时候 upo中的naturalname 不是全名，而是最后的名字。其中upo的parentdir必须填写,它是该upo对应的父结点id
	 * 
	 * @param upo
	 * @throws RemoteException
	 */
	Serializable addResource(UmsProtectedobject upo) throws RemoteException;

	/**
	 * 添加资源,在根节点下创建, 注意在创建的时候自动填写 aggregation的值 保持从小到大 <br>
	 * 注意添加的时候 upo中的naturalname 不是全名，而是最后的名字。另一个参数natrualname是该upo的父结点的名称<br>
	 * upo中 parentdir可以不填写
	 * 
	 * @param upo
	 * @throws RemoteException
	 */
	Serializable addResource(UmsProtectedobject upo, String natrualname)
			throws RemoteException;

	/**
	 * 在某结点下拷贝创建子资源(目标资源的中的的activeUrl非空，那么activeUrl将用于保存源资源的naturalname)
	 * 
	 * @param copyToId
	 *            要获得拷贝的父结点ID
	 * @param copyNatrualnames
	 *            所有需要拷贝的资源命名
	 * @param copylevel
	 *            所有被拷贝资源的拷贝层次 (注意：拷贝中系统会自动检查拷贝层次是否超出现有的层次,并且自动根据实际情况调整)
	 * @throws RemoteException
	 */
	void addFormCopyResource(String copyToId, String[] copyNatrualnames,
			int copylevel) throws RemoteException;



	/**
	 * 删除资源
	 * 
	 * @param id
	 * @return
	 * @throws RemoteException
	 */
	boolean dropResource(String id) throws RemoteException;

	/**
	 * 删除资源
	 * 
	 * @param id
	 * @return
	 * @throws RemoteException
	 */
	boolean dropAllSubResource(String id) throws RemoteException;

	/**
	 * 更新资源
	 * 
	 * @param upo
	 * @return
	 * @throws RemoteException
	 */
	boolean updateResource(UmsProtectedobject upo) throws RemoteException;

	/**
	 * 根据资源ID来装载资源
	 * 
	 * @param id
	 * @return
	 * @throws RemoteException
	 */
	UmsProtectedobject loadResourceById(String id) throws RemoteException;

	/**
	 * 根据资源的naturalname来装载资源
	 * 
	 * @param naturalname
	 * @return
	 * @throws RemoteException
	 */
	UmsProtectedobject loadResourceByNatural(String naturalname)
			throws RemoteException;

	/**
	 * 根据资源的父节点的ID 获得所有的子节点，只获得下一层
	 * 
	 * @param parentid 父资源的ID号，
	 * @return 返回子资源，注意，只返回一层子资源
	 * @throws RemoteException
	 */
	List subResource(String parentid) throws RemoteException;
	
	
	/**
	 * 根据资源的父节点的Naturalname，获得所有的子资源
	 * @param naturalname 父资源的naturalname
	 * @return 返回所有的子资源
	 * @throws RemoteException
	 */
	List subResourceByNaturalname(String naturalname) throws RemoteException;

	/**
	 * 资源的聚合控制,上位移运算
	 * 
	 * @param currentid
	 * @return
	 * @throws RemoteException
	 */
	boolean moveUpResource(String currentid) throws RemoteException;

	/**
	 * 资源的聚合控制,下位移运算
	 * 
	 * @param currentid
	 * @return
	 * @throws RemoteException
	 */
	boolean moveDownResource(String currentid) throws RemoteException;

	/**
	 * 根据角色对象来获得所有符合条件的角色
	 * 
	 * @param role
	 * @param comparekey
	 * @return
	 * @throws RemoteException
	 */
	List fetchRole(UmsRole role, Map comparekey, String conditionPre)
			throws RemoteException;

	/**
	 * 根据用户角色对象获得所有符合条件的用户角色条件关系
	 * 
	 * @param role
	 * @return
	 * @throws RemoteException
	 */
	List fetchUser2role(String code, User2Role user2role, Map map, String condtion)
			throws RemoteException;

	/**
	 * 增加角色
	 * @param code 
	 * 				人员的隶属于，在创建角色时，如果添加人员，那么就会产生人员与角色的关系，
	 * 				因此需要指定人员的隶属于
	 * @param map	里面包含UmsRole，UmsUser2Role,UmsRole2role,UmsRolepermission
	 * 				map.put(RoleDao._ROLE, urole);
					map.put(RoleDao._ROLE2USER, userrole);
					map.put(RoleDao._ROLE2ROLE, urolerole);
					map.put(RoleDao._ROLE2PERMISSION, urplist);
	 * @throws RemoteException
	 */
	Serializable addRole(String code, Map map) throws RemoteException;

	/**
	 * 删除角色
	 * @param id
	 * @return
	 * @throws RemoteException
	 */
	boolean dropRole(String id) throws RemoteException;

	/**
	 * 装载角色
	 * @param id
	 * @return
	 * @throws RemoteException
	 */
	UmsRole loadRole(Long id) throws RemoteException;

	/**
	 * 批量删除对象
	 * 
	 * @param objs
	 *            List
	 * @return boolean
	 */
	boolean dropRoles(List objs) throws RemoteException;

	/**
	 * 批量删除对象
	 * 
	 * @param objs
	 *            List
	 * @return boolean
	 */
	boolean dropUserRoles(String code, List objs) throws RemoteException;

	/**
	 * 更新角色
	 * @param code 
	 * 				用户的隶属于，更新该角色，涉及更行该角色授予的用户时，需要更新该角色和该隶属于下的用户之间的关系
	 * @param map
	 * @return
	 * @throws RemoteException
	 */
	boolean updateRole(String code, Map map) throws RemoteException;

	/**
	 * 基于对象模式的查询，获得所有符合条件的对象，支持分页
	 * 
	 * @param code 	
	 * 			  用户隶属于
	 * @param obj
	 *            Clerk
	 * 
	 * @param comparisonKey
	 *            查询比较符号,其中key是 字段ID,value是比较符号比如 >,<
	 * @param from
	 *            int 需要返回的对象数组的起始位置
	 * @param to
	 *            int 需要返回的对象数组的终了位置
	 * 
	 * @return List 符合条件的对象数组
	 */
	List queryObjectsClerk(String code, Clerk clerk, Map comparisonKey, int from, int to)
			throws RemoteException;

	/**
	 * 基于对象模式的查询，获得满足条件的对象总数
	 * 
	 * @param code
	 *            用户隶属于
	 * 
	 * @param comparisonKey
	 *            查询比较符号,其中key是 字段ID,value是比较符号比如 >,<
	 * 
	 * @param Object
	 *            Clerk
	 * 
	 * @return long 记录总数
	 */
	long queryObjectsNumberClerk(String code, Clerk clerk, Map comparisonKey)
			throws RemoteException;

	/**
	 * 基于对象模式的查询，获得所有符合条件的对象，支持分页
	 * 
	 * @param obj
	 *            UmsRole
	 * 
	 * @param comparisonKey
	 *            查询比较符号,其中key是 字段ID,value是比较符号比如 >,<
	 * @param from
	 *            int 需要返回的对象数组的起始位置
	 * @param to
	 *            int 需要返回的对象数组的终了位置
	 * 
	 * @return List 符合条件的对象数组
	 */
	List queryObjectsRole(UmsRole role, Map comparisonKey, int from, int to)
			throws RemoteException;

	/**
	 * 基于对象模式的查询，获得满足条件的对象总数
	 * 
	 * @param comparisonKey
	 *            查询比较符号,其中key是 字段ID,value是比较符号比如 >,<
	 * 
	 * @param Object
	 *            UmsRole
	 * 
	 * @return long 记录总数
	 */
	long queryObjectsNumberRole(UmsRole role, Map comparisonKey)
			throws RemoteException;

	/**
	 * 更新用户角色的关系
	 * 
	 * @param code 用户的隶属于
	 * @param userid 用户id
	 * @param role
	 *            算法：<br>
	 *            1）根据传入的userid值查询数据库，把所获得的数据全部删除
	 *            2）根据传入的UmsRole里包含的roleid与传入的userid构造新的UmsUser2role对象，加入集合中，再一起创建
	 */
	void roleRelationupdate(String code, String userid, List<UmsRole> role)
			throws RemoteException;

	/**
	 * 重置密码<br>
	 * 注: 该方法中需要调度短信接口发消息给用户
	 * 
	 * @param code 用户隶属于
	 * @param clerk 
	 * @return 初始化密码
	 */
	boolean resetPassword(String code, Clerk clerk) throws RemoteException;

	/**
	 * 获取用户具有的角色
	 * 
	 * @param code 用户隶属于
	 * @param userid 用户id
	 * @return List<UmsRole>
	 * @throws Exception
	 *             算法：<br>
	 *             1）根据传入的cn值查询数据库，获得UmsUser2role对象集合
	 *             2）用每个集合中的对象id来装载出UmsRole对象，再把结果放入新集合中返回
	 */
	List<UmsRole> getUserRoles(String code, String userid) throws Exception;

	/**
	 * 获得拥有该角色的用户
	 * 
	 * @param code 用户的隶属于
	 * @param roleId 角色id
	 * @return List<Clerk> 算法：<br>
	 *         1）根据roleId查询其所在的UmsUser2role对象
	 *         2）根据UmsRole2role2对象中的userid来装载成SearchResult
	 *         3）判断是否加载成功,若是则开始用SearchResult中的属性构造Clerk
	 */
	List<Clerk> fetchUser(String code, String roleId) throws RemoteException;

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
	UmsRole fetchExtendedRole(String roleId) throws RemoteException;

	/**
	 * 获得角色的授权
	 * 
	 * @param roleId
	 * @return List<UmsRolepermission>
	 */
	List<UmsRolepermission> fetchPermission(String roleId)
			throws RemoteException;

	/**
	 * 登陆验证
	 * 
	 * @param code 隶属于
	 * @param name 用户名
	 * @param pass 密码
	 * @return clerk 其中operationinfo字段是返回验证的错误信息
	 * @throws RemoteException
	 */
	Clerk validationUserOpe(String code, String name, String pass)
			throws RemoteException;
	
	
	/**
	 * 帐号同步
	 * @param ope	同步类型
	 * @param code	用户隶属于
	 * @param usercode	用户名
	 * @return同步返回的信息
	 * @throws RemoteException
	 */
	public String[] SyncUser(String ope, String code, String usercode)throws RemoteException;
	
	/**
	 * 资源的分页查询
	 * @param upo
	 * @param comparisonKey
	 * @param from
	 * @param to
	 * @return
	 * @throws RemoteException
	 */
	List queryObjectProtectedObj(UmsProtectedobject upo,
			Map comparisonKey, int from, int to, String conditionPre) throws RemoteException;
	
	/**
	 * 资源的分页查询，获得总数
	 * @param upo
	 * @param comparisonKey
	 * @param conditionPre
	 * @return
	 * @throws RemoteException
	 */
	long queryObjectNumberProtectedObj(UmsProtectedobject upo,
			Map comparisonKey, String conditionPre) throws RemoteException;

}
