package oe.frame.bus.workflow.actor.process;

import oe.midware.workflow.runtime.ormobj.TWfRuntime;

/**
 * 流程运做
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com<br>
 *         support by http://www.oesee.com
 * 
 */
public interface ProcessRunActor {

	void execute(TWfRuntime runtime);
}
