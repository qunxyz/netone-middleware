package oe.midware.workflow.engine.actor.activity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import oe.frame.bus.workflow.actor.activity.ActivityDeployActor;
import oe.frame.orm.OrmerEntry;
import oe.midware.workflow.engine.actor.activity.utils.ActivityTypeFetcher;
import oe.midware.workflow.engine.actor.activity.utils.NormalSyncTask;
import oe.midware.workflow.runtime.ActivityRef;
import oe.midware.workflow.runtime.RuntimeWorklistRef;
import oe.midware.workflow.runtime.ormobj.TWfRuntime;
import oe.midware.workflow.runtime.ormobj.TWfWorklist;
import oe.midware.workflow.xpdl.model.activity.Activity;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com<br>
 *         support by http://www.oesee.com
 * 
 */
public class ActivityDeployActorImp implements ActivityDeployActor {
	private Log _log = LogFactory.getLog(ActivityDeployActorImp.class);

	public void execute(TWfRuntime runtime, List activity) {
		if (runtime == null) {
			return;
		}
		List list = readyForNewActivityInstance(runtime, activity);

		OrmerEntry.fetchOrmer().fetchSerializer().creates(list);

		// 打开集成的异步任务
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			TWfWorklist element = (TWfWorklist) iter.next();
			_log.debug("run active:"+element.getActivityid());
			
			// 启动异步任务
			NormalSyncTask myTask = new NormalSyncTask(runtime.getRuntimeid(), element
					.getWorkcode());
		}
	}

	public void execute(TWfRuntime runtime, Activity activity) {

		List listActivity = new ArrayList();
		listActivity.add(activity);
		execute(runtime, listActivity);
	}

	private List readyForNewActivityInstance(TWfRuntime runtime,
			List listActivity) {
		List listNewActivity = new ArrayList();
		String nowTime = (new Timestamp(System.currentTimeMillis())).toString();
		String processid = runtime.getProcessid();
		for (Iterator itr = listActivity.iterator(); itr.hasNext();) {
			Activity actPre = (Activity) itr.next();
			TWfWorklist worklistPre = new TWfWorklist();

			worklistPre.setActivityid(actPre.getId());
			worklistPre.setRuntimeid(runtime.getRuntimeid());
			worklistPre.setStarttime(nowTime);
			worklistPre.setProcessid(processid);
			
			String types=ActivityTypeFetcher.fetchType(actPre);
			worklistPre.setTypes(types);
			if (ActivityRef.ACT_SUBFLOW_SYNC_KEY[0].equals(worklistPre
					.getTypes())
					|| ActivityRef.ACT_SUBFLOW_KEY[0].equals(worklistPre
							.getTypes())) {
				worklistPre
						.setExecutestatus(RuntimeWorklistRef.STATUS_SUBFLOW_WAITING[0]);
			} else {
				worklistPre
						.setExecutestatus(RuntimeWorklistRef.STATUS_RUNNING[0]);
			}

			listNewActivity.add(worklistPre);
			_log.debug("deploy new activity instance:" + actPre.getId()
					+ "in runtimeId=" + runtime.getRuntimeid());

			// 与消息服务同步-发送消息给相关的人员

		}
		return listNewActivity;

	}

}
