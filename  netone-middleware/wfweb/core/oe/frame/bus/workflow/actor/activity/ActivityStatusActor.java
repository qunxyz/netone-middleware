package oe.frame.bus.workflow.actor.activity;

import oe.midware.workflow.runtime.ormobj.TWfWorklist;

/**
 * …Ë÷√ªÓ∂Ø◊¥Ã¨
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com<br>
 *         support by http://www.oesee.com
 * 
 */
public interface ActivityStatusActor {

	boolean execute(TWfWorklist worklist, String status);

}
