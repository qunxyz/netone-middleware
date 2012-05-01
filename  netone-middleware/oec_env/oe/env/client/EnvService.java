package oe.env.client;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public interface EnvService extends Remote {
	/**
	 * ��ӻ�����Ϣ
	 * 
	 * @param key
	 * @param value
	 */
	void addEnv(String key, String value) throws RemoteException;

	/**
	 * ��û�����Ϣ
	 * 
	 * @param key
	 * @return
	 */
	String fetchEnvValue(String key) throws RemoteException;

	/**
	 * ���JPP�ű�
	 * 
	 * @param id
	 * @return
	 */
	String fetchJppScript(String id) throws RemoteException;

	/**
	 * ���JPP�ű�ģ��
	 * 
	 * @param id
	 * @return
	 */
	String fetchJppScriptModel(String id) throws RemoteException;

	/**
	 * ������еĽű�
	 * 
	 * @return
	 */
	String[][] fetchAllJppScript() throws RemoteException;
	
	/**
	 * �������ϵͳ��Ϣ
	 * 
	 * @return
	 */
	String[][] fetchAllEnvSystemInfo() throws RemoteException;

}
