package oe.frame.bus.workflow.actor.datafield;

import java.util.List;

import oe.midware.workflow.runtime.ormobj.TWfRelevantvar;

/**
 * 更新数据
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com<br>
 *         support by http://www.oesee.com
 * 
 */
public interface DataFieldUpdateActor {
	void execute(List var);

	void execute(TWfRelevantvar var);
}
