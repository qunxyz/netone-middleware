package oe.cav.bean.logic.bus;

import java.io.Serializable;
import java.util.List;

import javax.servlet.http.HttpServletResponse;


public interface BussDao {

	String _HTTP_INFO_CONTENT_TYPE = "application/octet-stream; charset=GB2312";

	/**
	 * 创建数据
	 * 
	 * @param bus
	 *            该参数由具体的实现类来指定,可以是任意的类型
	 * @return
	 * 
	 * @return bus Map , key=table value= real tablenmae <br>
	 *         Key=ds value=real ds from xml ds <br>
	 *         Key=columnidlist value= each columned <br>
	 *         Key=columnvaluelist value= each column value which user input
	 *         Key=typelist value= each column value type <br>
	 *         key=primarykey value=[][3] columnid,columntype,columnvalue
	 */
	boolean create(TCsBus bus);

	/**
	 * 批量创建
	 * 
	 * @param list :
	 *            list中的元素是对象
	 * @return
	 */
	boolean creates(List list);

	/**
	 * 创更新数据
	 * 
	 * @param bus
	 *            该参数由具体的实现类来指定,可以是任意的类型
	 * @return
	 */
	boolean update(TCsBus bus);

	/**
	 * 批量更新
	 * 
	 * @param list :
	 *            list中的元素是对象
	 * @return
	 */
	boolean updates(List bus);

	/**
	 * 创更新删除
	 * 
	 * @param bus
	 *            该参数由具体的实现类来指定,可以是任意的类型
	 * @return
	 */
	boolean drop(TCsBus bus);

	/**
	 * 批量删除
	 * 
	 * @param list :
	 *            list中的元素是对象
	 * @return
	 */
	boolean drops(List list);

	/**
	 * 导出数据
	 * 
	 * @param bus
	 *            该参数用于查询的条件构件,由具体的实现类来指定,可以是任意的类型
	 * @param condition
	 *            导出条件
	 * @param type
	 *            导出类型 参考BusTextendInfo中的 _TYPE_* 常量
	 * @param res
	 * @return
	 */
	boolean export(TCsBus bus, String condition, String type,
			HttpServletResponse res);

	/**
	 * 字段的位置移动
	 * 
	 * @param formcode
	 * @param lsh
	 * @param up
	 * @param participant
	 * @return
	 */
	boolean move(String formcode, String lsh, boolean up, String participant);

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
	public List queryObjects(TCsBus obj, int from, int to);

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
	public List queryObjects(TCsBus obj, int from, int to, String conditionPre);

	/**
	 * 基于对象模式的查询，获得满足条件的对象总数
	 * 
	 * @param Object
	 *            查询对象，可以使任意的已经和数据库中的相关表对应的POJO对象
	 * 
	 * @return long 记录总数
	 */
	public long queryObjectsNumber(TCsBus obj);

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
	public long queryObjectsNumber(TCsBus obj, String conditionPre);

	/**
	 * 基于对象模式的查询，获得满足条件的对象
	 * 
	 * @param Object
	 *            查询对象，可以使任意的已经和数据库中的相关表对应的POJO对象
	 * 
	 * @return List 对象数组
	 */
	public List queryObjects(TCsBus obj);

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
	public List queryObjects(TCsBus obj, String conditionPre);

	/**
	 * 装载对象
	 * 
	 * @param objClass
	 *            Class 对象类
	 * @param key
	 *            Serializable 对象ID
	 * @return Object 对象实例
	 */
	public TCsBus loadObject(String formcode, Serializable key);

}
