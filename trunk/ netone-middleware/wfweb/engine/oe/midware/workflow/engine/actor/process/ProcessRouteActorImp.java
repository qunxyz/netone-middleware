package oe.midware.workflow.engine.actor.process;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import oe.frame.bus.workflow.RuntimeInfo;
import oe.frame.bus.workflow.RuntimeMonitor;
import oe.frame.bus.workflow.WfEntry;
import oe.frame.bus.workflow.actor.process.ProcessRouteActor;
import oe.frame.bus.workflow.rule.RuleEngine;

import oe.midware.workflow.runtime.ormobj.TWfRuntime;
import oe.midware.workflow.runtime.ormobj.TWfWorklist;
import oe.midware.workflow.xpdl.model.activity.Activity;
import oe.midware.workflow.xpdl.model.condition.Condition;
import oe.midware.workflow.xpdl.model.transition.Transition;
import oe.midware.workflow.xpdl.model.workflow.WorkflowProcess;

/**
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com<br>
 *         support by http://www.oesee.com
 * 
 */
public class ProcessRouteActorImp implements ProcessRouteActor {

	private Log _log = LogFactory.getLog(ProcessRouteActorImp.class);

	private RuleEngine ruleEngine;

	public List execute(TWfWorklist curworklist) {
		if (curworklist == null) {
			_log.error(RuntimeInfo.OE_WF_RMT_ERR_003);
			throw new RuntimeException(RuntimeInfo.OE_WF_RMT_ERR_003);
		}
		TWfRuntime runtime = curworklist.fetchRuntime();
		if (runtime == null) {
			_log.error(RuntimeInfo.OE_WF_RMT_ERR_001);
			throw new RuntimeException(RuntimeInfo.OE_WF_RMT_ERR_001);
		}
		List listRouteAimActivity = new ArrayList();
		String activityid = curworklist.getActivityid();
		
		RuntimeMonitor runtimemonitor = (RuntimeMonitor) WfEntry
		.fetchBean("runtimemonitor");
		
		WorkflowProcess proc=null;
		try {
			proc = runtimemonitor.fetchWorkflowProcess(runtime.getProcessid());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Activity act = proc.getActivity(activityid);
		if (act == null) {
			_log.error(RuntimeInfo.OE_WF_DEF_ERR_006);
			throw new RuntimeException(RuntimeInfo.OE_WF_DEF_ERR_006);
		}
		Map map = act.getEfferentTransitions();
		if (map == null) {// β�
			return new ArrayList();
		}
		for (Iterator itr = map.values().iterator(); itr.hasNext();) {
			Transition trans = (Transition) itr.next();
			if (trans == null) {
				_log.warn(RuntimeInfo.OE_WF_RMT_ERR_007);
				continue;
			}
			Condition condition = trans.getCondition();
			String script = null;
			if (condition != null) {
				script = condition.getValue();
			}
			if (_log.isDebugEnabled()) {
				_log.debug("trans:" + trans.getId() + ",script:" + script);
			}
			boolean isOk = true;
			if (script != null && !"".equals(script)) {
				isOk = judgement(script, runtime);
			}
			if (isOk) {
				listRouteAimActivity.add(trans.getToActivity());
			}
		}

		if (_log.isDebugEnabled()) {
			StringBuffer buf = new StringBuffer();
			for (Iterator itr = listRouteAimActivity.iterator(); itr.hasNext();) {
				Activity actX = (Activity) itr.next();
				buf.append(actX.getId() + ",");
			}
			_log.debug("Route result:" + buf.toString());
		}
		return listRouteAimActivity;
	}

	private boolean judgement(String conditoins, TWfRuntime runtime) {
		if (conditoins == null || conditoins.equals("")) {
			return true;
		}

		return ruleEngine.rule(conditoins, runtime.getRuntimeid(),null);

	}

	public RuleEngine getRuleEngine() {
		return ruleEngine;
	}

	public void setRuleEngine(RuleEngine ruleEngine) {
		this.ruleEngine = ruleEngine;
	}

}
