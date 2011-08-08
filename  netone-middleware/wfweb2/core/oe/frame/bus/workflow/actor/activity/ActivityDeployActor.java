package oe.frame.bus.workflow.actor.activity;

import java.util.List;

import oe.midware.workflow.runtime.ormobj.TWfRuntime;
import oe.midware.workflow.xpdl.model.activity.Activity;

/**
 * 活动发布者
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com<br>
 *         support by [OESEE IT咨询服务] http://www.oesee.com
 * 
 */
public interface ActivityDeployActor {

	void execute(TWfRuntime runtime, List activity);

	void execute(TWfRuntime runtime, Activity activity);

}
