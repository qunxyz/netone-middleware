package oe.frame.bus.workflow.actor.process;

import oe.midware.workflow.runtime.ormobj.TWfRuntime;

/**
 * 初始化流程
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com<br>
 *         support by http://www.oesee.com
 * 
 */
public interface ProcessInitActor {
	void executes(TWfRuntime runtime);
}
