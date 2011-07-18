package oe.midware.dyform.service;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import oe.cav.bean.logic.column.TCsColumn;
import oe.cav.bean.logic.form.TCsForm;
import oe.cav.bean.logic.tools.DyObj;

/**
 * 表单设计服务
 * 
 * @author chen.jia.xun(Robanco)<br>
 *         mail:56414429@qq.com, oesee@139.com<br>
 *         tel:15860836998<br>
 */
public interface DyFormDesignService extends Remote {
	/**
	 * 创建表头,表名和KEY自动生成,可以通过返回值获得
	 * 
	 * @return String[]={tablenane,formcode}
	 */
	String[] create(TCsForm form, String belongname) throws RemoteException;

	/**
	 * 添加字段
	 * 
	 * @param column
	 * @return
	 */
	String addColumn(TCsColumn column) throws RemoteException;

	/**
	 * 删除字段
	 * 
	 * @param column
	 * @return
	 */
	String dropColumn(TCsColumn column) throws RemoteException;

	/**
	 * 修改字段
	 * 
	 * @param column
	 * @return
	 */
	String updateColumn(TCsColumn column) throws RemoteException;

	/**
	 * 仅仅修改XML中的字段描述,中文名
	 * 
	 * @param column
	 * @return
	 * @throws RemoteException
	 */
	public String updateColumnView(TCsColumn column) throws RemoteException;

	/**
	 * 通过数据源与table创建DyObj对象,这个table是可以是统计表,由使用create(),addColumn()等方法创建出来的
	 * 的table
	 * 
	 * @param ds
	 * @param sql
	 * @return
	 */
	DyObj fromSQL(String ds, String table) throws RemoteException;

	/**
	 * 通过动态表单创建DyObj对象
	 * 
	 * @return
	 */
	DyObj fromDy(String formcode) throws RemoteException;

	/**
	 * 从DyObj对象创建出XML
	 * 
	 * @param obj
	 * @return
	 */
	String fromDyObj(DyObj obj) throws RemoteException;

	/**
	 * 根据level的值获得表单
	 * 
	 * @param level

	 * @return
	 */
	public List listDyObjFromLevel(String level)
			throws RemoteException;

	public boolean updateForm(TCsForm form) throws RemoteException;

	public boolean dropForm(TCsForm form) throws RemoteException;

	/**
	 * 基于对象模式的查询，获得所有符合条件的对象，支持分页
	 * 
	 * @param obj
	 *            Object 查询对象，可以使任意的已经和数据库中的相关表对应的POJO对象
	 * @param from
	 *            int 需要返回的对象数组的起始位置
	 * @param to
	 *            int 需要返回的对象数组的终了位置
	 * 
	 * @return List 符合条件的对象数组
	 */
	public List queryObjects(TCsForm obj, int from, int to)
			throws RemoteException;

	/**
	 * 基于对象模式的查询，获得所有符合条件的对象，支持分页和SQL条件
	 * 
	 * @param obj
	 *            Object 查询对象，可以使任意的已经和数据库中的相关表对应的POJO对象
	 * @param from
	 *            int 需要返回的对象数组的起始位置
	 * @param to
	 *            int 需要返回的对象数组的终了位置
	 * @param conditionPre
	 *            String 补充条件,符合SQL92标准不含Where的查询条件,查询字段名可参考查询对象中的get/set中的属性
	 * 
	 * @return List 符合条件的对象数组
	 */
	public List queryObjects(TCsForm obj, int from, int to, String conditionPre)
			throws RemoteException;

	/**
	 * 基于对象模式的查询，获得满足条件的对象总数
	 * 
	 * @param Object
	 *            查询对象，可以使任意的已经和数据库中的相关表对应的POJO对象
	 * 
	 * @return long 记录总数
	 */
	public long queryObjectsNumber(TCsForm obj) throws RemoteException;

	/**
	 * 基于对象模式的查询，获得满足条件的对象总数，支持SQL条件
	 * 
	 * @param Object
	 *            查询对象，可以使任意的已经和数据库中的相关表对应的POJO对象
	 * 
	 * @param conditionPre
	 *            String 补充条件,符合SQL92标准的查询条件,查询字段名参考查询对象中的get/set中的属性
	 * 
	 * @return long
	 */
	public long queryObjectsNumber(TCsForm obj, String conditionPre)
			throws RemoteException;

	/**
	 * 基于对象模式的查询，获得满足条件的对象
	 * 
	 * @param Object
	 *            查询对象，可以使任意的已经和数据库中的相关表对应的POJO对象
	 * 
	 * @return List 对象数组
	 */
	public List queryObjects(TCsForm obj) throws RemoteException;

	/**
	 * 基于对象模式的查询，获得满足条件的对象，支持SQL条件
	 * 
	 * @param Object
	 *            查询对象，可以使任意的已经和数据库中的相关表对应的POJO对象
	 * 
	 * @param conditionPre
	 *            String 补充条件,符合SQL92标准的查询条件,查询字段名参考查询对象中的get/set中的属性
	 * 
	 * @return List 对象数组
	 */
	public List queryObjects(TCsForm obj, String conditionPre)
			throws RemoteException;

	/**
	 * 获得字段的位置
	 * 
	 * @param formcode
	 * @return
	 */
	long getNextIndexValue(String formcode) throws RemoteException;

	/**
	 * 获得下一个字段
	 * 
	 * @param formcode
	 * @return
	 */
	String getNextColumn(String formcode) throws RemoteException;

	/**
	 * 移动字段的位置
	 * 
	 * @param formcode
	 * @param columnid
	 * @param participant
	 * @return
	 */
	boolean moveupColumn(String formcode, String columnid, String participant)
			throws RemoteException;

	/**
	 * 移动字段的位置
	 * 
	 * @param formcode
	 * @param columnid
	 * @param participant
	 * @return
	 */
	boolean movedownColumn(String formcode, String columnid, String participant)
			throws RemoteException;

	/**
	 * 初始化字段的位置
	 * 
	 * @param formcode
	 * @param columnid
	 * @param participant
	 * @return
	 */
	boolean resizeColumnIndexValue(String formcode, String participant)
			throws RemoteException;

	/**
	 * 解析字段类型
	 * 
	 * @param type
	 * @return
	 */
	String[] parseViewType(String type) throws RemoteException;

	/**
	 * 装载对象
	 * 
	 * @param objClass
	 *            Class 对象类
	 * @param key
	 *            Serializable 对象ID
	 * @return Object 对象实例
	 */
	public TCsColumn loadColumn(String formcode, Serializable key)
			throws RemoteException;

	/**
	 * 基于对象模式的查询，获得所有符合条件的对象，支持分页
	 * 
	 * @param obj
	 *            Object 查询对象，可以使任意的已经和数据库中的相关表对应的POJO对象
	 * @param from
	 *            int 需要返回的对象数组的起始位置
	 * @param to
	 *            int 需要返回的对象数组的终了位置
	 * 
	 * @return List 符合条件的对象数组
	 */
	public List queryObjects(TCsColumn obj, int from, int to)
			throws RemoteException;

	/**
	 * 基于对象模式的查询，获得所有符合条件的对象，支持分页和SQL条件
	 * 
	 * @param obj
	 *            Object 查询对象，可以使任意的已经和数据库中的相关表对应的POJO对象
	 * @param from
	 *            int 需要返回的对象数组的起始位置
	 * @param to
	 *            int 需要返回的对象数组的终了位置
	 * @param conditionPre
	 *            String 补充条件,符合SQL92标准不含Where的查询条件,查询字段名可参考查询对象中的get/set中的属性
	 * 
	 * @return List 符合条件的对象数组
	 */
	public List queryObjects(TCsColumn obj, int from, int to,
			String conditionPre) throws RemoteException;

	/**
	 * 基于对象模式的查询，获得满足条件的对象总数
	 * 
	 * @param Object
	 *            查询对象，可以使任意的已经和数据库中的相关表对应的POJO对象
	 * 
	 * @return long 记录总数
	 */
	public long queryObjectsNumber(TCsColumn obj) throws RemoteException;

	/**
	 * 基于对象模式的查询，获得满足条件的对象总数，支持SQL条件
	 * 
	 * @param Object
	 *            查询对象，可以使任意的已经和数据库中的相关表对应的POJO对象
	 * 
	 * @param conditionPre
	 *            String 补充条件,符合SQL92标准的查询条件,查询字段名参考查询对象中的get/set中的属性
	 * 
	 * @return long
	 */
	public long queryObjectsNumber(TCsColumn obj, String conditionPre)
			throws RemoteException;

	/**
	 * 基于对象模式的查询，获得满足条件的对象
	 * 
	 * @param Object
	 *            查询对象，可以使任意的已经和数据库中的相关表对应的POJO对象
	 * 
	 * @return List 对象数组
	 */
	public List queryObjects(TCsColumn obj) throws RemoteException;

	/**
	 * 基于对象模式的查询，获得满足条件的对象
	 * 
	 * @param url
	 *            根据表单的URL获得所有的字段
	 * 
	 * @return List 对象数组
	 */
	public List queryObjectsUrl(String url) throws RemoteException;

	/**
	 * 基于对象模式的查询，获得满足条件的对象，支持SQL条件
	 * 
	 * @param Object
	 *            查询对象，可以使任意的已经和数据库中的相关表对应的POJO对象
	 * 
	 * @param conditionPre
	 *            String 补充条件,符合SQL92标准的查询条件,查询字段名参考查询对象中的get/set中的属性
	 * 
	 * @return List 对象数组
	 */
	public List queryObjects(TCsColumn obj, String conditionPre)
			throws RemoteException;

	/**
	 * 获得表单的描述信息
	 * 
	 * @param formcode
	 * @return
	 * @throws RemoteException
	 */
	public String formDescription(String formcode) throws RemoteException;
}
