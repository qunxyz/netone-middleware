package oe.midware.workflow.engine.actor.datafield;

import java.util.List;

import oe.frame.bus.workflow.actor.datafield.DataFieldUpdateActor;
import oe.frame.orm.OrmerEntry;
import oe.midware.workflow.runtime.ormobj.TWfRelevantvar;
/**
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com<br>
 *         support by http://www.oesee.com
 * 
 */
public class DataFieldUpdateActorImp implements DataFieldUpdateActor {

	public void execute( List var) {
		OrmerEntry.fetchOrmer().fetchSerializer().updates(var);
	}
	public void execute(TWfRelevantvar var) {
		OrmerEntry.fetchOrmer().fetchSerializer().update(var);
	}

}
