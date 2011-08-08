package oe.frame.bus.workflow;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import oe.midware.workflow.runtime.ormobj.TWfRelevantvar;
import oe.midware.workflow.runtime.ormobj.TWfRuntime;
import oe.midware.workflow.runtime.ormobj.TWfWorklist;
import oe.midware.workflow.xpdl.model.activity.Activity;
import oe.midware.workflow.xpdl.model.workflow.WorkflowProcess;

/**
 * ��������
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com<br>
 *         support by http://www.oesee.com
 * 
 */
public interface ProcessEngine {
	/**
	 * ��������ʵ��
	 * 
	 * @param proc
	 *            ����ģ�����
	 * @return Object ����ʵ��
	 */
	public TWfRuntime newProcess(WorkflowProcess proc) throws RemoteException;

	/**
	 * ��������
	 * 
	 * @param runtime
	 *            ����ʵ������
	 * @return boolean �Ƿ��Ѿ������ɹ�
	 */
	public void runProcess(TWfRuntime runtime) throws RemoteException;

	/**
	 * �ύ�
	 * 
	 * @param currentworklist
	 *            ��ǰ�ʵ��
	 */
	public void commitActivity(TWfWorklist currentworklist)
			throws RemoteException;

	/**
	 * �ύ�
	 * 
	 * @param currentworklist
	 *            ��ǰ�ʵ��
	 * @param ѡ���Ŀ��
	 */
	public void commitActivityByManual(TWfWorklist currentworklist, Activity act)
			throws RemoteException;

	/**
	 * �ύ�
	 * 
	 * @param runtime
	 *            ����ʵ������(��Ҫ����currentPointActivity)
	 * @return boolean
	 */
	public void commitActivityByManual(TWfWorklist currentworklist, List act)
			throws RemoteException;

	/**
	 * ���������������
	 * 
	 * @param runtime
	 *            ���̶���(��Ҫ����currentrelevantvar)
	 * @return boolean
	 */
	public void updateRelevantvars(List relevantvar) throws RemoteException;

	/**
	 * �����������
	 * 
	 * @param runtime
	 *            ���̶���(��Ҫ����currentrelevantvar)
	 * @return boolean
	 */
	public void updateRelevantvar(TWfRelevantvar relevantvar)
			throws RemoteException;

	/**
	 * ����������ݣ�����־
	 * 
	 * @param runtime
	 *            ���̶���(��Ҫ����currentrelevantvar)
	 * @param extendinfo
	 *            ��־������Ϣ
	 * 
	 * @return boolean
	 */
	public void updateRelevantvarUseLog(TWfRelevantvar relevantvar,
			String extendinfo) throws RemoteException;

	/**
	 * ��������������ݣ�����־
	 * 
	 * @param runtime
	 *            ���̶���(��Ҫ����currentrelevantvar)
	 * @param extendinfo
	 *            ��־������Ϣ
	 * @return boolean
	 */
	public void updateRelevantvarsUseLog(List relevantvar, String extendinfo)
			throws RemoteException;

	// /////ҵ�����ִ��////////
	/**
	 * ҵ���߼��ж�
	 * 
	 * @param elogicExpress
	 *            String
	 * @param values
	 *            List
	 * @return boolean
	 */
	public boolean judgementRule(String elogicExpress, String runtimeid)
			throws RemoteException;

	/**
	 * ҵ���߼�����,����ҵ����������,ͳһ��String ��ʽ���
	 * 
	 * @param elogicExpress
	 *            String
	 * @param values
	 *            List
	 * @return boolean
	 */
	public String exeScript(String elogicExpress, String runtimeid)
			throws RemoteException;

	/**
	 * ��ʼ�����̣������ָ̻�����ʼ״̬
	 * 
	 * @param runtime
	 *            ��������ʵ��
	 * @return boolean
	 */
	public void initProcess(TWfRuntime runtime) throws RemoteException;

	/**
	 * ɾ�����̣����������̵�ȫ��������Ϣ
	 * 
	 * @param runtime
	 *            ��������ʵ��
	 * @return boolean
	 */
	public void dropProcess(TWfRuntime runtime) throws RemoteException;

}
