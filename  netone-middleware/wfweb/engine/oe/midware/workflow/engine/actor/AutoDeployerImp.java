package oe.midware.workflow.engine.actor;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.List;

import oe.frame.bus.workflow.RuntimeInfo;
import oe.frame.bus.workflow.RuntimeMonitor;
import oe.frame.bus.workflow.WfEntry;
import oe.frame.bus.workflow.actor.AutoDeployer;
import oe.frame.bus.workflow.actor.NaviageActor;
import oe.frame.bus.workflow.actor.activity.ActivityDeployActor;
import oe.frame.bus.workflow.actor.activity.ActivityEndActor;
import oe.frame.bus.workflow.actor.process.ProcessEndActor;
import oe.frame.orm.OrmerEntry;

import oe.midware.workflow.runtime.ormobj.TWfRuntime;
import oe.midware.workflow.runtime.ormobj.TWfWorklist;
import oe.midware.workflow.xpdl.model.activity.Activity;
import oe.rmi.client.RmiEntry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com<br>
 *         support by http://www.oesee.com
 * 
 */
public class AutoDeployerImp implements AutoDeployer {

	private Log _log = LogFactory.getLog(AutoDeployerImp.class);

	private NaviageActor naviageActor;

	private ActivityDeployActor activityDeployActor;

	private ProcessEndActor processEndActor;

	private ActivityEndActor activityEndActor;

	public void execute(TWfWorklist worklist) {
		TWfRuntime runtime = (TWfRuntime) worklist.fetchRuntime();
		List listWaitToDeplpoyActivity = naviageActor.execute(worklist);
		// ///debug//////
		if (_log.isDebugEnabled()) {
			StringBuffer buf = new StringBuffer();
			for (Iterator itr = listWaitToDeplpoyActivity.iterator(); itr
					.hasNext();) {
				Activity act = (Activity) itr.next();
				buf.append(act.getId() + ",");
			}
			String atProcessInfo = ", [processid=" + runtime.getRuntimeid()
					+ ",runtimeid=" + runtime.getRuntimeid() + "]";
			_log.debug("naviageActor new activity to deploy is:"
					+ buf.toString() + atProcessInfo);
		}
		// ////////////
		if (listWaitToDeplpoyActivity == null
				|| listWaitToDeplpoyActivity.size() == 0) {
			dealWhenLose(runtime, worklist);
			return;
		}
		activityDeployActor.execute(runtime, listWaitToDeplpoyActivity);
	}

	private void dealWhenLose(TWfRuntime runtime, TWfWorklist worklist) {
		_log.debug("when lose deploy activity --");
		RuntimeMonitor runtimemonitor = (RuntimeMonitor) WfEntry
				.fetchBean("runtimemonitor");
		boolean isExit = false;
		try {
			isExit = runtimemonitor.fetchActivity(runtime.getProcessid(),
					worklist.getActivityid()).isExitActivity();

			if (isExit) {
				if (runtimemonitor.haveRunningOrSubflowWaittingWorklist(runtime
						.getRuntimeid())) {
					_log.info(RuntimeInfo.OE_WF_RMT_ERR_009);
				} else {
					processEndActor.execute(runtime);
					_log.info(RuntimeInfo.OE_WF_RMT_INFO_002);
					if (runtime.isSyncSubFlow()) {
						dealSyncSubFlow(runtime);
					}
				}
			} else {
				if (runtimemonitor.haveRunningOrSubflowWaittingWorklist(runtime
						.getRuntimeid())) {
					_log.info(RuntimeInfo.OE_WF_RMT_ERR_009);
				} else {
					_log.error(RuntimeInfo.OE_WF_RMT_ERR_007);
					throw new RuntimeException(RuntimeInfo.OE_WF_RMT_ERR_007);
				}
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void dealSyncSubFlow(TWfRuntime runtime) {
		String workcode = runtime.getBelongactivityid();
		TWfWorklist faterhworklist = (TWfWorklist) OrmerEntry.fetchOrmer()
				.fetchQuerister().loadObject(TWfWorklist.class, workcode);
		TWfRuntime fatherRuntime = faterhworklist.fetchRuntime();
		boolean isSubflowWaiting = faterhworklist.isStop();
		if (!isSubflowWaiting) {
			_log.error(RuntimeInfo.OE_WF_RMT_ERR_008);
			throw new RuntimeException(RuntimeInfo.OE_WF_RMT_ERR_008);
		}
		// 结束父结点活动
		activityEndActor.execute(faterhworklist);
		_log.info(RuntimeInfo.OE_WF_RMT_INFO_006);
		
		execute(faterhworklist);

//		// 反馈给soa服务
//		try {
//			SoaCenterService soc = (SoaCenterService) RmiEntry.iv("soaser");
//			soc.inDo(fatherRuntime.getBelongactivityid());
//		} catch (MalformedURLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (RemoteException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (NotBoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		// // 从新开始导航父流程
		// List list = naviageActor.execute(faterhworklist);
		// if (list == null || list.size() == 0) {
		// dealWhenLose(fatherRuntime, faterhworklist);
		// return;
		// }
		// activityDeployActor.execute(fatherRuntime, list);
		// ProcessEngine processEngine = (ProcessEngine) FetchBean
		// .getBusinessBean("processEngine");
		// for(Iterator itr=list.iterator();itr.hasNext();){
		// processEngine.commitActivity((TWfWorklist)itr.next());
		// }

	}

	public NaviageActor getNaviageActor() {
		return naviageActor;
	}

	public void setNaviageActor(NaviageActor naviageActor) {
		this.naviageActor = naviageActor;
	}

	public ActivityDeployActor getActivityDeployActor() {
		return activityDeployActor;
	}

	public void setActivityDeployActor(ActivityDeployActor activityDeployActor) {
		this.activityDeployActor = activityDeployActor;
	}

	public ProcessEndActor getProcessEndActor() {
		return processEndActor;
	}

	public void setProcessEndActor(ProcessEndActor processEndActor) {
		this.processEndActor = processEndActor;
	}

	public ActivityEndActor getActivityEndActor() {
		return activityEndActor;
	}

	public void setActivityEndActor(ActivityEndActor activityEndActor) {
		this.activityEndActor = activityEndActor;
	}
}
