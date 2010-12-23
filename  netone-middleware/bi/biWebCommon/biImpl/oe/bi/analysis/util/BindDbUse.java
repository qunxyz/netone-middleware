package oe.bi.analysis.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class BindDbUse {

	static {
		try {
			Class.forName("org.hsqldb.jdbcDriver").newInstance();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Connection fetchConnection() {
		try {
			return DriverManager.getConnection("jdbc:hsqldb:file:testdb", "sa",
					"");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
