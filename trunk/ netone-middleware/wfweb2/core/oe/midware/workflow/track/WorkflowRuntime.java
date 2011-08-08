package oe.midware.workflow.track;

import java.rmi.RemoteException;

/**
 * 流程轨迹控制
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com<br>
 *         support by http://www.oesee.com
 * 
 */
public interface WorkflowRuntime {
	/**
	 * 获得静态流程轨迹
	 * 
	 * @param processId
	 *            流程id
	 * @return
	 */
	String viewFlow(String processid) throws RemoteException;

	/**
	 * 获得动态流程轨迹
	 * 
	 * @param runtimeId
	 *            流程Id
	 * @return
	 */
	String controlFlow(String runtimeId) throws RemoteException;

	/**
	 * 获得动态流程轨迹
	 * 
	 * @param runtimeId
	 *            流程Id
	 * @return
	 */
	String useFlow(String runtimeId) throws RemoteException;

}
