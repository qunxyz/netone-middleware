package oe.midware.workflow.engine.actor;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import oe.frame.bus.workflow.RuntimeInfo;
import oe.frame.bus.workflow.actor.AutoMen;
import oe.frame.bus.workflow.actor.ProcessActor;
import oe.frame.bus.workflow.actor.SubProcessActor;
import oe.midware.workflow.runtime.ActivityRef;
import oe.midware.workflow.runtime.ormobj.TWfRuntime;
import oe.midware.workflow.runtime.ormobj.TWfWorklist;
/**
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com<br>
 *         support by http://www.oesee.com
 * 
 */
public class AutoMenImp implements AutoMen {
	private Log _log = LogFactory.getLog(AutoMenImp.class);

	private ProcessActor processActor;

	private SubProcessActor subProcessActor;

	public void execute(List list) {
		for (Iterator itr = list.iterator(); itr.hasNext();) {
			TWfWorklist curWorklist = (TWfWorklist) itr.next();
			String worklistType = curWorklist.getTypes().trim();

			boolean isRoute = ActivityRef.ACT_ROUTE_KEY[0]
					.equals(worklistType);
			if (isRoute) {
				_log.debug("commit auto activity:"
						+ curWorklist.getActivityid() + "( runtimeid="
						+ curWorklist.getRuntimeid() + ",workcode="
						+ curWorklist.getWorkcode());
				processActor.commitActivity(curWorklist);
			} else {
				boolean isSubflow = ActivityRef.ACT_SUBFLOW_KEY[0]
						.equals(worklistType);
				boolean isSyncSubflow = ActivityRef.ACT_SUBFLOW_SYNC_KEY[0]
						.equals(worklistType);
				if (isSubflow || isSyncSubflow) {
					_log.info("commit subflow in activity:"
							+ curWorklist.getActivityid() + "[ runtimeid="
							+ curWorklist.getRuntimeid() + ",workcode="
							+ curWorklist.getWorkcode());
					TWfRuntime runtimeSub=subProcessActor.executes(curWorklist);
					//拷贝相关数据
					TWfRuntime runtimeFa=curWorklist.fetchRuntime();
					
				} else {
					_log.warn(RuntimeInfo.OE_WF_RMT_WAR_009);
					processActor.commitActivity(curWorklist);
					
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
