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
 * 工作流业务数据获取
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public interface WorkflowView extends Remote, RuntimeMonitor {

	/**
	 * 获得指定流程的所有的相关数据对象(TWfRelevantvar)
	 * 
	 * @param processid
	 * @return
	 */
	public List listRelevantvar(String processid) throws RemoteException;

	/**
	 * 获得一个流程运行实例的相关数据对象(TWfRelevantvar)
	 * 
	 * @param runtimeId
	 * @return
	 * @throws RemoteException
	 */
	public List listRelevartvarInstance(String runtimeId)
			throws RemoteException;

	/**
	 * 获得一个流程运行实例的相关数据对象(TWfRelevantvar)
	 * 
	 * @param runtimeId
	 * @param dataid
	 * @return
	 * @throws RemoteException
	 */
	public TWfRelevantvar listRelevartvarInstance(String runtimeId,
			String dataid) throws RemoteException;

	/**
	 * 根据相关数据的ID获得相关数据对象实例
	 * 
	 * @param varcode
	 * @return
	 * @throws RemoteException
	 */
	public TWfRelevantvar loadRelevantvar(String varcode)
			throws RemoteException;

	/**
	 * 显示所有的流程实例
	 * 
	 * @param processid
	 * @return
	 */
	public List listinstance(String processid) throws RemoteException;

	/**
	 * 显示所有的活动节点
	 * 
	 * @param runtimeid
	 * @return
	 */
	public List listWorklist(String runtimeid) throws RemoteException;

	/**
	 * 装载活动
	 * 
	 * @param workcode
	 * @return
	 */
	public TWfWorklist loadWorklist(String workcode) throws RemoteException;

	/**
	 * 装载流程
	 * 
	 * @param processid
	 * @return
	 */
	public WorkflowProcess loadProcess(String processid) throws RemoteException;

	/**
	 * 装载流程
	 * 
	 * @param processid
	 * @return
	 */
	public WorkflowProcess loadProcessUrl(String url) throws RemoteException;

	/**
	 * 装载流程实例
	 * 
	 * @param runtimeid
	 * @return
	 */
	public TWfRuntime loadRuntime(String runtimeid) throws RemoteException;


}
