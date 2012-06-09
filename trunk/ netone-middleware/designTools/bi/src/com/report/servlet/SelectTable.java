package  com.report.servlet;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import oe.frame.web.db.DbTools;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;


public class SelectTable {
	public static ArrayList GetTableName(String urlString, String username,
			String pwd) {
		urlString = urlString.trim();
		username = username.trim();
		pwd = pwd.trim();
		ArrayList list = new ArrayList();
		String dbDrive = "com.mysql.jdbc.Driver";
		String url = urlString;
		String name = username;
		String password = pwd;
		Connection conn = null;
		DatabaseMetaData md = null;
		ResultSet rs = null;
		int i = 1;
		try {
			conn =  DbTools.getOuterCon(dbDrive, url, name, pwd);
			md = (DatabaseMetaData) conn.getMetaData();
			rs = (ResultSet) md.getTables(null, null, null, null);

			while (rs.next()) {
				list.add(rs.getString("TABLE_NAME"));
			}

			rs.close();
			conn.close();
		} catch (Exception e) {
		e.printStackTrace();
		}
		return list;

	}

	public static ArrayList GetTableName2(String urlString, String username,
			String pwd, String driver) {
		ArrayList list = new ArrayList();
		Connection conn = null;
		DatabaseMetaData md = null;
		ResultSet rs = null;
		int i = 1;
		urlString = urlString.trim();
		username = username.trim();
		pwd = pwd.trim();
		driver = driver.trim();
		try {
			conn = DbTools.getOuterCon(driver, urlString, username, pwd);
			md = (DatabaseMetaData) conn.getMetaData();
			rs = (ResultSet) md.getTables(null, null, null, null);

			while (rs.next()) {
				list.add(rs.getString("TABLE_NAME"));
			}
			rs.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;

	}

	public static ArrayList GetTableName1() throws SQLException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList list = new ArrayList();

		try {
			conn = DbTools.getCon();
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs = stmt
					.executeQuery("select name from sysobjects where xtype='U' and name<>'dtproperties' order by name");
			while (rs.next()) {
				list.add(rs.getString("NAME"));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (Exception e) {
			}
		}
		return list;

	}

	public  ArrayList<column> GetTableField2(String urlString,
			String username, String pwd, String driver, String tablename) {
		ArrayList<column> list =new ArrayList<column>();
		Connection conn = null;
		DatabaseMetaData md = null;
		ResultSet rs = null;
		Statement stmt = null;
		urlString = urlString.trim();
		username = username.trim();
		pwd = pwd.trim();
		driver = driver.trim();
		try {
			conn =DbTools.getOuterCon(driver, urlString, username, pwd);
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			String sql =tablename;
			boolean sqltype = stmt.execute(sql);
			if (!sqltype) {// 如果不是查询语句那么返回空
				return null;
			}
			// 获得查询结果集
			rs = stmt.getResultSet();
			// 获得查询结果集中的表字段信息
			ResultSetMetaData metaData = (ResultSetMetaData) rs.getMetaData();
		
			// 遍历结果集中的字段,将当前行的数据库记录数据,写入map对象中
			for (int k = 1; k <= metaData.getColumnCount(); k++) {
				// 获得字段名
				String columnname = metaData.getColumnLabel(k);
				String columnType = metaData.getColumnTypeName(k).toLowerCase();
				column clm = new column();
				clm.setColumname(columnname);
				clm.setColumnType(columnType);
				list.add(clm);
			}
			rs.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;

	}

	public ArrayList GetTableSql(String urlString, String username,
			String pwd, String driver, String sql) {
		ArrayList list = new ArrayList();
		Connection conn = null;
		DatabaseMetaData md = null;
		ResultSet rs = null;
		Statement stmt = null;
		int i = 1;
		urlString = urlString.trim();
		username = username.trim();
		pwd = pwd.trim();
		driver = driver.trim();
		try {
			conn = DbTools.getOuterCon(driver, urlString, username, pwd);
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			boolean sqltype = stmt.execute(sql);
			if (!sqltype) {// 如果不是查询语句那么返回空
				return null;
			}
			// 获得查询结果集
			rs = stmt.getResultSet();
			// 获得查询结果集中的表字段信息
			ResultSetMetaData metaData = (ResultSetMetaData) rs.getMetaData();
			column clm = new column();
				// 遍历结果集中的字段,将当前行的数据库记录数据,写入map对象中
				for (int k = 1; k <= metaData.getColumnCount(); k++) {
					// 获得字段名
					String columnname = metaData.getColumnLabel(k);
				
					list.add(columnname);
				}
			rs.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;

	}
		public String qudongxuanz(String name) throws MalformedURLException, RemoteException, NotBoundException{
			ResourceRmi resourceRmi = null;
				
			resourceRmi=(ResourceRmi) RmiEntry.iv("resource");

			UmsProtectedobject upof=resourceRmi.loadResourceByNatural(name);
			String obl=upof.getExtendattribute();
			
			return obl;
		}
	/**
	 * @param args
	 * @throws SQLException
	 */
	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		SelectTable st=new SelectTable();
		st.GetTableField2("jdbc:mysql://localhost:3306/dyform","root","xanadu625","com.mysql.jdbc.Driver","select 	* from dyform.dy_111318764666426 limit 0, 50");
	}

}
