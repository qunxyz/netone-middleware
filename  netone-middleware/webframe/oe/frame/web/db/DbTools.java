package oe.frame.web.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbcp.BasicDataSource;

/**
 * 通用数据库连接工具
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
	 * 获得数据库连接,连接参考来自配置文件 dbconfig.properties
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

		int maxActivev = 10;
		int maxWaitv = 1000;
		int maxIdlev = 20;
		int initialSizev = 10;
		try {
			ds = new BasicDataSource();

			String driverName = messages.getString("driverName");
			String dbURL = messages.getString("dbURL");
			String userName = messages.getString("userName");
			String userPwd = messages.getString("userPwd");

			String maxActive = messages.getString("maxActive");
			String maxWait = messages.getString("maxWait");
			String maxIdle = messages.getString("maxIdle");
			String initialSize = messages.getString("initialSize");
			maxActivev = Integer.parseInt(maxActive);
			maxWaitv = Integer.parseInt(maxWait);
			maxIdlev = Integer.parseInt(maxIdle);
			initialSizev = Integer.parseInt(initialSize);
			ds.setPassword(userPwd);
			ds.setUrl(dbURL);
			ds.setDriverClassName(driverName);
			ds.setUsername(userName);
			ds.setMaxActive(maxActivev);
			ds.setMaxIdle(maxIdlev);
			ds.setMaxWait(maxWaitv);
			ds.setInitialSize(initialSizev);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * @param driver
	 * @param url
	 * @param name
	 * @param password
	 * @return
	 */
	public static Connection getOuterCon(String driver, String url,
			String name, String password) throws Exception {

		Class.forName(driver).newInstance();
		return DriverManager.getConnection(url, name, password);
	}

	/**
	 * 执行SQL语句,返回SQl的生效结果,如果值为-1表示该SQL是查询语句,如果是insert,update,delete的sql
	 * 那么将返回被影响数据库的记录个数,如果返回值为0,可能存在两种情况:<br>
	 * 1: SQl是DDL语句 <br>
	 * 2: SQL是insert,update,delete语句,但是执行结果没有修改数据库的记录信息
	 * 
	 * @param sql
	 * 
	 * 
	 */
	public static int execute(String sql) {

		Connection con = null;
		try {
			// 获得数据库连接
			con = getCon();
			// 获得Statment句柄
			Statement st = con.createStatement();
			// 在连接的目标数据库上执行SQL
			st.execute(sql);
			// 返回SQL的执行结果
			return st.getUpdateCount();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("SQL执行失败:" + e.getMessage());
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
	 * 带事务模式<br>
	 * 执行SQL语句,返回SQl的生效结果,如果值为-1表示该SQL是查询语句,如果是insert,update,delete的sql
	 * 那么将返回被影响数据库的记录个数,如果返回值为0,可能存在两种情况:<br>
	 * 1: SQl是DDL语句 <br>
	 * 2: SQL是insert,update,delete语句,但是执行结果没有修改数据库的记录信息 <br>
	 * <br>
	 * 需要注意的是如果需要使用事务,那么请先将con的autocommit设置为false
	 * 
	 * @param sql
	 * 
	 * 
	 */
	public static int executeTransaction(String sql, Connection con) {

		try {
			// 获得Statment句柄
			Statement st = con.createStatement();
			// 在连接的目标数据库上执行SQL
			st.execute(sql);
			// 返回SQL的执行结果
			return st.getUpdateCount();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("SQL执行失败:" + e.getMessage());
		}
	}

	public static int[] executeTransaction(String[] sql, Connection con) {
		Statement st = null;
		try {
			// 获得Statment句柄
			st = con.createStatement();
			// 在连接的目标数据库上执行SQL
			for (int i = 0; i < sql.length; i++) {
				st.addBatch(sql[i]);
			}
			// 返回SQL的执行结果
			return st.executeBatch();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("SQL执行失败:" + e.getMessage());
		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 启动事务
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
	 * 结束事务
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
			// 获得数据库连接
			con = getCon();
			return queryData(sql, con);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList();
	}

	/**
	 * 执行查询操作
	 * 
	 * @param sql
	 *            查询语句
	 * @return sql的执行结果集,存储模式List<Map> 其中map中以column-value的模式保存数据库的数据<br>
	 *         如果参数中的sql不是查询语句那么返回结果为null
	 */
	public static List queryData(String sql, Connection con) {
		// 保存查询的结果信息
		List list = new ArrayList();
		Statement st = null;
		ResultSet rs = null;
		try {
			// 获得Statment句柄
			st = con.createStatement();
			// 在连接的目标数据库上执行SQL
			boolean sqltype = st.execute(sql);
			if (!sqltype) {// 如果不是查询语句那么返回空
				return null;
			}
			// 获得查询结果集
			rs = st.getResultSet();
			// 获得查询结果集中的表字段信息
			ResultSetMetaData metaData = rs.getMetaData();

			while (rs.next()) {
				// 创建一个map对象,来存储一条记录信息
				Map preRecord = new HashMap();
				// 遍历结果集中的字段,将当前行的数据库记录数据,写入map对象中
				for (int i = 1; i <= metaData.getColumnCount(); i++) {
					// 获得字段名
					String columnname = metaData.getColumnLabel(i);
					// 获得字段的值
					
					// 类型为VARBINARY时
					String columnType = metaData.getColumnTypeName(i);
					//System.out.println(columnname +":"+ columnType);
					
					if( "VARBINARY".equals(columnType) )
					{
						String tempStr= rs.getString(columnname);
						preRecord.put(columnname, tempStr);
					}
					else
					{
						Object value = rs.getObject(columnname);
						// 写入一个字段的值
						preRecord.put(columnname, value);
					}
				}
				// 将一条记录存入动态数组
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
	
	public static String byte2hex(byte[] b) // 二进制转字符串
	{
	   String hs = "";
	   String stmp = "";
	   for (int n = 0; n < b.length; n++) {
	    stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
	    if (stmp.length() == 1)
	     hs = hs + "0" + stmp;
	    else
	     hs = hs + stmp;
	   }
	   return hs;
	}

	public static long countData(String sql) {
		Connection con = null;
		try {
			// 获得数据库连接
			con = getCon();
			return countData(sql, con);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	/**
	 * 执行查询操作
	 * 
	 * @param sql
	 *            查询语句
	 * @return sql的执行结果集,存储模式List<Map> 其中map中以column-value的模式保存数据库的数据<br>
	 *         如果参数中的sql不是查询语句那么返回结果为null
	 */
	public static long countData(String sql, Connection con) {
		Statement st = null;
		ResultSet rs = null;
		try {

			// 获得Statment句柄
			st = con.createStatement();
			// 在连接的目标数据库上执行SQL
			boolean sqltype = st.execute(sql);
			if (!sqltype) {// 如果不是查询语句那么返回空
				return -1;
			}
			// 获得查询结果集
			rs = st.getResultSet();
			// 获得查询结果集中的表字段信息
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
			// 获得数据库连接
			con = getCon();
			return queryData(sql, classobj, con);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList();
	}

	/**
	 * 执行查询操作
	 * 
	 * @param sql
	 *            查询语句
	 * @return sql的执行结果集,存储模式List<Map> 其中map中以column-value的模式保存数据库的数据<br>
	 *         如果参数中的sql不是查询语句那么返回结果为null
	 */
	public static List queryData(String sql, Class classobj, Connection con) {
		// 保存查询的结果信息
		List list = new ArrayList();
		Statement st = null;
		ResultSet rs = null;
		try {
			// 获得Statment句柄
			st = con.createStatement();
			// 在连接的目标数据库上执行SQL
			boolean sqltype = st.execute(sql);
			if (!sqltype) {// 如果不是查询语句那么返回空
				return null;
			}
			// 获得查询结果集
			rs = st.getResultSet();
			// 获得查询结果集中的表字段信息
			ResultSetMetaData metaData = rs.getMetaData();

			while (rs.next()) {
				// 创建一个map对象,来存储一条记录信息
				Object obj = classobj.newInstance();
				// 遍历结果集中的字段,将当前行的数据库记录数据,写入map对象中
				for (int i = 1; i <= metaData.getColumnCount(); i++) {
					// 获得字段名
					String columnname = metaData.getColumnName(i);
					// 获得字段的值
					Object value = rs.getObject(columnname);
					// 写入一个字段的值
					BeanUtils.setProperty(obj, columnname, value);

				}
				// 将一条记录存入动态数组
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

	public static String[] fetchOneData(String sql, String key) {
		List list = queryData(sql);
		List data = new ArrayList();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Map object = (Map) iterator.next();
			data.add(object.get(key));
		}
		return (String[]) data.toArray(new String[0]);

	}

	public static String[] fetchOneData(String sql, String key, Connection con) {
		List list = queryData(sql,con);
		List data = new ArrayList();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Map object = (Map) iterator.next();
			data.add(object.get(key));
		}
		return (String[]) data.toArray(new String[0]);

	}
}
