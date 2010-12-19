package oe.frame.bus.workflow.rule;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 数据库连接
 * 
 * @author chen.jia.xun(Robanco)<br>
 *         mail:56414429@qq.com, chenjiaxun@oesee.com<br>
 *         tel:13328675083<br>
 */
public interface DbScriptFunction {
	/**
	 * 获得JDBC连接
	 * 
	 * @param driver
	 * @param dburl
	 * @param username
	 * @param password
	 * @return
	 */
	public Connection con(String driver, String dburl, String username,
			String password);

	/**
	 * 获得JDBC连接（通过Netone资源模型中注册的数据源Naturalname）
	 * 
	 * @param dsname
	 *            Netone资源模型中注册的Naturalname
	 * @return
	 */
	public Connection con(String dsname);

	/**
	 * 执行SQL
	 * 
	 * @param con
	 * @param sql
	 * @return
	 */
	public int execute(Connection con, String sql);

	/**
	 * 查询SQL
	 * 
	 * @param con
	 * @param sql
	 * @return
	 */
	public List queryData(Connection con, String sql);

	/**
	 * 获得指定位置的数据
	 * 
	 * @param list
	 *            通过执行queryData方法获得的数据结果
	 * @param row
	 *            数据所在的行
	 * @param col
	 *            数据的列字段ID
	 * @return
	 */
	public Object get(List list, int row, String col);

	/**
	 * 获得指定位置的整型数据
	 * 
	 * @param list
	 * @param row
	 * @param col
	 * @return
	 */
	public int getn(List list, int row, String col);

	/**
	 * 获得指定位置的长整型数据
	 * 
	 * @param list
	 * @param row
	 * @param col
	 * @return
	 */
	public long getl(List list, int row, String col);

	/**
	 * 获得指定位置的浮点型数据
	 * 
	 * @param list
	 * @param row
	 * @param col
	 * @return
	 */
	public float getf(List list, int row, String col);

	/**
	 * 获得指定位置的双精度数据
	 * 
	 * @param list
	 * @param row
	 * @param col
	 * @return
	 */
	public double getd(List list, int row, String col);



	public void close(Connection con);

}
