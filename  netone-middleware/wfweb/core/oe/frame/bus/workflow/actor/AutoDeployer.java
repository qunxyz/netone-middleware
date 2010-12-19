package oe.frame.bus.workflow.actor;

import oe.midware.workflow.runtime.ormobj.TWfWorklist;

/**
 * 自动发布者
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com<br>
 *         support by http://www.oesee.com
 * 
 */
public interface AutoDeployer {

	void execute(TWfWorklist worklist);
}
