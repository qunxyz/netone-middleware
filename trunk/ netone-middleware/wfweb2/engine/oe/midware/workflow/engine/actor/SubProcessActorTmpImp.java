package oe.midware.workflow.engine.actor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import oe.frame.bus.workflow.ActivityExtendAttribute;
import oe.frame.bus.workflow.ProcessEngine;
import oe.frame.bus.workflow.RuntimeMonitor;
import oe.frame.bus.workflow.WfEntry;
import oe.frame.bus.workflow.actor.ProcessActor;
import oe.frame.bus.workflow.actor.SubProcessActor;
import oe.frame.bus.workflow.actor.activity.ActivityStatusActor;
import oe.frame.orm.OrmerEntry;
import oe.midware.workflow.runtime.ActivityRef;
import oe.midware.workflow.runtime.RuntimeProcessRef;
import oe.midware.workflow.runtime.RuntimeWorklistRef;
import oe.midware.workflow.runtime.XpdlModelHandler;
import oe.midware.workflow.runtime.ormobj.TWfRelevantvar;
import oe.midware.workflow.runtime.ormobj.TWfRuntime;
import oe.midware.workflow.runtime.ormobj.TWfWorklist;
import oe.midware.workflow.xpdl.model.activity.Implementation;
import oe.midware.workflow.xpdl.model.activity.SubFlow;
import oe.midware.workflow.xpdl.model.data.DataField;
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
public class SubProcessActorTmpImp implements SubProcessActor {

	private Log _log = LogFactory.getLog(SubProcessActorTmpImp.class);

	private ProcessActor processActor;

	private ActivityStatusActor activityStatusActor;

	public TWfRuntime executes(TWfWorklist curWorklist) {
		String subprocessid = null;
		String subflowSyncmode = null;
		RuntimeMonitor runtimemonitor = (RuntimeMonitor) WfEntry
				.fetchBean("runtimemonitor");
		String processid =null;
		try {
			processid = runtimemonitor.loadRuntime(
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
						curWorklist.getRuntimeid()).getImplementation();
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
			curWorklist.setExecutestatus(RuntimeWorklistRef.STATUS_QUASH[0]);
			// 如果是异步子流程那么直接提交，父节点
			//processActor.commitActivity(curWorklist);
		}

		OrmerEntry.fetchOrmer().fetchSerializer().update(curWorklist);

		WorkflowProcess procsub = ((XpdlModelHandler) WfEntry.fetchBean

		("xpdlModelHandler")).fetchXpdlWorkflowByProcessid(subprocessid);
		_log.debug("start subflow:" + procsub.getName() + "ID:"
				+ procsub.getId());
		TWfRuntime runtimeSub = processActor.newProcess(procsub);
		
		// 增加子流程自动同步父流程的雷同的相关变量
		syncRelevantvar(runtimeSub, curWorklist, procsub, processid);
		
		// 添加子流程的隶属信息
		runtimeSub.setBelongactivityid(curWorklist.getWorkcode());
		runtimeSub.setBelongruntimeid(curWorklist.getRuntimeid());
		runtimeSub.setKind(subflowSyncmode);

		OrmerEntry.fetchOrmer().fetchSerializer().update(runtimeSub);
		
		//马上启动子流程
		processActor.runProcess(runtimeSub);

		return runtimeSub;
	}
	
	
	private void syncRelevantvar(TWfRuntime runtimeSub,
			TWfWorklist curWorklist, WorkflowProcess procsub,
			String processidFather) {
		RuntimeMonitor runtimemonitor = (RuntimeMonitor) WfEntry
				.fetchBean("runtimemonitor");

		ProcessEngine processEngine = (ProcessEngine) WfEntry
				.fetchBean("processEngine");

		WorkflowProcess procFather = ((XpdlModelHandler) WfEntry
				.fetchBean("xpdlModelHandler"))
				.fetchXpdlWorkflowByProcessid(processidFather);

		DataField[] dataFather = procFather.getDataField();
		
		if(_log.isDebugEnabled()){
			_log.debug("fatherData List");
			for (int i = 0; i < dataFather.length; i++) {
				_log.debug(dataFather[i].getName());
			}
		}

		// 获得子流程的ID
		String subflowRuntimeid = runtimeSub.getRuntimeid();
		// 获得父流程的ID
		String fatherflowRuntimeId = curWorklist.getRuntimeid();

		List syncData = new ArrayList();
		try {
			for (int i = 0; i < dataFather.length; i++) {
				String dataid = dataFather[i].getId();
				DataField subObject = procsub.getDataField(dataid);
				_log.debug("check sub data:"+dataid);
				if (subObject != null) {// 检查子流程是否存在与父流程雷同的变量
					_log.debug("check sub data exist:"+dataid);
					TWfRelevantvar revSub = runtimemonitor.fetchRelevantVar(
							subflowRuntimeid, dataid);
					TWfRelevantvar revFather = runtimemonitor.fetchRelevantVar(
							fatherflowRuntimeId, dataid);
					String fatherdataValue=revFather.getValuenow();
					_log.debug("add to sub data:"+fatherdataValue);
					revSub.setValuenow(revFather.getValuenow());// 将父流程的该变量值写入子流程变量中
					syncData.add(revSub);// 记录下，要做数据同步，
				}
			}
			processEngine.updateRelevantvars(syncData);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
