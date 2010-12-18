package oe.security3a.seupublic.client;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import oe.frame.orm.OrmerEntry;
import oe.security3a.SeudaoEntry;
import oe.security3a.SeumanEntry;
import oe.security3a.SeupubEntry;
import oe.security3a.SeuserEntry;
import oe.security3a.client.rmi.CupmRmi;
import oe.security3a.seucore.accountser.UserService;
import oe.security3a.seucore.auditingser.OptrLogDao;
import oe.security3a.seucore.obj.db.UmsOperationLog;
import oe.security3a.seucore.permission.PermissionManager;
import oe.security3a.seucore.permission.PermissionService;
import oe.security3a.seupublic.authentication.PDP;
import oe.security3a.seupublic.authentication.obj.RequestCtx;
import oe.security3a.seupublic.authentication.obj.ResponseCtx;
import oe.security3a.seupublic.authentication.obj.core.AttributeCtx;

public final class CupmRmiImpl extends UnicastRemoteObject implements CupmRmi {

	ResourceBundle messages = ResourceBundle.getBundle("jndi", Locale.CHINESE);

	public CupmRmiImpl() throws RemoteException {
		super();
	}

	UserService userservice = (UserService) SeuserEntry.iv("userService");

	PermissionService permissionService = (PermissionService) SeuserEntry
			.iv("permissionService");

	PermissionManager permissionManager = (PermissionManager) SeumanEntry
			.iv("permissionManager");

	/**
	 * 判断角色是否有某个资源的权限。
	 * 
	 * @param roleName
	 *            角色的名字
	 * @param dnname
	 *            保护对象的dnname名字
	 * @param action
	 *            操作(操作目前有三种模式,可以继续扩展 1,3,7)
	 * @return
	 */
	public boolean checkRolePermission(String rolename, String dnname,
			String action) throws Exception {

		String dn = permissionManager.getOuFromNaturalName(dnname);
		String roleId = permissionManager.getRoleIdFromNaturalName(rolename);
		return permissionService.checkRolePermission(roleId, dn, action);

	}

	/**
	 * 判断用户是否有某个资源的权限
	 * 
	 * @param loginname
	 *            用户登陆名
	 * @param dnname
	 *            保护对象的dnname
	 * @param action
	 *            操作(操作目前有三种模式,可以继续扩展 1,3,7)
	 * @return
	 */
	public boolean checkUserPermission(String code, String loginname, String dnname,
			String action) throws Exception {

		String dn = permissionManager.getOuFromNaturalName(dnname);

		boolean rs = permissionService.checkUserPermission(code, loginname, dn,
				action);
		return rs;
	}

	/**
	 * 操作日志
	 * 
	 * @param dnname
	 *            保护对象的dnname
	 * @param action
	 *            操作(操作目前有三种模式,可以继续扩展 1,3,7)
	 * @return
	 */
	public boolean log(String dnname, String ip, String userid, String rsinfo,
			String remark) throws Exception {
		UmsOperationLog log = new UmsOperationLog();
		log.setOperatetime(new Date());
		log.setRemark(remark);
		log.setResultinfo(rsinfo);
		log.setUserid(userid);
		log.setUserip(ip);
		log.setOperationid(dnname);
		OptrLogDao optrLogDao = (OptrLogDao) SeudaoEntry.iv("optrLogDao");
		optrLogDao.create(log);
		return true;
	}

	/**
	 * 通用逻辑
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String todo(String code, String request) throws Exception {
		if (request == null) {
			return "Ido";
		}
		InputStream input = new ByteArrayInputStream(request.getBytes("utf-8"));
		RequestCtx requestctx = RequestCtx.getInstance(input);
		Set<AttributeCtx> set = requestctx.getSubject().getAttribute();
		String operation = "";
		for (Iterator iter = set.iterator(); iter.hasNext();) {
			AttributeCtx attributectx = (AttributeCtx) iter.next();
			if ("ope".equals(attributectx.getName())) {
				operation = attributectx.getValue();
			}
		}
		// 根据操作和类型定位PDP驱动类
		PDP pdp = null;
		try {
			pdp = (PDP) SeupubEntry.iv(operation);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (pdp == null) {
			throw new RuntimeException("找不到请求[" + operation + "]所对应的PDP驱动程序");
		}

		ResponseCtx responsectx = pdp.evaluate(code, requestctx);
		if (responsectx != null) {
			return responsectx.toString();
		} else {
			throw new RuntimeException("反馈请求[" + operation + "]失败");
		}

	}

	public void initCacheall() throws Exception {
		permissionService.reflashAll();
	}

	public boolean checkRolePermissionCore(String roleid, String ou,
			String action) throws Exception {
		return permissionService.checkRolePermission(roleid, ou, action);
	}

	public boolean checkUserPermissionCore(String code, String loginname, String ou,
			String action) throws Exception {
		return permissionService.checkUserPermission(code, loginname, ou, action);
	}

	public boolean checkRoleSelfPermission(String rolename, String dnname,
			String action) throws Exception {
		String dn = permissionManager.getOuFromNaturalName(dnname);
		String roleId = permissionManager.getRoleIdFromNaturalName(rolename);
		return permissionService.checkRoleSelfPermission(roleId, dn, action);
	}

	public boolean checkRoleSelfPermissionCore(String roleid, String ou,
			String action) throws Exception {
		return permissionService.checkRoleSelfPermission(roleid, ou, action);
	}

	public void initCacheRole(String rolename) throws Exception {
		String roleId = permissionManager.getRoleIdFromNaturalName(rolename);
		permissionService.reflashRolePermission(roleId);
	}

	public void initCacheRoleCore(String roleid) throws Exception {
		permissionService.reflashRolePermission(roleid);

	}

	public void initCacheUser(String userid) throws Exception {
		permissionService.reflashUserRole(userid);

	}

	public String[] queryLog(String condition) throws Exception {
		UmsOperationLog opelog = new UmsOperationLog();
		List list = OrmerEntry.fetchOrmer("ds_config_log.xml").fetchQuerister().queryObjects(
				opelog, null, condition);
		String[] loginfo = new String[list.size()];
		int i = 0;
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			UmsOperationLog element = (UmsOperationLog) iter.next();
			loginfo[i] = element.getLogid() + "," + element.getOperationid()
					+ "," + element.getUserid() + "," + element.getUserip()
					+ "," + element.getResultinfo() + ","
					+ element.getResultinfo();
			i++;
			if (i >= 1000) {
				break;
			}
		}
		return loginfo;
	}

	public String fetchConfig(String name) throws Exception {

		return messages.getString(name);
	}

	public List logList(UmsOperationLog uolog, Map comparisonKey,
			String condition, int from, int to) throws Exception {
		// TODO Auto-generated method stub
		return OrmerEntry.fetchOrmer("ds_config_log.xml").fetchQuerister()
				.queryObjects(uolog, comparisonKey, from, to, condition);
	}

	public long logsNumber(UmsOperationLog uolog, Map compation,
			String condition) throws Exception {
		return OrmerEntry.fetchOrmer("ds_config_log.xml").fetchQuerister()
				.queryObjectsNumber(uolog, compation, condition);
	}
}
