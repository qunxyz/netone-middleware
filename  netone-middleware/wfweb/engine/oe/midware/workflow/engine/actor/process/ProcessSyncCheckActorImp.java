package oe.midware.workflow.engine.actor.process;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import oe.frame.bus.workflow.RuntimeInfo;
import oe.frame.bus.workflow.actor.process.ProcessSyncCheckActor;
import oe.frame.orm.OrmerEntry;

import oe.midware.workflow.runtime.RuntimeWorklistRef;
import oe.midware.workflow.runtime.ormobj.TWfWorklist;
import oe.midware.workflow.xpdl.model.activity.Activity;
import oe.midware.workflow.xpdl.model.transition.Join;
import oe.midware.workflow.xpdl.model.transition.Transition;
import oe.midware.workflow.xpdl.model.transition.TransitionRestriction;
/**
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com<br>
 *         support by http://www.oesee.com
 * 
 */
public class ProcessSyncCheckActorImp implements ProcessSyncCheckActor {
	private Log _log = LogFactory.getLog(ProcessSyncCheckActorImp.class);

	public List execute(TWfWorklist worklist, List activitys) {

		if (worklist == null) {
			_log.error(RuntimeInfo.OE_WF_RMT_ERR_003);
			throw new RuntimeException(RuntimeInfo.OE_WF_RMT_ERR_003);
		}
		List listCanDeployActivityList = new ArrayList();
		for (Iterator itr = activitys.iterator(); itr.hasNext();) {
			Activity activityPre = (Activity) itr.next();
			boolean checkJoinTypeIsAnd = checkJoinTypeIsAnd(activityPre
					.getTransitionRestriction());
			if (!checkJoinTypeIsAnd) {
				listCanDeployActivityList.add(activityPre);
				continue;
			}
			boolean checkSyncWorklistIsAllDone = checkSyncWorklistIsAllDone(
					activityPre, worklist);
			if (checkSyncWorklistIsAllDone) {
				listCanDeployActivityList.add(activityPre);
			}
		}
		return listCanDeployActivityList;
	}

	private boolean checkSyncWorklistIsAllDone(Activity act,
			TWfWorklist worklist) {
		_log.debug("sync check:" + act.getName());
		TWfWorklist worklistQuery = new TWfWorklist();
		worklistQuery.setRuntimeid(worklist.getRuntimeid());
		worklistQuery.setExecutestatus(RuntimeWorklistRef.STATUS_DONE[0]);

		List list = OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(
				worklistQuery,null);
		Map doenAct = new HashMap();
		for (Iterator itr = list.iterator(); itr.hasNext();) {
			TWfWorklist wk = (TWfWorklist) itr.next();
			doenAct.put(wk.getActivityid(), "");
		}
		// 检查所有的传入活动都已经执行完毕
		Map trns = act.getAfferentTransitions();
		for (Iterator itr = trns.keySet().iterator(); itr.hasNext();) {
			String trans = (String) itr.next();
			Transition from = (Transition) trns.get(trans);
			Activity formAct = from.getFromActivity();
			String activityid = formAct.getId();
			_log.debug(activityid);
			if (!doenAct.containsKey(activityid)) {
				return false;
			}
		}
		// 还需要检查所有的传入活动是否有在运行中的
		return true;
	}

	// private boolean checkSyncWorklistIsAllDone(TWfWorklist worklist) {
	//
	// TWfWorklist worklistQuery = new TWfWorklist();
	// worklistQuery.setRuntimeid(worklist.getRuntimeid());
	// worklistQuery.setStarttime(worklist.getStarttime());
	// List list = RuntimeHand.fetchRuntimeQuery().queryObjects(worklistQuery);
	// boolean sync = true;
	// for (Iterator itr = list.iterator(); itr.hasNext();) {
	// TWfWorklist worklistTogeter = (TWfWorklist) itr.next();
	// sync = sync && worklistTogeter.isDone();
	// }
	// return sync;
	// }

	private boolean checkJoinTypeIsAnd(
			TransitionRestriction[] transitionRestriction) {
		for (int i = 0; i < transitionRestriction.length; i++) {
			Join join = transitionRestriction[i].getJoin();
			if (join == null) {
				return false;
			}
			return "AND".equals(join.getType().toString());
		}
		return false;
	}

}
