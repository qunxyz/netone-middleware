package oe.product.fck.rmi;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;


/**
 * 利用rmi管理资源
 * @author user
 *
 */
public interface IResourceMgr {
	
	/**
	 * 创建资源，创建一个默认的资源，默认在FCK目录里创建
	 * @return
	 * @throws NotBoundException 
	 * @throws RemoteException 
	 * @throws MalformedURLException 
	 */
	public String CreateResource() throws MalformedURLException, RemoteException, NotBoundException;
	
	
	
	/**
	 * 通过RMI创建资源，资源的各种属性自定义
	 * @param name	中文名
	 * @param naturalname	名称
	 * @param inclusion		文件类型  0：文件     1：文件夹 
	 * @param path			路径
	 * @return
	 * @throws MalformedURLException
	 * @throws RemoteException
	 * @throws NotBoundException
	 */
	public String CreateResource(String name,String naturalname,String inclusion,String path)  throws MalformedURLException, RemoteException, NotBoundException;

}
