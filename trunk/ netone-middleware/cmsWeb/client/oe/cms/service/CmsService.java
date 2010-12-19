package oe.cms.service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

public interface CmsService extends Remote {

	/**
	 * ���ҳ
	 * 
	 * @param modelid
	 * @param name
	 * @param naturalname
	 * @param participant
	 * @param width
	 * @param height
	 * @return ҳID ,cellid
	 */
	String addpage(String modelid, String name, String naturalname,
			String participant, String width, String height)
			throws RemoteException;

	/**
	 * ���J++�ű�
	 * 
	 * @param cellid
	 * @return
	 */
	String fetchJppScript(String cellid) throws RemoteException;

	/**
	 * ִ��J++�ű�(���ﲻ֧��http����)
	 * 
	 * @param script
	 * 
	 * @return
	 */
	String executeJpp(String script) throws RemoteException;

	/**
	 * ���JPPģ��
	 * 
	 * @param script
	 * 
	 * @return
	 */
	String fetchJppTemplate(String id) throws RemoteException;

}
