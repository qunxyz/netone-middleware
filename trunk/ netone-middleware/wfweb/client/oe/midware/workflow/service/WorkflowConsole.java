package oe.midware.workflow.service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import oe.frame.bus.workflow.ProcessEngine;
import oe.midware.workflow.client.Session;

/**
 * 工作流控制服务
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public interface WorkflowConsole extends Remote, ProcessEngine {

	/**
	 * 创建流程实例
	 * 
	 * @param proc
	 *            流程ID
	 * @return Object 流程实例ID
	 */
	public String newProcess(String processsid) throws RemoteException;

	/**
	 * 启动流程
	 * 
	 * @param runtime
	 *            流程实例对象
	 * @return boolean 是否已经启动成功
	 */
	public void runProcess(String runtimeid) throws RemoteException;

	/**
	 * 初始化流程，将流程恢复到初始状态
	 * 
	 * @param runtime
	 *            流程运行实例
	 * @return boolean
	 */
	public void initProcess(String runtimeid) throws RemoteException;

	/**
	 * 删除流程，撤销该流程的全部运行信息
	 * 
	 * @param runtime
	 *            流程运行实例
	 * @return boolean
	 */
	public void dropProcess(String runtimeid) throws RemoteException;

	/**
	 * 提交活动
	 * 
	 * @param currentworklist
	 *            当前活动实例
	 */
	public void commitActivity(String workcode) throws RemoteException;

	/**
	 * 提交活动
	 * 
	 * @param currentworklist
	 *            当前活动实例
	 * @param 选择的目标活动
	 */
	public void commitActivityByManual(String workcode, String actid)
			throws RemoteException;

	/**
	 * 提交活动
	 * 
	 * @param runtime
	 *            流程实例对象(需要设置currentPointActivity)
	 * @return boolean
	 */
	public void commitActivityByManual(String workcode, String[] actid)
			throws RemoteException;

	/**
	 * 为活动绑定应用,如:表单,程序......
	 * 
	 * @param worklist
	 * @throws RemoteException
	 */
	public void worklistAppBind(String workcode, String apptype, String appvalue)
			throws RemoteException;

	/**
	 * 控制活动的状态
	 * 
	 * @param worklist
	 * @throws RemoteException
	 */
	public void updateWorklistStatus(String workcode, String status)
			throws RemoteException;

	/**
	 * 控制活动的扩展属性
	 * 
	 * @param worklist
	 * @throws RemoteException
	 */
	public void updateWorklistExtendattribute(String workcode, String ext)
			throws RemoteException;

	/**
	 * 启动子流程( 子流程的创建与普通流程是一样的,直接采用 newProcess即可,但是子流程的启动运行必须采用该方法)
	 * 
	 * @param subRuntimeid
	 *            子流程的id
	 * @param runtimeid
	 *            父流程ID
	 * @param workcode
	 * @param sync
	 * @return
	 * @throws RemoteException
	 */
	public void runSubFlow(String subRuntimeid, String fatherRuntimeid,
			String workcode, boolean sync) throws RemoteException;

	/**
	 * 基于Netone的SOA定义文件来执行流程节点中集成的外部应用
	 * 
	 * @param eaixml
	 * @param runtimeid
	 * @return
	 * @throws RemoteException
	 */
	public String eai(String eaixml, String runtimeid, String workcode)
			throws RemoteException;

	/***************************************************************************
	 * 获得流程实例过程中的会话
	 * 
	 * @param runtimeid
	 * @return
	 * @throws RemoteException
	 */
	public Object getSessionAttribute(String runtimeid, String name)
			throws RemoteException;

	/***************************************************************************
	 * 获得流程实例过程中的会话
	 * 
	 * @param runtimeid
	 * @return
	 * @throws RemoteException
	 */
	public void setSessionAttribute(String runtimeid, String name, Object obj)
			throws RemoteException;

	/**
	 * 清除会话
	 * 
	 * @param runtimeid
	 * @throws RemoteException
	 */
	public void removeSession(String runtimeid) throws RemoteException;

	/**
	 * 描述会话
	 * 
	 * @param runtimeid
	 * @return
	 * @throws RemoteException
	 */
	public Session descriptSession(String runtimeid) throws RemoteException;

	/**
	 * sql语句查询
	 * 
	 * @param sql
	 * @return 被影响的工作流中的数据个数
	 * 
	 * 可以最灵活的分析和获得工作流的详细信息
	 */
	int coreSqlhandle(String sql) throws RemoteException;

}
