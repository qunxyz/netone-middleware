package oe.frame.bus.workflow.actor.activity;

import java.util.List;

import oe.midware.workflow.runtime.ormobj.TWfRuntime;
import oe.midware.workflow.xpdl.model.activity.Activity;

/**
 * �������
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com<br>
 *         support by [OESEE IT��ѯ����] http://www.oesee.com
 * 
 */
public interface ActivityDeployActor {

	void execute(TWfRuntime runtime, List activity);

	void execute(TWfRuntime runtime, Activity activity);

}
