package oe.frame.bus.workflow.actor;

import java.util.List;

import oe.midware.workflow.runtime.ormobj.TWfRuntime;
import oe.midware.workflow.runtime.ormobj.TWfWorklist;
import oe.midware.workflow.xpdl.model.activity.Activity;
import oe.midware.workflow.xpdl.model.workflow.WorkflowProcess;

/**
 * 引擎执行体
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com<br>
 *         support by http://www.oesee.com
 * 
 */
public interface ProcessActor {
	// /////引擎执行部分////////
	/**
	 * 创建流程实例
	 * 
	 * @param proc
	 *            流程模板对象
	 * @return Object 流程实例
	 */
	public TWfRuntime newProcess(WorkflowProcess proc);

	/**
	 * 启动流程
	 * 
	 * @param runtime
	 *            流程实例对象
	 * @return boolean 是否已经启动成功
	 */
	public void runProcess(TWfRuntime runtime);

	/**
	 * 提交活动
	 * 
	 * @param runtime
	 *            流程实例对象(需要设置currentworklist)
	 * @return boolean
	 */
	public void commitActivity(TWfWorklist currentworklist);

	/**
	 * 提交活动
	 * 
	 * @param runtime
	 *            流程实例对象(需要设置currentPointActivity)
	 * @return boolean
	 */
	public void commitActivityByManual(TWfWorklist currentworklist, Activity act);

	/**
	 * 提交活动
	 * 
	 * @param runtime
	 *            流程实例对象(需要设置currentPointActivity)
	 * @return boolean
	 */
	public void commitActivityByManual(TWfWorklist currentworklist, List act);

}
