package oe.teach.oescript;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import oescript.parent.OeScript;

/**
 * 应用脚本内部对象 db
 * 
 * @author chen.jia.xun(Robanco)<br>
 *         mail:56414429@qq.com, chenjx@fjycit.com<br>
 *         tel:13328675083<br>
 */
public class DataBaseDemo extends OeScript {
	public static void main(String[] args) {
		test_demo2();
		// test_demo1();
	}

	/**
	 * 原始的JDBC
	 */
	public static void test_demo1() {
		// 获得数据库连接
		Connection con = db.con("com.mysql.jdbc.Driver",
				"jdbc:mysql://192.168.2.4:3306/netone", "root", "123");
		// 将结果保存list对像
		List list = db.queryData(con,
				"select count(*) cou from netone.t_cms_infocell");
		// 将结果打印到控制台
		System.out.println(db.getl(list, 0, "cou"));
		
		
		// 将结果保存list对像
		List data=new ArrayList();
		data.add("id1");
		data.add("mike");
		List list2 = db.queryData_p(con,
				"select count(*) cou from netone.t_cms_infocell where id=? and name=?",data);
		// 将结果打印到控制台
		System.out.println(db.getl(list, 0, "cou"));
		
		db.close(con);
	}

	/**
	 * 基于数据源的naturalname
	 */
	public static void test_demo2() {
		// 基于中间件平台中的数据源管理定义好的数据源,获得连接
		Connection con = db.con("DATASOURCE.DATASOURCE.PMS");
		// 将结果保存list对像
		List list = db.queryData(con,
				"select count(*) cou from mypms.daily_log");
		// 将结果打印到控制台
		System.out.println(db.getl(list, 0, "cou"));
		db.close(con);
	}
}
