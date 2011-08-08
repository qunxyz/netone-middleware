package oe.midware.workflow.engine.actor.datafield;

import java.rmi.RemoteException;

import oe.frame.bus.workflow.RuntimeMonitor;
import oe.frame.bus.workflow.WfEntry;
import oe.frame.bus.workflow.actor.datafield.DataFieldDropActor;
import oe.frame.orm.OrmerEntry;
import oe.midware.workflow.runtime.ormobj.TWfRuntime;

/**
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com<br>
 *         support by http://www.oesee.com
 * 
 */
public class DataFieldDropActorImp implements DataFieldDropActor {

	public void execute(TWfRuntime runtime) {
		RuntimeMonitor runtimemonitor = (RuntimeMonitor) WfEntry
				.fetchBean("runtimemonitor");
		try {
			OrmerEntry.fetchOrmer().fetchSerializer().drops(
					runtimemonitor.fetchRelevantVar(runtime.getRuntimeid()));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
