package oe.product.fck.rmi;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;


/**
 * ����rmi������Դ
 * @author user
 *
 */
public interface IResourceMgr {
	
	/**
	 * ������Դ������һ��Ĭ�ϵ���Դ��Ĭ����FCKĿ¼�ﴴ��
	 * @return
	 * @throws NotBoundException 
	 * @throws RemoteException 
	 * @throws MalformedURLException 
	 */
	public String CreateResource() throws MalformedURLException, RemoteException, NotBoundException;
	
	
	
	/**
	 * ͨ��RMI������Դ����Դ�ĸ��������Զ���
	 * @param name	������
	 * @param naturalname	����
	 * @param inclusion		�ļ�����  0���ļ�     1���ļ��� 
	 * @param path			·��
	 * @return
	 * @throws MalformedURLException
	 * @throws RemoteException
	 * @throws NotBoundException
	 */
	public String CreateResource(String name,String naturalname,String inclusion,String path)  throws MalformedURLException, RemoteException, NotBoundException;

}
