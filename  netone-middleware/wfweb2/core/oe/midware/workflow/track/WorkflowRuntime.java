package oe.midware.workflow.track;

import java.rmi.RemoteException;

/**
 * ���̹켣����
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com<br>
 *         support by http://www.oesee.com
 * 
 */
public interface WorkflowRuntime {
	/**
	 * ��þ�̬���̹켣
	 * 
	 * @param processId
	 *            ����id
	 * @return
	 */
	String viewFlow(String processid) throws RemoteException;

	/**
	 * ��ö�̬���̹켣
	 * 
	 * @param runtimeId
	 *            ����Id
	 * @return
	 */
	String controlFlow(String runtimeId) throws RemoteException;

	/**
	 * ��ö�̬���̹켣
	 * 
	 * @param runtimeId
	 *            ����Id
	 * @return
	 */
	String useFlow(String runtimeId) throws RemoteException;

}
