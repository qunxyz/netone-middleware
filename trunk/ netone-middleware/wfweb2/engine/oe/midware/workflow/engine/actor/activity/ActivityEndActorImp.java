package oe.midware.workflow.engine.actor.activity;

import java.sql.Timestamp;

import oe.frame.bus.workflow.actor.activity.ActivityEndActor;
import oe.frame.orm.OrmerEntry;
import oe.midware.workflow.engine.actor.activity.utils.NormalSyncTask;
import oe.midware.workflow.runtime.RuntimeWorklistRef;
import oe.midware.workflow.runtime.ormobj.TWfWorklist;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com<br>
 *         support by http://www.oesee.com
 * 
 */
public class ActivityEndActorImp implements ActivityEndActor {
	private Log _log = LogFactory.getLog(ActivityEndActorImp.class);

	public boolean execute(TWfWorklist worklistCurrent) {

		if (worklistCurrent == null) {
			_log.warn("lose current worklist obj, commit activity fail");
			return false;
		}

		_log.debug("end worklist instance:" + worklistCurrent.getActivityid()
				+ "in runtime:" + worklistCurrent.getRuntimeid() + ",workcode:"
				+ worklistCurrent.getWorkcode());
		worklistCurrent.setDonetime((new Timestamp(System.currentTimeMillis())
				.toString()));
		worklistCurrent.setExecutestatus(RuntimeWorklistRef.STATUS_DONE[0]);

		boolean rs = OrmerEntry.fetchOrmer().fetchSerializer().update(
				worklistCurrent);
		
		// 执行SOA 脚本任务
		NormalSyncTask myTask = new NormalSyncTask(worklistCurrent
				.getRuntimeid(), worklistCurrent.getWorkcode());

		return false;

	}

}
