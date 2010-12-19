package oe.midware.workflow.engine.actor;

import java.util.List;


import oe.frame.bus.workflow.actor.NaviageActor;
import oe.frame.bus.workflow.actor.process.ProcessRouteActor;
import oe.frame.bus.workflow.actor.process.ProcessSyncCheckActor;
import oe.midware.workflow.runtime.ormobj.TWfWorklist;
/**
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com<br>
 *         support by http://www.oesee.com
 * 
 */

public class NaviageActorImp implements NaviageActor {
	

	private ProcessRouteActor processRouteActor;

	private ProcessSyncCheckActor processSyncCheckActor;

	public List execute(TWfWorklist worklist) {
		// 获得当前流程中可路由到的活动列表
		List list = processRouteActor.execute(worklist);
		// 分析路由到的活动列表中的活动是否存在同步控制（也就是能否发布）
		return processSyncCheckActor.execute(worklist, list);
	}

	public ProcessRouteActor getProcessRouteActor() {
		return processRouteActor;
	}

	public void setProcessRouteActor(ProcessRouteActor processRouteActor) {
		this.processRouteActor = processRouteActor;
	}

	public ProcessSyncCheckActor getProcessSyncCheckActor() {
		return processSyncCheckActor;
	}

	public void setProcessSyncCheckActor(
			ProcessSyncCheckActor processSyncCheckActor) {
		this.processSyncCheckActor = processSyncCheckActor;
	}

}
