package oe.teach.oescript;

import oescript.parent.OeScript;

/**
 * Ӧ�ýű��ڲ����� msg
 * 
 * @author chen.jia.xun(Robanco)<br>
 *         mail:56414429@qq.com, chenjx@fjycit.com<br>
 *         tel:13328675083<br>
 */
public class EmailDemo extends OeScript {
	public static void main(String[] args) {

		// ��Ϣ����-����ָ����Ա
		msg.toMail("adminx@fjycit.com", "���", "11ni", "", "");

		// msg.toMen("0000", "adminx", "aaa111", "aaa111", "", "");
		System.out.println("ok");

		// // ��Ϣ����-���͸����ţ����ŵ�����Nautalname��
		// msg.toDept("adminx", "DEPT.DEPT.C.DEPT1", "aaa", "qqni",
		// "PIC.PIC.CIMG2800=CIMG2800,PIC.PIC.02=02", "");
		// System.out.println("ok");

		// // ��Ϣ����-���͸���ɫ
		// msg.toRole("adminx", "USER", "aaa", "33ni",
		// "PIC.PIC.CIMG2800=CIMG2800,PIC.PIC.02=02", "");
		// System.out.println("ok");

		// // ��Ϣ����-���͸����飨λ��BUSSENV.BUSSENV.BUSSCODE.POJTEAM Ŀ¼�µ��б�
		// msg.toGroupFirst("adminx", "BUSSENV.BUSSENV.BUSSCODE.POJTEAM.POJB",
		// "aaa",
		// "33ni", "PIC.PIC.CIMG2800=CIMG2800,PIC.PIC.02=02", "");
		// System.out.println("ok");
		// msg.toGroupSecond("adminx", "BUSSENV.BUSSENV.BUSSCODE.DEVTEAM.DEVB",
		// "aaa",
		// "44ni", "PIC.PIC.CIMG2800=CIMG2800,PIC.PIC.02=02", "");
		System.out.println("ok");

	}
}
