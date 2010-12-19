package oe.midware.workflow.service;

import java.rmi.Remote;
import java.rmi.RemoteException;

import oe.midware.workflow.track.WorkflowRuntime;

/**
 * 工作流设计
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public interface WorkflowDesign extends Remote, WorkflowRuntime {
	/**
	 * 保存流程信息
	 * 
	 * @param context
	 *            流程定义内容（XPDL)
	 * @param processid
	 *            流程id
	 * @param processname
	 *            流程名
	 * @throws RemoteException
	 */
	void saveOpe(String context, String processid, String processname)
			throws RemoteException;

	/**
	 * 删除流程定义文件
	 * 
	 * @param processid
	 *            流程定义ID
	 * @return
	 * @throws RemoteException
	 */
	boolean dropOpe(String processid) throws RemoteException;

	/**
	 * 更新流程定义信息
	 * 
	 * @param context
	 *            流程定义内容（XPDL)
	 * @param processid
	 *            流程ID
	 * @param processname
	 *            流程名
	 * @return
	 * @throws RemoteException
	 */
	boolean updateOpe(String context, String processid, String processname)
			throws RemoteException;

	/**
	 * 展现流程信息
	 * 
	 * @param processid
	 *            流程ID
	 * @return
	 * @throws RemoteException
	 */
	String viewOpe(String processid) throws RemoteException;

	/**
	 * 根据流程ID 获得流程的描叙信息（XPDL）
	 * 
	 * @param processid
	 *            流程ID
	 * @return
	 * @throws RemoteException
	 */
	String xpdldescription(String processid) throws RemoteException;

}
