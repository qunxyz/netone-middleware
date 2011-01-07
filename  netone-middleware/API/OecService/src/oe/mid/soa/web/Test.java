package oe.mid.soa.web;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import oe.rmi.client.RmiEntry;
import oe.teach.rmi.sample1.Hello;

/**
 * Ӧ��Oec RmiServer ����RMI
 * 
 * @author chen.jia.xun(Robanco)<br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com <br>
 *         tel: 13328675083
 * 
 */
public class Test {

	public static void main(String[] args) throws MalformedURLException,
			RemoteException, NotBoundException {
		//testCommonRMI();
		testWebRMI();
	}

	/**
	 * ��ͨRMI����(Ĭ�ϵ�hello RMI����)
	 * 
	 */
	public static void testCommonRMI() {
		try {
			Hello hello = (Hello) RmiEntry.iv("hello");
			String xxx = hello.todo("mike");
			System.out.println(xxx);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * ����WEB��RMI����
	 * 
	 */
	public static void testWebRMI() {
		try {
			Hello hello = (Hello) RmiEntry.iv("helloweb");
			String xxx = hello.todo("mike");
			System.out.println(xxx);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
