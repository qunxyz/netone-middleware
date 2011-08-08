package oe.frame.bus.workflow.actor;

import java.util.List;

import oe.midware.workflow.runtime.ormobj.TWfWorklist;
/**
 * 流程导航者,用于寻找流程中,当前节点的下一个节点
 * @author chen.jia.xun(Robanco) <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com<br>
 *         support by http://www.oesee.com
 *
 */
public interface NaviageActor {
	
	List execute(TWfWorklist worklist);

}
