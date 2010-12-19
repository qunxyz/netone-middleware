package oescript;

import java.sql.Connection;

import java.util.List;

import oe.frame.bus.workflow.WfEntry;
import oe.frame.bus.workflow.rule.DbScriptFunction;

public class TestDb {
	static DbScriptFunction db = (DbScriptFunction) WfEntry.fetchBean("db");

	public static void main(String[] args) {
		test_demo2();
		test_demo1();
	}

	/**
	 * 原始的JDBC
	 */
	public static void test_demo1() {
		Connection con = db.con("com.mysql.jdbc.Driver",
				"jdbc:mysql://10.192.15.84:3306/netone", "root", "123");
		List list = db.queryData(con, "select count(*) cou from t_cs_user");

		System.out.println(db.getl(list, 0, "cou"));
		db.close(con);
	}

	/**
	 * 基于数据源的naturalname
	 */
	public static void test_demo2() {
		Connection con = db.con("DATASOURCE.DATASOURCE.DB84");
		List list = db.queryData(con, "select count(*) cou from t_cs_user");

		System.out.println(db.getl(list, 0, "cou"));
		db.close(con);
	}

}
