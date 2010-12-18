package oe.security3a.client.rmi;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import oe.security3a.seucore.obj.db.UmsApplication;


/**
 * 域管理服务,域是资源的物理分类,所有的资源都隶属于不同的域中
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail: 56414429@qq.com, chenjiaxun@oesee.com<br>
 *         support by: http://www.oesee.com
 * 
 */
public interface ApplicationRmi extends Remote {

	/**
	 * 创建资源域
	 * 
	 * @param obj
	 *            Object
	 * @return boolean
	 */
	public String create(UmsApplication obj) throws RemoteException;

	/**
	 * 删除资源域
	 * 
	 * @param obj
	 *            Object
	 * @return boolean
	 */
	public boolean drop(Long id) throws RemoteException;

	/**
	 * 更新资源域
	 * 
	 * @param obj
	 *            Object
	 * @return boolean
	 */
	public boolean update(UmsApplication obj) throws RemoteException;

	/**
	 * 批量删除资源域
	 * 
	 * @param objs
	 *            List
	 * @return boolean
	 */
	public boolean drops(Long[] ids) throws RemoteException;

	/**
	 * 批量更新资源域
	 * 
	 * @param objs
	 *            List
	 * @return boolean
	 */
	public boolean updates(List objs) throws RemoteException;

	/**
	 * 查询资源域, 基于对象模式的查询，获得所有符合条件的对象，支持分页
	 * 
	 * @param obj
	 *            Object 查询对象，可以使任意的已经和数据库中的相关表对应的POJO对象
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
	public List queryObjects(UmsApplication obj, Map comparisonKey, int from,
			int to) throws RemoteException;

	/**
	 * 资源域,基于对象模式的查询，获得所有符合条件的对象，支持分页和SQL条件
	 * 
	 * @param obj
	 *            Object 查询对象，可以使任意的已经和数据库中的相关表对应的POJO对象
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
	public List queryObjects(UmsApplication obj, Map comparisonKey, int from,
			int to, String conditionPre) throws RemoteException;

	/**
	 * 资源域,基于对象模式的查询，获得满足条件的对象总数
	 * 
	 * @param comparisonKey
	 *            查询比较符号,其中key是 字段ID,value是比较符号比如 >,<
	 * 
	 * @param Object
	 *            查询对象，可以使任意的已经和数据库中的相关表对应的POJO对象
	 * 
	 * @return long 记录总数
	 */
	public long queryObjectsNumber(UmsApplication obj, Map comparisonKey)
			throws RemoteException;

	/**
	 * 基于对象模式的查询，获得满足条件的对象总数，支持SQL条件
	 * 
	 * @param Object
	 *            查询对象，可以使任意的已经和数据库中的相关表对应的POJO对象
	 * 
	 * @param comparisonKey
	 *            查询比较符号,其中key是 字段ID,value是比较符号比如 >,<
	 * 
	 * @param conditionPre
	 *            String 补充条件,符合SQL92标准的查询条件,查询字段名参考查询对象中的get/set中的属性
	 * 
	 * @return long
	 */
	public long queryObjectsNumber(UmsApplication obj, Map comparisonKey,
			String conditionPre) throws RemoteException;

	/**
	 * 资源域,基于对象模式的查询，获得满足条件的对象
	 * 
	 * @param Object
	 *            查询对象，可以使任意的已经和数据库中的相关表对应的POJO对象
	 * 
	 * @param comparisonKey
	 *            查询比较符号,其中key是 字段ID,value是比较符号比如 >,<
	 * 
	 * @return List 对象数组
	 */
	public List queryObjects(UmsApplication obj, Map comparisonKey)
			throws RemoteException;

	/**
	 * 资源域,基于对象模式的查询，获得满足条件的对象，支持SQL条件
	 * 
	 * @param Object
	 *            查询对象，可以使任意的已经和数据库中的相关表对应的POJO对象
	 * 
	 * @param comparisonKey
	 *            查询比较符号,其中key是 字段ID,value是比较符号比如 >,<
	 * 
	 * @param conditionPre
	 *            String 补充条件,符合SQL92标准的查询条件,查询字段名参考查询对象中的get/set中的属性
	 * 
	 * @return List 对象数组
	 */
	public List queryObjects(UmsApplication obj, Map comparisonKey,
			String conditionPre) throws RemoteException;

	/**
	 * 资源域,装载对象
	 * 
	 * @param objClass
	 *            Class 对象类
	 * 
	 * 
	 * @param key
	 *            Serializable 对象ID
	 * @return Object 对象实例
	 */
	public UmsApplication loadObject(Serializable key) throws RemoteException;
}
