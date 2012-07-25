package oe.rmi.message;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * ��Ϣ����ӿ�
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public interface MessageHandle extends Remote {

	String _MESSAGE_MODE_CLOSE = "1";

	String _MESSAGE_MODE_NEW = "0";

	String _MESSAGE_MODE_UNACTIVE = "2";

	String[][] _MESSAGE = { { "0", "����Ϣ" }, { "1", "�Ѵ���" }, { "2", "δ��Ч" } };

	/**
	 * ������Ϣ
	 * 
	 * @param worklist
	 *            ��Ϣ����
	 * @return ��ϢID
	 * @throws RemoteException
	 */
	Serializable send(UmsBussformworklist worklist) throws RemoteException;

	/**
	 * ����������Ϣ
	 * 
	 * @param worklist
	 *            ��Ϣ��������
	 * @return ��ϢID����
	 * @throws RemoteException
	 */
	Serializable[] send(List<UmsBussformworklist> worklist)
			throws RemoteException;

	/**
	 * ��ø��˵���Ϣ
	 * 
	 * @param participant
	 *            ��Ϣ������
	 * @param from
	 *            �ӵ�n����ʼ
	 * @param to
	 *            �ӵ�n������
	 * @param condition
	 *            ��������Sql������
	 * @return
	 * @throws RemoteException
	 */
	List personMessage(String participant, int from, int to, String condition)
			throws RemoteException;

	/**
	 * ����������ȡ��������Ϣ
	 * 
	 * @param worklist
	 *            ��Ϣ����
	 * @param from
	 *            �ӵ�n����ʼ
	 * @param to
	 *            �ӵ�n������
	 * 
	 * @return
	 * @throws RemoteException
	 */
	List query(UmsBussformworklist worklist, int from, int to, String condition)
			throws RemoteException;

	/**
	 * ɾ��������Ϣ
	 * 
	 * @param messageid
	 *            ��ϢID����
	 * @throws RemoteException
	 */
	void deleteMessage(String[] messageid) throws RemoteException;

	/**
	 * ɾ����Ϣ
	 * 
	 * @param messageid
	 *            ��ϢID
	 * @throws RemoteException
	 */
	void deleteMessage(String messageid) throws RemoteException;

	/**
	 * װ��һ����Ϣ
	 * 
	 * @param workid
	 *            ��ϢID
	 * @return
	 * @throws RemoteException
	 */
	UmsBussformworklist load(String workid) throws RemoteException;

	/**
	 * ��ѯĳ�˵���Ϣ������
	 * 
	 * @param loginname
	 *            ��Ϣ������
	 * @param condition
	 *            ��Ϣ��������SQL������
	 * @return
	 */
	int totalNum(String loginname, String condition) throws RemoteException;

	/**
	 * ���������ַ
	 * 
	 * @param email
	 */
	public void setEmail(String email) throws RemoteException;

	/**
	 * �����ʼ�����
	 * 
	 * @param password
	 */
	public void setPassword(String password) throws RemoteException;
}
