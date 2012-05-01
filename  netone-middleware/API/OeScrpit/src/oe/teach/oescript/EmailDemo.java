package oe.teach.oescript;

import oescript.parent.OeScript;

/**
 * 应用脚本内部对象 msg
 * 
 * @author chen.jia.xun(Robanco)<br>
 *         mail:56414429@qq.com, chenjx@fjycit.com<br>
 *         tel:13328675083<br>
 */
public class EmailDemo extends OeScript {
	public static void main(String[] args) {

		// 消息测试-发送指定人员
		msg.toMail("adminx@fjycit.com", "你好", "11ni", "", "");

		// msg.toMen("0000", "adminx", "aaa111", "aaa111", "", "");
		System.out.println("ok");

		// // 消息测试-发送给部门（部门的物理Nautalname）
		// msg.toDept("adminx", "DEPT.DEPT.C.DEPT1", "aaa", "qqni",
		// "PIC.PIC.CIMG2800=CIMG2800,PIC.PIC.02=02", "");
		// System.out.println("ok");

		// // 消息测试-发送给角色
		// msg.toRole("adminx", "USER", "aaa", "33ni",
		// "PIC.PIC.CIMG2800=CIMG2800,PIC.PIC.02=02", "");
		// System.out.println("ok");

		// // 消息测试-发送给分组（位于BUSSENV.BUSSENV.BUSSCODE.POJTEAM 目录下的列表）
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
