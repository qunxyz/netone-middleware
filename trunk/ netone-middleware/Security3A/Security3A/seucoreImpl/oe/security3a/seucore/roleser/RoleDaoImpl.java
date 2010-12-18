package oe.security3a.seucore.roleser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.beanutils.BeanUtils;

import oe.frame.orm.OrmerEntry;
import oe.security3a.seucore.obj.User2Role;
import oe.security3a.seucore.obj.db.UmsRole;
import oe.security3a.seucore.obj.db.UmsRole2role2;
import oe.security3a.seucore.obj.db.UmsRolepermission;
import oe.security3a.seucore.roleser.RoleDao;

/**
 * 角色Dao实现
 * 
 * @author ni.he.qing
 * 
 */
public class RoleDaoImpl implements RoleDao {

	private RoleDao roleDaopermission;

	private RoleDao roleDaouser;

	private RoleDao roleDaorole;

	static ResourceBundle messages = ResourceBundle.getBundle("jndi",
			Locale.CHINESE);

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
	 *         2）输入对象为map,里面包含UmsRole2role,UmsUser2Role,UmsRole,code，再判断是否为空
	 *         3）按UmsRole,UmsUser2Role,UmsRole2role,UmsRolepermission的顺序分别执行创建操作
	 *         4）注意在创建UmsUser2role,UmsUser2Role,UmsRole2role,UmsRolepermission的时候其roleid的值为刚创建的UmsRole的Id
	 *         5）code是用户的隶属于，如果在创建角色时添加了用户，则需要指定用户的隶属于
	 */
	public boolean create(Object obj) {
		if (obj == null) {
			return false;
		}
		Map map = (Map) obj;
		String code = map.get(RoleDao._CODE).toString();
		UmsRole urole = (UmsRole) map.get(RoleDao._ROLE);
		if (urole == null) {
			return false;
		}
		urole.setNaturalname(urole.getNaturalname().toUpperCase().trim());
		if (!OrmerEntry.fetchOrmer().fetchSerializer().create(urole)) {
			return false;
		}

		UmsRole2role2 urolerole = (UmsRole2role2) map.get(RoleDao._ROLE2ROLE);
		urolerole.setRelationalrolemainid(urole.getId().toString());
		if (!roleDaorole.create(urolerole)) {
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
			List<User2Role> roleusers = (List) map.get(RoleDao._ROLE2USER);
			for (User2Role u2r : roleusers) {
				u2r.setRoleid(urole.getId().toString());
				u2r.setCode(code);
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

		// 删除用户和角色之间的关系，这里不删除，只把角色删除，用户找不到对应的角色，也就没有权限
		//不做删除操作是因为，在删除角色前，已经判断该角色没有关联的用户了
		// User2Role userrole = new User2Role();
		// userrole.setRoleid(roleid);
		// userrole.setCode("0000");
		// List userlist = roleDaouser.queryObjects(userrole, null);
		// if (!roleDaouser.drops(userlist)) {
		// return false;
		// }
		UmsRole2role2 urolerole = new UmsRole2role2();
		urolerole.setRelationalrolemainid(roleid);
		List rolelist = roleDaorole.queryObjects(urolerole, null);
		if (!roleDaorole.drops(rolelist)) {
			return false;
		}
		UmsRole urole = (UmsRole) OrmerEntry.fetchOrmer().fetchQuerister()
				.loadObject(UmsRole.class, new Long(roleid));
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
	 *         2）输入对象为map,里面包含UmsRole2role,UmsUser2Role,UmsRolepermission,UmsRole，code再判断是否为空
	 *         3）首先得到UmsRole的roleid,查询UmsUser2role,UmsRole2role,UmsRolepermission,删除之，再重新用map里的对象创建
	 *         4）UmsRole直接用map里的对象进行修改
	 *         5)code是用户的隶属于，当修改角色时，涉及到授权给的用户时，需要指定用户的隶属于
	 */
	public boolean update(Object obj) {
		if (obj == null) {
			return false;
		}
		Map map = (Map) obj;
		String code = map.get(RoleDao._CODE).toString();
		UmsRole urole = (UmsRole) map.get(RoleDao._ROLE);
		if (urole == null) {
			return false;
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
			if (!roleDaorole.create(role2)) {
				return false;
			}
		}

		if (map.get(RoleDao._ROLE2USER) != null) {
			List<User2Role> roleusers = (List) map.get(RoleDao._ROLE2USER);
			User2Role u2r = new User2Role();
			u2r.setRoleid(roleid);
			u2r.setCode(code);
			List list1 = roleDaouser.queryObjects(u2r, null);
			List<User2Role> list2 = RoleDaoImplUserReference
					.ConverToUser2RoleList(code, list1);
			for (User2Role u2r2 : list2) {
				u2r2.setCode(code);
				roleDaouser.drop(u2r2);
			}

			for (User2Role newu2r : roleusers) {
				newu2r.setRoleid(urole.getId().toString());
				newu2r.setCode(code);
				roleDaouser.create(newu2r);
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
		return OrmerEntry.fetchOrmer().fetchQuerister().loadObject(objClass,
				key);
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
		return OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(obj,
				comparisonKey);
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

		return OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(obj,
				comparisonKey, conditionPre);
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
		return OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(obj,
				comparisonKey, from, to);
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
	public List queryObjects(Object obj, Map comparisonKey, int from, int to,
			String conditionPre) {
		return OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(obj,
				comparisonKey, from, to, conditionPre);
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
		return OrmerEntry.fetchOrmer().fetchQuerister().queryObjectsNumber(obj,
				comparisonKey);
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
	public long queryObjectsNumber(Object obj, Map comparisonKey,
			String conditionPre) {
		return OrmerEntry.fetchOrmer().fetchQuerister().queryObjectsNumber(obj,
				comparisonKey, conditionPre);
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
		return true;
	}

}
