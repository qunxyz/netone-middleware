package oe.frame.bus.workflow.actor.process;

import java.util.List;

import oe.midware.workflow.runtime.ormobj.TWfWorklist;

/**
 * 流程回退控制
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com<br>
 *         support by http://www.oesee.com
 * 
 */
public interface ProcessBackActor {
	List execute(TWfWorklist worklist);
}
