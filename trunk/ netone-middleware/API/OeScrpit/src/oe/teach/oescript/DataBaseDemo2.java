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
public class DataBaseDemo2 extends OeScript {
	public static void main(String[] args) {
		test_demo1();
		// test_demo1();
	}

	/**
	 * 原始的JDBC
	 */
	public static void test_demo1() {
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
	public static void test_demo2() {
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
}
