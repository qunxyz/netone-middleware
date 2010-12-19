package oe.cms.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 数据库查询对象
 * 
 * @author chen.jia.xun
 * 
 */
public interface xmlq {

	/**
	 * 获得满足条件的对象的总数
	 * 
	 * @param obj
	 *            Object
	 *            可设置查询条件的对象，该对象可以是com.rjsoft.masterflow.core.runtimepools 包 与
	 *            和com.rjsoft.masterflow.core.staticpools 包中的任何对象
	 * 
	 * @return Long 记录总数
	 */
	public long  findXmlNumber(Object obj);

	/**
	 * 获得查询对象列表
	 * 
	 * @param obj
	 *            Object
	 *            可设置查询条件的对象，该对象可以是com.rjsoft.masterflow.core.runtimepools 包 与
	 *            和com.rjsoft.masterflow.core.staticpools 包中的任何对象
	 * 
	 * @return List 对象数组
	 */
	public List  findXmls(Object obj);
	
	/**
	 * 获得查询对象列表
	 * 
	 * @param obj
	 *            Object
	 *            可设置查询条件的对象，该对象可以是com.rjsoft.masterflow.core.runtimepools 包 与
	 *            和com.rjsoft.masterflow.core.staticpools 包中的任何对象
	 * 
	 * @return List 对象数组
	 */
	public List  findXmls(Object obj,Map conditionkey);


	/**
	 * 装载对象
	 * 
	 * @param objClass
	 *            Class 对象类
	 * @param key
	 *            Serializable 对象ID
	 * @return Object 对象实例
	 */
	public Object loadObject(Class objClass, Serializable key);

}
