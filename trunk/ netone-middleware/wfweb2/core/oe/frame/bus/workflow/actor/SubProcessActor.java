package oe.frame.bus.workflow.actor;

import oe.midware.workflow.runtime.ormobj.TWfRuntime;
import oe.midware.workflow.runtime.ormobj.TWfWorklist;

/**
 * 子流程执行
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com<br>
 *         support by http://www.oesee.com
 * 
 */
public interface SubProcessActor {
	TWfRuntime executes(TWfWorklist worklist);
}
