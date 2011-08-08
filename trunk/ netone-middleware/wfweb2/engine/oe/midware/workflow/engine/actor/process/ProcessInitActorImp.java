package oe.midware.workflow.engine.actor.process;

import oe.frame.bus.workflow.RuntimeInfo;
import oe.frame.bus.workflow.actor.activity.ActivityDropActor;
import oe.frame.bus.workflow.actor.datafield.DataFieldDeployActor;
import oe.frame.bus.workflow.actor.datafield.DataFieldDropActor;
import oe.frame.bus.workflow.actor.process.ProcessInitActor;
import oe.frame.orm.OrmerEntry;
import oe.midware.workflow.runtime.RuntimeProcessRef;
import oe.midware.workflow.runtime.ormobj.TWfRuntime;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com<br>
 *         support by http://www.oesee.com
 * 
 */
public class ProcessInitActorImp implements ProcessInitActor {
	private Log _log = LogFactory.getLog(ProcessDropActorImp.class);

	private DataFieldDropActor dataFieldDropActor;

	private DataFieldDeployActor dataFieldDeployActor;

	private ActivityDropActor activityDropActor;

	public ActivityDropActor getActivityDropActor() {
		return activityDropActor;
	}

	public void setActivityDropActor(ActivityDropActor activityDropActor) {
		this.activityDropActor = activityDropActor;
	}

	public void executes(TWfRuntime runtime) {
		if (runtime == null) {
			_log.error(RuntimeInfo.OE_WF_RMT_ERR_001);
			throw new RuntimeException(RuntimeInfo.OE_WF_RMT_ERR_001);
		}
		dataFieldDropActor.execute(runtime);
		dataFieldDeployActor.execute(runtime);
		activityDropActor.execute(runtime);
		readyForInitRuntimeProcess(runtime);
		OrmerEntry.fetchOrmer().fetchSerializer().update(runtime);
	}

	private static void readyForInitRuntimeProcess(TWfRuntime runtime) {
		runtime.setStatusnow(RuntimeProcessRef.STATUS_READY[0]); // 设置当前状态为Ready
		runtime.setEndtime(null);
	}

	public DataFieldDeployActor getDataFieldDeployActor() {
		return dataFieldDeployActor;
	}

	public void setDataFieldDeployActor(
			DataFieldDeployActor dataFieldDeployActor) {
		this.dataFieldDeployActor = dataFieldDeployActor;
	}

	public DataFieldDropActor getDataFieldDropActor() {
		return dataFieldDropActor;
	}

	public void setDataFieldDropActor(DataFieldDropActor dataFieldDropActor) {
		this.dataFieldDropActor = dataFieldDropActor;
	}

}
