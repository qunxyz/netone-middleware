package oe.security3a.seucore.roleser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import oe.frame.orm.OrmerEntry;
import oe.frame.orm.util.IdServer;

import org.apache.commons.lang.StringUtils;

import oe.security3a.SeuserEntry;
import oe.security3a.seucore.obj.db.UmsProtectedobject;
import oe.security3a.seucore.obj.db.UmsRolepermission;
import oe.security3a.seucore.resourceser.ProtectedObjectService;

/**
 * 角色Dao实现(关于UmsRolepermission)
 * 
 * @author ni.he.qing
 * 
 */
public class RoleDaoImplpermission2 implements RoleDao {

	private ProtectedObjectService pos = (ProtectedObjectService) SeuserEntry
			.iv("protectedObjectService");

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
	 *            UmsRolepermission
	 * @return boolean
	 * 
	 */
	public boolean create(Object obj) {
		if (obj == null) {
			return false;
		}
		UmsRolepermission urper = (UmsRolepermission) obj;
		String dninfo = urper.getDninfo();
		dninfo = StringUtils.replace(dninfo, ".*", "");
		dninfo = StringUtils.replace(dninfo, ".-", "");
		UmsProtectedobject upo = (UmsProtectedobject) pos.fetchDao()
				.loadObject(UmsProtectedobject.class,
						StringUtils.substringAfterLast(dninfo, "."));
		dninfo = "ou=" + upo.getNaturalname() + "," + upo.getParentdir();
		urper.setDninfo(dninfo);
		urper.setLsh(Long.valueOf(IdServer.xnumID()));
		return OrmerEntry.fetchOrmer().fetchSerializer().create(urper);
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
			UmsRolepermission urper = (UmsRolepermission) iter.next();
			if (!this.create(urper)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 删除对象
	 * 
	 * @param obj
	 *            UmsRolepermission
	 * @return boolean
	 * 
	 */
	public boolean drop(Object obj) {
		if (obj == null) {
			return false;
		}
		UmsRolepermission urper = (UmsRolepermission) obj;
		return OrmerEntry.fetchOrmer().fetchSerializer().drop(urper);
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
			UmsRolepermission urper = (UmsRolepermission) iter.next();
			if (!this.drop(urper)) {
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
	 *            UmsRolepermission
	 * @return boolean
	 * 
	 */
	public boolean update(Object obj) {
		if (obj == null) {
			return false;
		}
		UmsRolepermission urper = (UmsRolepermission) obj;
		return OrmerEntry.fetchOrmer().fetchSerializer().update(urper);
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
			UmsRolepermission urper = (UmsRolepermission) iter.next();
			if (!this.update(urper)) {
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
		UmsRolepermission umsper = (UmsRolepermission) OrmerEntry.fetchOrmer()
				.fetchQuerister().loadObject(objClass, key);
		UmsProtectedobject upo = (UmsProtectedobject) pos.fetchDao()
				.loadObject(UmsProtectedobject.class, umsper.getDninfo());
		String comment = umsper.getComments();
		if (StringUtils.contains(comment, "*")) {
			umsper.setDninfo(upo.getOu() + ".*");
		} else if (StringUtils.contains(comment, "-")) {
			umsper.setDninfo(upo.getOu() + ".-");
		} else {
			umsper.setDninfo(upo.getOu());
		}
		return umsper;
	}

	/**
	 * 基于对象模式的查询，获得满足条件的对象
	 * 
	 * @param Object
	 *            UmsRolepermission
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
	 *            UmsRolepermission
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
		UmsRolepermission urole = (UmsRolepermission) obj;
		List list = OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(
				urole, comparisonKey, conditionPre);
		if (list != null && !list.isEmpty()) {
			List<UmsRolepermission> tmplist = new ArrayList<UmsRolepermission>();
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				UmsRolepermission umsper = (UmsRolepermission) iterator.next();
				UmsProtectedobject upo = (UmsProtectedobject) pos.fetchDao()
						.loadObject(UmsProtectedobject.class,
								umsper.getDninfo());
				String comment = umsper.getComments();
				if (StringUtils.contains(comment, "*")) {
					umsper.setDninfo(upo.getOu() + ".*");
				} else if (StringUtils.contains(comment, "-")) {
					umsper.setDninfo(upo.getOu() + ".-");
				} else {
					umsper.setDninfo(upo.getOu());
				}
				tmplist.add(umsper);
			}
			list = tmplist;
		}
		return list;
	}

	/**
	 * 基于对象模式的查询，获得所有符合条件的对象，支持分页
	 * 
	 * @param obj
	 *            UmsRolepermission
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
	 *            UmsRolepermission
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
		if (obj == null) {
			return null;
		}
		UmsRolepermission urole = (UmsRolepermission) obj;
		List list = OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(
				urole, comparisonKey, from, to, conditionPre);
		if (list != null && !list.isEmpty()) {
			List<UmsRolepermission> tmplist = new ArrayList<UmsRolepermission>();
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				UmsRolepermission umsper = (UmsRolepermission) iterator.next();
				UmsProtectedobject upo = (UmsProtectedobject) pos.fetchDao()
						.loadObject(UmsProtectedobject.class,
								umsper.getDninfo());
				String comment = umsper.getComments();
				if (StringUtils.contains(comment, "*")) {
					umsper.setDninfo(upo.getOu() + ".*");
				} else if (StringUtils.contains(comment, "-")) {
					umsper.setDninfo(upo.getOu() + ".-");
				} else {
					umsper.setDninfo(upo.getOu());
				}
				tmplist.add(umsper);
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
	 *            UmsRolepermission
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
	 *            UmsRolepermission
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
		List list = queryObjects(obj, comparisonKey, conditionPre);
		if (list != null && list.size() > 0) {
			return list.size();
		}
		return -1;
	}

	public boolean moveRoleDept(String roleid, String ouOri, String ouAim) {
		return false;
	}

}
