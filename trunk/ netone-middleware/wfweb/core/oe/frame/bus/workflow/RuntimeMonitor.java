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
 * 流程监控，移植自Runtime对象中的成员方法
 * 
 * @author chen.jia.xun(Robanco)<br>
 *         mail:56414429@qq.com, chenjiaxun@oesee.com<br>
 *         tel:13328675083<br>
 */
public interface RuntimeMonitor {
	/**
	 * 获得流程实例中的所有运行中的活动节点
	 * 
	 * @param runtimeid
	 *            流程实例ID
	 * @return
	 * @throws RemoteException
	 */
	public List fetchRunningWorklist(String runtimeid) throws RemoteException;

	/**
	 * 获得流程实例中所有运行结束的活动节点
	 * 
	 * @param runtimeid
	 *            流程实例ID
	 * @return
	 * @throws RemoteException
	 */
	public List fetchDoneWorklist(String runtimeid) throws RemoteException;

	/**
	 * 获得流程实例中所有异常的活动节点
	 * 
	 * @param runtimeid
	 *            流程实例ID
	 * @return
	 * @throws RemoteException
	 */
	public List fetchExceptionWorklist(String runtimeid) throws RemoteException;

	/**
	 * 获得流程实例中所有被撤销 的活动节点
	 * 
	 * @param runtimeid
	 *            流程实例ID
	 * @return
	 * @throws RemoteException
	 */
	public List fetchQuashWorklist(String runtimeid) throws RemoteException;

	/**
	 * 获得流程实例中所有的活动节点
	 * 
	 * @param runtimeid
	 *            流程实例ID
	 * @return
	 * @throws RemoteException
	 */
	public List fetchWorklist(String runtimeid) throws RemoteException;

	/**
	 * 获得流程实例中的所有相关数据
	 * 
	 * @param runtimeid
	 *            流程实例ID
	 * @return
	 * @throws RemoteException
	 */
	public List fetchRelevantVar(String runtimeid) throws RemoteException;

	/**
	 * 获得流程实例中指定节点的所有运行实例
	 * 
	 * @param runtimeid
	 *            流程实例ID
	 * @param activityId
	 *            活动节点ID
	 * @return
	 * @throws RemoteException
	 */
	public List fetchRunningWorklist(String runtimeid, String activityId)
			throws RemoteException;

	/**
	 * 获得流程实例中所有的等待节点（ 等待节点可能由两种情况导致，1：节点被暂停，2：该节点负责调度同步子流程，需要等待流程执行结束，方可激活）
	 * 
	 * @param runtimeid
	 *            流程实例ID
	 * @return
	 * @throws RemoteException
	 */
	public boolean haveRunningOrSubflowWaittingWorklist(String runtimeid)
			throws RemoteException;

	/**
	 * 获得流程实例中指定节点的实例对象
	 * 
	 * @param runtimeid
	 *            流程实例ID
	 * @param activityId
	 *            节点ID
	 * @return
	 * @throws RemoteException
	 */
	public List fetchDoneWorklist(String runtimeid, String activityId)
			throws RemoteException;

	/**
	 * 获得流程实例中指定节点的所有异常实例
	 * 
	 * @param runtimeid
	 *            流程实例ID
	 * @param activityId
	 *            节点ID
	 * @return
	 * @throws RemoteException
	 */
	public List fetchExceptionWorklist(String runtimeid, String activityId)
			throws RemoteException;

	/**
	 * 获得流程实例中指定节点的所有异常实例
	 * 
	 * @param runtimeid
	 *            流程实例ID
	 * @param activityId
	 *            节点ID
	 * @return
	 * @throws RemoteException
	 */
	public List fetchQuashWorklist(String runtimeid, String activityId)
			throws RemoteException;

	/**
	 * 获得流程实例中指定节点的所有实例
	 * 
	 * @param runtimeid
	 *            流程实例ID
	 * @param activityId
	 *            节点ID
	 * @return
	 * @throws RemoteException
	 */
	public List fetchWorkList(String runtimeid, String activityId)
			throws RemoteException;

	/**
	 * 获得流程实例中所有同步等待活动实例
	 * 
	 * @param runtimeid
	 *            流程实例ID
	 * @return
	 * @throws RemoteException
	 */
	public List fetchSyncWaittingWorkList(String runtimeid)
			throws RemoteException;

	/**
	 * 获得流程实例中所有的相关数据
	 * 
	 * @param runtimeid
	 *            流程实例ID
	 * @param relevantVarID
	 *            相关数据ID
	 * @return
	 * @throws RemoteException
	 */
	public TWfRelevantvar fetchRelevantVar(String runtimeid,
			String relevantVarID) throws RemoteException;

	/**
	 * 获得流程实例中的所有子流程实例
	 * 
	 * @param runtimeid
	 *            流程实例ID
	 * @return
	 * @throws RemoteException
	 */
	public List fetchSubflowByRuntimeid(String runtimeid)
			throws RemoteException;

	/**
	 * 获得活动节点实例所对应的所有子流程
	 * 
	 * @param workcode
	 * @return
	 * @throws RemoteException
	 */
	public List fetchSubflowByWorkcode(String workcode) throws RemoteException;

	/**
	 * 根据流程定义ID获得流程的明细定义对象
	 * 
	 * @param processid
	 *            流程定义ID
	 * @return
	 * @throws RemoteException
	 */
	public WorkflowProcess fetchWorkflowProcess(String processid)
			throws RemoteException;

	/**
	 * 获得指定流程实例中的所有相关数据
	 * 
	 * @param runtimeid
	 * @return
	 * @throws RemoteException
	 */
	public Map fetchRelevantvarMap(String runtimeid) throws RemoteException;

	/**
	 * 获得流程中的指定节点的定义对象
	 * 
	 * @param processid
	 *            流程ID
	 * @param activityid
	 *            活动ID
	 * @return
	 * @throws RemoteException
	 */
	public Activity fetchActivity(String processid, String activityid)
			throws RemoteException;

	/**
	 * 获得指定流程的所有实例
	 * 
	 * @param processid
	 *            流程定义ID
	 * @param extinfo
	 *            条件
	 * @return 流程实例列表
	 * @throws RemoteException
	 */
	public List listxinstance(String processid, String extinfo)
			throws RemoteException;

	/**
	 * 获得流程实例中的所有活动实例列表
	 * 
	 * @param runtimeid
	 *            流程实例ID
	 * @param extinfo
	 *            查询条件
	 * @return
	 * @throws RemoteException
	 */
	public List listxWorkListInstance(String runtimeid, String extinfo)
			throws RemoteException;

	/**
	 * 获得流程的所有相关数据实例
	 * 
	 * @param runtimeId
	 *            流程实例ID
	 * @param extinfo
	 *            相关数据查询条件信息
	 * @return
	 * @throws RemoteException
	 */
	public List listxRelevartvarInstance(String runtimeId, String extinfo)
			throws RemoteException;

	/**
	 * 获得指定流程的所有的相关数据对象(TWfRelevantvar)
	 * 
	 * @param processid
	 *            流程定义ID
	 * @return
	 */
	public List listRelevantvar(String processid) throws RemoteException;

	/**
	 * 获得一个流程运行实例的相关数据对象(TWfRelevantvar)
	 * 
	 * @param runtimeId
	 *            流程实例ID
	 * @return
	 * @throws RemoteException
	 */
	public List listRelevartvarInstance(String runtimeId)
			throws RemoteException;

	/**
	 * 获得一个流程运行实例的相关数据对象(TWfRelevantvar)
	 * 
	 * @param runtimeId
	 *            流程实例ID
	 * @param dataid
	 *            相关数据定义ID
	 * @return
	 * @throws RemoteException
	 */
	public TWfRelevantvar listRelevartvarInstance(String runtimeId,
			String dataid) throws RemoteException;

	/**
	 * 根据相关数据的ID获得相关数据对象实例
	 * 
	 * @param varcode
	 *            相关数据的实例ID
	 * @return
	 * @throws RemoteException
	 */
	public TWfRelevantvar loadRelevantvar(String varcode)
			throws RemoteException;

	/**
	 * 显示所有的流程实例
	 * 
	 * @param processid
	 *            流程定义ID
	 * @return
	 */
	public List listinstance(String processid) throws RemoteException;

	/**
	 * 显示所有的活动节点
	 * 
	 * @param runtimeid
	 *            流程实例ID
	 * @return
	 */
	public List listWorklist(String runtimeid) throws RemoteException;

	/**
	 * 装载活动
	 * 
	 * @param workcode
	 *            获得节点实例ID
	 * @return
	 */
	public TWfWorklist loadWorklist(String workcode) throws RemoteException;

	/**
	 * 装载流程
	 * 
	 * @param processid
	 *            流程定义id
	 * @return
	 */
	public WorkflowProcess loadProcess(String processid) throws RemoteException;

	/**
	 * 装载流程
	 * 
	 * @param url
	 *            流程定义文件URL
	 * @return
	 */
	public WorkflowProcess loadProcessUrl(String url) throws RemoteException;

	/**
	 * 装载流程实例
	 * 
	 * @param runtimeid
	 *            流程实例ID
	 * @return
	 */
	public TWfRuntime loadRuntime(String runtimeid) throws RemoteException;

}
