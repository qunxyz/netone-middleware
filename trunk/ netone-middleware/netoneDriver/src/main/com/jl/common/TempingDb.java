package com.jl.common;

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

import oe.frame.web.WebCache;

/**
 * �����ݿ����ӣ�ͨ����̬IP��ִ�У����ཨ��ʹ�����칹�ֲ������е����ݲ�ѯ�� <br>
 * Ŀǰ��Ӧ��ֻ֧��mysql���ݿ⣬����������123���ʻ���root
 * 
 * @author chenjx <br>
 *         mail:15860836998@139.com
 * 
 */
public class TempingDb {

	public static final String _TEMPING_DBIP_KEY = "tempingdb";

	/**
	 * ������ݿ�����,���Ӳο����������ļ� dbconfig.properties
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Connection fetchConnection() throws Exception {

		String url = (String) WebCache.getCache(_TEMPING_DBIP_KEY);
		String _DRIVER = "com.mysql.jdbc.Driver";
		String _URL = "jdbc:mysql://" + url + ":3306/iss";
		String _USERNAME = "root";
		String _PASSWORD = "123";

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
	public static int execute(String sql) {

		Connection con = null;
		try {
			// ������ݿ�����
			con = fetchConnection();
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
	 * ִ�в�ѯ����
	 * 
	 * @param sql
	 *            ��ѯ���
	 * @return sql��ִ�н����,�洢ģʽList<Map> ����map����column-value��ģʽ�������ݿ������<br>
	 *         ��������е�sql���ǲ�ѯ�����ô���ؽ��Ϊnull
	 */
	public static List queryData(String sql) {
		// �����ѯ�Ľ����Ϣ
		List list = new ArrayList();
		Connection con = null;
		try {
			// ������ݿ�����
			con = fetchConnection();
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
