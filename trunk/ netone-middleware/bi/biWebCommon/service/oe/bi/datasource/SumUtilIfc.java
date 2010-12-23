package oe.bi.datasource;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public interface SumUtilIfc extends Remote {
	/**
	 * ����ֶ�
	 */
	public String checkColumnlist(List list) throws RemoteException;

	/**
	 * ɾ�����¼
	 */
	public void deleteTable(String tablename) throws RemoteException;

	/**
	 * ִ��ͬ��SQL���(ȫ�����)
	 */
	public String searchToAdd(String sql, String cname, String tablename,
			String formcode) throws RemoteException;

	/**
	 * ִ��ͬ��SQL���(����ʱ��)
	 */
	public String searchToAddByTime(String sql, String cname, String tablename,
			String formcode, String limittime) throws RemoteException;

	/**
	 * ִ��ͬ��SQL���(���ݺ�N��)
	 */
	public String searchToAddByCount(String sql, String cname,
			String tablename, String formcode, String limitcount)
			throws RemoteException;

	/**
	 * ִ��ͬ��SQL���(�����ֹ�ѡ��)
	 */
	public String searchToAddByNumber(String sql, String cname,
			String tablename, String formcode, String limitnumber)
			throws RemoteException;

	/**
	 * ��ȡĳ�����ݿ���
	 */
	public String getDatabaseName(String driver, String url)
			throws RemoteException;

	/**
	 * ��ȡĳ�����ݿ����б���
	 * 
	 * @param driver
	 *            DB��URL���������������������ַ�ʽ 1:driver��ֻ��һ��Ԫ�ص�������Systemid
	 *            2:driver����4��Ԫ�ص��������driver,url,username,password
	 * @param databaseName
	 * @return
	 * @throws RemoteException
	 */
	public List getAllTableName(String[] driver, String databaseName)
			throws RemoteException;

	/**
	 * ͨ����������ֶ���Ϣ
	 * 
	 * @param driver
	 *            DB��URL���������������������ַ�ʽ 1:driver��ֻ��һ��Ԫ�ص�������Systemid
	 *            2:driver����4��Ԫ�ص��������driver,url,username,password
	 * @param tableName
	 *            ����
	 * @return
	 * @throws RemoteException
	 */
	public List getColumnNameByTableName(String[] driver, String tableName)
			throws RemoteException;

	/**
	 * ����sql��ȡsql������ֶ������ֶ�����
	 * 
	 * @param driver
	 *            DB��URL���������������������ַ�ʽ 1:driver��ֻ��һ��Ԫ�ص�������Systemid
	 *            2:driver����4��Ԫ�ص��������driver,url,username,password
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
	 * ����sql������
	 */
	public List getResultBySQL(String sql, String formcode)
			throws RemoteException;

	/**
	 * ��Excel�����ݶ�Ӧ�Ŀ������
	 * 
	 * @param sql
	 * @return
	 * @throws RemoteException
	 */
	public Map excelInSqlExe(String sql) throws RemoteException;

	/**
	 * ��Excel������д���Ӧ�Ŀ��
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
	 * ����Excel���
	 * 
	 * @param tablename
	 * @param checkedkeys
	 * @throws RemoteException
	 */
	public void excelTableCreate(String tablename, String[] checkedkeys)
			throws RemoteException;

	/**
	 * ������̬������
	 * 
	 * @param sql
	 * @param map1
	 * @return
	 * @throws RuntimeException
	 */
	public List exportDyData(String sql, Map map1) throws RemoteException;
}
