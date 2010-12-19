package oe.frame.bus.workflow.rule;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * ���ݿ�����
 * 
 * @author chen.jia.xun(Robanco)<br>
 *         mail:56414429@qq.com, chenjiaxun@oesee.com<br>
 *         tel:13328675083<br>
 */
public interface DbScriptFunction {
	/**
	 * ���JDBC����
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
	 * ���JDBC���ӣ�ͨ��Netone��Դģ����ע�������ԴNaturalname��
	 * 
	 * @param dsname
	 *            Netone��Դģ����ע���Naturalname
	 * @return
	 */
	public Connection con(String dsname);

	/**
	 * ִ��SQL
	 * 
	 * @param con
	 * @param sql
	 * @return
	 */
	public int execute(Connection con, String sql);

	/**
	 * ��ѯSQL
	 * 
	 * @param con
	 * @param sql
	 * @return
	 */
	public List queryData(Connection con, String sql);

	/**
	 * ���ָ��λ�õ�����
	 * 
	 * @param list
	 *            ͨ��ִ��queryData������õ����ݽ��
	 * @param row
	 *            �������ڵ���
	 * @param col
	 *            ���ݵ����ֶ�ID
	 * @return
	 */
	public Object get(List list, int row, String col);

	/**
	 * ���ָ��λ�õ���������
	 * 
	 * @param list
	 * @param row
	 * @param col
	 * @return
	 */
	public int getn(List list, int row, String col);

	/**
	 * ���ָ��λ�õĳ���������
	 * 
	 * @param list
	 * @param row
	 * @param col
	 * @return
	 */
	public long getl(List list, int row, String col);

	/**
	 * ���ָ��λ�õĸ���������
	 * 
	 * @param list
	 * @param row
	 * @param col
	 * @return
	 */
	public float getf(List list, int row, String col);

	/**
	 * ���ָ��λ�õ�˫��������
	 * 
	 * @param list
	 * @param row
	 * @param col
	 * @return
	 */
	public double getd(List list, int row, String col);



	public void close(Connection con);

}
