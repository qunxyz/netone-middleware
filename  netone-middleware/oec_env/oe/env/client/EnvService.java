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
	 * 添加环境信息
	 * 
	 * @param key
	 * @param value
	 */
	void addEnv(String key, String value) throws RemoteException;

	/**
	 * 获得环境信息
	 * 
	 * @param key
	 * @return
	 */
	String fetchEnvValue(String key) throws RemoteException;

	/**
	 * 获得JPP脚本
	 * 
	 * @param id
	 * @return
	 */
	String fetchJppScript(String id) throws RemoteException;

	/**
	 * 获得JPP脚本模板
	 * 
	 * @param id
	 * @return
	 */
	String fetchJppScriptModel(String id) throws RemoteException;

	/**
	 * 获得所有的脚本
	 * 
	 * @return
	 */
	String[][] fetchAllJppScript() throws RemoteException;
	
	/**
	 * 获得所有系统信息
	 * 
	 * @return
	 */
	String[][] fetchAllEnvSystemInfo() throws RemoteException;

}
