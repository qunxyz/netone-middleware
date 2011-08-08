package oe.midware.workflow.service;

import java.rmi.Remote;
import java.rmi.RemoteException;

import oe.midware.workflow.track.WorkflowRuntime;

/**
 * ���������
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public interface WorkflowDesign extends Remote, WorkflowRuntime {
	/**
	 * ����������Ϣ
	 * 
	 * @param context
	 *            ���̶������ݣ�XPDL)
	 * @param processid
	 *            ����id
	 * @param processname
	 *            ������
	 * @throws RemoteException
	 */
	void saveOpe(String context, String processid, String processname)
			throws RemoteException;

	/**
	 * ɾ�����̶����ļ�
	 * 
	 * @param processid
	 *            ���̶���ID
	 * @return
	 * @throws RemoteException
	 */
	boolean dropOpe(String processid) throws RemoteException;

	/**
	 * �������̶�����Ϣ
	 * 
	 * @param context
	 *            ���̶������ݣ�XPDL)
	 * @param processid
	 *            ����ID
	 * @param processname
	 *            ������
	 * @return
	 * @throws RemoteException
	 */
	boolean updateOpe(String context, String processid, String processname)
			throws RemoteException;

	/**
	 * չ��������Ϣ
	 * 
	 * @param processid
	 *            ����ID
	 * @return
	 * @throws RemoteException
	 */
	String viewOpe(String processid) throws RemoteException;

	/**
	 * ��������ID ������̵�������Ϣ��XPDL��
	 * 
	 * @param processid
	 *            ����ID
	 * @return
	 * @throws RemoteException
	 */
	String xpdldescription(String processid) throws RemoteException;

}
