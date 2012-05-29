package oe.teach.oescript;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import oescript.parent.OeScript;

/**
 * Ӧ�ýű��ڲ����� db
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
	 * ԭʼ��JDBC
	 */
	public static void test_demo1() {
		// ������ݿ�����
		Connection con = db.con("DATASOURCE.DATASOURCE.DYFORM");

		
		
		// ���������list����
		List data=new ArrayList();
		data.add("JPP07c678240bdd11e0be621bd8d1e853ad");
		data.add("2012-12-12 00:00:00.000");
		db.execute_p(con,"insert into netone.message(guid,roomGuid,sendDate)values(22,?,?)",data);

		
		db.close(con);
	}

	/**
	 * ��������Դ��naturalname
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
