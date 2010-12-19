package oe.frame.bus.workflow.actor;

import java.util.List;

import oe.midware.workflow.runtime.ormobj.TWfRuntime;
import oe.midware.workflow.runtime.ormobj.TWfWorklist;

/**
 * 自动执行者 用途:在流程引擎中,执行自动化活动,包括:空活动自动提交(执行里面的规则脚本),自动调度子流程, 自动执行活动中的规则脚本,流程执行结束
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com<br>
 *         support by http://www.oesee.com
 * 
 */
public interface AutoActor {
	/**
	 * 
	 * @param list
	 *            需要自动运行的活动列表
	 */
	void execute(List list);
}
