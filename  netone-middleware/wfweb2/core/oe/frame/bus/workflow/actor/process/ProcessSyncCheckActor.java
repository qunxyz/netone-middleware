package oe.frame.bus.workflow.actor.process;

import java.util.List;

import oe.midware.workflow.runtime.ormobj.TWfWorklist;

/**
 * 流程同步控制
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com<br>
 *         support by http://www.oesee.com
 * 
 */
public interface ProcessSyncCheckActor {
	/**
	 * 
	 * @param runtime
	 *            流程运行实例
	 * @param worklist
	 *            当前活动
	 * @param listworklist
	 *            被路由到的可发布的活动
	 * @return
	 */
	List execute(TWfWorklist worklist, List listworklist);

}
