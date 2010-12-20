package oe.cav.bean.logic.column;

import java.io.Serializable;
import java.util.List;

public interface ColumnDao {

	String COLUMNID_PRE = "column";

	/**
	 * 创建字段
	 * 
	 * @param column
	 * @return
	 */
	String create(TCsColumn column);

	/**
	 * 更新字段
	 * 
	 * @param column
	 * @return
	 */
	String update(TCsColumn column);

	public String updateView(TCsColumn column);

	/**
	 * 删除字段
	 * 
	 * @param column
	 * @return
	 */
	String drop(TCsColumn column);

	/**
	 * 获得字段的位置
	 * 
	 * @param formcode
	 * @return
	 */
	long getNextIndexValue(String formcode);

	/**
	 * 获得下一个字段
	 * 
	 * @param formcode
	 * @return
	 */
	String getNextColumn(String formcode);

	/**
	 * 移动字段的位置
	 * 
	 * @param formcode
	 * @param columnid
	 * @param participant
	 * @return
	 */
	boolean moveupColumn(String formcode, String columnid, String participant);

	/**
	 * 移动字段的位置
	 * 
	 * @param formcode
	 * @param columnid
	 * @param participant
	 * @return
	 */
	boolean movedownColumn(String formcode, String columnid, String participant);

	/**
	 * 初始化字段的位置
	 * 
	 * @param formcode
	 * @param columnid
	 * @param participant
	 * @return
	 */
	boolean resizeColumnIndexValue(String formcode, String participant);

	/**
	 * 解析字段类型
	 * 
	 * @param type
	 * @return
	 */
	String[] parseViewType(String type);

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
	public List queryObjects(TCsColumn obj, int from, int to);

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
			String conditionPre);

	/**
	 * 基于对象模式的查询，获得满足条件的对象总数
	 * 
	 * @param Object
	 *            查询对象，可以使任意的已经和数据库中的相关表对应的POJO对象
	 * 
	 * @return long 记录总数
	 */
	public long queryObjectsNumber(TCsColumn obj);

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
	public long queryObjectsNumber(TCsColumn obj, String conditionPre);

	/**
	 * 基于对象模式的查询，获得满足条件的对象
	 * 
	 * @param Object
	 *            查询对象，可以使任意的已经和数据库中的相关表对应的POJO对象
	 * 
	 * @return List 对象数组
	 */
	public List queryObjects(TCsColumn obj);

	/**
	 * 基于对象模式的查询，获得满足条件的对象
	 * 
	 * @param url
	 *            根据表单的URL获得所有的字段
	 * 
	 * @return List 对象数组
	 */
	public List queryObjectsUrl(String url);

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
	public List queryObjects(TCsColumn obj, String conditionPre);

	/**
	 * 装载对象
	 * 
	 * @param objClass
	 *            Class 对象类
	 * @param key
	 *            Serializable 对象ID
	 * @return Object 对象实例
	 */
	public TCsColumn loadObject(String formcode, Serializable key);

}
