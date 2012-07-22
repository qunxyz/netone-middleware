package oe.teach.oescript;

import java.sql.Connection;
import java.sql.SQLException;
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
public class DataBaseDemo extends OeScript {
	public static void main(String[] args) {
		test_demo2();
		// test_demo1();
	}

	/**
	 * ԭʼ��JDBC
	 */
	public static void test_demo1() {
		// ������ݿ�����
		Connection con = db.con("com.mysql.jdbc.Driver",
				"jdbc:mysql://192.168.2.4:3306/netone", "root", "123");
		// ���������list����
		List list = db.queryData(con,
				"select count(*) cou from netone.t_cms_infocell");
		// �������ӡ������̨
		System.out.println(db.getl(list, 0, "cou"));
		

		
		db.close(con);
	}

	/**
	 * ��������Դ��naturalname
	 */
	public static void test_demo2() {
		// �����м��ƽ̨�е�����Դ������õ�����Դ,�������
		Connection con = db.con("DATASOURCE.DATASOURCE.PMS");
		// ���������list����
		List list = db.queryData(con,
				"select count(*) cou from mypms.daily_log");
		// �������ӡ������̨
		System.out.println(db.getl(list, 0, "cou"));
		db.close(con);
	}
	

	/**
	 * ԭʼ��JDBC
	 */
	public static void test_demo3() {
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
	 * ��������Ƶ����ݿ�ű�Ӧ��
	 */
	public static void test_demo5(){
		Connection con =db.con("DATASOURCE.DATASOURCE.HGDB");
		
		try {
			con.setAutoCommit(false);
			db.execute(con, "sql1");
			db.execute(con, "sql2");
			Connection conq =db.con("DATASOURCE.DATASOURCE.HGDB");
			db.queryData(conq, "sqlx");// ע���ѯ���Զ��ر�con��ֻ����һ���´����ٿ�
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
