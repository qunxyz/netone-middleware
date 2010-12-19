package oe.midware.workflow.service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import oe.frame.bus.workflow.RuntimeMonitor;
import oe.midware.workflow.client.SoaObj;
import oe.midware.workflow.runtime.ormobj.TWfRelevantvar;
import oe.midware.workflow.runtime.ormobj.TWfRuntime;
import oe.midware.workflow.runtime.ormobj.TWfWorklist;
import oe.midware.workflow.xpdl.model.workflow.WorkflowProcess;

/**
 * ������ҵ�����ݻ�ȡ
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public interface WorkflowView extends Remote, RuntimeMonitor {

	/**
	 * ���ָ�����̵����е�������ݶ���(TWfRelevantvar)
	 * 
	 * @param processid
	 * @return
	 */
	public List listRelevantvar(String processid) throws RemoteException;

	/**
	 * ���һ����������ʵ����������ݶ���(TWfRelevantvar)
	 * 
	 * @param runtimeId
	 * @return
	 * @throws RemoteException
	 */
	public List listRelevartvarInstance(String runtimeId)
			throws RemoteException;

	/**
	 * ���һ����������ʵ����������ݶ���(TWfRelevantvar)
	 * 
	 * @param runtimeId
	 * @param dataid
	 * @return
	 * @throws RemoteException
	 */
	public TWfRelevantvar listRelevartvarInstance(String runtimeId,
			String dataid) throws RemoteException;

	/**
	 * ����������ݵ�ID���������ݶ���ʵ��
	 * 
	 * @param varcode
	 * @return
	 * @throws RemoteException
	 */
	public TWfRelevantvar loadRelevantvar(String varcode)
			throws RemoteException;

	/**
	 * ��ʾ���е�����ʵ��
	 * 
	 * @param processid
	 * @return
	 */
	public List listinstance(String processid) throws RemoteException;

	/**
	 * ��ʾ���еĻ�ڵ�
	 * 
	 * @param runtimeid
	 * @return
	 */
	public List listWorklist(String runtimeid) throws RemoteException;

	/**
	 * װ�ػ
	 * 
	 * @param workcode
	 * @return
	 */
	public TWfWorklist loadWorklist(String workcode) throws RemoteException;

	/**
	 * װ������
	 * 
	 * @param processid
	 * @return
	 */
	public WorkflowProcess loadProcess(String processid) throws RemoteException;

	/**
	 * װ������
	 * 
	 * @param processid
	 * @return
	 */
	public WorkflowProcess loadProcessUrl(String url) throws RemoteException;

	/**
	 * װ������ʵ��
	 * 
	 * @param runtimeid
	 * @return
	 */
	public TWfRuntime loadRuntime(String runtimeid) throws RemoteException;


}
