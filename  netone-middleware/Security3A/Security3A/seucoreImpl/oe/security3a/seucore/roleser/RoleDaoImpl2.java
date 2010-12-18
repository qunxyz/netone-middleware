package oe.security3a.seucore.roleser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.naming.directory.SearchControls;

import oe.frame.orm.OrmerEntry;
import oe.frame.orm.util.IdServer;

import org.apache.commons.lang.StringUtils;


import oe.security3a.SeuserEntry;
import oe.security3a.seucore.obj.db.UmsProtectedobject;
import oe.security3a.seucore.obj.db.UmsRole;
import oe.security3a.seucore.obj.db.UmsRole2role2;
import oe.security3a.seucore.obj.db.UmsRolepermission;
import oe.security3a.seucore.obj.db.UmsUser2role;
import oe.security3a.seucore.resourceser.ProtectedObjectService;

/**
 * 角色Dao实现
 * 
 * @author ni.he.qing
 * 
 */
public class RoleDaoImpl2 implements RoleDao {

	private RoleDao roleDaopermission;

	private RoleDao roleDaouser;

	private RoleDao roleDaorole;

	private ProtectedObjectService pos = (ProtectedObjectService) SeuserEntry.iv("protectedObjectService");

	/**
	 * 安全检察，在对象中加入用户信息
	 * 
	 * @param obj
	 * @return
	 */
	public boolean checkSecrity(Object obj) {
		throw new RuntimeException("该方法没有实现");
	}

	/**
	 * 保存对象
	 * 
	 * @param obj
	 *            Map
	 * @return boolean 算法：<br>
	 *         1）判断输入对象是否为空
	 *         2）输入对象为map,里面包含UmsRole2role,UmsUser2Role,UmsRole，再判断是否为空
	 *         3）按UmsRole,UmsUser2Role,UmsRole2role,UmsRolepermission的顺序分别执行创建操作
	 *         4）注意在创建UmsUser2role,UmsUser2Role,UmsRole2role,UmsRolepermission的时候其roleid的值为刚创建的UmsRole的Id
	 */
	public boolean create(Object obj) {
		if (obj == null) {
			return false;
		}
		Map map = (Map) obj;
		UmsRole urole = (UmsRole) map.get(RoleDao._ROLE);
		if (urole == null) {
			return false;
		}
		urole.setNaturalname(urole.getNaturalname().toUpperCase().trim());
		urole.setId(Long.valueOf(IdServer.xnumID()));
		UmsProtectedobject upo = new UmsProtectedobject();
		upo.setNaturalname(urole.getBelongingness());
		List upolist = pos.fetchDao().queryObjects(upo, null);
		if (upolist != null && upolist.size() == 1) {
			upo = (UmsProtectedobject) upolist.get(0);
			urole.setBelongingness("ou=" + upo.getNaturalname() + "," + upo.getParentdir());
		} else {
			return false;
		}
		if (!OrmerEntry.fetchOrmer().fetchSerializer().create(urole)) {
			return false;
		}

		UmsRole2role2 urolerole = (UmsRole2role2) map.get(RoleDao._ROLE2ROLE);
		urolerole.setRelationalrolemainid(urole.getId().toString());
		urolerole.setId(Long.valueOf(IdServer.xnumID()));
		if (StringUtils.isNotEmpty(urolerole.getRelationalrolesubid()) && !roleDaorole.create(urolerole)) {
			return false;
		}
		List list = (List) map.get(RoleDao._ROLE2PERMISSION);
		List<UmsRolepermission> newlist = new ArrayList<UmsRolepermission>();
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			UmsRolepermission uroleper = (UmsRolepermission) iter.next();
			uroleper.setRoleid(urole.getId().toString());
			newlist.add(uroleper);
		}
		if (!roleDaopermission.creates(newlist)) {
			return false;
		}
		if (map.get(RoleDao._ROLE2USER) != null) {
			List<UmsUser2role> roleusers = (List) map.get(RoleDao._ROLE2USER);
			for (UmsUser2role u2r : roleusers) {
				u2r.setRoleid(urole.getId().toString());
				roleDaouser.create(u2r);
			}
		}

		return true;
	}

	/**
	 * 批量创建对象
	 * 
	 * @param objs
	 *            List
	 * @return boolean
	 */
	public boolean creates(List objs) {
		if (objs == null) {
			return false;
		}
		for (Iterator iter = objs.iterator(); iter.hasNext();) {
			Map map = (Map) iter.next();
			if (!create(map)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 删除对象
	 * 
	 * @param obj
	 *            String
	 * @return boolean 算法：<br>
	 *         1）判断输入对象是否为空 2）输入对象为角色id
	 *         3）按UmsRolepermission,UmsUser2Role,UmsRole2role,UmsRole的顺序分别查找对应的集合，删除之
	 */
	public boolean drop(Object obj) {
		if (obj == null) {
			return false;
		}
		String roleid = (String) obj;
		UmsRolepermission uroleper = new UmsRolepermission();
		uroleper.setRoleid(roleid);
		List roleperlist = roleDaopermission.queryObjects(uroleper, null);
		if (!roleDaopermission.drops(roleperlist)) {
			return false;
		}
		UmsUser2role uuserrole = new UmsUser2role();
		uuserrole.setRoleid(roleid);
		List userlist = roleDaouser.queryObjects(uuserrole, null);
		if (!roleDaouser.drops(userlist)) {
			return false;
		}
		UmsRole2role2 urolerole = new UmsRole2role2();
		urolerole.setRelationalrolemainid(roleid);
		List rolelist = roleDaorole.queryObjects(urolerole, null);
		if (!roleDaorole.drops(rolelist)) {
			return false;
		}
		UmsRole urole = (UmsRole) OrmerEntry.fetchOrmer().fetchQuerister().loadObject(UmsRole.class, new Long(roleid));
		if (!OrmerEntry.fetchOrmer().fetchSerializer().drop(urole)) {
			return false;
		}
		return true;
	}

	/**
	 * 批量删除对象
	 * 
	 * @param objs
	 *            List
	 * @return boolean
	 */
	public boolean drops(List objs) {
		if (objs == null) {
			return false;
		}
		for (Iterator iter = objs.iterator(); iter.hasNext();) {
			String roleid = (String) iter.next();
			if (!drop(roleid)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 刷新对象
	 * 
	 * @param objClass
	 *            Class 对象类
	 * @param key
	 *            Serializable 对象ID
	 */
	public void refreshObject(Object objClass, Serializable key) {
		throw new RuntimeException("该方法没有实现");
	}

	public boolean serial(Object obj) {
		throw new RuntimeException("该方法没有实现");
	}

	/**
	 * 更新对象
	 * 
	 * @param obj
	 *            Map
	 * @return boolean 算法：<br>
	 *         1）判断输入对象是否为空
	 *         2）输入对象为map,里面包含UmsRole2role,UmsUser2Role,UmsRolepermission,UmsRole，再判断是否为空
	 *         3）首先得到UmsRole的roleid,查询UmsUser2role,UmsRole2role,UmsRolepermission,删除之，再重新用map里的对象创建
	 *         4）UmsRole直接用map里的对象进行修改
	 */
	public boolean update(Object obj) {
		if (obj == null) {
			return false;
		}
		Map map = (Map) obj;
		UmsRole urole = (UmsRole) map.get(RoleDao._ROLE);
		if (urole == null) {
			return false;
		}
		if (StringUtils.isNotEmpty(urole.getBelongingness())) {
			UmsProtectedobject upo = new UmsProtectedobject();
			upo.setNaturalname(urole.getBelongingness());
			List upolist = pos.fetchDao().queryObjects(upo, null);
			if (upolist != null && upolist.size() == 1) {
				upo = (UmsProtectedobject) upolist.get(0);
				urole.setBelongingness("ou=" + upo.getNaturalname() + "," + upo.getParentdir());
			} else {
				return false;
			}
		}
		if (!OrmerEntry.fetchOrmer().fetchSerializer().update(urole)) {
			return false;
		}

		String roleid = urole.getId().toString();

		UmsRolepermission uroleper = new UmsRolepermission();
		uroleper.setRoleid(roleid);
		List roleperlist = roleDaopermission.queryObjects(uroleper, null);
		if (!roleDaopermission.drops(roleperlist)) {
			return false;
		}
		List list = (List) map.get(RoleDao._ROLE2PERMISSION);
		if (list != null) {
			if (!roleDaopermission.creates(list)) {
				return false;
			}
		}
		UmsRole2role2 urolerole = new UmsRole2role2();
		urolerole.setRelationalrolemainid(roleid);
		List rolelist = roleDaorole.queryObjects(urolerole, null);
		if (!roleDaorole.drops(rolelist)) {
			return false;
		}
		UmsRole2role2 role2 = (UmsRole2role2) map.get(RoleDao._ROLE2ROLE);
		if (role2 != null) {
			role2.setId(Long.valueOf(IdServer.xnumID()));
			if (StringUtils.isNotEmpty(role2.getRelationalrolesubid()) && !roleDaorole.create(role2)) {
				return false;
			}
		}
		if (map.get(RoleDao._ROLE2USER) != null) {
			UmsUser2role dropu2r = new UmsUser2role();
			dropu2r.setRoleid(roleid);
			List u2rlist = roleDaouser.queryObjects(dropu2r, null);
			roleDaouser.drops(u2rlist);
			List<UmsUser2role> roleusers = (List) map.get(RoleDao._ROLE2USER);
			for (UmsUser2role u2r : roleusers) {
				u2r.setRoleid(urole.getId().toString());
				roleDaouser.create(u2r);
			}
		}
		return true;
	}

	/**
	 * 批量更新对象
	 * 
	 * @param objs
	 *            List
	 * @return boolean
	 */
	public boolean updates(List objs) {
		if (objs == null) {
			return false;
		}
		for (Iterator iter = objs.iterator(); iter.hasNext();) {
			Map map = (Map) iter.next();
			if (!update(map)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 装载对象
	 * 
	 * @param objClass
	 *            Class 对象类
	 * 
	 * 
	 * @param key
	 *            Serializable 对象ID
	 * @return Object 对象实例
	 */
	public Object loadObject(Class objClass, Serializable key) {
		try {
			UmsRole urole = (UmsRole) OrmerEntry.fetchOrmer().fetchQuerister().loadObject(objClass, key);
			UmsProtectedobject upo = (UmsProtectedobject) pos.fetchDao().loadObject(UmsProtectedobject.class,
					urole.getBelongingness());
			urole.setBelongingness(upo.getNaturalname());
			return urole;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * 基于对象模式的查询，获得满足条件的对象
	 * 
	 * @param Object
	 *            UmsRole
	 * 
	 * @param comparisonKey
	 *            查询比较符号,其中key是 字段ID,value是比较符号比如 >,<
	 * 
	 * @return List 对象数组
	 */
	public List queryObjects(Object obj, Map comparisonKey) {
		return queryObjects(obj, comparisonKey, null);
	}

	/**
	 * 基于对象模式的查询，获得满足条件的对象，支持SQL条件
	 * 
	 * @param Object
	 *            UmsRole
	 * 
	 * @param comparisonKey
	 *            查询比较符号,其中key是 字段ID,value是比较符号比如 >,<
	 * 
	 * @param conditionPre
	 *            String 补充条件,符合SQL92标准的查询条件,查询字段名参考查询对象中的get/set中的属性
	 * 
	 * @return List 对象数组
	 */
	public List queryObjects(Object obj, Map comparisonKey, String conditionPre) {
		if (obj == null) {
			return null;
		}
		UmsRole role = (UmsRole) obj;
		if (StringUtils.isNotEmpty(conditionPre)) {
			String belong = StringUtils.substringBetween(conditionPre, "'", "'");
			if (StringUtils.isNotEmpty(role.getBelongingness())) {
				String belong2 = StringUtils.replace(role.getBelongingness(), "%", "");
				if (StringUtils.contains(belong2, belong)) {
					role.setBelongingness(belong2);
				} else {
					return null;
				}
			} else {
				role.setBelongingness(belong);
			}
			conditionPre = null;
		}
		if (StringUtils.isNotEmpty(role.getBelongingness())) {
			UmsProtectedobject upo = new UmsProtectedobject();
			upo.setNaturalname(StringUtils.replace(role.getBelongingness(), "%", ""));
			List upolist = pos.fetchDao().queryObjects(upo, comparisonKey);
			if (upolist != null && upolist.size() == 1) {
				upo = (UmsProtectedobject) upolist.get(0);
				role.setBelongingness("ou=" + upo.getNaturalname() + "," + upo.getParentdir());
			} else {
				return null;
			}
		}
		if (comparisonKey == null) {
			comparisonKey = new LinkedHashMap();
		}
		comparisonKey.put("searchScope", String.valueOf(SearchControls.ONELEVEL_SCOPE));
		List list = OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(obj, comparisonKey, conditionPre);
		if (list != null && !list.isEmpty()) {
			List tmplist = new ArrayList();
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				UmsRole urole = (UmsRole) iterator.next();
				UmsProtectedobject upo = (UmsProtectedobject) pos.fetchDao().loadObject(UmsProtectedobject.class,
						urole.getBelongingness());
				urole.setBelongingness(upo.getNaturalname());
				tmplist.add(urole);
			}
			list = tmplist;
		}
		return list;
	}

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
	public List queryObjects(Object obj, Map comparisonKey, int from, int to) {
		return queryObjects(obj, comparisonKey, from, to, null);
	}

	/**
	 * 基于对象模式的查询，获得所有符合条件的对象，支持分页和SQL条件
	 * 
	 * @param obj
	 *            UmsRole
	 * @param comparisonKey
	 *            查询比较符号,其中key是 字段ID,value是比较符号比如 >,<
	 * @param from
	 *            int 需要返回的对象数组的起始位置
	 * @param to
	 *            int 需要返回的对象数组的终了位置
	 * @param conditionPre
	 *            String 补充条件,符合SQL92标准不含Where的查询条件,查询字段名可参考查询对象中的get/set中的属性
	 * 
	 * @return List 符合条件的对象数组
	 */
	public List queryObjects(Object obj, Map comparisonKey, int from, int to, String conditionPre) {
		if (obj == null) {
			return null;
		}
		UmsRole role = (UmsRole) obj;
		if (StringUtils.isNotEmpty(role.getBelongingness())) {
			UmsProtectedobject upo = (UmsProtectedobject) pos.fetchDao().loadObject(UmsProtectedobject.class,
					role.getBelongingness());
			if (upo != null) {
				role.setBelongingness("ou=" + upo.getNaturalname() + "," + upo.getParentdir());
			}
			if (comparisonKey == null) {
				comparisonKey = new LinkedHashMap();
			}
			comparisonKey.put("searchScope", String.valueOf(SearchControls.ONELEVEL_SCOPE));
		}
		List list = OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(obj, comparisonKey, from, to, conditionPre);
		if (list != null && !list.isEmpty()) {
			List tmplist = new ArrayList();
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				UmsRole urole = (UmsRole) iterator.next();
				UmsProtectedobject upo = (UmsProtectedobject) pos.fetchDao().loadObject(UmsProtectedobject.class,
						urole.getBelongingness());
				urole.setBelongingness(upo.getId());
				tmplist.add(urole);
			}
			list = tmplist;
		}
		return list;
	}

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
	public long queryObjectsNumber(Object obj, Map comparisonKey) {
		List list = queryObjects(obj, comparisonKey);
		if (list != null && list.size() > 0) {
			return list.size();
		}
		return -1;
	}

	/**
	 * 基于对象模式的查询，获得满足条件的对象总数，支持SQL条件
	 * 
	 * @param Object
	 *            UmsRole
	 * 
	 * @param comparisonKey
	 *            查询比较符号,其中key是 字段ID,value是比较符号比如 >,<
	 * 
	 * @param conditionPre
	 *            String 补充条件,符合SQL92标准的查询条件,查询字段名参考查询对象中的get/set中的属性
	 * 
	 * @return long
	 */
	public long queryObjectsNumber(Object obj, Map comparisonKey, String conditionPre) {
		List list = queryObjects(obj, comparisonKey, conditionPre);
		if (list != null && list.size() > 0) {
			return list.size();
		}
		return -1;
	}

	public RoleDao getRoleDaopermission() {
		return roleDaopermission;
	}

	public void setRoleDaopermission(RoleDao roleDaopermission) {
		this.roleDaopermission = roleDaopermission;
	}

	public RoleDao getRoleDaouser() {
		return roleDaouser;
	}

	public void setRoleDaouser(RoleDao roleDaouser) {
		this.roleDaouser = roleDaouser;
	}

	public RoleDao getRoleDaorole() {
		return roleDaorole;
	}

	public void setRoleDaorole(RoleDao roleDaorole) {
		this.roleDaorole = roleDaorole;
	}

	public boolean moveRoleDept(String roleid, String ouOri, String ouAim) {
		try {
			String[] str = new String[2];
			if (StringUtils.isNotEmpty(ouAim) && StringUtils.isNotEmpty(ouOri)) {
				UmsProtectedobject upo = new UmsProtectedobject();
				upo.setNaturalname(ouOri);
				List list = pos.fetchDao().queryObjects(upo, null);
				if (list != null && list.size() == 1) {
					UmsProtectedobject tmpupo = (UmsProtectedobject) list.get(0);
					ouOri = "cn=" + roleid + ",ou=" + tmpupo.getNaturalname() + "," + tmpupo.getParentdir();
				} else {
					return false;
				}
				upo.setNaturalname(ouAim);
				list = pos.fetchDao().queryObjects(upo, null);
				if (list != null && list.size() == 1) {
					UmsProtectedobject tmpupo = (UmsProtectedobject) list.get(0);
					ouAim = "cn=" + roleid + ",ou=" + tmpupo.getNaturalname() + "," + tmpupo.getParentdir();
				} else {
					return false;
				}
			}
			str[0] = ouOri;
			str[1] = ouAim;
			if (!str[0].equals(str[1])) {
				OrmerEntry.fetchOrmer().fetchSerializer().update(str);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

}
