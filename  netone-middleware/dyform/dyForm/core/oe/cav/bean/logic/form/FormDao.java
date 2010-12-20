package oe.cav.bean.logic.form;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface FormDao {

	String FORM_VIEWMODE_LIST = "1";// 普通列表显示
	String FORM_VIEWMODE_LISTSMALL = "2";// 列表显示简易模式
	String FORM_VIEWMODE_LISTVIEW = "3";// 列表显示仅仅察看模式
	String FORM_VIEWMODE_VIEW = "4";// 显示表单
	String FORM_VIEWMODE_CREATE = "5"; // 显示创建表单
	String FORM_VIEWMODE_LISTVIEWX = "6"; // 显示创建表单

	/**
	 * 创建表单
	 * 
	 * 表单在逻辑意义上是隶属于systemid的范畴(systemid可以是数据库名,也可以是一个xml文档)
	 * 创建一个表单意味着在systemid的范畴中,创建一个元素(并且持久化),该元素的属性等效于TCsForm对象中的属性
	 * 
	 * @param form
	 * @return
	 */
	public boolean create(TCsForm form, String belongname);

	/**
	 * 更新表单,对表单属性的修改
	 * 
	 * @param form
	 * @return
	 */
	public boolean update(TCsForm form);

	/**
	 * 删除表单
	 * 
	 * @param form
	 * @return
	 */
	public boolean drop(TCsForm form);

	/**
	 * 获得表单中的全部字段
	 * 
	 * 表单中的全部字段,本质上也就是表单中的子元素,如果表单是数据库表,那么其子元素 就是表中的字段
	 * 
	 * @param url
	 * @return TCsColumn
	 */
	public List fetchColumnListUrl(String url);

	/**
	 * 获得表单中的全部字段
	 * 
	 * 表单中的全部字段,本质上也就是表单中的子元素,如果表单是数据库表,那么其子元素 就是表中的字段
	 * 
	 * @param formcode
	 * @return TCsColumn
	 */
	public List fetchColumnList(String formcode);

	/**
	 * 获得动态数据的列表标题头信息
	 * 
	 * 
	 * 算法说明: 从Form的xml的other属性中获得[listinfo]对应的数据,如果为空那么值为1,2,3,4,5,6,7,8"
	 * 然后根据listinfo的值(对应的索引位置)去获得FileMapping对象(比如:1对应着第一个,3对应着第三个...获得过程注意要做错误处理,如果索引所对应的FileMapping对象不存在)
	 * 将能获得到的Filemapping对象中放入Map 其中key是 索引值,value为{uuid,name}
	 * 
	 * 
	 * 
	 * @param formcode
	 * @return Map key:tcsColumn.indexvalue,
	 *         value:[]{tcsColumn.columnid,tcsColumn.columnname}
	 */
	public Map fetchTitleInfos(String formcode);

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
	public List queryObjects(TCsForm obj, int from, int to);

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
	public List queryObjects(TCsForm obj, int from, int to, String conditionPre);

	/**
	 * 基于对象模式的查询，获得满足条件的对象总数
	 * 
	 * @param Object
	 *            查询对象，可以使任意的已经和数据库中的相关表对应的POJO对象
	 * 
	 * @return long 记录总数
	 */
	public long queryObjectsNumber(TCsForm obj);

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
	public long queryObjectsNumber(TCsForm obj, String conditionPre);

	/**
	 * 基于对象模式的查询，获得满足条件的对象
	 * 
	 * @param Object
	 *            查询对象，可以使任意的已经和数据库中的相关表对应的POJO对象
	 * 
	 * @return List 对象数组
	 */
	public List queryObjects(TCsForm obj);

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
	public List queryObjects(TCsForm obj, String conditionPre);

	/**
	 * 装载对象
	 * 
	 * @param key
	 *            Serializable 对象ID
	 * @return Object 对象实例
	 */
	public TCsForm loadObject(String key);

	/**
	 * 装载对象
	 * 
	 * @param key
	 *            Serializable 对象ID
	 * @return Object 对象实例
	 */
	public TCsForm loadObjectUrl(String key);

	/**
	 * 根据维度的水平值装载获得表单
	 * 
	 * @param level
	 *            水平值
	 * 
	 * @return
	 */
	public List listByLevel(String level);

	/**
	 * 获得表单的描述信息
	 * 
	 * @param formcode
	 * @return
	 * @throws RemoteException
	 */
	public String formDescription(String formcode) throws RemoteException;

}
