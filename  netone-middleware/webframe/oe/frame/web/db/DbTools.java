package oe.frame.web.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.sql.DataSource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbcp.BasicDataSource;

/**
 * ͨ�����ݿ����ӹ���
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public class DbTools {
	static ResourceBundle messages = null;
	private static BasicDataSource ds = null;
	static {
		try {
			messages = ResourceBundle.getBundle("jdbc", Locale.CHINESE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ������ݿ�����,���Ӳο����������ļ� dbconfig.properties
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Connection getCon() {

		try {
			return ds.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	static {
		ds = new BasicDataSource();

		String driverName = messages.getString("driverName");
		String dbURL = messages.getString("dbURL");
		String userName = messages.getString("userName");
		String userPwd = messages.getString("userPwd");

		String maxActive = messages.getString("maxActive");
		String maxWait = messages.getString("maxWait");
		String maxIdle = messages.getString("maxIdle");
		String initialSize = messages.getString("initialSize");
		int maxActivev = 10;
		int maxWaitv = 1000;
		int maxIdlev = 20;
		int initialSizev = 10;
		try {
			maxActivev = Integer.parseInt(maxActive);
			maxWaitv = Integer.parseInt(maxWait);
			maxIdlev = Integer.parseInt(maxIdle);
			initialSizev = Integer.parseInt(initialSize);
		} catch (Exception e) {
			e.printStackTrace();
		}

		ds.setPassword(userPwd);
		ds.setUrl(dbURL);
		ds.setDriverClassName(driverName);
		ds.setUsername(userName);
		ds.setMaxActive(maxActivev);
		ds.setMaxIdle(maxIdlev);
		ds.setMaxWait(maxWaitv);
		ds.setInitialSize(initialSizev);

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
			con = getCon();
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
	 * ��������
	 * 
	 * @return
	 */
	public static Connection beginTransaction() {
		Connection con;
		try {
			con = DbTools.getCon();
			con.setAutoCommit(false);
			return con;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * ��������
	 * 
	 * @param con
	 */
	public static void endTransaction(Connection con) {
		if (con != null) {
			try {
				con.commit();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static List queryData(String sql) {
		Connection con = null;
		try {
			// ������ݿ�����
			con = getCon();
			return queryData(sql, con);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList();
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
		Statement st = null;
		ResultSet rs = null;
		try {
			// ���Statment���
			st = con.createStatement();
			// �����ӵ�Ŀ�����ݿ���ִ��SQL
			boolean sqltype = st.execute(sql);
			if (!sqltype) {// ������ǲ�ѯ�����ô���ؿ�
				return null;
			}
			// ��ò�ѯ�����
			rs = st.getResultSet();
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
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (st != null) {
				try {
					st.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
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

	public static long countData(String sql) {
		Connection con = null;
		try {
			// ������ݿ�����
			con = getCon();
			return countData(sql, con);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	/**
	 * ִ�в�ѯ����
	 * 
	 * @param sql
	 *            ��ѯ���
	 * @return sql��ִ�н����,�洢ģʽList<Map> ����map����column-value��ģʽ�������ݿ������<br>
	 *         ��������е�sql���ǲ�ѯ�����ô���ؽ��Ϊnull
	 */
	public static long countData(String sql, Connection con) {
		Statement st = null;
		ResultSet rs = null;
		try {

			// ���Statment���
			st = con.createStatement();
			// �����ӵ�Ŀ�����ݿ���ִ��SQL
			boolean sqltype = st.execute(sql);
			if (!sqltype) {// ������ǲ�ѯ�����ô���ؿ�
				return -1;
			}
			// ��ò�ѯ�����
			rs = st.getResultSet();
			// ��ò�ѯ������еı��ֶ���Ϣ
			rs.next();
			return rs.getLong(1);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (st != null) {
				try {
					st.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return -1;
	}

	public static List queryData(String sql, Class classobj) {
		Connection con = null;
		try {
			// ������ݿ�����
			con = getCon();
			return queryData(sql, classobj, con);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList();
	}

	/**
	 * ִ�в�ѯ����
	 * 
	 * @param sql
	 *            ��ѯ���
	 * @return sql��ִ�н����,�洢ģʽList<Map> ����map����column-value��ģʽ�������ݿ������<br>
	 *         ��������е�sql���ǲ�ѯ�����ô���ؽ��Ϊnull
	 */
	public static List queryData(String sql, Class classobj, Connection con) {
		// �����ѯ�Ľ����Ϣ
		List list = new ArrayList();
		Statement st = null;
		ResultSet rs = null;
		try {
			// ���Statment���
			st = con.createStatement();
			// �����ӵ�Ŀ�����ݿ���ִ��SQL
			boolean sqltype = st.execute(sql);
			if (!sqltype) {// ������ǲ�ѯ�����ô���ؿ�
				return null;
			}
			// ��ò�ѯ�����
			rs = st.getResultSet();
			// ��ò�ѯ������еı��ֶ���Ϣ
			ResultSetMetaData metaData = rs.getMetaData();

			while (rs.next()) {
				// ����һ��map����,���洢һ����¼��Ϣ
				Object obj = classobj.newInstance();
				// ����������е��ֶ�,����ǰ�е����ݿ��¼����,д��map������
				for (int i = 1; i <= metaData.getColumnCount(); i++) {
					// ����ֶ���
					String columnname = metaData.getColumnName(i);
					// ����ֶε�ֵ
					Object value = rs.getObject(columnname);
					// д��һ���ֶε�ֵ
					BeanUtils.setProperty(obj, columnname, value);

				}
				// ��һ����¼���붯̬����
				list.add(obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (st != null) {
				try {
					st.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
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

	public static void freeDbConnectoin(Connection conn, Statement st,
			ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		try {
			if (st != null) {
				st.close();
				st = null;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		try {
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

	}

}
