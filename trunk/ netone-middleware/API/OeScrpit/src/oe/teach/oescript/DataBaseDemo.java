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
		
		
		// ���������list����
		List data=new ArrayList();
		data.add("id1");
		data.add("mike");
		List list2 = db.queryData_p(con,
				"select count(*) cou from netone.t_cms_infocell where id=? and name=?",data);
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
}
