package oe.common.security3a;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * DRP安全应用接口<br>
 * 
 * 业务系统需要借助外部独立的专门安全服务来管理业务系统内部的安全，该接口作为一个适配器为了方便该系统在实际应用总可<br>
 * 切换不同的安全产品来支持更加灵活的内部安全管理<br>
 * 
 * 
 * @author chenjx <br>
 *         mail:15860836998@139.com
 * 
 */
public interface Security3AIfc {
	/**
	 * 鉴权许可的标志 PERMISSION_ALLOW
	 */
	String PERMISSION_ALLOW = "3";
	
	String PERMISSION_FOBIT = "1";
	/**
	 * 方法执行成功后，返回结果的前缀 ORG_OPE_TIP_SUCCESS
	 */
	String OPE_TIP_SUCCESS = "SUCCESS:";
	/**
	 * 方法执行失败后，返回结果的前缀 ORG_OPE_TIP_ERROR
	 */
	String OPE_TIP_ERROR = "ERROR:";
	/**
	 * 帐户禁用标志 ACCOUNT_FORBIT_MARK
	 */
	String ACCOUNT_FORBIT_MARK = "9$9$";
	/**
	 * 外部安全构件的根应用目录
	 */
	String DEFAULT_DEPT_APP = "DEPT";
	/**
	 * 帐户所隶属的域
	 */
	String DEMAIN_CODE = "0000";

	/**
	 * 获得在线用户
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	Client3A onlineUser(HttpServletRequest request) throws Exception;

	/**
	 * 根据客户id装载客户信息
	 * 
	 * @param clientid
	 * @return
	 */
	Client3A loadUser(String clientid)throws Exception;

	/**
	 * 对当前用户进行鉴权，识别其是否有权限
	 * 
	 * @param clientid
	 *            帐户ID
	 * @param resourceNaturalname
	 *            需要鉴权的资源，该是在外部安全服务中注册的资源目录Naturalname
	 * @return
	 */
	boolean permission(String clientid, String resourceNaturalname)
			throws Exception;

	/**
	 * 创建新帐号
	 * 
	 * @param bussDirId
	 *            业务系统的目录id
	 * @param clientId
	 *            帐号名 注：帐户名是唯一的，用于登录时识别用户身份
	 * @param displayname
	 *            帐户显示名
	 * @param extAttribute
	 *            帐户的扩展属性
	 * @return 创建结果
	 */
	String newAccount(String bussDirId, String clientId, String displayname,
			Map extAttribute) throws Exception;

	/**
	 * 编辑帐户属性
	 * 
	 * @param bussDirId
	 *            业务系统的目录id
	 * @param clientId
	 *            帐号名 注：帐户名是唯一的，用于登录时识别用户身份
	 * @param displayname
	 *            帐户显示名
	 * @param extAttribute
	 *            帐户的扩展属性
	 * @return
	 * 
	 * 注：该方法将检查用户的实际隶属目录与参数bussDirId是否一致
	 */
	String editAccount(String bussDirId, String clientId, String displayname,
			Map extAttribute) throws Exception;

	/**
	 * 删除帐号
	 * 
	 * @param clientId
	 * @return
	 * @throws Exception
	 */
	String deleteAccount(String clientId) throws Exception;

	/**
	 * 禁用帐户
	 * 
	 * @param clientId
	 *            帐户名
	 * @return
	 */
	String fobidAccount(String clientId) throws Exception;

	/**
	 * 创建新的组织机构节点
	 * 
	 * @param parentBussDirId
	 *            隶属的组织的业务ID
	 * @param bussDirId
	 *            当前节点Id
	 * @param displayName
	 *            当前节点显示名
	 * @param extAttribute
	 *            节点的扩展属性
	 * @return
	 * @throws Exception
	 */
	String newOrganization(String parentBussDirId, String bussDirId,
			String displayName, Map extAttribute) throws Exception;

	/**
	 * 编辑组织机构节点
	 * 
	 * @param parentBussDirId
	 *            隶属的组织的业务ID
	 * @param bussDirId
	 *            当前节点Id
	 * @param displayName
	 *            节点的显示名
	 * @param extAttribute
	 *            节点的扩展属性
	 * @return
	 * @throws Exception
	 */
	String editOrganization(String parentBussDirId, String bussDirId,
			String displayName, Map extAttribute) throws Exception;

	/**
	 * 删除组织节点
	 * 
	 * @param bussDirId
	 *            当前节点Id
	 * @throws Exception
	 */
	String deleteOrganization(String bussDirId) throws Exception;

	/**
	 * 获得安全全局配置参数
	 * 
	 * @param key
	 * @return
	 */
	String getSecurityEnv(String key);

}
