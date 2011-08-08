package oe.frame.bus.workflow.actor;

import java.util.List;

/**
 * 自动机器人,可以自动处理流程
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com<br>
 *         support by http://www.oesee.com
 * 
 */
public interface AutoMen {
	/**
	 * 
	 * @param list
	 *            需要自动运行的活动列表
	 */
	void execute(List list);
}
