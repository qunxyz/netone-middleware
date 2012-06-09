package oe.netone.bi;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.jl.common.workflow.DbTools;

public class ConnDB {

	Connection con = null;

	public ConnDB() {
		con = DbTools.getCon();
	}

	public ResultSet doQuery(String sql) {
		try {
			return con.createStatement().executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void close() {
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
