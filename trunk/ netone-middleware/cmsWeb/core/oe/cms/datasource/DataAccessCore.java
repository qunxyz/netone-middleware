package oe.cms.datasource;

import java.util.List;

/**
 * 数据出口核心操作
 * 
 * 当前需要实现一个基于SQL的驱动
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public interface DataAccessCore {
	/**
	 * 获得数据
	 * 
	 * @param sql
	 *            sql的表达式
	 * @param column
	 *            需要查询的字段
	 * @return 记录使用Map来存储，Key为column[i]，value为resultset的值
	 */
	List fetchData(String sql, String[] column);

	/**
	 * 获得数据
	 * 
	 * @param sql
	 *            sql的表达式
	 * @return 记录使用Map来存储，Key为column[i]，value为resultset的值
	 * 
	 */
	List fetchData(String sql);

	/**
	 * 获得数据，带提示
	 * 
	 * @param sql
	 *            sql的表达式
	 * @param column
	 *            需要查询的字段
	 * @param 数据源ID
	 * @return 记录使用Map来存储，Key为column[i]，value为resultset的值
	 *         其中第一个Map的值是存储相关Tip的信息，Key为 tipId,Value为tip的value
	 */
	List fetchDataWithTip(String sql, String[] column, String sourceid);

	/**
	 * 获得数据，带提示
	 * 
	 * @param sql
	 *            sql的表达式
	 * @param 数据源ID
	 * @return 记录使用Map来存储，Key为column[i]，value为resultset的值
	 *         其中第一个Map的值是存储相关Tip的信息，Key为 tipId,Value为tip的value
	 */
	List fetchDataWithTip(String sql, String sourceid);

}
