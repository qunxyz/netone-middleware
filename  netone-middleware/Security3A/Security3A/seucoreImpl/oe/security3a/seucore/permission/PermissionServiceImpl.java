package oe.security3a.seucore.permission;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import oe.security3a.SeumanEntry;
import oe.security3a.seucore.accountser.UserManager;
import oe.security3a.seucore.obj.db.UmsRole;
import oe.security3a.seucore.obj.db.UmsRolepermission;
import oe.security3a.seucore.permission.InheritNmcPermission;
import oe.security3a.seucore.permission.NmcPermission;
import oe.security3a.seucore.permission.NmcPermissionCollection;
import oe.security3a.seucore.permission.PermissionService;
import oe.security3a.seucore.roleser.RoleManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PermissionServiceImpl implements PermissionService {

	static private ResourceBundle messages = ResourceBundle.getBundle("jndi",
			Locale.CHINESE);

	static private String admin;

	static private boolean cache;

	final String _USER_KEY = "U";

	final String _ROLE_KEY = "R";

	/**
	 * 角色授权缓存
	 */
	Map cacheRoleL1 = new HashMap();

	/**
	 * 角色授权缓存(不包含继承逻辑)
	 */
	Map cacheRoleL2 = new HashMap();

	/**
	 * 用户角色缓存
	 */
	Map cacheUserL1 = new HashMap();

	static {
		admin = messages.getString("admin");
		if ("true".equals(messages.getString("cache"))) {
			cache = true;
		}
	}

	private Log log = LogFactory.getLog(PermissionServiceImpl.class);

	private boolean checkRolePermissionInner(String roleid, String podn,
			String action) throws Exception {
		try {
			List<NmcPermissionCollection> npclist = getHierarchyRole(roleid);
			InheritNmcPermission inp = new InheritNmcPermission();
			inp.addOrderdParents(npclist);
			NmcPermission np = new NmcPermission(podn, action);

			return inp.implies(np);

		} catch (Exception e) {
			log.error("判断角色有没有给出的权限出错！", e);
		}
		return false;
	}

	public boolean checkRolePermission(String roleid, String podn, String action)
			throws Exception {
		// 不使用缓存的处理逻辑
		if (!cache) {
			return checkRolePermissionInner(roleid, podn, action);
		}
		// 使用缓存的处理逻辑
		if (!cacheRoleL1.containsKey(roleid)) {
			Map value = new HashMap();
			cacheRoleL1.put(roleid, value);
		}
		Map valueCache = (Map) cacheRoleL1.get(roleid);
		String key = podn + action;
		if (!valueCache.containsKey(key)) {
			boolean rs = checkRolePermissionInner(roleid, podn, action);
			valueCache.put(key, rs);
		}
		return ((Boolean) valueCache.get(key)).booleanValue();
	}

	private boolean checkRoleSelfPermissionInner(String roleid, String podn,
			String action) throws Exception {

		try {
			RoleManager rs = (RoleManager) SeumanEntry.iv("roleManager");
			List<UmsRolepermission> urplist = rs.fetchPermission(roleid);
			NmcPermissionCollection rolenpc = new NmcPermissionCollection();
			for (UmsRolepermission rolepermission : urplist) {
				rolenpc.add(createNmcPermission(rolepermission));
			}
			NmcPermission np = new NmcPermission(podn, action);
			return rolenpc.implies(np);
		} catch (Exception e) {
			log.error("判断角色有没有给出的权限出错！", e);
		}
		return false;
	}

	public boolean checkRoleSelfPermission(String roleid, String podn,
			String action) throws Exception {
		if (!cache) {
			return checkRoleSelfPermissionInner(roleid, podn, action);
		}

		if (!cacheRoleL2.containsKey(roleid)) {
			Map value = new HashMap();
			cacheRoleL2.put(roleid, value);
		}
		Map valueCache = (Map) cacheRoleL2.get(roleid);
		String key = podn + action;
		if (!valueCache.containsKey(key)) {
			boolean rs = checkRoleSelfPermissionInner(roleid, podn, action);
			valueCache.put(key, rs);
		}
		return ((Boolean) valueCache.get(key)).booleanValue();
	}

	private boolean checkUserPermissionInner(String code, String userid,
			String podn, String action) throws Exception {
		UserManager um = (UserManager) SeumanEntry.iv("userManager");
		boolean rs = false;
		try {
			List<UmsRole> rolelist = um.getUserRoles(code, userid);
			if (rolelist != null) {
				for (UmsRole r : rolelist) {
					if (checkRolePermission(r.getId().toString(), podn, action)) {
						rs = true;
						break;
					}
				}
			}
		} catch (Exception e) {
			log.error("判断用户的权限出错！", e);
		}
		return rs;
	}

	public boolean checkUserPermission(String code, String userid, String podn,
			String action) throws Exception {

		if (admin.equals(userid)) {
			return true;
		}
		
		if (!cache) {
			return checkUserPermissionInner(code, userid, podn, action);
		}

		if (!cacheUserL1.containsKey(userid)) {
			Map value = new HashMap();
			cacheUserL1.put(userid, value);
		}
		Map valueCache = (Map) cacheUserL1.get(userid);
		String key = _USER_KEY + userid + podn + action;
		if (!valueCache.containsKey(key)) {
			UserManager um = (UserManager) SeumanEntry.iv("userManager");
			boolean rs = checkUserPermissionInner(code, userid, podn, action);
			valueCache.put(key, rs);
		}
		return ((Boolean) valueCache.get(key)).booleanValue();

	}

	private NmcPermission createNmcPermission(UmsRolepermission umsp)
			throws Exception {
		NmcPermission tp = new NmcPermission(umsp.getDninfo(), ""
				+ umsp.getOperationid());
		return tp;
	}

	private List<NmcPermissionCollection> getHierarchyRole(String roleid)
			throws Exception {
		List<NmcPermissionCollection> list = new ArrayList<NmcPermissionCollection>();
		RoleManager rs = (RoleManager) SeumanEntry.iv("roleManager");

		String tmproleid = roleid;
		Set<String> idset = new HashSet<String>();
		do {
			idset.add(tmproleid);
			List<UmsRolepermission> urplist = rs.fetchPermission(tmproleid);
			NmcPermissionCollection npc = new NmcPermissionCollection();
			for (UmsRolepermission rolepermission : urplist) {
				npc.add(createNmcPermission(rolepermission));
			}
			list.add(npc);
			UmsRole urole = rs.fetchExtendedRole(tmproleid);
			if (urole != null) {
				if (idset.contains(urole.getId().toString())) {
					log.error("!!!!!  ID为：" + roleid + "的角色的继承出现死循环！");
					break;
				}
				tmproleid = urole.getId().toString();
			} else {
				tmproleid = null;
			}
		} while (tmproleid != null);

		return list;
	}

	public void reflashRolePermission(String roleid) {
		cacheRoleL1.remove(roleid);
		cacheRoleL2.remove(roleid);
	}

	public void reflashUserRole(String userid) {
		cacheUserL1.remove(userid);
	}

	public void reflashAll() {
		cacheRoleL1 = null;
		cacheUserL1 = null;
		cacheRoleL2 = null;
		cacheRoleL1 = new HashMap();
		cacheUserL1 = new HashMap();
		cacheRoleL2 = new HashMap();
	}

}
