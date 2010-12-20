package oe.cav.bean.logic.tools.reference;

import java.sql.Connection;
import java.sql.ResultSet;

public class GetPK {
	/**
	 * 获取主键名，用于SQL语句
	 * 
	 * @param conn
	 * @param tableName
	 * @return
	 */
	public static String getPKColumnName(Connection conn, String tableName) {
		ResultSet rs = null;
		String s = null;
		try {
			rs = conn.getMetaData().getPrimaryKeys(null, null, tableName);
			int i = 0;
			while (rs.next()) {
				if (i == 0) {
					s = rs.getString("COLUMN_NAME");
					i++;
				} else {
					s = s + "," + rs.getString("COLUMN_NAME");
					i++;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			s = null;
		}
		return s;
	}

	/**
	 * 获取主键名，将结果放入数组
	 * 
	 * @param conn
	 * @param tableName
	 * @return
	 */
	public static String[] getPK(Connection conn, String tableName) {
		ResultSet rs = null;
		String s[] = null;
		try {
			rs = conn.getMetaData().getPrimaryKeys(null, null, tableName);
			rs.last();
			s = new String[rs.getRow()];
			rs = conn.getMetaData().getPrimaryKeys(null, null, tableName);
			int i = 0;
			while (rs.next()) {
				s[i] = rs.getString("COLUMN_NAME");
				i++;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return s;
	}
}
