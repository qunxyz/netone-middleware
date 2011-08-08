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
 * ���̼�أ���ֲ��Runtime�����еĳ�Ա����
 * 
 * @author chen.jia.xun(Robanco)<br>
 *         mail:56414429@qq.com, chenjiaxun@oesee.com<br>
 *         tel:13328675083<br>
 */
public interface RuntimeMonitor {
	/**
	 * �������ʵ���е����������еĻ�ڵ�
	 * 
	 * @param runtimeid
	 *            ����ʵ��ID
	 * @return
	 * @throws RemoteException
	 */
	public List fetchRunningWorklist(String runtimeid) throws RemoteException;

	/**
	 * �������ʵ�����������н����Ļ�ڵ�
	 * 
	 * @param runtimeid
	 *            ����ʵ��ID
	 * @return
	 * @throws RemoteException
	 */
	public List fetchDoneWorklist(String runtimeid) throws RemoteException;

	/**
	 * �������ʵ���������쳣�Ļ�ڵ�
	 * 
	 * @param runtimeid
	 *            ����ʵ��ID
	 * @return
	 * @throws RemoteException
	 */
	public List fetchExceptionWorklist(String runtimeid) throws RemoteException;

	/**
	 * �������ʵ�������б����� �Ļ�ڵ�
	 * 
	 * @param runtimeid
	 *            ����ʵ��ID
	 * @return
	 * @throws RemoteException
	 */
	public List fetchQuashWorklist(String runtimeid) throws RemoteException;

	/**
	 * �������ʵ�������еĻ�ڵ�
	 * 
	 * @param runtimeid
	 *            ����ʵ��ID
	 * @return
	 * @throws RemoteException
	 */
	public List fetchWorklist(String runtimeid) throws RemoteException;

	/**
	 * �������ʵ���е������������
	 * 
	 * @param runtimeid
	 *            ����ʵ��ID
	 * @return
	 * @throws RemoteException
	 */
	public List fetchRelevantVar(String runtimeid) throws RemoteException;

	/**
	 * �������ʵ����ָ���ڵ����������ʵ��
	 * 
	 * @param runtimeid
	 *            ����ʵ��ID
	 * @param activityId
	 *            ��ڵ�ID
	 * @return
	 * @throws RemoteException
	 */
	public List fetchRunningWorklist(String runtimeid, String activityId)
			throws RemoteException;

	/**
	 * �������ʵ�������еĵȴ��ڵ㣨 �ȴ��ڵ����������������£�1���ڵ㱻��ͣ��2���ýڵ㸺�����ͬ�������̣���Ҫ�ȴ�����ִ�н��������ɼ��
	 * 
	 * @param runtimeid
	 *            ����ʵ��ID
	 * @return
	 * @throws RemoteException
	 */
	public boolean haveRunningOrSubflowWaittingWorklist(String runtimeid)
			throws RemoteException;

	/**
	 * �������ʵ����ָ���ڵ��ʵ������
	 * 
	 * @param runtimeid
	 *            ����ʵ��ID
	 * @param activityId
	 *            �ڵ�ID
	 * @return
	 * @throws RemoteException
	 */
	public List fetchDoneWorklist(String runtimeid, String activityId)
			throws RemoteException;

	/**
	 * �������ʵ����ָ���ڵ�������쳣ʵ��
	 * 
	 * @param runtimeid
	 *            ����ʵ��ID
	 * @param activityId
	 *            �ڵ�ID
	 * @return
	 * @throws RemoteException
	 */
	public List fetchExceptionWorklist(String runtimeid, String activityId)
			throws RemoteException;

	/**
	 * �������ʵ����ָ���ڵ�������쳣ʵ��
	 * 
	 * @param runtimeid
	 *            ����ʵ��ID
	 * @param activityId
	 *            �ڵ�ID
	 * @return
	 * @throws RemoteException
	 */
	public List fetchQuashWorklist(String runtimeid, String activityId)
			throws RemoteException;

	/**
	 * �������ʵ����ָ���ڵ������ʵ��
	 * 
	 * @param runtimeid
	 *            ����ʵ��ID
	 * @param activityId
	 *            �ڵ�ID
	 * @return
	 * @throws RemoteException
	 */
	public List fetchWorkList(String runtimeid, String activityId)
			throws RemoteException;

	/**
	 * �������ʵ��������ͬ���ȴ��ʵ��
	 * 
	 * @param runtimeid
	 *            ����ʵ��ID
	 * @return
	 * @throws RemoteException
	 */
	public List fetchSyncWaittingWorkList(String runtimeid)
			throws RemoteException;

	/**
	 * �������ʵ�������е��������
	 * 
	 * @param runtimeid
	 *            ����ʵ��ID
	 * @param relevantVarID
	 *            �������ID
	 * @return
	 * @throws RemoteException
	 */
	public TWfRelevantvar fetchRelevantVar(String runtimeid,
			String relevantVarID) throws RemoteException;

	/**
	 * �������ʵ���е�����������ʵ��
	 * 
	 * @param runtimeid
	 *            ����ʵ��ID
	 * @return
	 * @throws RemoteException
	 */
	public List fetchSubflowByRuntimeid(String runtimeid)
			throws RemoteException;

	/**
	 * ��û�ڵ�ʵ������Ӧ������������
	 * 
	 * @param workcode
	 * @return
	 * @throws RemoteException
	 */
	public List fetchSubflowByWorkcode(String workcode) throws RemoteException;

	/**
	 * �������̶���ID������̵���ϸ�������
	 * 
	 * @param processid
	 *            ���̶���ID
	 * @return
	 * @throws RemoteException
	 */
	public WorkflowProcess fetchWorkflowProcess(String processid)
			throws RemoteException;

	/**
	 * ���ָ������ʵ���е������������
	 * 
	 * @param runtimeid
	 * @return
	 * @throws RemoteException
	 */
	public Map fetchRelevantvarMap(String runtimeid) throws RemoteException;

	/**
	 * ��������е�ָ���ڵ�Ķ������
	 * 
	 * @param processid
	 *            ����ID
	 * @param activityid
	 *            �ID
	 * @return
	 * @throws RemoteException
	 */
	public Activity fetchActivity(String processid, String activityid)
			throws RemoteException;

	/**
	 * ���ָ�����̵�����ʵ��
	 * 
	 * @param processid
	 *            ���̶���ID
	 * @param extinfo
	 *            ����
	 * @return ����ʵ���б�
	 * @throws RemoteException
	 */
	public List listxinstance(String processid, String extinfo)
			throws RemoteException;

	/**
	 * �������ʵ���е����лʵ���б�
	 * 
	 * @param runtimeid
	 *            ����ʵ��ID
	 * @param extinfo
	 *            ��ѯ����
	 * @return
	 * @throws RemoteException
	 */
	public List listxWorkListInstance(String runtimeid, String extinfo)
			throws RemoteException;

	/**
	 * ������̵������������ʵ��
	 * 
	 * @param runtimeId
	 *            ����ʵ��ID
	 * @param extinfo
	 *            ������ݲ�ѯ������Ϣ
	 * @return
	 * @throws RemoteException
	 */
	public List listxRelevartvarInstance(String runtimeId, String extinfo)
			throws RemoteException;

	/**
	 * ���ָ�����̵����е�������ݶ���(TWfRelevantvar)
	 * 
	 * @param processid
	 *            ���̶���ID
	 * @return
	 */
	public List listRelevantvar(String processid) throws RemoteException;

	/**
	 * ���һ����������ʵ����������ݶ���(TWfRelevantvar)
	 * 
	 * @param runtimeId
	 *            ����ʵ��ID
	 * @return
	 * @throws RemoteException
	 */
	public List listRelevartvarInstance(String runtimeId)
			throws RemoteException;

	/**
	 * ���һ����������ʵ����������ݶ���(TWfRelevantvar)
	 * 
	 * @param runtimeId
	 *            ����ʵ��ID
	 * @param dataid
	 *            ������ݶ���ID
	 * @return
	 * @throws RemoteException
	 */
	public TWfRelevantvar listRelevartvarInstance(String runtimeId,
			String dataid) throws RemoteException;

	/**
	 * ����������ݵ�ID���������ݶ���ʵ��
	 * 
	 * @param varcode
	 *            ������ݵ�ʵ��ID
	 * @return
	 * @throws RemoteException
	 */
	public TWfRelevantvar loadRelevantvar(String varcode)
			throws RemoteException;

	/**
	 * ��ʾ���е�����ʵ��
	 * 
	 * @param processid
	 *            ���̶���ID
	 * @return
	 */
	public List listinstance(String processid) throws RemoteException;

	/**
	 * ��ʾ���еĻ�ڵ�
	 * 
	 * @param runtimeid
	 *            ����ʵ��ID
	 * @return
	 */
	public List listWorklist(String runtimeid) throws RemoteException;

	/**
	 * װ�ػ
	 * 
	 * @param workcode
	 *            ��ýڵ�ʵ��ID
	 * @return
	 */
	public TWfWorklist loadWorklist(String workcode) throws RemoteException;

	/**
	 * װ������
	 * 
	 * @param processid
	 *            ���̶���id
	 * @return
	 */
	public WorkflowProcess loadProcess(String processid) throws RemoteException;

	/**
	 * װ������
	 * 
	 * @param url
	 *            ���̶����ļ�URL
	 * @return
	 */
	public WorkflowProcess loadProcessUrl(String url) throws RemoteException;

	/**
	 * װ������ʵ��
	 * 
	 * @param runtimeid
	 *            ����ʵ��ID
	 * @return
	 */
	public TWfRuntime loadRuntime(String runtimeid) throws RemoteException;

}
