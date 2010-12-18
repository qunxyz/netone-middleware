package oe.security3a.seucore.auditingser;

import java.sql.Connection;
import java.sql.SQLException;

import oe.frame.orm.OrmerEntry;

public class JDBCAppender extends org.apache.log4j.jdbc.JDBCAppender {

	protected Connection getConnection() throws SQLException {
		try {
			if (connection == null) {
				connection = OrmerEntry.fetchDS("ds_config_log.xml").getConnection();
			} else if (connection.isClosed()) {
				connection = OrmerEntry.fetchDS("ds_config_log.xml").getConnection();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}

	/**
	 * protected void execute(String sql) throws SQLException {
	 * 
	 * Connection con = null; Statement stmt = null;
	 * 
	 * try { con = getConnection();
	 * 
	 * stmt = con.createStatement(); stmt.executeUpdate(sql); } catch
	 * (SQLException e) { if (stmt != null) stmt.close(); throw e; }
	 * stmt.close(); closeConnection(con);
	 * 
	 * System.out.println("Execute: " + sql); }
	 */
}
