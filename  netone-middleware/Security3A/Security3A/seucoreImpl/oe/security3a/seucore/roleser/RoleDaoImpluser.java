package oe.security3a.seucore.roleser;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.beanutils.BeanUtils;


import oe.frame.orm.OrmerEntry;
import oe.security3a.seucore.obj.db.UmsUser2role;
import oe.security3a.seucore.roleser.RoleDao;


/**
 * 角色Dao实现(关于UmsUser2role)
 * 
 * @author ni.he.qing
 * 
 */
public class RoleDaoImpluser implements RoleDao {
	
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
	 *            UmsUser2role
	 * @return boolean
	 * 
	 */
	public boolean create(Object obj) {
		if(obj==null){
			return false;
		}
		String code="";
		try {
			code = BeanUtils.getProperty(obj, "code");
		} catch (Exception e) {
			e.printStackTrace();
		} 
		String jndix = "_ACCOUNT_" + code;
		String ds = messages.getString(jndix);
		Object user2role = RoleDaoImplUserReference.ConverToUmsUser2Role(obj);
		return OrmerEntry.fetchOrmer(ds).fetchSerializer().create(user2role);
	}

	/**
	 * 批量创建对象
	 * 
	 * @param objs
	 *            List
	 * @return boolean
	 */
	public boolean creates(List objs) {
		if(objs==null){
			return false;
		}
		boolean result = false;
		List list = new ArrayList();
		for (Iterator iter = objs.iterator(); iter.hasNext();) {
			Object obj =  iter.next();
			result = this.create(obj);
		}
		return result;
	}

	/**
	 * 删除对象
	 * 
	 * @param obj
	 *            UmsUser2role
	 * @return boolean
	 */
	public boolean drop(Object obj) {
		if(obj==null){
			return false;
		}
		String code="";
		try {
			code = BeanUtils.getProperty(obj, "code");
		} catch (Exception e) {
			e.printStackTrace();
		} 
		String jndix = "_ACCOUNT_" + code;
		String ds = messages.getString(jndix);
		Object user2role = RoleDaoImplUserReference.ConverToUmsUser2Role(obj);
		return OrmerEntry.fetchOrmer(ds).fetchSerializer().drop(user2role);
	}

	/**
	 * 批量删除对象
	 * 
	 * @param objs
	 *            List
	 * @return boolean
	 */
	public boolean drops(List objs) {
		if(objs==null){
			return false;
		}
		boolean relult = false;
		List list = new ArrayList();
		for (Iterator iter = objs.iterator(); iter.hasNext();) {
			Object obj =  iter.next();
			relult = this.drop(obj);
		}
		return relult;
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
	 *            UmsUser2role
	 * @return boolean
	 * 
	 */
	public boolean update(Object obj) {
		if(obj==null){
			return false;
		}
		String code="";
		try {
			code = BeanUtils.getProperty(obj, "code");
		} catch (Exception e) {
			e.printStackTrace();
		} 
		String jndix = "_ACCOUNT_" + code;
		String ds = messages.getString(jndix);
		Object user2role = RoleDaoImplUserReference.ConverToUmsUser2Role(obj);
		return OrmerEntry.fetchOrmer(ds).fetchSerializer().update(user2role);
	}

	/**
	 * 批量更新对象
	 * 
	 * @param objs
	 *            List
	 * @return boolean
	 */
	public boolean updates(List objs) {
		if(objs==null){
			return false;
		}
		boolean result = false;
		List list = new ArrayList();
		for (Iterator iter = objs.iterator(); iter.hasNext();) {
			Object obj =  iter.next();
			result = this.update(obj);
		}
		return result;

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
		return OrmerEntry.fetchOrmer().fetchQuerister().loadObject(objClass, key);
	}

	/**
	 * 基于对象模式的查询，获得满足条件的对象
	 * 
	 * @param Object
	 *            UmsUser2role
	 *            
	 * @param comparisonKey
	 *            查询比较符号,其中key是 字段ID,value是比较符号比如 >,<
	 * 
	 * @return List 对象数组
	 */
	public List queryObjects(Object obj, Map comparisonKey) {
		if(obj==null){
			return null;
		}
		String code = "";
		try {
			code = BeanUtils.getProperty(obj, "code");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String jndix = "_ACCOUNT_" + code;
		String ds = messages.getString(jndix);
		Object user2role = RoleDaoImplUserReference.ConverToUmsUser2Role(obj);
		return OrmerEntry.fetchOrmer(ds).fetchQuerister().queryObjects(user2role, comparisonKey);
	}

	/**
	 * 基于对象模式的查询，获得满足条件的对象，支持SQL条件
	 * 
	 * @param Object
	 *            UmsUser2role
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
		if(obj==null){
			return null;
		}
		String code = "";
		try {
			code = BeanUtils.getProperty(obj, "code");
		} catch (Exception e) {
			e.printStackTrace();
		}
		String jndix = "_ACCOUNT_" + code;
		String ds = messages.getString(jndix);
		Object user2role = RoleDaoImplUserReference.ConverToUmsUser2Role(obj);
		return OrmerEntry.fetchOrmer(ds).fetchQuerister().queryObjects(user2role, comparisonKey, conditionPre);
	}

	/**
	 * 基于对象模式的查询，获得所有符合条件的对象，支持分页
	 * 
	 * @param obj
	 *            UmsUser2role
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
		if(obj==null){
			return null;
		}
		String code = "";
		try {
			code = BeanUtils.getProperty(obj, "code");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String jndix = "_ACCOUNT_" + code;
		String ds = messages.getString(jndix);
		Object user2role = RoleDaoImplUserReference.ConverToUmsUser2Role(obj);
		return OrmerEntry.fetchOrmer(ds).fetchQuerister().queryObjects(user2role, comparisonKey, from, to);
	}

	/**
	 * 基于对象模式的查询，获得所有符合条件的对象，支持分页和SQL条件
	 * 
	 * @param obj
	 *            UmsUser2role
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
		if(obj==null){
			return null;
		}
		String code="";
		try {
			code = BeanUtils.getProperty(obj, "code");
		} catch (Exception e) {
			e.printStackTrace();
		} 
		String jndix = "_ACCOUNT_" + code;
		String ds = messages.getString(jndix);
		Object user2role = RoleDaoImplUserReference.ConverToUmsUser2Role(obj);
		return OrmerEntry.fetchOrmer(ds).fetchQuerister().queryObjects(user2role, comparisonKey, from, to, conditionPre);
	}

	/**
	 * 基于对象模式的查询，获得满足条件的对象总数
	 * 
	 * @param comparisonKey
	 *            查询比较符号,其中key是 字段ID,value是比较符号比如 >,<
	 *            
	 * @param Object
	 *            UmsUser2role
	 * 
	 * @return long 记录总数
	 */
	public long queryObjectsNumber(Object obj, Map comparisonKey) {
		if(obj==null){
			return -1;
		}
		String code = "";
		try {
			code = BeanUtils.getProperty(obj, "code");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String jndix = "_ACCOUNT_" + code;
		String ds = messages.getString(jndix);
		Object user2role = RoleDaoImplUserReference.ConverToUmsUser2Role(obj);
		return OrmerEntry.fetchOrmer(ds).fetchQuerister().queryObjectsNumber(user2role, comparisonKey);
	}

	/**
	 * 基于对象模式的查询，获得满足条件的对象总数，支持SQL条件
	 * 
	 * @param Object
	 *            UmsUser2role
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
		if(obj==null){
			return -1;
		}
		String code = "";
		try {
			code = BeanUtils.getProperty(obj, "code");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String jndix = "_ACCOUNT_" + code;
		String ds = messages.getString(jndix);
		Object user2role = RoleDaoImplUserReference.ConverToUmsUser2Role(obj);
		return OrmerEntry.fetchOrmer(ds).fetchQuerister().queryObjectsNumber(user2role, comparisonKey, conditionPre);
	}

	public boolean moveRoleDept(String roleid, String ouOri, String ouAim) {
		return false;
	}

}
