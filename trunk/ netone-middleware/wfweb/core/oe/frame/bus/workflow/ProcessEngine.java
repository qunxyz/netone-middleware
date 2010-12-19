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
 * 流程引擎
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com<br>
 *         support by http://www.oesee.com
 * 
 */
public interface ProcessEngine {
	/**
	 * 创建流程实例
	 * 
	 * @param proc
	 *            流程模板对象
	 * @return Object 流程实例
	 */
	public TWfRuntime newProcess(WorkflowProcess proc) throws RemoteException;

	/**
	 * 启动流程
	 * 
	 * @param runtime
	 *            流程实例对象
	 * @return boolean 是否已经启动成功
	 */
	public void runProcess(TWfRuntime runtime) throws RemoteException;

	/**
	 * 提交活动
	 * 
	 * @param currentworklist
	 *            当前活动实例
	 */
	public void commitActivity(TWfWorklist currentworklist)
			throws RemoteException;

	/**
	 * 提交活动
	 * 
	 * @param currentworklist
	 *            当前活动实例
	 * @param 选择的目标活动
	 */
	public void commitActivityByManual(TWfWorklist currentworklist, Activity act)
			throws RemoteException;

	/**
	 * 提交活动
	 * 
	 * @param runtime
	 *            流程实例对象(需要设置currentPointActivity)
	 * @return boolean
	 */
	public void commitActivityByManual(TWfWorklist currentworklist, List act)
			throws RemoteException;

	/**
	 * 批量更新相关数据
	 * 
	 * @param runtime
	 *            流程对象(需要设置currentrelevantvar)
	 * @return boolean
	 */
	public void updateRelevantvars(List relevantvar) throws RemoteException;

	/**
	 * 更新相关数据
	 * 
	 * @param runtime
	 *            流程对象(需要设置currentrelevantvar)
	 * @return boolean
	 */
	public void updateRelevantvar(TWfRelevantvar relevantvar)
			throws RemoteException;

	/**
	 * 更新相关数据，带日志
	 * 
	 * @param runtime
	 *            流程对象(需要设置currentrelevantvar)
	 * @param extendinfo
	 *            日志补充信息
	 * 
	 * @return boolean
	 */
	public void updateRelevantvarUseLog(TWfRelevantvar relevantvar,
			String extendinfo) throws RemoteException;

	/**
	 * 批量更新相关数据，带日志
	 * 
	 * @param runtime
	 *            流程对象(需要设置currentrelevantvar)
	 * @param extendinfo
	 *            日志补充信息
	 * @return boolean
	 */
	public void updateRelevantvarsUseLog(List relevantvar, String extendinfo)
			throws RemoteException;

	// /////业务规则执行////////
	/**
	 * 业务逻辑判断
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
	 * 业务逻辑处理,返回业务处理结果数据,统一以String 形式表达
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
	 * 初始化流程，将流程恢复到初始状态
	 * 
	 * @param runtime
	 *            流程运行实例
	 * @return boolean
	 */
	public void initProcess(TWfRuntime runtime) throws RemoteException;

	/**
	 * 删除流程，撤销该流程的全部运行信息
	 * 
	 * @param runtime
	 *            流程运行实例
	 * @return boolean
	 */
	public void dropProcess(TWfRuntime runtime) throws RemoteException;

}
