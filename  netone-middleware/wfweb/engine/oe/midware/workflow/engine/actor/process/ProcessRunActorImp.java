package oe.midware.workflow.engine.actor.process;

import java.rmi.RemoteException;

import oe.frame.bus.workflow.RuntimeInfo;
import oe.frame.bus.workflow.RuntimeMonitor;
import oe.frame.bus.workflow.WfEntry;
import oe.frame.bus.workflow.actor.activity.ActivityDeployActor;
import oe.frame.bus.workflow.actor.process.ProcessRunActor;
import oe.frame.orm.OrmerEntry;
import oe.midware.workflow.runtime.RuntimeProcessRef;
import oe.midware.workflow.runtime.ormobj.TWfRuntime;
import oe.midware.workflow.xpdl.model.activity.Activity;
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
public class ProcessRunActorImp implements ProcessRunActor {
	private Log _log = LogFactory.getLog(ProcessRunActorImp.class);

	private ActivityDeployActor activityDeployActor;

	public void execute(TWfRuntime runtime) {
		if (runtime == null) {
			_log.warn(RuntimeInfo.OE_WF_RMT_ERR_001);
			return;
		}
		boolean isReady = runtime.isReady();
		if (!isReady) {
			_log.error(RuntimeInfo.OE_WF_RMT_ERR_004);
			throw new RuntimeException(RuntimeInfo.OE_WF_RMT_ERR_004);
		}
		RuntimeMonitor runtimemonitor = (RuntimeMonitor) WfEntry
				.fetchBean("runtimemonitor");

		WorkflowProcess proc=null;
		try {
			proc = runtimemonitor.fetchWorkflowProcess(runtime
					.getProcessid());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Activity[] act = proc.getActivity();
		for (int i = 0; i < act.length; i++) {
			boolean isStartActivity = act[i].isStartActivity();
			if (isStartActivity) {
				activityDeployActor.execute(runtime, act[i]);
			}
		}
		runtime.setStatusnow(RuntimeProcessRef.STATUS_RUNNING[0]);
		OrmerEntry.fetchOrmer().fetchSerializer().update(runtime);
	}

	public ActivityDeployActor getActivityDeployActor() {
		return activityDeployActor;
	}

	public void setActivityDeployActor(ActivityDeployActor activityDeployActor) {
		this.activityDeployActor = activityDeployActor;
	}

}
