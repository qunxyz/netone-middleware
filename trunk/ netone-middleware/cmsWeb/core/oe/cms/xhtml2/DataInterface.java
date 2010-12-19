package oe.cms.xhtml2;

/**
 * 数据入口(负责抽取获得原始的数据信息)
 * 
 * 目前主要的数据源来自两个地方 <br>
 * 1：html页面，这里的信息通过 正则表达式来获取<br>
 * 2: sql数据库，这里的信息通过sql语句来获取
 * 
 * @author Robanco (Oesee leader)
 * 
 */
public interface DataInterface {

	/**
	 * 抽取html信息
	 * 
	 * @param url:
	 *            url地址
	 * @param encode:
	 *            数据编码
	 * @return url对应的html信息
	 */
	String insect(String url, String encode);

	/**
	 * 正则表达式
	 * 
	 * @param info
	 *            字符信息
	 * @param regularRule
	 *            正则表达式
	 * @return
	 */
	String[] regular(String info, String regularRule);

	/**
	 * 正则表达式
	 * 
	 * @param info
	 *            字符信息
	 * @param regularRule
	 *            正则表达式
	 * @return
	 */
	String[] regular(String info, String regularRule, String regularRule1);

	/**
	 * 执行SQL获得数据
	 * 
	 * @param sql
	 *            sql表达式
	 * @return
	 */
	String[][] executeSql(String sql);

	/**
	 * 执行带参数的SQL获得数据
	 * 
	 * @param sql
	 *            带参数的SQL,参数为某些条件字段为?
	 *            SQL中的参数值，需要注意的是，如果参数值是字符的话需要用''包含，如果是时间的需要to_date('yyyy-mm-nn
	 *            ','');
	 * @return
	 */
	String[][] executePrepareSql(String sql, String[] paramervalue);

	/**
	 * 使用内部数据库
	 * 
	 * @param sql
	 *            任意的SQL语句,如果是非查询SQL那么返回的是是一个只有1个维度的1条记录的二维数组,里面保存着SQL的对数据库更新的内容
	 *            如果是查询SQL,那么返回结果的列数依次对应着SQL中的查询字段,行个数依次对应的查询结果
	 * @return
	 */
	String[][] useTempDb(String sql);

}
