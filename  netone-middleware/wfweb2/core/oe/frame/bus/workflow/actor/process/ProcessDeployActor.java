package oe.frame.bus.workflow.actor.process;

import oe.midware.workflow.runtime.ormobj.TWfRuntime;
import oe.midware.workflow.xpdl.model.workflow.WorkflowProcess;
/**
 * ·¢²¼¿ØÖÆ
 * @author chen.jia.xun(Robanco) <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com<br>
 *         support by http://www.oesee.com
 *
 */
public interface ProcessDeployActor {

	TWfRuntime executes(WorkflowProcess proc);
}
