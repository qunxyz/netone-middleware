package oe.midware.workflow.engine.actor;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import oe.frame.bus.workflow.RuntimeInfo;
import oe.frame.bus.workflow.actor.AutoDeployer;
import oe.frame.bus.workflow.actor.ProcessActor;
import oe.frame.bus.workflow.actor.activity.ActivityDeployActor;
import oe.frame.bus.workflow.actor.activity.ActivityEndActor;
import oe.frame.bus.workflow.actor.process.ProcessDeployActor;
import oe.frame.bus.workflow.actor.process.ProcessRunActor;

import oe.midware.workflow.engine.ProcessEngineImp;

import oe.midware.workflow.runtime.ormobj.TWfRuntime;
import oe.midware.workflow.runtime.ormobj.TWfWorklist;
import oe.midware.workflow.xpdl.model.activity.Activity;
import oe.midware.workflow.xpdl.model.workflow.WorkflowProcess;
/**
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com<br>
 *         support by http://www.oesee.com
 * 
 */
public class ProcessActorImp implements ProcessActor {

	private Log _log = LogFactory.getLog(ProcessEngineImp.class);

	private ProcessDeployActor processDeployActor;
	
	private ProcessRunActor processRunActor;

	private ActivityDeployActor activityDeployActor;

	private ActivityEndActor activityEndActor;

	private AutoDeployer autoDeployer;



	public TWfRuntime newProcess(WorkflowProcess proc) {
		if(proc==null){
			_log.error(RuntimeInfo.OE_WF_DEF_ERR_005);
			throw new RuntimeException(RuntimeInfo.OE_WF_DEF_ERR_005);
		}

		return processDeployActor.executes(proc);
	}

	public void runProcess(TWfRuntime runtime) {
		processRunActor.execute(runtime);
	}

	public void commitActivity(TWfWorklist worklist) {
		if (worklist == null || !worklist.isRunning()) {
			_log.warn(RuntimeInfo.OE_WF_RMT_ERR_003);
			return;
		}
		activityEndActor.execute(worklist);
		autoDeployer.execute(worklist);
	}

	public void commitActivityByManual(TWfWorklist worklist, Activity arg2) {
		activityEndActor.execute(worklist);
		activityDeployActor.execute(worklist.fetchRuntime(), arg2);
	}

	public void commitActivityByManual(TWfWorklist worklist, List arg2) {
		activityEndActor.execute(worklist);
		activityDeployActor.execute(worklist.fetchRuntime(), arg2);
	}

	public ActivityDeployActor getActivityDeployActor() {
		return activityDeployActor;
	}

	public void setActivityDeployActor(ActivityDeployActor activityDeployActor) {
		this.activityDeployActor = activityDeployActor;
	}

	public ActivityEndActor getActivityEndActor() {
		return activityEndActor;
	}

	public void setActivityEndActor(ActivityEndActor activityEndActor) {
		this.activityEndActor = activityEndActor;
	}

	public ProcessDeployActor getProcessDeployActor() {
		return processDeployActor;
	}

	public void setProcessDeployActor(ProcessDeployActor processDeployActor) {
		this.processDeployActor = processDeployActor;
	}
	public AutoDeployer getAutoDeployer() {
		return autoDeployer;
	}

	public void setAutoDeployer(AutoDeployer autoDeployer) {
		this.autoDeployer = autoDeployer;
	}

	public ProcessRunActor getProcessRunActor() {
		return processRunActor;
	}

	public void setProcessRunActor(ProcessRunActor processRunActor) {
		this.processRunActor = processRunActor;
	}
}
