package oe.midware.workflow.engine.actor;

import java.util.Map;

import oe.frame.bus.workflow.ActivityExtendAttribute;
import oe.frame.bus.workflow.RuntimeMonitor;
import oe.frame.bus.workflow.WfEntry;
import oe.frame.bus.workflow.actor.ProcessActor;
import oe.frame.bus.workflow.actor.SubProcessActor;
import oe.frame.bus.workflow.actor.activity.ActivityStatusActor;
import oe.frame.orm.OrmerEntry;
import oe.midware.workflow.XMLException;
import oe.midware.workflow.runtime.ActivityRef;
import oe.midware.workflow.runtime.RuntimeProcessRef;
import oe.midware.workflow.runtime.RuntimeWorklistRef;
import oe.midware.workflow.runtime.XpdlModelHandler;
import oe.midware.workflow.runtime.ormobj.TWfRuntime;
import oe.midware.workflow.runtime.ormobj.TWfWorklist;
import oe.midware.workflow.xpdl.model.activity.Implementation;
import oe.midware.workflow.xpdl.model.activity.SubFlow;
import oe.midware.workflow.xpdl.model.workflow.WorkflowProcess;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com<br>
 *         support by http://www.oesee.com
 * 
 */
public class SubProcessActorImp implements SubProcessActor {

	private Log _log = LogFactory.getLog(SubProcessActorImp.class);

	private ProcessActor processActor;

	private ActivityStatusActor activityStatusActor;

	public TWfRuntime executes(TWfWorklist curWorklist) {
		String subprocessid = null;
		String subflowSyncmode = null;

		RuntimeMonitor runtimemonitor = (RuntimeMonitor) WfEntry
				.fetchBean("runtimemonitor");
		try {
			String processid = runtimemonitor.loadRuntime(
					curWorklist.getRuntimeid()).getProcessid();
			Map mapExtend = runtimemonitor.fetchActivity(processid,
					curWorklist.getActivityid()).getExtendedAttributes()
					.getMap();
			if (mapExtend.containsKey(ActivityExtendAttribute._SUBFLOW_ID)) {
				subprocessid = (String) mapExtend
						.get(ActivityExtendAttribute._SUBFLOW_ID);
				String syncmode = (String) mapExtend
						.get(ActivityExtendAttribute._SUBFLOW_SYNCMODE);
				if (ActivityExtendAttribute._VALUE_SUBFLOW_SYNCMODE_SYNC
						.equals(syncmode)) {
					subflowSyncmode = RuntimeProcessRef.TYPE_SYNC_SUB_FLOW[0];
				} else {
					subflowSyncmode = RuntimeProcessRef.TYPE_SUB_FLOW[0];
				}
			} else {
				Implementation imp = runtimemonitor.fetchActivity(processid,
						curWorklist.getActivityid()).getImplementation();
				SubFlow subflow = (SubFlow) imp;
				processid = subflow.getId();
				subflowSyncmode = fetchExecuteType(subflow);
				subprocessid = processid;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		_log.debug("子流程执行模式:" + subflowSyncmode);
		if (RuntimeProcessRef.TYPE_SYNC_SUB_FLOW[0].equals(subflowSyncmode)) {
			curWorklist
					.setExecutestatus(RuntimeWorklistRef.STATUS_SUBFLOW_STOP[0]);
		} else {
			curWorklist.setExecutestatus(RuntimeWorklistRef.STATUS_RUNNING[0]);
		}

		OrmerEntry.fetchOrmer().fetchSerializer().update(curWorklist);

		WorkflowProcess procsub = ((XpdlModelHandler) WfEntry.fetchBean

		("xpdlModelHandler")).fetchXpdlWorkflowByProcessid(subprocessid);
		_log.debug("start subflow:" + procsub.getName() + "ID:"
				+ procsub.getId());
		TWfRuntime runtimeSub = processActor.newProcess(procsub);

		// 添加子流程的隶属信息
		runtimeSub.setBelongactivityid(curWorklist.getWorkcode());
		runtimeSub.setBelongruntimeid(curWorklist.getRuntimeid());
		runtimeSub.setKind(subflowSyncmode);

		OrmerEntry.fetchOrmer().fetchSerializer().update(runtimeSub);
		
		//马上启动子流程
		processActor.runProcess(runtimeSub);

		return runtimeSub;
	}

	private String fetchExecuteType(SubFlow subflow) {
		String executeType = subflow.getExecution().toString();
		if (ActivityRef.FLOW_SYNCHR.equalsIgnoreCase(executeType)) {
			return RuntimeProcessRef.TYPE_SYNC_SUB_FLOW[0];
		}
		return RuntimeProcessRef.TYPE_SUB_FLOW[0];
	}

	public ActivityStatusActor getActivityStatusActor() {
		return activityStatusActor;
	}

	public void setActivityStatusActor(ActivityStatusActor activityStatusActor) {
		this.activityStatusActor = activityStatusActor;
	}

	public ProcessActor getProcessActor() {
		return processActor;
	}

	public void setProcessActor(ProcessActor processActor) {
		this.processActor = processActor;
	}

}
