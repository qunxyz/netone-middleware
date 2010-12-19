package oe.midware.workflow.engine.actor.activity;

import oe.frame.bus.workflow.actor.activity.ActivityStatusActor;
import oe.frame.orm.OrmerEntry;
import oe.midware.workflow.runtime.ormobj.TWfWorklist;
/**
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com<br>
 *         support by http://www.oesee.com
 * 
 */
public class ActivityStatusActorImp implements ActivityStatusActor {

	public boolean execute( TWfWorklist worklist,String arg1) {
		worklist.setExecutestatus(arg1);
		return OrmerEntry.fetchOrmer().fetchSerializer().update(worklist);
	}

}
