package oe.security3a.seucore.accountser;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


import oe.security3a.seucore.obj.Clerk;

/**
 * 
 * @author chen.jia.xun
 * 
 */
public interface UserDao {

	String _USER_STATUS_DEL = "0";
	String _USER_STATUS_OK = "1";

	/**
	 * 登陆验证
	 * 
	 * @param code 用户隶属于
	 * @param name 用户名
	 * @param pass 密码
	 * @return clerk 其中operationinfo字段是返回验证的错误信息
	 */
	public Clerk validationUser(String code, String name, String pass);

	/**
	 * 移动人员
	 * 
	 * @param ouOri
	 *            原始部门的OU
	 * @param ouAim
	 *            目标部门的OU
	 * @return
	 */
	public boolean moveUserDept(String loginname, String ouOri, String ouAim);

	/**
	 * 注销
	 * 
	 * @param name
	 * @return
	 */
	public boolean logout(String name);
	
	
	//添加的方法
	
	public boolean checkSecrity(Object obj);
	public boolean create(Object obj);
	public boolean creates(List objs);
	public boolean drop(Object obj);
	public boolean drops(List objs);
	public void refreshObject(Object objClass, Serializable key);
	public boolean serial(Object obj);
	public boolean update(Object obj);
	public boolean updates(List objs);
	
	/**
	 * loadObject方法的参数变化，把Class换成code 
	 * @param code 隶属于
	 * @param key  关键字
	 * @return
	 */
	public Object loadObject(String code, Serializable key);
	
	
	public List queryObjects(Object obj, Map comparisonKey);
	public List queryObjects(Object obj, Map comparisonKey, String conditionPre);
	public List queryObjects(Object obj, Map comparisonKey, int from, int to) ;
	public List queryObjects(Object obj, Map comparisonKey, int from, int to,
			String conditionPre) ;
	public long queryObjectsNumber(Object obj, Map comparisonKey,
			String conditionPre) ;
	public long queryObjectsNumber(Object obj, Map comparisonKey);

}
