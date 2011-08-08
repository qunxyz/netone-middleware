package oe.midware.workflow.engine.actor.process;

import java.sql.Timestamp;

import oe.frame.bus.workflow.RuntimeInfo;
import oe.frame.bus.workflow.actor.datafield.DataFieldDeployActor;
import oe.frame.bus.workflow.actor.process.ProcessDeployActor;
import oe.frame.orm.OrmerEntry;
import oe.midware.workflow.runtime.RuntimeProcessRef;
import oe.midware.workflow.runtime.ormobj.TWfRuntime;
import oe.midware.workflow.xpdl.model.misc.Duration;
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
public class ProcessDeployActorImp implements ProcessDeployActor {
	private Log _log = LogFactory.getLog(ProcessDeployActorImp.class);
	private DataFieldDeployActor dataFieldDeployActor;

	public TWfRuntime executes(WorkflowProcess proc) {
		if(proc==null){
			_log.error(RuntimeInfo.OE_WF_DEF_ERR_010);
			throw new RuntimeException(RuntimeInfo.OE_WF_DEF_ERR_010);
		}
		TWfRuntime runtime = readyForNewRuntimeProcess(proc);
		// 创建流程实例
		OrmerEntry.fetchOrmer().fetchSerializer().create(runtime);
		// 创建相关数据
		dataFieldDeployActor.execute(runtime);

		return runtime;
	}

	private static TWfRuntime readyForNewRuntimeProcess(WorkflowProcess proc) {
		
		String currentTime = (new Timestamp(System.currentTimeMillis())).toString();

		TWfRuntime runtime = new TWfRuntime();

		runtime.setKind(RuntimeProcessRef.TYPE_MAIN_FLOW[0]); // 设置流程的类型为主流程
		Duration limit = proc.getProcessHeader().getLimit();
		if (limit != null) {
			runtime.setLimits(String.valueOf(limit.getValue()));
		}
		runtime.setPriority(proc.getProcessHeader().getPriority());
		runtime.setProcessid(proc.getId());
		runtime.setStarttime(currentTime);
		runtime.setStatusnow(RuntimeProcessRef.STATUS_READY[0]); // 设置当前状态为Ready
		runtime.setKind(RuntimeProcessRef.TYPE_MAIN_FLOW[0]);
		return runtime;
	}

	public DataFieldDeployActor getDataFieldDeployActor() {
		return dataFieldDeployActor;
	}

	public void setDataFieldDeployActor(DataFieldDeployActor dataFieldDeployActor) {
		this.dataFieldDeployActor = dataFieldDeployActor;
	}

}
