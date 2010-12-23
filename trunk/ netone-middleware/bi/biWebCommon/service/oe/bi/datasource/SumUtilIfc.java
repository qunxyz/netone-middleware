package oe.bi.datasource;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public interface SumUtilIfc extends Remote {
	/**
	 * 检查字段
	 */
	public String checkColumnlist(List list) throws RemoteException;

	/**
	 * 删除表记录
	 */
	public void deleteTable(String tablename) throws RemoteException;

	/**
	 * 执行同步SQL语句(全部添加)
	 */
	public String searchToAdd(String sql, String cname, String tablename,
			String formcode) throws RemoteException;

	/**
	 * 执行同步SQL语句(根据时间)
	 */
	public String searchToAddByTime(String sql, String cname, String tablename,
			String formcode, String limittime) throws RemoteException;

	/**
	 * 执行同步SQL语句(根据后N条)
	 */
	public String searchToAddByCount(String sql, String cname,
			String tablename, String formcode, String limitcount)
			throws RemoteException;

	/**
	 * 执行同步SQL语句(根据手工选择)
	 */
	public String searchToAddByNumber(String sql, String cname,
			String tablename, String formcode, String limitnumber)
			throws RemoteException;

	/**
	 * 获取某个数据库名
	 */
	public String getDatabaseName(String driver, String url)
			throws RemoteException;

	/**
	 * 获取某个数据库所有表名
	 * 
	 * @param driver
	 *            DB的URL驱动，驱动的输入有两种方式 1:driver中只有一个元素的是输入Systemid
	 *            2:driver中有4个元素的输入的是driver,url,username,password
	 * @param databaseName
	 * @return
	 * @throws RemoteException
	 */
	public List getAllTableName(String[] driver, String databaseName)
			throws RemoteException;

	/**
	 * 通过表明获得字段信息
	 * 
	 * @param driver
	 *            DB的URL驱动，驱动的输入有两种方式 1:driver中只有一个元素的是输入Systemid
	 *            2:driver中有4个元素的输入的是driver,url,username,password
	 * @param tableName
	 *            表名
	 * @return
	 * @throws RemoteException
	 */
	public List getColumnNameByTableName(String[] driver, String tableName)
			throws RemoteException;

	/**
	 * 根据sql获取sql查出的字段名和字段类型
	 * 
	 * @param driver
	 *            DB的URL驱动，驱动的输入有两种方式 1:driver中只有一个元素的是输入Systemid
	 *            2:driver中有4个元素的输入的是driver,url,username,password
	 * @param SQL
	 * @param sb1
	 * @param sb2
	 * @return Map
	 *         key=column,value=List;key=name,value=Stringbuffer;key=types,name=Stringbuffer;
	 * @throws RemoteException
	 */
	public Map getColumnNameBySQL(String[] driver, String SQL)
			throws RemoteException;

	/**
	 * 根据sql查出结果
	 */
	public List getResultBySQL(String sql, String formcode)
			throws RemoteException;

	/**
	 * 读Excel的数据对应的库表数据
	 * 
	 * @param sql
	 * @return
	 * @throws RemoteException
	 */
	public Map excelInSqlExe(String sql) throws RemoteException;

	/**
	 * 将Excel的数据写入对应的库表
	 * 
	 * @param insertsql
	 * @param filename
	 * @param sheetname
	 * @param checkedkeys
	 * @return
	 */
	public boolean excelInAdddata(String insertsql, String filename,
			String sheetname, String[] checkedkeys) throws RemoteException;

	/**
	 * 创建Excel库表
	 * 
	 * @param tablename
	 * @param checkedkeys
	 * @throws RemoteException
	 */
	public void excelTableCreate(String tablename, String[] checkedkeys)
			throws RemoteException;

	/**
	 * 到出动态表单数据
	 * 
	 * @param sql
	 * @param map1
	 * @return
	 * @throws RuntimeException
	 */
	public List exportDyData(String sql, Map map1) throws RemoteException;
}
