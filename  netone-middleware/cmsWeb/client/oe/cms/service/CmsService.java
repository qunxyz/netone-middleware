package oe.cms.service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

public interface CmsService extends Remote {

	/**
	 * 添加页
	 * 
	 * @param modelid
	 * @param name
	 * @param naturalname
	 * @param participant
	 * @param width
	 * @param height
	 * @return 页ID ,cellid
	 */
	String addpage(String modelid, String name, String naturalname,
			String participant, String width, String height)
			throws RemoteException;

	/**
	 * 获得J++脚本
	 * 
	 * @param cellid
	 * @return
	 */
	String fetchJppScript(String cellid) throws RemoteException;

	/**
	 * 执行J++脚本(这里不支持http请求)
	 * 
	 * @param script
	 * 
	 * @return
	 */
	String executeJpp(String script) throws RemoteException;

	/**
	 * 获得JPP模版
	 * 
	 * @param script
	 * 
	 * @return
	 */
	String fetchJppTemplate(String id) throws RemoteException;

}
