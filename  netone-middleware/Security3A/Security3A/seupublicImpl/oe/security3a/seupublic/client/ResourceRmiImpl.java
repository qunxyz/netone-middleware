package oe.security3a.seupublic.client;

import java.io.Serializable;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import oe.frame.orm.OrmerEntry;
import oe.frame.web.WebCache;
import oe.security3a.SeumanEntry;
import oe.security3a.SeuserEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.accountser.UserManager;
import oe.security3a.seucore.accountser.UserService;
import oe.security3a.seucore.obj.Clerk;
import oe.security3a.seucore.obj.ProtectedObjectReference;
import oe.security3a.seucore.obj.User2Role;
import oe.security3a.seucore.obj.db.UmsProtectedobject;
import oe.security3a.seucore.obj.db.UmsRole;
import oe.security3a.seucore.obj.db.UmsRolepermission;
import oe.security3a.seucore.permission.PermissionManager;
import oe.security3a.seucore.resourceser.ProtectedObjectService;
import oe.security3a.seucore.roleser.RoleDao;
import oe.security3a.seucore.roleser.RoleManager;
import oe.security3a.seucore.roleser.RoleService;
import oe.security3a.sso.util.SyncUser;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

public class ResourceRmiImpl extends UnicastRemoteObject implements ResourceRmi {

	private UserService userservice = (UserService) SeuserEntry
			.iv("userService");

	private ProtectedObjectService pos = (ProtectedObjectService) SeuserEntry
			.iv("protectedObjectService");

	private RoleService roleservice = (RoleService) SeuserEntry
			.iv("roleService");

	private PermissionManager permissionManager = (PermissionManager) SeumanEntry
			.iv("permissionManager");

	private UserManager usermanager = (UserManager) SeumanEntry
			.iv("userManager");

	private RoleManager rolemanager = (RoleManager) SeumanEntry
			.iv("roleManager");

	public ResourceRmiImpl() throws RemoteException {
		super();
	}

	public boolean moveUserDeptOpe(String loginname, String ouOri, String ouAim)
			throws RemoteException {
		return usermanager.moveUserDeptOpe(loginname, ouOri, ouAim);
	}

	public boolean moveRoleDeptOpe(String roleid, String ouOri, String ouAim)
			throws RemoteException {
		return rolemanager.moveRoleDeptOpe(roleid, ouOri, ouAim);
	}

	public boolean addClerk(String code, Clerk user) throws RemoteException {
		user.setOfficeNO(code);
		return userservice.fetchDao().create(user);
	}

	public Serializable addResource(UmsProtectedobject upo)
			throws RemoteException {
		// 先计算最大的聚合值
		UmsProtectedobject upoMaxAggregation = new UmsProtectedobject();
		upoMaxAggregation.setParentdir(upo.getParentdir());
		List list = pos.fetchDao().queryObjects(upoMaxAggregation, null, 0, 1,
				" order by aggregation desc");
		if (list == null || list.size() == 0) {
			upo.setAggregation(new Long(0));
		} else {
			UmsProtectedobject upoMaxAggregationFetch = (UmsProtectedobject) list
					.get(0);
			Long aggregation = upoMaxAggregationFetch.getAggregation();
			if (aggregation == null) {
				aggregation = new Long(-1);
			}
			upo.setAggregation(aggregation.longValue() + 1);
		}
		if (StringUtils.isEmpty(upo.getActive())) {
			upo.setActive(ProtectedObjectReference._OBJ_ACTIVE_YES);
		}
		pos.fetchDao().create(upo);
		return upo.getId();
	}

	public List fetchClerk(String code, Clerk user, Map comparekey,
			String condition) throws RemoteException {
		user.setOfficeNO(code);
		return userservice.fetchDao().queryObjects(user, comparekey, condition);
	}

	public List fetchResource(UmsProtectedobject pro, Map comparekey,
			String condition) throws RemoteException {
		return pos.fetchDao().queryObjects(pro, comparekey, condition);
	}

	public List fetchResource(UmsProtectedobject pro, Map comparekey)
			throws RemoteException {
		return pos.fetchDao().queryObjects(pro, comparekey);
	}

	public List fetchRole(UmsRole role, Map comparekey, String conditionPre)
			throws RemoteException {
		return roleservice.fetchDao().queryObjects(role, comparekey,
				conditionPre);
	}

	public boolean dropClerk(String code, String id) throws RemoteException {
		WebCache.removeCache("USERX_"+id);
		Clerk clerk = new Clerk();
		clerk.setDescription(id);
		clerk.setOfficeNO(code);
		return userservice.fetchDao().drop(clerk);

	}

	public boolean dropResource(String id) throws RemoteException {
		UmsProtectedobject ps = (UmsProtectedobject) pos.fetchDao().loadObject(
				UmsProtectedobject.class, id);
		WebCache.removeCache("NA_" + ps.getNaturalname());
		WebCache.removeCache("NID_" + ps.getId());
		return pos.fetchDao().drop(ps);

	}

	public boolean updateClerk(String code, Clerk user) throws RemoteException {
		WebCache.removeCache("USERX_"+user.getDescription());
		user.setOfficeNO(code);
		return userservice.fetchDao().update(user);

	}

	public boolean updateResource(UmsProtectedobject upo)
			throws RemoteException {
		WebCache.removeCache("NA_" + upo.getNaturalname());
		WebCache.removeCache("NID_" + upo.getId());
		return pos.fetchDao().update(upo);

	}

	public Clerk loadClerk(String code, String id) throws RemoteException {
		if (!WebCache.containCache("USERX_" + id)) {
			Clerk clerk = (Clerk) userservice.fetchDao().loadObject(code, id);
			WebCache.setCache("USERX_" + id, clerk, null);
			return clerk;
		} else {
			return (Clerk) WebCache.getCache("USERX_" + id);
		}

	}

	private boolean checkCreate(String parentid, String subNatrualname)
			throws RemoteException {
		try {
			return permissionManager.checkCreate(parentid, subNatrualname);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public List subResource(String parentid) throws RemoteException {
		UmsProtectedobject poj = new UmsProtectedobject();
		poj.setParentdir(parentid);
		// poj.setDiris("1");
		String defaultCondition = " order by aggregation desc";
		return pos.fetchDao().queryObjects(poj, null, defaultCondition);
	}

	public Serializable addResource(UmsProtectedobject upo, String appname)
			throws RemoteException {
		if (appname == null) {
			throw new RuntimeException("参数 Naturalname 为空");
		}
		UmsProtectedobject app = new UmsProtectedobject();
		// 该应用通常都在根节点下处理
		app.setNaturalname(appname);
		List list = OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(app,
				null);
		if (list == null || list.size() != 1) {
			throw new RuntimeException("不存在应用程序或者应用程序定义异常: " + list);
		}

		UmsProtectedobject appThis = (UmsProtectedobject) list.get(0);
		upo.setAppid(appThis.getAppid());
		upo.setParentdir(appThis.getId());
		return this.addResource(upo);
	}

	public UmsProtectedobject loadResourceById(String id)
			throws RemoteException {
		if (id == null) {
			return null;
		}
		if (!WebCache.containCache("NID_" + id)) {
			UmsProtectedobject upo = (UmsProtectedobject) pos.fetchDao()
					.loadObject(UmsProtectedobject.class, id);
			WebCache.setCache("NID_" + id, upo, null);
			return upo;
		} else {
			return (UmsProtectedobject) WebCache.getCache("NID_" + id);
		}
	}

	public UmsProtectedobject loadResourceByNatural(String naturalname)
			throws RemoteException {
		if (naturalname == null) {
			return null;
		}
		naturalname=naturalname.toUpperCase();
		if (!WebCache.containCache("NA_" + naturalname)) {
			UmsProtectedobject up = new UmsProtectedobject();
			up.setNaturalname(naturalname.toUpperCase());
			List list = pos.fetchDao().queryObjects(up, null);
			if (list == null || list.size() != 1) {
				if (list == null)
					list = new ArrayList();
				throw new RuntimeException("存储的保护对象出现异常:" + naturalname
						+ " 对应的资源数为：" + list.size());
			}
			UmsProtectedobject upo = (UmsProtectedobject) list.get(0);
			WebCache.setCache("NA_" + naturalname, upo, null);
			return upo;
		} else {
			return (UmsProtectedobject) WebCache.getCache("NA_" + naturalname);
		}

	}

	public boolean moveDownResource(String currentid) throws RemoteException {
		return moveCore(currentid, ">");
	}

	public boolean moveUpResource(String currentid) throws RemoteException {
		return moveCore(currentid, "<");
	}

	private boolean moveCore(String currentid, String compareSymbol) {
		UmsProtectedobject up = (UmsProtectedobject) pos.fetchDao().loadObject(
				UmsProtectedobject.class, currentid);
		// 先计算最大/最小的聚合值
		UmsProtectedobject upoMaxAggregation = new UmsProtectedobject();
		upoMaxAggregation.setParentdir(up.getParentdir());
		Long aggreation = upoMaxAggregation.getAggregation();
		List list = pos.fetchDao().queryObjects(
				upoMaxAggregation,
				null,
				0,
				1,
				"and aggreation" + compareSymbol + aggreation
						+ " order by aggregation desc");
		if (list == null || list.size() == 0) {
			return false;// 已经是最下了 或者最上了
		} else {
			UmsProtectedobject upoMaxAggregationFetch = (UmsProtectedobject) list
					.get(0);
			// 交换聚合值
			up.setAggregation(upoMaxAggregationFetch.getAggregation());
			upoMaxAggregationFetch.setAggregation(aggreation);
			pos.fetchDao().update(up);
			pos.fetchDao().update(upoMaxAggregationFetch);
		}

		return true;
	}

	// 这里的User2Role并不是一个和数据库关联的类，在这个方法中还没有进行转化，将在查询的方法中进行转化
	// code决定了将转化成哪一个类
	public List fetchUser2role(String code, User2Role user2role, Map map,
			String condition) throws RemoteException {
		user2role.setCode(code);
		return roleservice.fetchDao("roleDaouser").queryObjects(user2role, map,
				condition);
	}

	public boolean checkExist(String naturalname) throws RemoteException {

		UmsProtectedobject upo = new UmsProtectedobject();
		upo.setNaturalname(naturalname.toUpperCase());

		List list = fetchResource(upo, null);
		if (list.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	public Serializable addRole(String code, Map map) throws RemoteException {
		map.put(RoleDao._CODE, code);
		roleservice.fetchDao().create(map);
		return ((UmsRole) map.get(RoleDao._ROLE)).getId().toString();
	}

	public boolean dropRole(String id) throws RemoteException {
		WebCache.removeCache("ROLER_" + id);
		return roleservice.fetchDao().drop(id);
	}

	public boolean dropRoles(List objs) throws RemoteException {
		return roleservice.fetchDao().drops(objs);
	}

	public UmsRole fetchExtendedRole(String roleId) throws RemoteException {
		return rolemanager.fetchExtendedRole(roleId);
	}

	public List<UmsRolepermission> fetchPermission(String roleId)
			throws RemoteException {
		return rolemanager.fetchPermission(roleId);
	}

	public List<Clerk> fetchUser(String code, String roleId)
			throws RemoteException {
		if (!WebCache.containCache("ROLER_" + roleId)) {
			List clerk = rolemanager.fetchUser(code, roleId);
			WebCache.setCache("ROLER_" + roleId, clerk, null);
			return clerk;
		} else {
			return (List) WebCache.getCache("ROLER_" + roleId);
		}
	}

	public List<UmsRole> getUserRoles(String code, String userid)
			throws Exception {
		return usermanager.getUserRoles(code, userid);
	}

	public long queryObjectsNumberClerk(String code, Clerk clerk,
			Map comparisonKey) throws RemoteException {
		clerk.setOfficeNO(code);
		return userservice.fetchDao().queryObjectsNumber(clerk, comparisonKey);
	}

	public long queryObjectsNumberRole(UmsRole role, Map comparisonKey)
			throws RemoteException {
		return roleservice.fetchDao().queryObjectsNumber(role, comparisonKey);
	}

	public List queryObjectsRole(UmsRole role, Map comparisonKey, int from,
			int to) throws RemoteException {
		return roleservice.fetchDao().queryObjects(role, comparisonKey, from,
				to);
	}

	public List queryObjectsClerk(String code, Clerk clerk, Map comparisonKey,
			int from, int to) throws RemoteException {
		clerk.setOfficeNO(code);
		return userservice.fetchDao().queryObjects(clerk, comparisonKey, from,
				to);
	}

	public boolean resetPassword(String code, Clerk clerk)
			throws RemoteException {
		WebCache.removeCache("USERX_"+clerk.getDescription());
		return usermanager.resetPassword(code, clerk);
	}

	public void roleRelationupdate(String code, String userid,
			List<UmsRole> role) throws RemoteException {
		usermanager.roleRelationupdate(code, userid, role);
		for (Iterator iterator = role.iterator(); iterator.hasNext();) {
			UmsRole umsRole = (UmsRole) iterator.next();
			WebCache.removeCache("ROLER_" + umsRole.getId());
		}
	}

	public boolean updateRole(String code, Map map) throws RemoteException {
		WebCache.removeCache("ROLER_" + ((UmsRole)map.get("role")).getId());
		map.put(RoleDao._CODE, code);
		return roleservice.fetchDao().update(map);
	}

	public UmsRole loadRole(Long id) throws RemoteException {
		return (UmsRole) roleservice.fetchDao().loadObject(UmsRole.class, id);
	}

	public boolean dropUserRoles(String code, List objs) throws RemoteException {
		List<User2Role> newobjs = new ArrayList<User2Role>();
		for (Iterator iter = objs.iterator(); iter.hasNext();) {
			Object obj = new Object();
			User2Role newobj = new User2Role();
			try {
				obj = iter.next();
				String id = BeanUtils.getProperty(obj, "id");
				String userid = BeanUtils.getProperty(obj, "userid");
				String roleid = BeanUtils.getProperty(obj, "roleid");

				if (userid.length() > 0 && userid != null)
					BeanUtils.setProperty(newobj, "userid", userid);
				if (roleid.length() > 0 && roleid != null)
					BeanUtils.setProperty(newobj, "roleid", roleid);
				if (id.length() > 0 && id != null)
					BeanUtils.setProperty(newobj, "id", Long.valueOf(id));
				BeanUtils.setProperty(newobj, "code", code);
			} catch (Exception e) {
				e.printStackTrace();
			}
			newobjs.add(newobj);
		}
		return roleservice.fetchDao("roleDaouser").drops(newobjs);
	}

	public Clerk validationUserOpe(String code, String name, String pass)
			throws RemoteException {
		return usermanager.validationUserOpe(code, name, pass);
	}

	public List subResourceByNaturalname(String naturalname)
			throws RemoteException {
		UmsProtectedobject poj = new UmsProtectedobject();
		poj.setNaturalname(naturalname + ".%");
		Map map = new HashMap();
		map.put("naturalname", "like");
		// poj.setDiris("1");
		String defaultCondition = " order by aggregation desc";
		return pos.fetchDao().queryObjects(poj, map, defaultCondition);

	}

	public void addFormCopyResource(String id, String[] naturalname, int level)
			throws RemoteException {
		try {

			if (level >= 0 && naturalname.length > 0) {
				for (int i = 0; i < naturalname.length; i++) {
					UmsProtectedobject upo = loadResourceByNatural(naturalname[i]);
					String xnat = StringUtils.substringAfterLast(upo
							.getNaturalname(), ".");

					UmsProtectedobject uponext = new UmsProtectedobject();
					BeanUtils.copyProperties(uponext, upo);
					uponext.setNaturalname(xnat);
					uponext.setParentdir(id);
					String urlact = uponext.getActionurl();
					// 如果原先的actionurl非空的话，那么将源的naturalname保存于此
					if (urlact == null || urlact.equals("")) {
						uponext.setActionurl(upo.getNaturalname());
					}

					Serializable idnext = addResource(uponext);

					List list = subResource(upo.getId());
					String[] naturalnameNext = new String[list.size()];
					int loop = 0;
					for (Iterator iterator = list.iterator(); iterator
							.hasNext();) {
						UmsProtectedobject object = (UmsProtectedobject) iterator
								.next();
						naturalnameNext[loop++] = object.getNaturalname();
					}
					addFormCopyResource(idnext.toString(), naturalnameNext,
							--level);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public boolean dropAllSubResource(String id) throws RemoteException {
		UmsProtectedobject upo = this.loadResourceById(id);

		UmsProtectedobject upox = new UmsProtectedobject();
		String naturalname = upo.getNaturalname();

		String condition = " and naturalname like '" + naturalname + ".%'";
		List list = pos.fetchDao().queryObjects(upox, null, condition);
		return pos.fetchDao().drops(list);

	}

	public String[] SyncUser(String ope, String code, String usercode)
			throws RemoteException {
		SyncUser syncUser = new SyncUser();
		Clerk clerk=this.loadClerk(code, usercode);
		return syncUser.syncUser(ope, clerk);

	}

	public List queryObjectProtectedObj(UmsProtectedobject upo,
			Map comparisonKey, int from, int to, String conditionPre)
			throws RemoteException {

		return pos.fetchDao().queryObjects(upo, comparisonKey, from, to,
				conditionPre);
	}

	public long queryObjectNumberProtectedObj(UmsProtectedobject upo,
			Map comparisonKey, String conditionPre) throws RemoteException {

		return pos.fetchDao().queryObjectsNumber(upo, comparisonKey,
				conditionPre);
	}

}
