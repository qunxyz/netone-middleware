/**
 * TestMail.java	2007/1/5
 * 
 */
package oe.rmi.message.mail;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.List;

import oe.rmi.client.RmiEntry;
import oe.rmi.message.MessageHandle;
import oe.rmi.message.UmsBussformworklist;

/**
 * @author HTB
 * 
 */
public class TestMail {
	private static MessageHandle mail;

	/**
	 * @return
	 * @throws RemoteException
	 * 
	 */
	public static void init() throws RemoteException {
		try {
			mail = (MessageHandle) RmiEntry.iv("msghandle");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ���Ի��ÿ����Ϣ
	 * 
	 * @throws RemoteException
	 */
	public static void testPersonMessage() throws RemoteException {
		List list = mail.personMessage("", 0, 1, "");
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			UmsBussformworklist element = (UmsBussformworklist) iter.next();
			System.out.println(element.getParticipant());
			System.out.println(element.getParticipantname());
			System.out.println(element.getSender());
			System.out.println(element.getSendername());
			System.out.println(element.getWorkid());
			System.out.println(element.getCreated().toGMTString());
			System.out.println("---------------------------------------------");
		}
	}

	public static void testTotalNum() {
		try {
			System.out.println("Message Count:" + mail.totalNum("", ""));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * ����ɾ����Ϣ
	 * 
	 * @throws RemoteException
	 */
	public static void testDeleteMessage() throws RemoteException {
		mail.deleteMessage(new String[] { "test" });
	}

	/**
	 * ���Բ�ѯ��Ϣ
	 * 
	 * @throws RemoteException
	 */
	public static void testQuery() throws RemoteException {
		List list = mail.query(null, 0, 100, "");
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			String element = (String) iter.next();
			System.out.println(element);
		}
	}

	/**
	 * ���Է�����Ϣ
	 * 
	 * @throws RemoteException
	 */
	public static void testSend() throws RemoteException {
		// private String workid;

		// private Date created;

		// private String participant;
		//
		// private String participantname;

		// private String extattr1;

		// private String extattr2;

		// private String extattr3;

		// private String extattr4;

		// private String statusinfo;

		// private String sender;
		//
		// private String sendername;

		// String workid
		// Date created �����ʼ�������
		// String participant �����ʼ��ߵĵ�ַ
		// String participantname �����ʼ��ߵĵ�ַ
		// String extattr1 �ʼ�����
		// String extattr2 �ʼ�����
		// String extattr3 ������Ϣ
		// String extattr4
		// String statusinfo
		// String sender �����ߵ�ַ
		// String sendername �����ߵ�ַ
		UmsBussformworklist worklist = new UmsBussformworklist(null,
				"wangwj@10.1.5.4", "", "", "", "", "", "");
		worklist.setExtattr1("�Ȱ����,֧�ְ���,���Բض�NEW");
		worklist.setExtattr2("�ʼ���Ϣ�塣");
		mail.send(worklist);
	}

	/**
	 * @param args
	 * @throws RemoteException
	 */
	public static void main(String[] args) throws RemoteException {
		init();
		testPersonMessage();
		testTotalNum();
		// testDeleteMessage();// ����ɾ����Ϣ
		// testQuery();// ���Բ�ѯ��Ϣ
		// for (int i = 0; i < 1; i++)
		// testSend();// ���Է�����Ϣ
		// System.out.println("ok");
	}
}
