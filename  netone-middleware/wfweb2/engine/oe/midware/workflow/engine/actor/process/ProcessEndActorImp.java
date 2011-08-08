package oe.midware.workflow.engine.actor.process;

import java.sql.Timestamp;

import oe.frame.bus.workflow.actor.process.ProcessEndActor;
import oe.frame.orm.OrmerEntry;
import oe.midware.workflow.runtime.RuntimeProcessRef;
import oe.midware.workflow.runtime.ormobj.TWfRuntime;
import oe.midware.workflow.service.WorkflowConsole;

import oe.rmi.client.RmiEntry;

/**
 * 结束流程，配置业务应用在流程中存在会话，结束的时候自动清理回话
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com<br>
 *         support by http://www.oesee.com
 * 
 */
public class ProcessEndActorImp implements ProcessEndActor {

	public void execute(TWfRuntime runtime) {
		runtime.setEndtime((new Timestamp(System.currentTimeMillis()))
				.toString());
		runtime.setStatusnow(RuntimeProcessRef.STATUS_END[0]);
		OrmerEntry.fetchOrmer().fetchSerializer().update(runtime);

		// 配合会话应用，流程执行结束后关闭会话
		WorkflowConsole wfhandle = null;
		try {
			wfhandle = (WorkflowConsole) RmiEntry.iv("wfhandle");
			wfhandle.removeSession(runtime.getRuntimeid());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
