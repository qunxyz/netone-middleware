package oe.midware.workflow.engine.actor;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import oe.frame.bus.workflow.actor.AutoActor;
import oe.frame.bus.workflow.actor.ProcessActor;
import oe.frame.bus.workflow.actor.SubProcessActor;
import oe.midware.workflow.runtime.ActivityRef;
import oe.midware.workflow.runtime.ormobj.TWfWorklist;

/**
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com<br>
 *         support by http://www.oesee.com
 * 
 */
public class AutoActorImp implements AutoActor {

	private Log _log = LogFactory.getLog(AutoActorImp.class);

	private ProcessActor processActor;

	private SubProcessActor subProcessActor;

	public void execute(List list) {
		for (Iterator itr = list.iterator(); itr.hasNext();) {
			TWfWorklist curWorklist = (TWfWorklist) itr.next();
			String worklistType = curWorklist.getTypes().trim();

			boolean isRoute = ActivityRef.ACT_ROUTE_KEY[0].equals(worklistType);
			if (isRoute) {
				_log.info("自动活动提交");
				processActor.commitActivity(curWorklist);
			} else {
				boolean isSubflow = ActivityRef.ACT_SUBFLOW_KEY[0]
						.equals(worklistType);
				boolean isSyncSubflow = ActivityRef.ACT_SUBFLOW_SYNC_KEY[0]
						.equals(worklistType);
				if (isSubflow || isSyncSubflow) {
					_log.info("提交子流程");
					subProcessActor.executes(curWorklist);
				} else {
					throw new RuntimeException("无效自动状态");
				}
			}
		}
	}

	public ProcessActor getProcessActor() {
		return processActor;
	}

	public void setProcessActor(ProcessActor processActor) {
		this.processActor = processActor;
	}

	public SubProcessActor getSubProcessActor() {
		return subProcessActor;
	}

	public void setSubProcessActor(SubProcessActor subProcessActor) {
		this.subProcessActor = subProcessActor;
	}
}
