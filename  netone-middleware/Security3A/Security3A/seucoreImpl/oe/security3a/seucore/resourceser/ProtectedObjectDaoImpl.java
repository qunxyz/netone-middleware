package oe.security3a.seucore.resourceser;

import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import oe.frame.orm.OrmerEntry;
import oe.security3a.SeuserEntry;
import oe.security3a.seucore.obj.db.UmsApplication;
import oe.security3a.seucore.obj.db.UmsProtectedobject;
import oe.security3a.seucore.resourceser.ApplicationService;
import oe.security3a.seucore.resourceser.ProtectedObjectDao;


import org.apache.commons.lang.StringUtils;


/**
 * 保护对象Dao实现
 * 
 * @author ni.he.qing
 * 
 */
public class ProtectedObjectDaoImpl implements ProtectedObjectDao {

	private ApplicationService appservice = (ApplicationService) SeuserEntry
			.iv("applicationService");

	/**
	 * 安全检察，在对象中加入用户信息
	 * 
	 * @param obj
	 * @return
	 */
	public boolean checkSecrity(Object obj) {
		throw new RuntimeException("该方法没有实现");
	}

	private boolean checkExist(String naturalname) {
		UmsProtectedobject obj = new UmsProtectedobject();
		obj.setNaturalname(naturalname);
		List list = OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(obj,
				null);
		if (list.size() == 1) {
			return true;
		}
		if (list.size() > 1) {
			throw new RuntimeException("存储异常出现多个重复命名:" + naturalname);
		}
		return false;

	}

	/**
	 * 保存对象
	 * 
	 * @param obj
	 *            UmsProtectedobject
	 * @return boolean 算法：<br>
	 *         1）把appid装载，检查是否存在该application 2）把输入对象uproobj执行创建操作
	 *         3）尝试按输入对象uproobj的parentdir来装载UmsProtectedobject
	 *         4）若是成功，则判断输入对象的ou是否为空，若为空则用父节点的ou加本身id来构造 5）把新生成的ou修改入装载出来的对象中
	 */
	public boolean create(Object obj) {
		if (obj == null) {
			return false;
		}
		// 1 预处理下保护对象的相关属性
		UmsProtectedobject currentObj = (UmsProtectedobject) obj;
		currentObj.setNaturalname(currentObj.getNaturalname().toUpperCase()
				.trim());
		currentObj.setCreated(new Date());
		// 2 创建当保护对象的naturalname
		String parentNaturalname = "";
		String parentOu = "";

		if ("0".equals(currentObj.getParentdir())) {// 如果没有父节点的时候,直接使用app的naturalname
			parentOu = currentObj.getAppid().toString();
			UmsApplication app = (UmsApplication) appservice.fetchDao()
					.loadObject(UmsApplication.class, currentObj.getAppid());
			parentNaturalname = app.getNaturalname();
		} else {// 存在父节点的时候
			UmsProtectedobject parentObj = (UmsProtectedobject) OrmerEntry
					.fetchOrmer().fetchQuerister()
					.loadObject(UmsProtectedobject.class,
							currentObj.getParentdir());
			parentOu = parentObj.getOu();
			parentNaturalname = parentObj.getNaturalname();
		}

		// 3 修订当前对象的ou和naturalname

		currentObj.setNaturalname(parentNaturalname + "."
				+ currentObj.getNaturalname());

		if (checkExist(currentObj.getNaturalname())) {
			return false;
		}

		OrmerEntry.fetchOrmer().fetchSerializer().create(currentObj);

		currentObj.setOu(parentOu + "." + currentObj.getId());
		return OrmerEntry.fetchOrmer().fetchSerializer().update(currentObj);

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
			UmsProtectedobject uproobj = (UmsProtectedobject) iter.next();
			if (!create(uproobj)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 删除对象
	 * 
	 * @param obj
	 *            UmsProtectedobject
	 * @return boolean
	 */
	public boolean drop(Object obj) {
		if (obj == null) {
			return false;
		}
		UmsProtectedobject uproobj = (UmsProtectedobject) obj;
		UmsProtectedobject temp = new UmsProtectedobject();
		temp.setParentdir(uproobj.getId());
		if (OrmerEntry.fetchOrmer().fetchQuerister().queryObjectsNumber(temp,
				null) == 0) {
			return OrmerEntry.fetchOrmer().fetchSerializer().drop(uproobj);
		} else {
			return false;
		}
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
		return OrmerEntry.fetchOrmer().fetchSerializer().drops(objs);
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
	 *            UmsProtectedobject
	 * @return boolean
	 */
	public boolean update(Object obj) {
		if (obj == null) {
			return false;
		}
		UmsProtectedobject uproobj = (UmsProtectedobject) obj;
		return OrmerEntry.fetchOrmer().fetchSerializer().update(uproobj);
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
			UmsProtectedobject uproobj = (UmsProtectedobject) iter.next();
			if (!this.update(uproobj)) {
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
	 *            UmsProtectedobject
	 * 
	 * @param comparisonKey
	 *            查询比较符号,其中key是 字段ID,value是比较符号比如 >,<
	 * 
	 * @return List 对象数组
	 */
	public List queryObjects(Object obj, Map comparisonKey) {
		if (obj == null) {
			return null;
		}
		
		UmsProtectedobject uproobj = (UmsProtectedobject) obj;
		String ou = uproobj.getOu();
		if (ou != null) {
			String keyReal = StringUtils.substringAfterLast(ou, ".");
			UmsProtectedobject up = new UmsProtectedobject();
			up.setParentdir(keyReal);
			return OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(up,
					comparisonKey);
		}
		return OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(uproobj,
				comparisonKey);
	}

	/**
	 * 基于对象模式的查询，获得满足条件的对象，支持SQL条件
	 * 
	 * @param Object
	 *            UmsProtectedobject
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
		UmsProtectedobject uproobj = (UmsProtectedobject) obj;
		String ou = uproobj.getOu();
		if (ou != null) {
			String keyReal = StringUtils.substringAfterLast(ou, ".");
			uproobj.setParentdir(keyReal);
			return OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(
					uproobj, comparisonKey, conditionPre);
		}
		return OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(uproobj,
				comparisonKey, conditionPre);
	}

	/**
	 * 基于对象模式的查询，获得所有符合条件的对象，支持分页
	 * 
	 * @param obj
	 *            UmsProtectedobject
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
		if (obj == null) {
			return null;
		}
		UmsProtectedobject uproobj = (UmsProtectedobject) obj;
		String ou = uproobj.getOu();
		if (ou != null) {
			String keyReal = StringUtils.substringAfterLast(ou, ".");
			UmsProtectedobject up = new UmsProtectedobject();
			up.setParentdir(keyReal);
			return OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(up,
					comparisonKey, from, to);
		}
		return OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(uproobj,
				comparisonKey, from, to);
	}

	/**
	 * 基于对象模式的查询，获得所有符合条件的对象，支持分页和SQL条件
	 * 
	 * @param obj
	 *            UmsProtectedobject
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
		UmsProtectedobject uproobj = (UmsProtectedobject) obj;
		String ou = uproobj.getOu();
		if (ou != null) {
			String keyReal = StringUtils.substringAfterLast(ou, ".");
			UmsProtectedobject up = new UmsProtectedobject();
			up.setParentdir(keyReal);
			return OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(up,
					comparisonKey, from, to, conditionPre);
		}
		return OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(uproobj,
				comparisonKey, from, to, conditionPre);
	}

	/**
	 * 基于对象模式的查询，获得满足条件的对象总数
	 * 
	 * @param comparisonKey
	 *            查询比较符号,其中key是 字段ID,value是比较符号比如 >,<
	 * 
	 * @param Object
	 *            UmsProtectedobject
	 * 
	 * @return long 记录总数
	 */
	public long queryObjectsNumber(Object obj, Map comparisonKey) {
		if (obj == null) {
			return -1;
		}
		UmsProtectedobject uproobj = (UmsProtectedobject) obj;
		return OrmerEntry.fetchOrmer().fetchQuerister().queryObjectsNumber(
				uproobj, comparisonKey);
	}

	/**
	 * 基于对象模式的查询，获得满足条件的对象总数，支持SQL条件
	 * 
	 * @param Object
	 *            UmsProtectedobject
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
		if (obj == null) {
			return -1;
		}
		UmsProtectedobject uproobj = (UmsProtectedobject) obj;
		return OrmerEntry.fetchOrmer().fetchQuerister().queryObjectsNumber(
				uproobj, comparisonKey, conditionPre);
	}
	
	
	

}