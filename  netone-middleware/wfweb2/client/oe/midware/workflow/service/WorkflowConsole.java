package oe.midware.workflow.service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import oe.frame.bus.workflow.ProcessEngine;
import oe.midware.workflow.client.Session;

/**
 * ���������Ʒ���
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public interface WorkflowConsole extends Remote, ProcessEngine {

	/**
	 * ��������ʵ��
	 * 
	 * @param proc
	 *            ����ID
	 * @return Object ����ʵ��ID
	 */
	public String newProcess(String processsid) throws RemoteException;

	/**
	 * ��������
	 * 
	 * @param runtime
	 *            ����ʵ������
	 * @return boolean �Ƿ��Ѿ������ɹ�
	 */
	public void runProcess(String runtimeid) throws RemoteException;

	/**
	 * ��ʼ�����̣������ָ̻�����ʼ״̬
	 * 
	 * @param runtime
	 *            ��������ʵ��
	 * @return boolean
	 */
	public void initProcess(String runtimeid) throws RemoteException;

	/**
	 * ɾ�����̣����������̵�ȫ��������Ϣ
	 * 
	 * @param runtime
	 *            ��������ʵ��
	 * @return boolean
	 */
	public void dropProcess(String runtimeid) throws RemoteException;

	/**
	 * �ύ�
	 * 
	 * @param currentworklist
	 *            ��ǰ�ʵ��
	 */
	public void commitActivity(String workcode) throws RemoteException;

	/**
	 * �ύ�
	 * 
	 * @param currentworklist
	 *            ��ǰ�ʵ��
	 * @param ѡ���Ŀ��
	 */
	public void commitActivityByManual(String workcode, String actid)
			throws RemoteException;

	/**
	 * �ύ�
	 * 
	 * @param runtime
	 *            ����ʵ������(��Ҫ����currentPointActivity)
	 * @return boolean
	 */
	public void commitActivityByManual(String workcode, String[] actid)
			throws RemoteException;

	/**
	 * Ϊ���Ӧ��,��:��,����......
	 * 
	 * @param worklist
	 * @throws RemoteException
	 */
	public void worklistAppBind(String workcode, String apptype, String appvalue)
			throws RemoteException;

	/**
	 * ���ƻ��״̬
	 * 
	 * @param worklist
	 * @throws RemoteException
	 */
	public void updateWorklistStatus(String workcode, String status)
			throws RemoteException;

	/**
	 * ���ƻ����չ����
	 * 
	 * @param worklist
	 * @throws RemoteException
	 */
	public void updateWorklistExtendattribute(String workcode, String ext)
			throws RemoteException;

	/**
	 * ����������( �����̵Ĵ�������ͨ������һ����,ֱ�Ӳ��� newProcess����,���������̵��������б�����ø÷���)
	 * 
	 * @param subRuntimeid
	 *            �����̵�id
	 * @param runtimeid
	 *            ������ID
	 * @param workcode
	 * @param sync
	 * @return
	 * @throws RemoteException
	 */
	public void runSubFlow(String subRuntimeid, String fatherRuntimeid,
			String workcode, boolean sync) throws RemoteException;

	/**
	 * ����Netone��SOA�����ļ���ִ�����̽ڵ��м��ɵ��ⲿӦ��
	 * 
	 * @param eaixml
	 * @param runtimeid
	 * @return
	 * @throws RemoteException
	 */
	public String eai(String eaixml, String runtimeid, String workcode)
			throws RemoteException;

	/***************************************************************************
	 * �������ʵ�������еĻỰ
	 * 
	 * @param runtimeid
	 * @return
	 * @throws RemoteException
	 */
	public Object getSessionAttribute(String runtimeid, String name)
			throws RemoteException;

	/***************************************************************************
	 * �������ʵ�������еĻỰ
	 * 
	 * @param runtimeid
	 * @return
	 * @throws RemoteException
	 */
	public void setSessionAttribute(String runtimeid, String name, Object obj)
			throws RemoteException;

	/**
	 * ����Ự
	 * 
	 * @param runtimeid
	 * @throws RemoteException
	 */
	public void removeSession(String runtimeid) throws RemoteException;

	/**
	 * �����Ự
	 * 
	 * @param runtimeid
	 * @return
	 * @throws RemoteException
	 */
	public Session descriptSession(String runtimeid) throws RemoteException;

	/**
	 * sql����ѯ
	 * 
	 * @param sql
	 * @return ��Ӱ��Ĺ������е����ݸ���
	 * 
	 * ���������ķ����ͻ�ù���������ϸ��Ϣ
	 */
	int coreSqlhandle(String sql) throws RemoteException;

}
