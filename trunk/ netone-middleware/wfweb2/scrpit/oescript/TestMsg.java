package oescript;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import oe.frame.bus.workflow.WfEntry;
import oe.frame.bus.workflow.rule.MsgScriptFunction;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.Clerk;

public class TestMsg {

	static MsgScriptFunction msg = (MsgScriptFunction) WfEntry.fetchBean("msg");

	public static void main(String[] args) {

		// ��Ϣ����-����ָ����Ա
		msg.toMen("adminx", "adminx", "aaa1", "11ni",
				"PIC.PIC.CIMG2800=CIMG2800,PIC.PIC.02=02", "");
		System.out.println("ok");
		// ��Ϣ����-���͸����ţ����ŵ�����Nautalname��
		msg.toDept("adminx", "DEPT.DEPT.C.DEPT1", "aaa", "qqni",
				"PIC.PIC.CIMG2800=CIMG2800,PIC.PIC.02=02", "");
		System.out.println("ok");
		// ��Ϣ����-���͸���ɫ
		msg.toRole("adminx", "USER", "aaa", "33ni",
				"PIC.PIC.CIMG2800=CIMG2800,PIC.PIC.02=02", "");
		System.out.println("ok");
		// ��Ϣ����-���͸����飨λ��BUSSENV.BUSSENV.BUSSCODE.POJTEAM Ŀ¼�µ��б�
		msg.toGroupFirst("adminx", "BUSSENV.BUSSENV.BUSSCODE.POJTEAM.POJB", "aaa",
				"33ni", "PIC.PIC.CIMG2800=CIMG2800,PIC.PIC.02=02", "");
		System.out.println("ok");

		msg.toGroupSecond("adminx", "BUSSENV.BUSSENV.BUSSCODE.DEVTEAM.DEVB", "aaa",
				"44ni", "PIC.PIC.CIMG2800=CIMG2800,PIC.PIC.02=02", "");
		System.out.println("ok");

	}

}
