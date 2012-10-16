package oe.frame.bus.workflow.rule;

import java.rmi.RemoteException;

public interface WfScriptFunction extends ScriptFunction {

	/**
	 * 初始化流程，将流程恢复到初始状态
	 * 
	 * @param runtime
	 *            流程运行实例
	 * @return boolean
	 */
	public void initProc(String runtimeid) throws RemoteException;

	/**
	 * 删除流程，撤销该流程的全部运行信息
	 * 
	 * @param runtime
	 *            流程运行实例
	 * @return boolean
	 */
	public void dropProc(String runtimeid) throws RemoteException;

	/**
	 * 提交活动
	 * 
	 * @param currentworklist
	 *            当前活动实例
	 */
	public void commitAct(String workcode) throws RemoteException;

	/**
	 * 提交活动
	 * 
	 * @param currentworklist
	 *            当前活动实例
	 * @param 选择的目标活动
	 */
	public void commitActAndPoint(String workcode, String actid)
			throws RemoteException;

	/**
	 * 提交活动
	 * 
	 * @param runtime
	 *            流程实例对象(需要设置currentPointActivity)
	 * @return boolean
	 */
	public void commitActAndPoints(String workcode, String[] actid)
			throws RemoteException;

	/**
	 * 为活动绑定应用,如:表单,程序......
	 * 
	 * @param worklist
	 * @throws RemoteException
	 */
	void worklistAppBind(String workcode, String apptype, String appvalue)
			throws RemoteException;

	/**
	 * 控制活动的状态
	 * 
	 * @param worklist
	 * @throws RemoteException
	 */
	void updateWorklistStatus(String workcode, String status);

	/**
	 * 使用工作流会话,会话的作用类似于相关数据，但是会话是非预先定义的,支持的数据范围还有灵活性更好
	 * 
	 * @param name
	 * @param value
	 */
	void setSession(String name, Object value);

	/**
	 * 使用工作流会话,会话的作用类似于相关数据,但是会话是非预先定义的,支持的数据范围还有灵活性更好
	 * 
	 * @param name
	 * @param value
	 */
	Object getSession(String name);
	
	/**
	 * 获得流程相关变量的扩展属性
	 * 
	 * @param id
	 * @param paramname
	 * @param extname
	 * @return
	 */
	String getext(String id,String paramname,String extname);
	
	/**
	 * 根据workcode获得当前用户
	 * @param workcode
	 * @return
	 */
	String[] getUser(String workcode);
	/**
	 * 获得流程节点的提交者
	 * @param workcode
	 * @return
	 */
	String getCommiter(String workcode);

}
