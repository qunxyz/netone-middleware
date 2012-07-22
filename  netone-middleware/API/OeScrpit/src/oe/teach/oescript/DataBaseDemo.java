package oe.teach.oescript;

import java.sql.Connection;
import java.sql.SQLException;
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
	

	/**
	 * 原始的JDBC
	 */
	public static void test_demo3() {
		// 获得数据库连接
		Connection con = db.con("DATASOURCE.DATASOURCE.DYFORM");

		
		
		// 将结果保存list对像
		List data=new ArrayList();
		data.add("JPP07c678240bdd11e0be621bd8d1e853ad");
		data.add("2012-12-12 00:00:00.000");
		db.execute_p(con,"insert into netone.message(guid,roomGuid,sendDate)values(22,?,?)",data);

		
		db.close(con);
	}

	/**
	 * 基于数据源的naturalname
	 */
	public static void test_demo4() {
		Connection con = db.con("DATASOURCE.DATASOURCE.HGDB");
		List list = new ArrayList();

		list.add(1);
		list.add(java.util.UUID.randomUUID().toString());
		list.add(7155);
		list.add(1);
		list.add(1);
		list.add("111");

		db
				.execute_p(
						con,
						"insert into t_item (FItemClassID,FNumber,FParentID,FDetail,FLevel,FName) values (?,?,?,?,?,?)",
						list);

		db.close(con);
	}
	/**
	 * 带事务控制的数据库脚本应用
	 */
	public static void test_demo5(){
		Connection con =db.con("DATASOURCE.DATASOURCE.HGDB");
		
		try {
			con.setAutoCommit(false);
			db.execute(con, "sql1");
			db.execute(con, "sql2");
			Connection conq =db.con("DATASOURCE.DATASOURCE.HGDB");
			db.queryData(conq, "sqlx");// 注意查询会自动关闭con，只用用一个下次用再开
			db.execute(con, "sql3");
			con.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
