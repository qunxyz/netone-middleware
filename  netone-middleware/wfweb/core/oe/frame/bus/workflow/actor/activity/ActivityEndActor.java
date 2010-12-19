package oe.frame.bus.workflow.actor.activity;

import oe.midware.workflow.runtime.ormobj.TWfWorklist;

/**
 * 活动结束者
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com<br>
 *         support by http://www.oesee.com
 * 
 */
public interface ActivityEndActor {
	boolean execute(TWfWorklist worklist);
}
