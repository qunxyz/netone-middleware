package oe.security3a.seucore.permission.tag;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.Map;

/**
 * ͨ�����ݿ����ӹ���
 * 
 * @since 1.0
 * @author chen.jia.xun(Robanco) <br>
 *         <br>
 *         mail:chenjiaxun@oesee.com <br>
 * 
 */
public class DbTools {

	/**
	 * ������ݿ�����,���Ӳο����������ļ� dbconfig.properties
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Connection fetchConnection(String driver, String url,
			String username, String password) throws Exception {
		String _DRIVER = driver;
		String _URL = url;
		String _USERNAME = username;
		String _PASSWORD = password;

		Class.forName(_DRIVER).newInstance();
		return DriverManager.getConnection(_URL, _USERNAME, _PASSWORD);
	}

	/**
	 * ִ��SQL���,����SQl����Ч���,���ֵΪ-1��ʾ��SQL�ǲ�ѯ���,�����insert,update,delete��sql
	 * ��ô�����ر�Ӱ�����ݿ�ļ�¼����,�������ֵΪ0,���ܴ����������:<br>
	 * 1: SQl��DDL��� <br>
	 * 2: SQL��insert,update,delete���,����ִ�н��û���޸����ݿ�ļ�¼��Ϣ
	 * 
	 * @param sql
	 * 
	 * 
	 */
	public static int execute(String sql, Connection con) {
		try {
			// ���Statment���
			Statement st = con.createStatement();
			// �����ӵ�Ŀ�����ݿ���ִ��SQL
			st.execute(sql);
			// ����SQL��ִ�н��
			return st.getUpdateCount();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("SQLִ��ʧ��:" + e.getMessage());
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * ������ģʽ<br>
	 * ִ��SQL���,����SQl����Ч���,���ֵΪ-1��ʾ��SQL�ǲ�ѯ���,�����insert,update,delete��sql
	 * ��ô�����ر�Ӱ�����ݿ�ļ�¼����,�������ֵΪ0,���ܴ����������:<br>
	 * 1: SQl��DDL��� <br>
	 * 2: SQL��insert,update,delete���,����ִ�н��û���޸����ݿ�ļ�¼��Ϣ <br>
	 * <br>
	 * ��Ҫע����������Ҫʹ������,��ô���Ƚ�con��autocommit����Ϊfalse
	 * 
	 * @param sql
	 * 
	 * 
	 */
	public static int executeTransaction(String sql, Connection con) {

		try {

			// ���Statment���
			Statement st = con.createStatement();
			// �����ӵ�Ŀ�����ݿ���ִ��SQL
			st.execute(sql);
			// ����SQL��ִ�н��
			return st.getUpdateCount();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("SQLִ��ʧ��:" + e.getMessage());
		}
	}

	/**
	 * ִ�в�ѯ����
	 * 
	 * @param sql
	 *            ��ѯ���
	 * @return sql��ִ�н����,�洢ģʽList<Map> ����map����column-value��ģʽ�������ݿ������<br>
	 *         ��������е�sql���ǲ�ѯ�����ô���ؽ��Ϊnull
	 */
	public static List queryData(String sql, Connection con) {
		// �����ѯ�Ľ����Ϣ
		List list = new ArrayList();

		try {

			// ���Statment���
			Statement st = con.createStatement();
			// �����ӵ�Ŀ�����ݿ���ִ��SQL
			boolean sqltype = st.execute(sql);
			if (!sqltype) {// ������ǲ�ѯ�����ô���ؿ�
				return null;
			}
			// ��ò�ѯ�����
			ResultSet rs = st.getResultSet();
			// ��ò�ѯ������еı��ֶ���Ϣ
			ResultSetMetaData metaData = rs.getMetaData();

			while (rs.next()) {
				// ����һ��map����,���洢һ����¼��Ϣ
				Map preRecord = new HashMap();
				// ����������е��ֶ�,����ǰ�е����ݿ��¼����,д��map������
				for (int i = 1; i <= metaData.getColumnCount(); i++) {
					// ����ֶ���
					String columnname = metaData.getColumnName(i);
					// ����ֶε�ֵ
					Object value = rs.getObject(columnname);
					// д��һ���ֶε�ֵ
					preRecord.put(columnname, value);
				}
				// ��һ����¼���붯̬����
				list.add(preRecord);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return list;
	}
}
